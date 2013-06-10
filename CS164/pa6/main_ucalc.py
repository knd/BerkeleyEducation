#!/usr/bin/env python

import sys, parser_generator
import grammar_parser

if __name__ == '__main__':
    if len(sys.argv) != 2:
        print "Please give one argument the input filename."
        sys.exit(1)
        
    grammar = 'tests/ucalc/grammar.grm'
    parser = parser_generator.makeParser(grammar_parser.parse(open(grammar).read()))
    print parser.parse(open(sys.argv[1]).read())
