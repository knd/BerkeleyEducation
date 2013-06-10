#!/usr/bin/env python
# Interface to our online parser

import sys, json, webbrowser
from os.path import abspath

from itertools import chain
from urllib import urlencode
from urllib2 import urlopen
from optparse import OptionParser
import rparse

def generate_html(grammar, input, response, evaluate=False):
    try:
        result = eval(response) if evaluate else response
    except Exception as e:
        result = '<p class="error">{0}</p>'.format(rparse.htmlencode(e))

    with open('results-template.html') as template_file:
        template = template_file.read()
        html = template.format(input=input, output=result,
                               grammar=rparse.htmlencode(grammar))
    return html

if __name__ == '__main__':
    # TODO: Use ArgumentParser in Python 2.7+.
    parser = OptionParser(usage='usage: %prog [options] input...')
    server_url = 'https://cs164parser.appspot.com/parser'
    parser.add_option('-g', '--grammar', dest='grammar', metavar='GRAMMAR',
                      help=('read the grammar from GRAMMAR; '
                            'if unspecified, let the server choose a grammar'))
    parser.add_option('-r', '--remote_grammar', dest='remote_grammar',
                      metavar='REMOTE_GRAMMAR',default='regex.grm',
                      help=('read the grammar from the REMOTE_GRAMMAR file '
                            'that resides on the remote host'))
    parser.add_option('-s', '--server', dest='server', metavar='SERVER',
                      help='use SERVER as the remote service provider',
                      default=server_url)

    options, args = parser.parse_args()

    if len(args) < 1:
        print "Please give at least one argument, the text to parse. Run with "\
              "-h to see the options"
        sys.exit(1)

    if options.grammar is not None:
        with open(options.grammar) as grammar_file:
            grammar = grammar_file.read()
    else:
        grammar = None

    inputs = args
    response = rparse.remote_parse(inputs, grammar, options.remote_grammar, \
                                   options.server)

    html_urls = []
    for ii, key in enumerate(response):
        html_file_path = 'results-{0}.html'.format(ii)
        try:
            with open(html_file_path, 'w') as html_file:
                html_file.write(generate_html(grammar, key, response[key]))
            html_urls.append('file://' + abspath(html_file_path))
        except Exception as e:
            print 'failed to write to {0}: {1}'.format(html_file_path, e)

    for url in html_urls:
        webbrowser.open_new_tab(url)

    exit(0)
