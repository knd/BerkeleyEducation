# implementation notes:
#
# The staring code is Step 4 from the calculator we developed in the lecture
# The parser should no longer consults the function lookupUnit() to check
#   an identifier is unit.
#   Instead, hardcode the units into the parser.
#   This will allow us to parse multi word units, such as "metric ton".
#   The interpreter still needs to convert the units, and this will be
#   still be done by lookupUnit().
# 
# Limitations compared to Google Calculator:
#     - ft/(2*m)/s" this does not associate correctly (should be 1 (ft / (2 * m)) / s) but is 1 ft / ((2 * m) / s)
#

import math

def eval(e):
    if type(e) == type(1): return (e,{}) 
    if type(e) == type(1.1): return (e,{})
    if type(e) == type('m'): return lookupUnit(e)
    if type(e) == type(()):
        if e[0] == '+':  return add(eval(e[1]), eval(e[2]))
        if e[0] == '-':  return sub(eval(e[1]), eval(e[2]))
        if e[0] == '*':  return mul(eval(e[1]), eval(e[2]))
        if e[0] == '/':  return div(eval(e[1]), eval(e[2]))
        if e[0] == '^':  return pow(eval(e[1]), eval(e[2]))
        if e[0] == '!':  return fact(eval(e[1]))
        if e[0] == 'in': return inUnit(eval(e[1]), e[2])

def inUnit((n1,u1),C):
    (n2,u2_SI) = eval(C)
    u2_non_SI = normalize(C)
    if u1 != u2_SI: raise ValueError("Incompatible Units")
    else: return (n1/n2,u2_non_SI)

def normalize(e):
    if type(e) is type('unit'): return {e:1}
    elif type(e) is type(()):
        if e[0] == '*': return mulUnits(normalize(e[1]),normalize(e[2]))
        if e[0] == '/': return divUnits(normalize(e[1]),normalize(e[2]))
        if e[0] == '^': return powUnits(normalize(e[1]),e[2])

def lookupUnit(u):
    return {  'm'    : (1, {'m':1}),
              'ft'   : (0.3048, {'m':1}),
              'inch' : (0.0254, {'m':1}),
              'km'   : (1000, {'m':1}),
              'AU'   : (149598000000, {'m':1}),
              'mile'   : (1609.344, {'m':1}),
              'lightyear': (9460528400000000, {'m':1}),

              'acre' : (4046.85642, {'m':2}),
              
              's'   : (1, {'s':1}),
              'min': (60, {'s':1}),
              'hour': (3600, {'s':1}),
              'day': (86400, {'s':1}),
              'year': (31556926, {'s':1}),
                     
              'kg' :  (1, {'kg':1}),
              'lb' :  (0.45359237, {'kg':1}),

              'l' : (.001, {'m':3}),
              'cup' : (0.000236588236, {'m':3}),
              'pint' : (0.000473176473, {'m':3}),
              'floz' : (0.0000295735296, {'m':3}),
              'kilderkin' : (.0818296538, {'m':3}),

              'knot': (0.514444444, {'m':1, 's':-1}),
              'mph' : (0.44704, {'m':1, 's':-1}),
              'c' : (299792458, {'m':1, 's':-1}),

              'joule' : (1, {'kg':1, 'm':2, 's':-2}),
              'calorie' : (4.18400, {'kg':1, 'm':2, 's':-2}),
              'Calorie' : (4184.00, {'kg':1, 'm':2, 's':-2}),

              'watt' : (1, {'kg':1, 'm':2, 's':-3}),
             
            }[u];
        
def add(v1,v2):
    n1,u1 = v1
    n2,u2 = v2
    if u1 != u2: raise Exception("Adding incompatible units")
    return (n1+n2,u1)
def sub((n1,u1), (n2,u2)):
    if u1 != u2: raise Exception("Adding incompatible units")
    return (n1-n2,u1)
def mul((n1,u1), (n2,u2)):
    return (n1*n2,mulUnits(u1,u2))
# correction to the solution developed in Step 1: 1/2 converted returns 0.5 not 0; this bug shows up in '1 m/year'
# see the definition of op below
def div((n1,u1), (n2,u2)):
    return (divWithCoercion(n1,n2),divUnits(u1,u2))
def divWithCoercion(x,y):
    if type(x) is type(1) or type(x) is type(1L) \
    and type(y) is type(1) or type(y) is type(1L):
        (q,r) = divmod(x,y)
        if r != 0: return float(x)/y # convert to float so that precision is not lost
        else: return q
    else:
        return x/y
def pow((n1,u1), (n2,u2)):
    if u2 != {}: raise Exception("Exponent must be a unit-less value")
    return (n1**n2, powUnits(u1,n2))
def fact((n1,u1)):
    if u1 != {}: raise Exception("Arg to factorial must be a unit-less value")
    return (math.factorial(n1), u1)
def canonize(u):
    return dict([(s,u[s]) for s in u if u[s]!=0])
def mulUnits(u1,u2):
    u = u1.copy()
    for s in u2:
        if s in u: u[s] = u[s] + u2[s]
        else: u[s] = u2[s]
    return canonize(u)    
def divUnits(u1,u2):
    u = u1.copy()
    for s in u2:
        if s in u: u[s] = u[s] - u2[s]
        else: u[s] = -u2[s]
    return canonize(u)
def powUnits(u,n):
    return canonize(dict([(s,u[s]*n) for s in u.keys()]))

# printers

def formatUnit(U):
    q = lambda a,b,c:(b,c)[not a]
    return reduce(lambda s,u: s+u+q(U[u]==1,'','^'+str(U[u]))+' ', sorted(U.keys()), '')

def printAST(n):
    if type(n) == type(()):
        return '(' + \
            printAST(n[1]) + \
            str(n[0]) + \
            (printAST(n[2]) if len(n)>=3 else "") + \
            ')'
    else:
        return str(n)
