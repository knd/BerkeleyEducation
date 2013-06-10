#!/usr/bin/env python
# Interface to our online parser

import sys, os
from optparse import OptionParser
import rparse, prolog2lua

if __name__ == '__main__':
    server_url = 'https://cs164parser.appspot.com/parser'
    prolog_grammar = 'prolog.grm'
    prolog_ast = 'prolog_ast.lua'
    prolog_int = 'prolog.lua'
    
    if len(sys.argv) != 2:
        print "Please give one prolog source file to execute."
        sys.exit(1)

    def load(filelist, grammar):
        srcs = []
        for filename in filelist:
            with open(filename) as f:
                srcs.append(f.read())

        result = rparse.remote_parse(srcs, None, grammar, server_url)
        return [result[src] for src in srcs] if result else []

    past = load([sys.argv[1]], prolog_grammar)[0]
    if past:
        with open(prolog_ast, 'w+') as out:
            prolog2lua.prettyPrint(past, out)

    os.system('lua ' + prolog_int)
