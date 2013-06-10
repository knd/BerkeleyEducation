#!/usr/bin/env python

import os, sys, subprocess, difflib, time, signal

class Timeout(RuntimeError):
    def __str__(self):
        return "Timeout"
        
def runProc(process, timeout):
    start = time.time()
    while process.poll() is None:
        time.sleep(0.1)
        if float(time.time() - start) > float(timeout):
            os.kill(process.pid, signal.SIGKILL)
            os.waitpid(-1, os.WNOHANG)
            raise Timeout
    return process.communicate()

def compare(ref, test):
    diffOutput = difflib.unified_diff(open(ref,'U').readlines(), 
                                      open(test,'U').readlines(), 
                                      fromfile="Solutions", 
                                      tofile="Test")
    tx = ''
    for line in diffOutput:
        tx = tx + line
        
    if tx: return 0
    else: return 1

def runTest(grammar, test, ref_output, test_output):
    try:
        p = subprocess.Popen('python %s %s %s > %s' % (program, grammar, test,\
                             test_output), shell=True)
        runProc(p, timeout)
        return compare(ref_output, test_output)
    except Timeout:
        print ("Timeout")
        return 0
    except:
        print ("Interpreter Error")
        return 0

program = "main.py"
testDir = "tests" + os.sep
timeout = 10

categories = os.listdir(testDir)
categories.sort()
tests = {}
scores = {}
max_scores = {}
for cat in categories:
    tests[cat] = filter(lambda f: f.endswith(".in"), os.listdir(testDir + cat))
    scores[cat] = 0
    max_scores[cat] = 0


for (cat, tl) in tests.items():
    for test in tl:
        testPath = testDir + cat + os.sep + test
        grammar = testDir + cat + os.sep + 'grammar.grm'
        ref_output = os.path.splitext(testPath)[0] + ".out"
        test_output = os.path.splitext(testPath)[0] + '.tmp'
        res = runTest(grammar, testPath, ref_output, test_output)
        if (res == 0):
            print (cat + os.sep + test + '  Failed')
        scores[cat] = scores[cat] + res
        max_scores[cat] = max_scores[cat] + 1
      
total = 0
max_total = 0  
print ("\nScores:")
for cat in categories:
    print ("\t" + cat + ": " + str(scores[cat]) + " passed out of " + \
           str(max_scores[cat]))
    total = total + scores[cat]
    max_total = max_total + max_scores[cat]

print ("Total: " + str(total) + " \ " + str(max_total))

