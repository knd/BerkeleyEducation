#!/usr/bin/env python

import sys, parser_generator, grammar_parser
from util import Ambiguous

if __name__ == '__main__':
    if len(sys.argv) != 3:
        print "Please give two arguments, the grammar filename and the input filename."
        sys.exit(1)
        
    parser = parser_generator.makeParser(grammar_parser.parse(open(sys.argv[1]).read()))
    try:
        ast = parser.parse(open(sys.argv[2]).read())
        print ast
    except SyntaxError, e:
        print e
