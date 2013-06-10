#!/usr/bin/env python
import string

# Homework: Finish the implementation of the Earley parser
#           and make it run in linear time on the string
#                 'a+a*a-a(a*a+a), ... ,a+a*a-a(a*a+a)'
#           from the language
#                   S->E|S,S
#                   E->E+E|E-E|E*E|(E)|-E|V
#                   V->a|b|c

# Grammars are represented in a simple fashion, no encapsulation in a class:
#   - representation is efficient but not general, as tokens are single
#     characters:
#   - the grammar is a hash table mapping nonterminals to a tuple containing
#     productions
#   - each symbol (non-terminal and terminal) is a single ASCII symbol
#   - non-terminals are uppercase; the rest are terminals
#   - the start nonterminal is always called S
#
# Example: grammar S->S+S|(S)|a is represented with
# 
#   G={'S':('S+S','(S)','a')}
#

# Suggested graph representation: 
#  edge is a tuple (u:int,v:int,state:tuple),
#      u, v: source and destination of the edge, given as indices into input
#            string.
#      state: production with a dot; see lecture notes for description of Earley
#             algorithm
#  state is a tuple (LHS:string,RHS,:string,position-of-dot:int)
#      LHS: nonterminal on left-hand side of a production
#      RHS: right-hand side of production
#      position-of-dot: where the dot is in the RHS, ranges from 0 to len[RHS],
#                       inclusive

# Set to True if you want to generate graphviz file.
visualize = True

# The Earley algorithm

def parse(grammar, inp, incr):

    if visualize:
        # write a graph that can be visualized with graphviz.
        # to view the graph, use the command $ dotty graph.dot
        gviz = open('graph.dot','w')
        gviz.write('digraph G {\nrankdir="LR";')
        for i in range(0,len(inp)):
            gviz.write('    %s -> %s [label = "%s",width=2];\n' % (i, i+1, inp[i]))

        def drawEdge(e):
            dot = e[2][2]
            rhs = e[2][1][0:dot]+'.'+e[2][1][dot:]
            gviz.write('    %s -> %s [label="%s --> %s", constraint=false];\n' % \
                           (e[0],e[1],e[2][0],rhs))
    
    graph=[]
    
    def addEdge(e):
        "add edge to graph and worklist if not present in graph already"
        incr(0)
        if e not in graph:
            graph.append(e)
            if visualize:
                drawEdge(e)
    
    # Add edge (0,0,(S -> . alpha)) to worklist, for all S -> alpha
    for P in grammar['S']:
        addEdge((0,0,('S',P,0)))
    
    # for all tokens on the input
    for j in xrange(0,len(inp)+1):

        # skip in first iteration; we'll complete and predict start nonterminal
        # before we start walking over input
        if j > 0:
            # ADVANCE across the next token:
            # for each edge (i,j-1,N -> alpha . inp[j] beta)
            #     add edge (i,j,N -> alpha inp[j] . beta)
            for (i,_j,(N,RHS,pos)) in graph:
                incr(1)
                if _j==j-1 and pos < len(RHS) and RHS[pos]==inp[j-1]:
                    addEdge((i,j,(N,RHS,pos+1)))

        # Repeat COMPLETE and PREDICT until no more dges can be added
        graphSize = -1
        while graphSize < len(graph):
            graphSize = len(graph)
            # COMPLETE productions
            # for each edge (i,j,N -> alpha .)
            #    for each edge (k,i,M -> beta . N gamma)
            #        add edge (k,j,M -> beta N . gamma)
            for (i,_j,(N,RHS,pos)) in graph:
                incr(2)
                if _j==j and pos == len(RHS):
                    for (k,_i,(M,RHS2,pos2)) in graph:
                        incr(3)
                        if _i==i and pos2 < len(RHS2) and RHS2[pos2]==N:
                            addEdge((k,j,(M,RHS2,pos2+1)))
            
            # PREDICT what the parser is to see on input (move dots in edges 
            # that are in progress)
            #
            # for each edge (i,j,N -> alpha . M beta)
            #     for each production M -> gamma
            #          add edge (j,j,M -> . gamma)
            for (i,_j,(N,RHS,pos)) in graph:
                incr(4)
                # print (i,_j,(N,RHS,pos))
                # non-terminals are upper case
                if _j==j and pos < len(RHS) and \
                   RHS[pos] in string.ascii_uppercase:  
                    M = RHS[pos]
                    # prediction: for all rules D->alpha add edge (j,j,.alpha)
                    for RHS in grammar[M]:
                        addEdge((j,j,(M,RHS,0)))

    if visualize:
        gviz.write('}')
        gviz.close()

    # input has been parsed OK if anf only if an edge (0,n,S -> alpha .) exists
    for RHS in grammar['S']:
        if (0,len(inp),('S',RHS,len(RHS))) in graph:
            return True
    return False
