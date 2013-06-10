#!/usr/bin/env python
# Interface to our online parser

import sys
from optparse import OptionParser
import rparse, interpreter

if __name__ == '__main__':
    server_url = 'https://cs164parser.appspot.com/parser'
    server_grammar = 'cs164b.grm'
    library = 'library.164'

    parser = OptionParser(usage='usage: %prog [options] files...')
    parser.add_option('--no-library', dest='nolib',
                      help=('do not automatically load the standard library'),
                      action="store_true", default=False)
    options, args = parser.parse_args()

    def load(filelist):
        srcs = []
        for filename in filelist:
            with open(filename) as f:
                srcs.append(f.read())

        result = rparse.remote_parse(srcs, None, server_grammar, server_url)
        return [result[src] for src in srcs] if result else []

    if not args:
        print "No file given"
        sys.exit(0)

    asts = load(args if options.nolib else [library] + args)
    if not None in asts:
        for ast in asts:
            interpreter.ExecGlobal(ast)
