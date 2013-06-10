#!/usr/bin/env python

import sys, parser_generator, grammar_parser, os, prolog2lua

if __name__ == '__main__':
    if len(sys.argv) != 2:
        print "Please give one argument (the input filename)."
        sys.exit(1)
        
    prolog_grammar = 'tests/prolog/grammar.grm'
    cs164_grammar = './cs164b.grm'
    prolog_ast_file = 'prolog_ast.lua'
    prolog_interpreter = 'prolog.lua'
    
    # Generate parser
    prolog_parser = parser_generator.makeParser(grammar_parser.parse(open(prolog_grammar).read()))
    
    # Load prolog AST
    prolog_ast = prolog_parser.parse(open(sys.argv[1]).read())

    with open(prolog_ast_file, 'w+') as out:
        prolog2lua.prettyPrint(prolog_ast, out)

    os.system('lua ' + prolog_interpreter)
