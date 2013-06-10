#!/usr/bin/env python
# Interface to our online parser

import sys, json
from itertools import chain
from urllib import urlencode
from urllib2 import urlopen
import rparse, interpreter

if __name__ == '__main__':
    if len(sys.argv) != 2:
        print "Please give one argument, the input filename."
        sys.exit(1)
        
    server_url = 'https://cs164parser.appspot.com/parser'
    server_grammar = 'cs164a.grm'
    cs164input = open(sys.argv[1]).read()
    result = rparse.remote_parse([cs164input], None, server_grammar, server_url)
    ast = result[cs164input]
    if ast is not None:
        interpreter.Run(ast)
