# PA2: Bytecode interpreter for lambdas and coroutines.  Motivation: stackful
# interpreter cannot implement coroutines.  You are free to modify this file in
# any way you want as long as you do not change the interpreter invokation by
# main.py

import sys, getopt

###############################################################################
#                           BYTECODE COMPILER                                 #
###############################################################################
# The bytecode array stores instructions of the main scope; bytecode arrays
# for lambda bodies are nested bytecode arrays, stored in the call instruction

# Bytecode instruction
#   opcode: instuction type
#   ret:    return register
#   reg1:   register of first operand
#   reg2:   register of second operand
#   reg3:   register of third operand
#   args:   list of arguments
#   body:   code
#   callee: function to call
class BcInst(object): # Used for unary instructions
    def __init__(self, opcode, ret):
        self.opcode = opcode
        self.ret = ret
    def __str__(self):
        return '%s\t%s' % (self.opcode[:6], self.ret)
class BcInstStd(BcInst): # Used for most instructions
    def __init__(self, opcode, ret, reg1 = None, reg2 = None, reg3 = None):
        super(BcInstStd, self).__init__(opcode, ret)
        self.reg1 = reg1
        self.reg2 = reg2
        self.reg3 = reg3
    def __str__(self):
        [r1, r2, r3] = map(lambda r: r is None and ' ' or ', ' + str(r), \
                           [self.reg1, self.reg2, self.reg3])
        return '%s\t%s%s%s%s' % (self.opcode[:6], self.ret, r1, r2, r3)
class BcInstAux(BcInst): # Used for call and lambda
    def __init__(self, opcode, ret, args = None, body = None, callee = None):
        super(BcInstAux, self).__init__(opcode, ret)
        self.callee = callee
        self.body = body
        self.args = args
    def __str__(self):
        ex = self.callee is None and self.args or self.callee
        return '%s\t%s, %s' % (self.opcode[:6], self.ret, ex)

cnt = 0
def newTemp(prefix = '$'):
    global cnt
    cnt = cnt + 1
    return prefix + str(cnt)

def bytecode(e):
    def bc(e,t):
        t1, t2, t3 = newTemp(), newTemp(), newTemp()
        # e is a list of statements (body of function or outer level code)
        if type(e) == type([]):
            # FIXME: Add explicit 'return' instruction after each block of
            # instructions and fix return value handling.
            if len(e) == 0:
                return [BcInst('null', t), BcInst('return', t)]
            else:
                insts = reduce(lambda stmts, s: stmts + bc(s, newTemp()),
                               e[:-1], [])
                insts += bc(e[-1], t)
                insts += [BcInst('return', t)]
                return insts

        # e is an expression or a statement
        if type(e) == type(()):
            # Expressions
            if e[0] == 'null':
                return [BcInst('null', t)]
            if e[0] == 'type':
                return bc(e[1], t1) + [BcInstStd('type', t, t1)]
            if e[0] in ['int-lit', 'fp-lit', 'var']:
                return [BcInstStd('def', t, e[1])]
            if e[0] == 'string-lit':
                return [BcInstStd('string', t, e[1])]
            if e[0] == 'dict-lit':
                return [BcInst('dict', t)]
            if e[0] in ['+', '-', '*', '/', '==', '!=', '<=', '>=', '<', '>', '&&', '||', 'in', 'get']:            
                return bc(e[1],t1) + bc(e[2],t2) + [BcInstStd(e[0], t, t1, t2)]
            if e[0] == 'len':
                return bc(e[1], t1) + [BcInstStd('len', t, t1)]
            if e[0] == 'ite':
                return bc(e[1],t1) + bc(e[2],t2) + bc(e[3],t3) + \
                       [BcInstStd('ite', t, t1, t2, t3)]
            if e[0] == 'lambda':
                return [BcInstAux('lambda', t, e[1], bc(e[2],t1))]
            if e[0] == 'call':
                args = e[2]
                func = e[1]
                # You must implement calls with more than zero argument.
                # temp registers for actual arguments
                arg_temps = [newTemp() for i in range(len(args))]
                # create a copy
                ts = list(arg_temps)
                
                insts = bc(func, t1)
                # evaluate each argument, and store result in one of arg_temps
                insts += reduce(lambda code, s: code + \
                                bc(s, ts.pop(0)), args, [])
                insts += [BcInstAux('call', t, arg_temps, None, t1)]
                return insts
            if e[0] == 'input':     
                return [BcInst('input', t)]
            # Statements
            if e[0] == 'exp':
                return bc(e[1],t)
            # 'asgn' always write to user-defined vars
            if e[0] == 'asgn':
                return bc(e[2],t) + [BcInstStd('asgn', e[1], t)]
            if e[0] == 'put':        
                return bc(e[1],t1) + bc(e[2],t2) + bc(e[3],t3) + \
                       [BcInstStd('put',t1,t2,t3), BcInstStd('def',t,t1)]
            if e[0] == 'def':
                return bc(e[2],t) + [BcInstStd('def', e[1], t)]
            if e[0] == 'print':
                return bc(e[1],t) + [BcInstStd('print', t)]
            if e[0] == 'error':
                return bc(e[1],t) + [BcInst('error', t)]
            if e[0] == 'coroutine':
                return bc(e[1], t1) + [BcInstStd('coroutine', t, t1)]
            if e[0] == 'yield':
                return bc(e[1], t1) + [BcInstStd('yield', t, t1)]
            if e[0] == 'resume':
                return bc(e[1], t1) + bc(e[2], t2) + [BcInstStd('resume', t, t1, t2)]
            if e[0] == 'nativecall':
                arg, func = e[2][0], e[1]
                ret = newTemp()
                return bc(arg, ret) + [BcInstAux(func, t, ret, None, ret)]
                
        raise SyntaxError("Illegal AST node %s " % str(e))
    t = newTemp()
    return t,bc(e,t)

def print_bytecode(p,indent=0):
    for inst in p:
        if inst.opcode != 'lambda':
            print >> sys.stderr, " "*8*indent, inst
        else:
            print >> sys.stderr, " "*8*indent, str(inst)
            print_bytecode(inst.body,indent+1)

def tcall_opt(insts):
    for i in insts:
        if (i.opcode == 'call'):
            idx = insts.index(i)
            if idx + 1 < len(insts) and insts[idx + 1].opcode == 'return':
                i.opcode = 'tcall'
        if i.opcode == 'lambda':
             tcall_opt(i.body)


###############################################################################
#                           BYTECODE INTERPRETER                              #
###############################################################################
class State:                        # The interpreter state.
    def __init__(self, stmts, env, pc = 0, callstack = None):
        self.stmts = stmts
        self.env = env
        self.pc = pc
        if callstack:
            self.callstack = callstack
        else:
            self.callstack = []
class ProgramError(Exception):      # Exception thrown for runtime-errors
    def __init__(self, msg):
        # Optional msg to be printed to stderr
        self.msg = msg
class Fun:                          # The function: (ret-var, arg list, body)
    def __init__(self, argList, body):
        self.argList = argList
        self.body = body
class FunVal:                       # Function value (a closure): (fun, env)
    def __init__(self, fun, env):
        self.fun = fun
        self.env = env
class Coroutine:
    def __init__(self, lhsVar, state, finished = False):
        self.lhsVar = lhsVar
        self.state = state
        self.finished = finished
# This is the main function of the bytecode interpreter.
# Error handling is missing from the skeleton.
def Resume(state):
    """ Arguments represent the state of the coroutine (as well as of the main
    program) stmts: array of bytecodes, pc: index into stmts where the
    execution should (re)start callstack: the stack of calling context of calls
    pending in the coroutine env: the current environment. """
    def lookup(name):
        def _lookup(name, env):
            try: return env[name]
            except:
                if env['__up__'] is None:
                    print "Error"
                    sys.exit(-1)
                return _lookup(name, env['__up__'])
        return _lookup(name, state.env)
    def update(name, val, env):
        if name in env: env[name] = val
        else: update(name, val, env['__up__'])
    def define(name,val):
        state.env[name] = val
    def addScope(parentEnv):
        " create an empty scope and link it to parentEnv "
        return {'__up__': parentEnv}

    def execPrint(isError):
        v = lookup(inst.ret)
        print v if v != None else "null" # None is a representation for null
        sys.stdout.flush()
        if (isError): sys.exit(-1)
    def execGet():
        o = lookup(inst.reg1)
        k = lookup(inst.reg2)
        def get(obj):
            try: 
                if (isinstance(obj, tuple) and k < len(obj)) or k in obj:
                    return obj[k]
                if '__mt' in obj:
                    return get(obj['__mt'])
            except KeyError:
                print "Error"
                sys.exit(-1)
        define(inst.ret, get(o))
    def execPut():
        lookup(inst.ret)[lookup(inst.reg1)] = lookup(inst.reg2)
    def execLen():
        obj = lookup(inst.reg1)
        if type(obj) == type({}):
            length = 0;
            try:
                while obj[length] != None:
                    length += 1
            except KeyError:
                pass
            define(inst.ret, length)
        elif type(obj) == type(""):
            define(inst.ret, len(obj))
        else:
            raise ProgramError("164 Error: called 'len' on object of %s (%s)" %\
                               (type(obj),obj))
    def execCall():
        # This interpreter does not handle the callstack, the environment,
        # function arguments and return values.
        # Hints:
        #     Preparing the environment:
        #         - do you need to create a new scoping frame? If yes, what
        #           is its parent?
        #     Preparing the callstack:
        #         - what do you need to remember in order to return to the
        #           calling function?
        #           Hint: a callstack's frame is a 4-tuple in our solution.
        #     Handling return value:
        #         - in which register is the return value right after return?
        #         - is this register in the scope (frame) of the caller or of
        #           the callee?
        #         - in which register does the return value need to be when the
        #           call is
        #           done (ie, is ready to be transferred to the next
        #           instruction)?
        #         - is this register in the scope (frame) of the caller or of
        #           the callee
        #     Handling arguments:
        #         - you need to extend the bytecode translator so that it
        #           generates code
        #           that computes the values of arguments
        #         - at the time of the call, which registers store the values
        #           of arguments?
        #         - you need to map parameters to arguments: into which scope
        #           (frame)
        #           will you insert the mapping?  Caller's, callee's, or
        #           another one?

        # get the variable to store the result
        lhsVar = inst.ret
        # decompose the function value
        func = lookup(inst.callee)
        if not isinstance(func, FunVal):
            raise TypeError("Trying to call non-lambda")
            print "Error"
            sys.exit(-1)
        fbody = func.fun.body
        fargs = func.fun.argList
        fenv = func.env
        
        # get the register list of actual argument registers
        aargs = [lookup(a) for a in inst.args]
        # push the current context
        state.callstack.append((state.stmts, state.pc, state.env, lhsVar))
        # add scope and initialize with arguments
        state.env = addScope(fenv)
        state.env.update(dict(zip(fargs, aargs)))
        
        # jump to body of the callee
        # each function has own list of statements (the body)
        # and its body starts at index 0
        state.stmts = fbody
        state.pc = 0
        
    def execReturn():
        # Restore caller's context:
        #   - update the callstack
        #   - restore the caller's environment
        #   - handle return values
        if len(state.callstack) == 0:
            # the main program has just terminated.
            # return (lookup(inst.reg1), inst.ret, False)
            raise ProgramError((lookup(inst.ret), None, True))
        
        retVal = lookup(inst.ret)
        (state.stmts, state.pc, state.env, lhsVar) = state.callstack.pop()
        
        # set the return value
        define(lhsVar, retVal)

    def execCoroutine():
        # Implement coroutine.  You must consider what information you
        # need to store for a coroutine.  This is of course dependent on how
        #p you implement resume and yield.
        func = lookup(inst.reg1)
        define(inst.ret, Coroutine(func.fun.argList[0], State(func.fun.body,
                                                              {'__up__': func.env}, 0, [])))
    def execResume():
        # Implement resume.  The second argument to resume is the
        # argument it passes to the coroutine.  You must ensure that you handle
        # the case where you try to resume a coroutine that has already ended.
        # In section, we discussed how you might use the Resume function of
        # this interpreter to handle coroutines.  What do you pass it?  How do
        # you handle its return value?
        coroutine = lookup(inst.reg1)
        if coroutine.finished:
            print "Error"
            sys.exit(-1)
        coroutine.state.env[coroutine.lhsVar] = lookup(inst.reg2)
        result, lhsVar, finished = Resume(coroutine.state)
        # update the return value for the next resume
        coroutine.lhsVar = lhsVar
        coroutine.finished = finished
        # return the value passed by yield
        define(inst.ret, result)

    def execYield():
        # Implement yield.  The one parameter is an argument passed to
        # whomever resumed the coroutine.  Consider how you can store the
        # information necessary to allow the coroutine to be resumed later and
        # pick up in the right place.  In section, we discussed how to return
        # the information we have here back to whoever resumed this
        # coroutine.
        raise ProgramError((lookup(inst.reg1), inst.ret, False))  
    def getType():
        v1 = lookup(inst.reg1)
        if isinstance(v1, dict):
            define(inst.ret, 'table')
        else:
            define(inst.ret, 'other')   
    def execAddition():
        a = lookup(inst.reg1)
        b = lookup(inst.reg2)
        if isinstance(a, str) or isinstance(b, str):
            define(inst.ret, str(a) + str(b))
        else:
            define(inst.ret, a + b)
    def execDivision():
        a = lookup(inst.reg1)
        b = lookup(inst.reg2)
        if b != 0:
            define(inst.ret, a / b)
        else:
            print "Error"
            sys.exit(-1)

    def divCheckAndReturn():
        r1 = lookup(inst.reg1, state.env)
        r2 = lookup(inst.reg2, state.env)
        if (r2 == 0):
            print "Error"
        else:
            define(inst.ret, r1 / r2)
            
    def execFunc():
        kwargs = lookup(inst.callee)
        function = globFuncs[inst.opcode]
        ret = inst.ret
        state.env[ret] = function(**kwargs)

    actions = {
        # represent 164 null with Python's None
        'null':      lambda: define(inst.ret, None),
        'type':      getType,
        'string':    lambda: define(inst.ret, inst.reg1),
        'int-lit':   lambda: define(inst.ret, inst.reg1),
        'fp-lit':    lambda: define(inst.ret, inst.reg1),
        'dict':      lambda: define(inst.ret, {}),
        'input':     lambda: define(inst.ret, raw_input()),
        'def':       lambda: define(inst.ret, inst.reg1 if type(inst.reg1) == type(1) \
                                    else lookup(inst.reg1)),
        '+':         execAddition,
        '-':         lambda: define(inst.ret, lookup(inst.reg1) - lookup(inst.reg2)),
        '*':         lambda: define(inst.ret, lookup(inst.reg1) * lookup(inst.reg2)),
        '/':         execDivision,
        '==':        lambda: define(inst.ret, lookup(inst.reg1) == lookup(inst.reg2)),
        '!=':        lambda: define(inst.ret, lookup(inst.reg1) != lookup(inst.reg2)),
        '<=':        lambda: define(inst.ret, lookup(inst.reg1) <= lookup(inst.reg2)),
        '>=':        lambda: define(inst.ret, lookup(inst.reg1) >= lookup(inst.reg2)),
        '<':         lambda: define(inst.ret, lookup(inst.reg1) < lookup(inst.reg2)),
        '>':         lambda: define(inst.ret, lookup(inst.reg1) > lookup(inst.reg2)),
        'in':        lambda: define(inst.ret, 1) if \
                             (lookup(inst.reg1) in \
                             lookup(inst.reg2)) else \
                             define(inst.ret, 0),
        'ite':       lambda: define(inst.ret, lookup(inst.reg2) if \
                                              lookup(inst.reg1) else \
                                              lookup(inst.reg3)),
        'lambda':    lambda: define(inst.ret, \
                             FunVal(Fun(inst.args, inst.body), state.env)),
        'asgn':      lambda: update(inst.ret, lookup(inst.reg1), \
                                    state.env),
        'print':     lambda: execPrint(False),
        'error':     lambda: execPrint(True),
        'get':       execGet,
        'put':       execPut,
        'len':       execLen,
        'call' :     execCall,
        'tcall' :    execCall, # Optional (but fun): tail-call elimination.
        'return':    execReturn,
        'coroutine': execCoroutine,
        'resume':    execResume,
        'yield':     execYield,
        'mcall':  execFunc
    }

    while True:
        inst = state.stmts[state.pc]
        state.pc = state.pc + 1

        try:
            global globFuncs
            func = None
            if (inst.opcode in globFuncs):
                func = actions['mcall']
            else:
                func = actions[inst.opcode]
            func()
        except ProgramError as e:
            return e.msg
        except KeyError as e:
            raise SyntaxError(str(e) + ' not yet implemented')
    return NeverReached

def desugar(stmts):
    def desugarExp(e):
        if e[0] in ['+', '-', '*', '/', '==', '!=', '>', '<', '>=', '<=', 'in', 'get']:
            return (e[0], desugarExp(e[1]), desugarExp(e[2]))
        elif e[0] == '&&':
            falseBr = ('lambda', [], [('exp', ('int-lit', 0))])
            trueBr = ('lambda', [], [('exp', desugarExp(e[2]))])
            return ('call', ('ite', desugarExp(e[1]), trueBr, falseBr), [])
        elif e[0] == '||':
            trueBr = ('lambda', [], [('exp', ('int-lit', 1))])
            falseBr = ('lambda', [], [('exp', desugarExp(e[2]))])
            return ('call', ('ite', desugarExp(e[1]), trueBr, falseBr), [])
        elif (e[0] == 'call'):
            e1 = desugarExp(e[1])
            dArgs = []
            for arg in e[2]:
                dArgs.append(desugarExp(arg))
            return (e[0], e1, dArgs)
        elif (e[0] == 'lambda'):
            return (e[0], e[1], desugarStmts(e[2]))
        elif (e[0] == 'ite'):
            e1 = desugarExp(e[1])
            e2 = desugarExp(e[2])
            e3 = desugarExp(e[3])
            return (e[0], e1, e2, e3)
        elif (e[0] == 'dict-lit'):
            if len(e[1]) == 0:
                return (e[0],)
            inner = []
            inner.append(('def', '#dict', ('dict-lit',)))
            for init in e[1]:
                inner.append(('put', ('var', '#dict'), ('string-lit', init[0]),\
                             desugarExp(init[1])))
            inner.append(('exp', ('var', '#dict')))
            return ('exp', ('call', ('lambda', [], inner), []))
        elif e[0] == 'comprehension':
            body = e[1]
            lst = e[3]
            
            inner = []
            inner.append(('def', '#out', ('dict-lit', [])))
            inner.append(('def', '#i', ('int-lit', 0)))
            
            forInner = []
            forInner.append(('put', ('var', '#out'), ('var', '#i'), body))
            forInner.append(('asgn', '#i', ('+', ('var', '#i'), \
                                            ('int-lit', 1))))
            
            inner.append(('for', e[2], lst, forInner))
            inner.append(('exp', ('var', '#out')))
            
            inner = desugarStmts(inner)
            return ('call', ('lambda', [], inner), [])
        elif e[0] in ['len', 'coroutine', 'yield']:
            return (e[0], desugarExp(e[1]))
        elif e[0] == 'resume':
            return (e[0], desugarExp(e[1]), desugarExp(e[2]))
        elif e[0] == 'coloncall':
            global colonCall
            colonCall += 1
            h = desugarExp(('def', '__coloncall__' + str(colonCall), desugarExp(e[1])))
            coloncall = ('var', '__coloncall__' + str(colonCall))
            func = ('get', coloncall, e[2])
            e1, args = desugarExp(func), [coloncall,]
            for tmpE in e[3]:
                args.append(desugarExp(tmpE))
            return ('call', ('lambda', [], [h, ('call', func, args)]), [])
        elif e[0] == 'nativecall':
            global globFuncs
            if e[1] == '__builtin__':
                globFuncs[e[2]] = eval(e[2])
                return ('nativecall', e[2], [desugarExp(e[3][0])])
            func = getattr(__import__(e[1]), e[2])
            globFuncs[e[2]] = func
            return ('nativecall', e[2], [desugarExp(e[3][0])])
        else:
            return e
    def desugarStmts(stmts):
        dStmts = []
        for s in stmts:
            if s[0] == 'exp' or s[0] == 'print' or s[0] == 'error':
                s1 = desugarExp(s[1])
                dStmts.append((s[0], s1))
            elif s[0] == 'put':
                dStmts.append((s[0], desugarExp(s[1]), desugarExp(s[2]), desugarExp(s[3])))
            elif s[0] == 'asgn' or s[0] == 'def':
                s2 = desugarExp(s[2])
                dStmts.append((s[0], s[1], s2))
            elif s[0] == 'fdef':
                s3 = desugarStmts(s[3])
                body = ('lambda', s[2], s3)
                dStmts.append(('def', s[1], body))
            elif s[0] == 'if':
                s1 = desugarExp(s[1])
                s2 = desugarStmts(s[2])
                if s[3]:
                    s3 = desugarStmts(s[3])
                else:
                    s3 = []
                ifBranch = ('lambda', [], s2)
                elseBranch = ('lambda', [], s3)
                dStmts.append(('exp', ('call', ('ite', s1, ifBranch, elseBranch), [])))
            elif s[0] == 'while':
                cond = ('lambda', [], [('exp', desugarExp(s[1]))])
                body = ('lambda', [], desugarStmts(s[2]))
                dStmts.append(('exp', ('call', ('var', '_while_'), [cond, body])))
            elif s[0] == 'for':
                inner = []
                inner.append(('def', '#iter', s[2]))
                
                checkType = ('==', ('type', ('var', '#iter')), \
                             ('string-lit', 'table'))
                getIterator = [('asgn', '#iter', ('call', ('var', \
                                '_getIterator_'), [('var', '#iter')]))]
                inner.append(('if', checkType, getIterator, None))
                
                inner.append(('def', '#for', ('lambda', [], [
                             ('def', s[1], ('call', ('var', '#iter'), [])),
                             ('if', ('!=', ('var', s[1]), ('null',)),
                              s[3] + [('exp', ('call', ('var', '#for'), []))],
                              [])])))
                inner.append(('exp', ('call', ('var', '#for'), [])))
                inner = desugarStmts(inner)
                dStmts.append(('exp', ('call', ('lambda', [], inner), [])))
            else:
                dStmts.append(s)
        return dStmts
    return desugarStmts(stmts)

# Global environment.  Persists across invocations of the ExecGlobal function
globEnv = {'__up__':None}
colonCall = 0
globFuncs = {}

def Exec(stmts):
    """ Execute a sequence of statements at the outermost level"""
    env = {'__up__':None}
    return Resume(State(stmts, env)) # return the last statement's value
def ExecFun(closure, args):
    """ Execute a function with arguments args."""
    env = dict(zip(closure.fun.argList,args))
    env['__up__'] = closure.env
    return Resume(State(closure.fun.body, env))
def ExecFunByName(stmts, funName, args):
    """ Execute stmts and then call function with name 'funName'
        with argumetns args.  The function definition must be among
        the statements. """
    env = {'__up__':None}
    Resume(State(stmts, env))
    return ExecFun(env[funName],args)
def ExecGlobal(ast, bindings={}):
    globEnv.update(bindings)
    #bc = bytecode(desugar(ast))[1]
    #print "AST is:"
    #print ast
    sast = desugar(ast)
    #print "simplified AST is:"
    #print sast
    bc = bytecode(sast)[1]
    tcall_opt(bc)
    #print_bytecode(bc)
    Resume(State(bc, globEnv))

# vim: set et ff=unix sts=4 sw=4 fdm=manual:
