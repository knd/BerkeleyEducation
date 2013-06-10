#!/usr/bin/env python

import sys, getopt, string

cnt = {}
def freshname(prefix):
    global cnt
    if prefix in cnt:
        cnt[prefix] = cnt[prefix] + 1
        res = prefix + str(cnt[prefix])
    else:
        cnt[prefix] = 1
        res = prefix + "1"
    return res

def strRep(n):
    if n[0] in ['Var', 'Cst']:
        return n[1]
    elif n[0] == 'Clause':
        ts = string.join(map(lambda t: strRep(t), n[2]), ', ')
        return '%s(%s)' % (n[1], ts)

def getVars(n):
    print str(n)
    if n[0] == 'Var':
        return [n[1]]
    elif n[0] == 'Cst':
        return []
    elif n[0] == 'Clause':
        return [v for sl in  map(getVars,n[2]) for v in sl]
    

def ppTerm(term, out):
    tid = freshname("term")
    if term[0] == 'Var':
        typ = "var"
    elif term[0] == 'Cst':
        typ = "cst"
    elif term[0] == 'Clause':
        typ = "cls"
        cid = ppClause(term, out)
    else:
        out.write("Unknown term type: " + term[0] + '\n')
        sys.exit()
    
    out.write(tid + " = {}\n")
    out.write(tid + ".typ = \"" + typ + "\"\n")
    out.write(tid + ".str = \"" + strRep(term) + "\"\n")
    if typ == 'cls':
        out.write(tid + ".ref = " + cid + '\n')
    else:
        out.write(tid + ".name = \"" + term[1] + "\"\n")
        
    out.write('\n')
    return tid

def ppClause(clause, out, cname = None, negated = False):
    if clause[1] == 'List':
        clist = list(clause[2])
        clist.reverse()
        return ppClause(reduce(lambda cls, t: ('Clause', 'Cons', [t, cls]), clist, ('Clause', 'Nil', [])), out)

    if cname == None:
        cid = freshname("clause")
    else:
        cid = cname
    tids = {}
    i = 1
    for term in clause[2]:
        tids[i] = ppTerm(term, out)
        i = i + 1      
    out.write(cid + " = {}\n")
    out.write(cid + ".name = \"" + clause[1] + "\"\n")
    if negated:
      out.write(cid + ".negated = true\n")
    else:
      out.write(cid + ".negated = false\n")
    sRep = '~' + strRep(clause) if negated else strRep(clause)
    out.write(cid + ".str = \"" + sRep + "\"\n")
   
    for k in tids:
        out.write(cid+ "["+str(k)+"] = " + tids[k] + '\n')
    
    out.write('\n')
    return cid
    
def ppClauses(clauses, out):
    cset = freshname("clauseset")
    cids = {}
    i = 1
    for aclause in clauses:
        cids[i] = ppClause(aclause[1], out, None, aclause[0] == 'Neg')
        i = i + 1
        
    out.write(cset + " = {}\n")
    for k in cids:
        out.write(cset+ "["+str(k)+"] = " + cids[k] + '\n')
        
    out.write('\n')
    return cset

def ppRule(rule, out):
    if rule[0] == 'Axiom':
        rid = freshname("axiom")
        cid = ppClause(rule[1], out)
        out.write(rid + " = {}\n")
        out.write(rid + ".head = " + cid + '\n')
        out.write(rid + ".body = null\n")
    elif rule[0] == 'Rule':
        rid = freshname("rule")
        cid1 = ppClause(rule[1], out)
        cid2 = ppClauses(rule[2], out)
        out.write(rid + " = {}\n")
        out.write(rid + ".head = " + cid1 + '\n')
        out.write(rid + ".body = " + cid2 + '\n')
    else: 
        out.write("Unknown rule type: " + rule[0] + '\n')
        sys.exit()
             
    out.write('\n')
    return rid

def prettyPrint(ast, out):
    rules, query = ast
    # Print out the rules and collect their unique IDs.
    ruleIds = [ppRule(rule, out) for rule in rules]
        
    out.write("prog  = {}\n")
    for index, ruleId in enumerate(ruleIds):
        out.write("prog[%d] = %s\n" % (index + 1, ruleId))

    ppClause(ast[1], out, "query")
    out.write('\n')

def convert(ast, filename):
    out = open(filename, 'w')
    prettyPrint(ast, out)
    out.close()

