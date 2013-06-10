#!/usr/bin/env python

import sys, parser_generator, grammar_parser
from util import Ambiguous

if __name__ == '__main__':
    if len(sys.argv) != 3:
        print "Please give two arguments, the grammar filename and the input"\
              " filename."
        sys.exit(1)
        
    recognizer = parser_generator.makeRecognizer(grammar_parser.parse(open(\
                                                 sys.argv[1]).read()))
    try:
        if recognizer.recognize(open(sys.argv[2]).read()):
            print 'Yes'
        else:
            print 'No'
    except Ambiguous, e:
        print e.value
