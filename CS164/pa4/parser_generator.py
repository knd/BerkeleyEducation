import grammar, grammar_parser, re, sys, types, util, string, pprint
from util import Ambiguous

##-----------------------------------------------------------------------------
## Module interface
##

def makeRecognizer (gram, type='earley'):
    '''Construct and return a "recognizer" of GRAM.

    A recognizer is an object with a method recognize(inp), returning
    True if INP is a valid string in GRAM, and False otherwise.
    '''
    class Recognizer:
        def __init__ (self, parser):
            self.parser = parser

        def dump (self, f=sys.stdout):
            self.parser.dump (f)
 
        def recognize (self, inp):
            if self.parser.recognize(inp):
                return True
            return False

    return Recognizer (makeParser (gram, type))


def makeParser (gram, type='earley'):
    '''Construct and return a parser of GRAM.

    A parser is an object with a method parse(inp), returning the AST
    constructed from the parse tree of INP by the semantic actions in GRAM.
    '''
    if type == 'earley':
        return EarleyParser (gram)
    else:
        raise TypeError, 'Unknown parser type specified'

##-----------------------------------------------------------------------------
## Parse Tree code removed for now.  You will get it in PA6
 

##-----------------------------------------------------------------------------
## Earley Parser
##
class EarleyParser:
    '''A parser implementing the Earley algorithm.'''

    def __init__ (self, gram):
        '''Create a new Earley parser.'''
        self.grammar = gram
        #print gram.rules
        self.terminals, self.invRenamedTerminals = EarleyParser.preprocess(gram)
        # self.dump()

    def recognize (self, inp):
        '''Return the result of parsing INP.'''

        # Tokenize the input
        try:
            tokens = self.tokenize (inp)
##            print self.terminals
##            print self.invRenamedTerminals
##            print tokens
            TOKEN = 0; LEXEME = 1
        except Exception, pos:
            util.error ('Lexical error.  Cannot tokenize "at pos %s. Context:'\
                        ' %s"'% (pos, inp[pos[0]:pos[0]+24]))
            return False

        # TODO Write recognizer.
        # Print "complete" edges in the Earley parse graph in lexicographic
        # order.
        # Return False when input is NOT in language.
        # Return True when in language and non-ambiguous.
        # Throw Exception("Ambiguous, resolved") when ambiguous and resolved
        # (see handout)
        # Throw Exception("Ambiguous, unresolved") when ambiguous and NOT 
        # resolved (see handout)

        # this is how you notify main that the parse succeeded but unresolved
        # ambiguity was found
        
        visualize = False
        if visualize:
            # write a graph that can be visualized with graphviz.
            # to view the graph, use the command $ dotty graph.dot
            gviz = open('graph.dot','w')
            gviz.write('digraph G {\nrankdir="LR";')
            for i in range(0,len(inp)):
                gviz.write('    %s -> %s [label = "%s",width=2];\n' % (i, i+1, inp[i]))

            def drawEdge(e):
                
                dot = e[2][2]
                print e[2][1][0:dot]
                rhs = e[2][1][0:dot]+'.'+e[2][1][dot:]
                gviz.write('    %s -> %s [label="%s --> %s", constraint=false];\n' % \
                               (e[0],e[1],e[2][0],rhs))
        
        graph=set([])
        graphnotend={}
        graphend={}
        
        def addEdge(e):
            "add edge to graph and worklist if not present in graph already"
            incr(0)
            #print e
            if e not in graph:
                # print e[2][1].RHS
                # print self.invRenamedTerminals
                if e[2][2] < len(e[2][1].RHS):
                    if e[1] in graphnotend:
                        graphnotend[e[1]].add(e)
                    else: graphnotend[e[1]] = set([e])
                elif e[2][2] == len(e[2][1].RHS):
                    if e[1] in graphend:
                        graphend[e[1]].add(e)
                    else: graphend[e[1]] = set([e])
                
                graph.add(e)
                if visualize:
                    drawEdge(e)
        
        # Add edge (0,0,(S -> . alpha)) to worklist, for all S -> alpha
        for P in self.grammar.rules[0].productions:
            #print tokens[0][1]
            #print tokens
            #print self.invRenamedTerminals
            if P.RHS == () or P.RHS[0] not in self.invRenamedTerminals or P.RHS[0] == tokens[0][0]:
                #print type(P.RHS)
                addEdge((0,0,(P.LHS,P,0)))
        
        # for all tokens on the input
        for j in xrange(0,len(tokens)+1):

            # skip in first iteration; we'll complete and predict start nonterminal
            # before we start walking over input
            if j > 0:
                # ADVANCE across the next token:
                # for each edge (i,j-1,N -> alpha . inp[j] beta)
                #     add edge (i,j,N -> alpha inp[j] . beta)
                if (j-1) in graphnotend:
                    for (i,_j,(N,RHS,pos)) in list(graphnotend[j-1]):
                        #print graphnotend[j-1]
                        incr(1)
                        if _j==j-1 and pos < len(RHS.RHS) and RHS.RHS[pos]==tokens[j-1][0]:
                            #print ((i,j,(N,RHS,pos+1)))
                            addEdge((i,j,(N,RHS,pos+1)))
            # Repeat COMPLETE and PREDICT until no more edges can be added
            graphSize = -1
            while graphSize < len(graph):
                graphSize = len(graph)
                # COMPLETE productions
                # for each edge (i,j,N -> alpha .)
                #    for each edge (k,i,M -> beta . N gamma)
                #        add edge (k,j,M -> beta N . gamma)
                if j in graphend:
                    #print graphnotend
                    for (i,_j,(N,RHS,pos)) in list(graphend[j]):
                        incr(2)
                        #print (i,_j,(N,RHS,pos))
                        if pos == len(RHS.RHS):
                            for (k,_i,(M,RHS2,pos2)) in list(graphnotend[i]):
                                #print (k,_i,(M,RHS2,pos2))
                                incr(3)
                                if pos2 < len(RHS2.RHS) and RHS2.RHS[pos2]==N:
                                    if pos2 < (len(RHS2.RHS) -1):
                                        #print ((k,j,(M,RHS2,pos2+1)))
                                        #print j
                                        #print len(tokens)
                                        if j < (len(tokens)):
                                            #print ((k,j,(M,RHS2,pos2+1)))
                                            # print RHS2.RHS[pos2 + 1]
                                            # print tokens[j][0]
                                            #if RHS2.RHS[pos2 + 1] == tokens[j][0]:
                                            # print ((k,j,(M,RHS2,pos2+1)))
                                            addEdge((k,j,(M,RHS2,pos2+1)))
                                    else:
                                        # print ((k,j,(M,RHS2,pos2+1)))
                                        addEdge((k,j,(M,RHS2,pos2+1)))
                                    #print (RHS2.RHS)
                
                # PREDICT what the parser is to see on input (move dots in edges 
                # that are in progress)
                #
                # for each edge (i,j,N -> alpha . M beta)
                #     for each production M -> gamma
                #          add edge (j,j,M -> . gamma)
                if j in graphnotend:
                    for (i,_j,(N,RHS,pos)) in list(graphnotend[j]):
                        incr(4)
                        # non-terminals are upper case
                        #print RHS.RHS[pos]
                        if pos < len(RHS.RHS) and RHS.RHS[pos] not in self.invRenamedTerminals:
                            M = RHS.RHS[pos]
                            K = []
                            for rules in self.grammar.rules:
                                if rules.lhs == M: K = rules.productions
                            # prediction: for all rules D->alpha add edge (j,j,.alpha)
                            for rhs in K:
                                # print rhs.RHS
                                #print tokens[j]
                                if rhs.RHS == () or rhs.RHS[0] not in self.invRenamedTerminals or rhs.RHS[0] == tokens[j][0]:
                                    #print (RHS)
                                    #print M
                                    #print rhs.toString(self.invRenamedTerminals)
                                    addEdge((j,j,(M,rhs,0)))
                                    
        if visualize:
            gviz.write('}')
            gviz.close()
        # input has been parsed OK if anf only if an edge (0,n,S -> alpha .) exists
        for RHS in self.grammar.rules[0].productions:
            #print (0,len(tokens),(RHS.LHS,RHS.RHS,len(RHS.RHS)))
            #print graphend
            newgraphend = {}
            for i in list(graphend.values()):
                #print i
                for j in i:
                        #print newgraphend.keys()
                        #print j
                    if j[0] in newgraphend.keys():
                        #print newgraphend[j[0]]
                        newgraphend[j[0]].append(j)
                    else:
                        newgraphend[j[0]] = [j]
            oldgraphend = newgraphend
            # print graph
            newgraphend, changed, unresolved = self.selectEdges(oldgraphend)
            edges = []
            for i in list(newgraphend.values()):
            # for i in list(oldgraphend.values()):
                for j in i:
                    # print j
                    edges.append((j[0], j[1], j[2][1].toString(self.invRenamedTerminals)))
            edges.sort()
            if (0,len(tokens),(RHS.LHS,RHS,len(RHS.RHS))) in graph:
                pprint.pprint(edges)
                if changed:
                    # raise Ambiguous("Ambiguous, resolved")  
                    
                    if not unresolved:
                        raise Ambiguous("Ambiguous, resolved")  
                    else:
                        raise Ambiguous("Ambiguous, unresolved")
                
                return True
        return False
                    
        raise Ambiguous("Ambiguous, unresolved")
        
        return False
        
    
    
    def selectEdges(self, newgraphend):
        retGraph, changed, unresolved = {}, False, False
        for k, v in newgraphend.items():
            # v is a list of tuples
            tupDict = {}
            for tup in v:
                #print tup
                #print tup[2][1].opPrec
                tempKey = (tup[0], tup[1], tup[2][0])
                if not tupDict.has_key(tempKey):
                    tupDict[tempKey] = tup
                else:
                    addedTup = tupDict[tempKey]
                    changed = True
                    # print tup[2][1].opPrec, addedTup[2][1].opPrec
                    
                    if tup[2][1].opPrec != None and addedTup[2][1].opPrec != None:
                        if tup[2][1].opPrec == addedTup[2][1].opPrec:
                            if addedTup[2][1].assoc != None:
                                pass
                            elif tup[2][1].assoc != None:
                                tupDict[tempKey] = tup
                            # if not addedTup[2][1].assoc != None:
 #                                tupDict[tempKey] = tup
                        elif tup[2][1].opPrec < addedTup[2][1].opPrec:
                            tupDict[tempKey] = tup
                    elif tup[2][1].prec != -1 and addedTup[2][1].prec != -1:
                        if tup[2][1].prec >= addedTup[2][1].prec:
                            tupDict[tempKey] = tup
                    else:
                        unresolved = True
                            
                        
            # if not unresolved:
            #     count = {}
            #     for tup in v:
            #         tempKey = (tup[0], tup[1], tup[2][0])
            #         if tupDict.has_key(tempKey) and tup[2][1].opPrec == tupDict[tempKey][2][1].opPrec:
            #             if not count.has_key(tempKey):
            #                 count[tempKey] = True
            #             else:
            #                 unresolved = True
            #                 break
                        
                     
            retGraph[k] = []
            for value in tupDict.values():
                retGraph[k].append(value)
                   
        return retGraph, changed, unresolved
                
        
    def tokenize (self, inp):
        '''Return the tokenized version of INP, a sequence of
        (token, lexeme) pairs.
        '''
        tokens = []
        pos = 0

        while True:
            matchLHS = 0
            matchText = None
            matchEnd = -1

            for regex, lhs in self.terminals:
                match = regex.match (inp, pos)
                if match and match.end () > matchEnd:
                    matchLHS = lhs
                    matchText = match.group ()
                    matchEnd = match.end ()

            if pos == len (inp):
                if matchLHS:  tokens.append ((matchLHS, matchText))
                break
            elif pos == matchEnd:       # 0-length match
                raise Exception, pos
            elif matchLHS is None:      # 'Ignore' tokens
                pass
            elif matchLHS:              # Valid token
                tokens.append ((matchLHS, matchText))
            else:                       # no match
                raise Exception, pos

            pos = matchEnd

        return tokens


    def dump (self, f=sys.stdout):
        '''Print a representation of the grammar to f.'''

        self.grammar.dump()

        for regex, lhs in self.terminals:
            if lhs is None:  lhs = '(ignore)'
            print lhs, '->', regex.pattern


    ##---  STATIC  ------------------------------------------------------------

    TERM_PFX = '*'     # prefix of nonterminals replacing terminals
    NONTERM_PFX = '@'  # prefix of nonterminals replacing RHSs with > 2 symbols

    @staticmethod
    def preprocess (gram):
        '''Returns the tuple:
         
        (
          [ (regex, lhs) ],             # pattern/token list
        )

        WARNING: modifies GRAM argument.
        '''
        #print gram.startSymbol
        REGEX = re.compile ('')
        
        terminals = []
        renamedTerminals = {}
        epsilons = []

        # Import all the grammar's modules into a new global object
        try:
            glob = util.doImports (gram.imports)
        except Exception, e:
            util.error ('problem importing %s: %s' % (gram.imports, str(e)))
            sys.exit(1)

        # Add 'ignore' patterns to the terminals list
        for regex in gram.ignores:
            terminals.append ((regex, None))

        # Add 'optional' patterns to the terminals list
        for sym, regex in gram.optionals:
            terminals.append ((regex, sym))

        # Build a lookup table for operator associativity/precedences
        operators = {}
        for op, prec, assoc in gram.getAssocDecls ():
            operators [op.pattern] = (prec, assoc)

        # First pass -- pull out epsilon productions, add terminal rules
        # and take care of semantic actions
        ruleNum = 0                     # newly-created rules
        for rule in gram.rules:
            lhs = rule.lhs
            for production in rule.productions:
                actions = production.actions
                rhs = list(production.RHS)

                # Create the S-action, if specified
                if actions[len (rhs)]:
                    actions[len (rhs)] = EarleyParser.makeSemantFunc (
                        actions[len (rhs)], len (rhs), glob)

                # Pull out epsilons and terminals
                for i, sym in enumerate (rhs):
                    if sym == grammar.Grammar.EPSILON:
                        # Epsilon
                        assert len (rhs) == 1
                        rhs = [] # in Earley, we model empsilon as an empty rhs
                        production.RHS = []

                    elif type (sym) == type (REGEX):
                        # Terminal symbol
                        if sym.pattern in renamedTerminals:
                            # Terminal was already factored out
                            termSym = renamedTerminals[sym.pattern]
                        else:
                            # Add *N -> sym rule, replace old symbol
                            termSym = '%s%d'% (EarleyParser.TERM_PFX, ruleNum)
                            ruleNum += 1
                            renamedTerminals[sym.pattern] = termSym
                            terminals.append ((sym, termSym))

                        if sym.pattern in operators:
                            # This pattern has a global assoc/prec declaration
                            # (which might be overridden later)
                            prec, assoc = operators[sym.pattern]
                            production.opPrec = prec
                            production.opAssoc = assoc
                        rhs[i] = termSym

                    if actions[i]:
                        # I-action for this symbol
                        actions[i] = EarleyParser.makeSemantFunc (
                            actions[i], len (rhs), glob)

                production.RHS = tuple(rhs)

        # Second pass -- build the symbol mapping and collect parsing info
        ruleNum = 0
        for rule in gram.rules:
            for production in rule.productions:
                lhs = rule.lhs
                rhs = production.RHS

                if len (rhs) == 1 and rhs[0] == grammar.Grammar.EPSILON:
                    # Epsilon production, skip it
                    continue

                # Collect precedence/associativity info
                if production.assoc != None:
                    # This production had a %prec declaration
                    opPrec, assoc = operators[production.assoc.pattern]
                elif production.opPrec != None:
                    # This production had a terminal symbol with an assoc/prec
                    # declaration
                    opPrec = production.opPrec
                    assoc = production.opAssoc
                else:
                    # No declarations ==> undefined prec, assoc
                    opPrec, assoc = None, None

                # Collect dprec info
                if production.prec != -1:
                    # Production had a %dprec declaration
                    dprec = production.prec
                else:
                    # No declaration ==> undefined dprec
                    dprec = None

                # Information about this production to be used during parsing
                production.info = (opPrec, assoc, dprec, production)
        
        return terminals, dict([(new,orig) for (orig,new) in \
               renamedTerminals.iteritems()])


    @staticmethod
    def makeSemantFunc (code, numArgs, globalObject):
        args = ['n0']
        for i in xrange (numArgs):
            args.append ('n%d'% (i+1))
        try:
            return util.createFunction (util.uniqueIdentifier (),
                                        args, code, globalObject)
        except Exception, e:
            util.error ("couldn't create semantic function: " + str(e))
            sys.exit(1)

    @staticmethod
    def __isTermSymbol (sym):
        '''Return TRUE iff SYM is a 'virtual' nonterminal for a
        terminal symbol, created during grammar normalization.
        '''
        return sym[0] == EarleyParser.TERM_PFX


    @staticmethod
    def dumpEdges (edges):
        '''Print a representation of the edge set EDGES to stdout.'''
        for sym, frm, to in edges:
            print '(%d)--%s--(%d)'% (frm, sym, to)


    @staticmethod
    def dumpTree (tree, edges, level=0):
        '''Print a representation of the parse tree TREE to stdout.'''
        sym, frm, to = tree[0:3]
        if len (tree) > 3:
            children = tree[3]
        else:
            children = edges[(sym, frm, to)][3]
        if (isinstance (children, basestring) or
            children is grammar.Grammar.EPSILON):
            print '%s%s "%s")'% ('-'*level*2, sym, children)
        else:
            print '%s%s %d-%d'% ('-'*level*2, sym, frm, to)
            for child in children:
                EarleyParser.dumpTree (child, edges, level + 1)

# For instrumentation 
def incr(id):
    pass

if __name__ == '__main__':
    pass