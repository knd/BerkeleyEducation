#!/usr/bin/python
import csv
import subprocess
import itertools

# variables you will need to change
PSQL_CLIENT = "/home/knd/pgsql/bin/psql"
# variables you may need to change
PSQL_PORT = 11111
# variables you could change
FREQUENCY_ERROR_THRESHOLD = 0.04
FILENAMES_TO_TABLES = {'data1':'numbers1',
                       'data2':'numbers2',
                       'data3':'numbers3'}
K_VALUES = (10, 5)
WIDTH_VALUES = (100, 300, 700, 1300, 2300, 4300)
DEPTH_VALUES = (2, 4, 8, 16)
WIDTH_DEPTH_ASCENDING = sorted(itertools.product(WIDTH_VALUES, DEPTH_VALUES),
                               key=lambda pair: pair[0]*pair[1])

def fork_psql(*args):
    '''
    Calls psql using global vars and given additional args.

    Returns subprocess.Popen object
    '''
    return subprocess.Popen([PSQL_CLIENT, '-p ' + str(PSQL_PORT)] + list(args),
                            stdin=subprocess.PIPE, stdout=subprocess.PIPE,
                            stderr=subprocess.PIPE)

def query_to_dictreader(query):
    '''
    Runs the given query string in psql based on config vars.

    Returns a csv.DictReader
    '''
    psql_popen = fork_psql('-d', 'nums', '-A', '-F', ',')
    stdout, stderr = psql_popen.communicate(query)
    print "QUERY: %s" % (query)
    print "RESULT:\n%s" % (stdout)
    if stderr:
        print "There was some sort of error when communicating with psql:"
        print stderr
    return csv.DictReader(stdout.decode('ascii').splitlines()[:-1])

def test_parameters(table, k, width, depth):
    '''
    Returns whether we are "happy" when running
    APPROX_COUNT(k, width, depth) on the given table

    We are happy if the following conditions are met:
    1. We get the same top results (num column) as the accurate query - a.k.a
    100 % recall (not necessarily in same order).
    2. The frequency for each result is within 4% (c column) of the accurate
    query => |reported freq - actual freq| / actual freq < .04
    '''
    print "TESTING k=%i, width=%i, depth=%i ON %s" % (k, width, depth, table)
    accurate_query = 'select num, count(*) from %s GROUP BY num ORDER BY count DESC limit %i;' % (table, k)
    # dict mapping top_k value to frequency
    accurate_val_to_freq = {}
    for row in query_to_dictreader(accurate_query):
        num = int(row['num'])
        freq = int(row['count'])
        accurate_val_to_freq[num] = freq
    approx_query = 'select num, approx_count(%i, %i, %i) as count from %s GROUP BY num ORDER BY count DESC;' % (k, width, depth, table)
    # dict mapping approx top_k value to frequency
    approx_val_to_freq = {}
    for row in query_to_dictreader(approx_query):
        num = int(row['num'])
        freq = int(row['count'])
        approx_val_to_freq[num] = freq

    # CHECK CONDITION 1 - same top results, may be out of order
    accurate_vals = accurate_val_to_freq.keys()
    approx_vals = approx_val_to_freq.keys()
    if len(accurate_vals) != len(approx_vals):
        print "ACCURATE query returned %i vals, APPROX returned %i" % (len(accurate_vals),
                                                                       len(approx_vals))
        return False
    for val in accurate_vals:
        if val not in approx_vals:
            print "value in ACCURATE not found in APPROX: %i" % (val)
            return False

    # CHECK CONDITION 2 - |reported freq - actual freq| / actual freq < .04
    for val, actual_freq in accurate_val_to_freq.items():
        reported_freq = approx_val_to_freq[val]
        diff = float(abs(reported_freq - actual_freq))/actual_freq
        if diff >= FREQUENCY_ERROR_THRESHOLD:
            print "ACCURATE freq, %i, and APPROX freq, %i, for %i differ by %f" % (actual_freq, reported_freq, val, diff)
            return False
    print "This is happy"
    return True

def generate_results():
    '''
    Whee nesting
    '''
    with open('results.txt', 'w') as f:
        for filename, table in FILENAMES_TO_TABLES.items():
            for k in K_VALUES:
                for width, depth in WIDTH_DEPTH_ASCENDING:
                    if test_parameters(table, k, width, depth):
                        print "for %s, k=%i min (width, depth) is (%i, %i)" % (filename,
                                                                               k,
                                                                               width,
                                                                               depth)
                        # writing "<filename>,<k>,<width>,<depth>\n"
                        result_string = "%s,%i,%i,%i\n" % (filename, k, width, depth)
                        print "writing %s" % (result_string)
                        f.write(result_string)
                        break

if __name__ == '__main__':                    
    generate_results()

