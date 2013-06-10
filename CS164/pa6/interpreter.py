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
            # TODO: Need clarification and cleaning
            result = []
            for elem in e:
                result += bc(elem, t)
            if result == []: return result
            if e[len(e)-1][0] in ["def", "print", "asgn", "put", "error"]: t = None
            return (result + [BcInst('return', t)])
        # e is an expression or a statement
        if type(e) == type(()):
            # Expressions
            if e[0] =='var':
                return [BcInstStd('def', t, e[1])]
            if e[0] in ['null', 'dict-lit']:
                return [BcInst(e[0], t)]
            if e[0] in ['int-lit', 'fp-lit', 'string-lit']:
                return [BcInstStd(e[0], t, e[1])]
            if e[0] in ['+', '-', '*', '/', '==', '!=', '>=', '<=', '<', '>', '&&', '||', 'in', 'get']:
                return bc(e[1],t1) + bc(e[2],t2) + [BcInstStd(e[0], t, t1, t2)]
            if e[0] == 'ite':
                return bc(e[1],t1) + bc(e[2],t2) + bc(e[3],t3) + \
                       [BcInstStd(e[0], t, t1, t2, t3)]
            if e[0] == 'lambda':
                if e[2] == []: return [BcInstAux('lambda', t, e[1], [BcInst('return', None)])]
                return [BcInstAux('lambda', t, e[1], bc(e[2],t1))] 
            if e[0] == 'call':
                args = e[2]
                func = e[1]
                #You must implement calls with more than zero argument.
                res = bc(func, t1)
                instArgs = []
                for arg in args:
                    tempT = newTemp()
                    instArgs.append(tempT)
                    res += bc(arg,tempT)
                return res + [BcInstAux('call', t, instArgs, None, t1)]
            if e[0] == 'len':        
                return bc(e[1],t1) + [BcInstStd('len', t, t1)]
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
                return bc(e[1],t1) + [BcInstStd('coroutine', t, t1)]
            if e[0] == 'resume':
                args = e[2:]
                func = e[1]
                res = []
                for arg in args:
                    res += bc(arg,newTemp())
                return res + [BcInstAux('resume', t, [], None, func[1])]
            if e[0] == 'yield':
                return bc(e[1], t) + [BcInst('yield', t)]
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

# This is the main function of the bytecode interpreter.
# Error handling is missing from the skeleton.
def Resume(state):
    """ Arguments represent the state of the coroutine (as well as of the main
    program) stmts: array of bytecodes, pc: index into stmts where the
    execution should (re)start callstack: the stack of callign context of calls
    ppending in teh coroutine env: the current environment. """
    def lookup(name, env):
        try: return env[name]
        except:
            if env['__up__'] is None:
                raise ProgramError('Cannot find ' + str(name))
            return lookup(name, env['__up__'])

    def update(name, val, env):
        if not env: 
            raise ProgramError("Can't assign undefined variable")
        if env.has_key(name): env[name] = val
        else: update(name, val, env['__up__'])

    def define(name,val):
        state.env[name] = val

    def addScope(parentEnv):
        " create an empty scope and link it to parentEnv "
        return {'__up__': parentEnv}

    def execPrint(isError):
        v = lookup(inst.ret, state.env)
        print v if v != None else "null" # None is a representation for null
        sys.stdout.flush()
        if (isError): sys.exit(1)
    def execGet():
        o = lookup(inst.reg1, state.env)
        k = lookup(inst.reg2, state.env)
        def get(obj):
            try: return obj[k]
            except KeyError:
                raise ProgramError("164 Error: key '%s' not found in the dictionary" % k)
        define(inst.ret, get(o))

    def execPut():
        lookup(inst.ret, state.env)[lookup(inst.reg1, state.env)] = \
            lookup(inst.reg2, state.env)

    def execLen():
	obj = lookup(inst.reg1, state.env)
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

        func    = lookup(inst.callee, state.env)
        try:
            fbody   = func.fun.body
            fargs   = func.fun.argList
        except AttributeError as e:
            raise ProgramError('Calling not a function')
        # jump to body of the callee
        #stmts = fbody  # each function has own list of statements (the body)
        #pc = 0         # and its body starts at index 0
        stmts = fbody
        pc = 0
        if len(fargs) != len(inst.args): raise ProgramError("Wrong number of arguments")
        state.callstack.append((state.env, inst.ret, state.pc, state.stmts))
        newEnv = addScope(func.env)
        state.env[inst.ret] = None
        for arg, instArg in zip(fargs, inst.args):  
            newEnv[arg] = lookup(instArg, state.env)       
        state.env = newEnv
        state.stmts = fbody
        state.pc = pc
        
    def execReturn():
        # Restore caller's context:
        #   - update the callstack
        #   - restore the caller's environment
        #   - handle return values
        if len(state.callstack) == 0:
            if inst.ret is None:
                return 0
            return lookup(inst.ret, state.env) 
        poppedFrame = state.callstack.pop()
        poppedEnv, poppedRet, poppedPc, poppedStmts = poppedFrame
        if inst.ret is not None:
            update(poppedRet, lookup(inst.ret, state.env), poppedEnv)
        else:
            poppedEnv[poppedRet] = None
        state.env, state.stmts, state.pc = poppedEnv, poppedStmts, poppedPc

    def execCoroutine():
        # Implement coroutine.  You must consider what information you
        # need to store for a coroutine.  This is of course dependent on how
        # you implement resume and yield.
        func = lookup(inst.reg1, state.env)
        tempret = func.fun.body[len(func.fun.body)-1]
        tempyield = BcInst('yield', tempret.ret)
        func.fun.body[len(func.fun.body)-1] = tempyield
        state.env[inst.ret] = (State(func.fun.body, addScope(func.env)), func.fun.argList)


    def execResume():
        # Implement resume.  The second argument to resume is the
        # argument it passes to the coroutine.  You must ensure that you handle
        # the case where you try to resume a coroutine that has already ended.
        # In section, we discussed how you might use the Resume function of
        # this interpreter to handle coroutines.  What do you pass it?  How do
        # you handle its return value?
        coroutine = lookup(inst.callee, state.env)
        if coroutine[0] is None:
            print('Error')
            sys.exit(-1)
        state.pc = state.pc - len(coroutine[1]) - 1
        for elem in coroutine[1]:
            coroutine[0].env[elem] = lookup(state.stmts[state.pc].ret, state.env)
            state.pc += 1
        state.pc += 1
        result = Resume(coroutine[0])
        if isinstance(result, tuple):
            state.env[inst.ret] = result[0]
            update(inst.callee, (result[1], coroutine[1]), state.env)
        else:
            state.env[inst.ret] = result
            update(inst.callee, (None, coroutine[1]), state.env)

    def execYield():
        # Implement yield.  The one parameter is an argument passed to
        # whomever resumed the coroutine.  Consider how you can store the
        # information necessary to allow the coroutine to be resumed later and
        # pick up in the right place.  In section, we discussed how to return
        # the information we have here back to whoever resumed this
        # coroutine.
        if state.pc == len(state.stmts): 
            if not inst.ret:
                return None
            return lookup(inst.ret, state.env)
        return (lookup(inst.ret, state.env), state)

        
    def handleDiv():
        try:
            define(inst.ret, lookup(inst.reg1, state.env) / \
                             lookup(inst.reg2, state.env))
        except:
            raise ProgramError("Division by 0")
    
    def handleAdd():
        t1 = lookup(inst.reg1, state.env)
        t2 = lookup(inst.reg2, state.env)
        if (type(t1) == str and type(t2) == str) or \
            (type(t1) == str and type(t2) == int) or \
            (type(t1) == int and type(t2) == str):
            define(inst.ret, str(t1) + str(t2))
        elif type(t1) == int and type(t2) == int:
            define(inst.ret, t1 + t2)
        else:
            raise ProgramError("Cannot concat dictionaries/lists")
    
    def handleIte():
        t1 = lookup(inst.reg1, state.env)
        if type(t1) not in [int,] and t1 != None: raise ProgramError('Bad Expression')
        t2 = lookup(inst.reg2, state.env)
        t3 = lookup(inst.reg3, state.env)
        define(inst.ret, t2 if t1 else t3)
        
    def handleDef():
        if str(inst.ret)[0] != '$' and state.env.has_key(inst.ret):
            raise ProgramError("Cannot redefine")
        define(inst.ret, lookup(inst.reg1, state.env))
        
    def handleAsgn():
        update(inst.ret, lookup(inst.reg1, state.env), \
                                            state.env)
                                            
    def handleOr():
        define(inst.ret, (lambda: 1 if lookup(inst.reg1, state.env) or \
                                                                    lookup(inst.reg2, state.env) else 0)())

    actions = {
        # represent 164 null with Python's None
        'null':      lambda: define(inst.ret, None),
        'string-lit':lambda: define(inst.ret, inst.reg1),
        'int-lit':   lambda: define(inst.ret, inst.reg1),
        'fp-lit':    lambda: define(inst.ret, inst.reg1),
        'dict-lit':  lambda: define(inst.ret, {}),
        'input':     lambda: define(e[1], input()),
        '+':         handleAdd,
        '-':         lambda: define(inst.ret, lookup(inst.reg1, state.env) - \
                                              lookup(inst.reg2, state.env)),
        '*':         lambda: define(inst.ret, lookup(inst.reg1, state.env) * \
                                              lookup(inst.reg2, state.env)), 
        '/':         handleDiv,
                                
        'def':       handleDef,
        '==':        lambda: define(inst.ret, (lambda: 1 if lookup(inst.reg1, state.env) == \
                                                            lookup(inst.reg2, state.env) else 0)()),
        '!=':        lambda: define(inst.ret, (lambda: 1 if lookup(inst.reg1, state.env) != \
                                                            lookup(inst.reg2, state.env) else 0)()),
        '<=':        lambda: define(inst.ret, (lambda: 1 if lookup(inst.reg1, state.env) <= \
                                                            lookup(inst.reg2, state.env) else 0)()),
        '>=':        lambda: define(inst.ret, (lambda: 1 if lookup(inst.reg1, state.env) >= \
                                                            lookup(inst.reg2, state.env) else 0)()),
        '<':         lambda: define(inst.ret, (lambda: 1 if lookup(inst.reg1, state.env) < \
                                                            lookup(inst.reg2, state.env) else 0)()),
        '>':         lambda: define(inst.ret, (lambda: 1 if lookup(inst.reg1, state.env) > \
                                                            lookup(inst.reg2, state.env) else 0)()),
        '&&':        lambda: define(inst.ret, (lambda: 1 if lookup(inst.reg1, state.env) and \
                                                            lookup(inst.reg2, state.env) else 0)()),
        '||':        handleOr,
        'in':        lambda: define(inst.ret, 1) if \
                             (lookup(inst.reg1, state.env) in \
                             lookup(inst.reg2, state.env)) else \
                             define(inst.ret, 0),
        'ite':       handleIte,
        'lambda':    lambda: define(inst.ret, \
                             FunVal(Fun(inst.args,inst.body), state.env)),
        'asgn':      handleAsgn,
        'print':     lambda: execPrint(False),
        'error':     lambda: execPrint(True),
        'get':       execGet,
        'put':       execPut,
        'len':       execLen,
        'call' :     execCall,
        'tcall' :    execCall, # Extra credit (but fun): tail-call elimination.
        'return':    execReturn,
        'coroutine': execCoroutine,
        'resume':    execResume,
        'yield':     execYield
    }

    while True:

        # TODO: Remove me.  Unneeded once return instruction is implemented in
        # the bytecode compiler.
        # Hint: handle return using exception.
        if state.pc >= len(state.stmts):
            return 0
        inst = state.stmts[state.pc]
        state.pc = state.pc + 1

        try:
            if (inst.opcode in ['yield',]): 
                return actions[inst.opcode]()
            else:
                actions[inst.opcode]()
        except ProgramError as e:
            print "Error"
            # Printing to stderr is not captured by the autograder
            print >> sys.stderr, e.msg
            sys.exit(1)
        except KeyError as e:
            raise SyntaxError(str(e) + ' not yet implemented')
    return NeverReached

def desugar(stmts):

    def desugarExp(e):
        # FIXME: add the other operators
        if e[0] in ['+', '-', '*', '/', '!=', '<=', '>=', '<', '>', '==', 'in', 'get']:
            return (e[0], desugarExp(e[1]), desugarExp(e[2]))
        elif e[0] == '||':
            return desugarStmts([('if', e[1], [('exp', ('int-lit', 1))], [e[2]])])
        elif e[0] == '&&':
            return desugarStmts([('if', ('==', e[1], ('int-lit', 0)), [('exp', ('int-lit', 0))], [e[2]])])
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
            inner = [('def', '$j', ('int-lit', 0)), ('def', 'l2', ('dict-lit', []))]
            inner.append(('for', e[2], e[3], [('put', ('var', 'l2'), ('var', '$j'), e[1]), ('asgn', '$j', ('+', ('var', '$j'), ('int-lit', 1)))]))
            inner.append(('exp', ('var', 'l2')))
            return ('exp', ('call', ('lambda', [], desugarStmts(inner)), []))
        elif e[0] == 'len':
            return ('len', desugarExp(e[1]))
        elif e[0] == 'coroutine':
            return ('coroutine', desugarExp(e[1]))
        else:
            return e

    def desugarStmts(stmts):
        # FIXME: add if, while, for
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
            elif s[0] == "if":
                s2 = ('lambda', [], desugarStmts(s[2]))
                if s[3] is None:
                    s3 = ('lambda', [], [])
                else:
                    s3 = ('lambda', [], desugarStmts(s[3]))
                dStmts.append(('exp', ('call', ('ite', desugarExp(s[1]), s2, s3), [])))
            elif s[0] == "while":
                fuck = newTemp()
                s2 = ('lambda', [], desugarStmts(s[2]))
                s3 = ('lambda', [], [])
                s4 = ('lambda', [], [('exp',('call', ('var', fuck), [('var', 'e'), ('var','body')]))])
                innerStmts = []
                innerStmts.append(('def', '$x', ('call', ('var', 'e'), [])))
                innerStmts.append(('exp', ('call', ('ite', ('var', '$x'), ('var', 'body'), s3), [])))
                innerStmts.append(('exp', ('call', ('ite', ('var', '$x'), s4, s3), [])))
                dStmts.append(('def', fuck, ('lambda', ['e', 'body'], innerStmts)))
                dStmts.append(('exp', ('call', ('var', fuck), [('lambda', [], [('exp', desugarExp(s[1]))]), s2])))
            elif s[0] == "for":
                if s[2][0] == 'call':
                    s2 = ('lambda', [], [('exp', ('call', ('var', 's'), [])), ('exp', ('call', ('var', 'for'), [('var','e')]))])
                    dStmts.append(('def', 'e', s[2]))
                    innerStmts = [('def', s[1], ('call', ('var', 'e'),[]))]
                    innerStmts.append(('def', 's', ('lambda', [], desugarStmts(s[3]))))
                    innerStmts.append(('exp', ('call', ('ite', ('var', s[1]), s2, ('lambda', [], [])), [])))
                    dStmts.append(('def', 'for', ('lambda', ['e'], innerStmts)))
                    dStmts.append(('exp', ('call', ('var', 'for'), [('var','e')])))
                else:
                    dStmts.append(('def', '$i', ('int-lit', 0)))
                    body = [('def', s[1],('get', s[2],('var', '$i')))] + desugarStmts(s[3]) + [('asgn', '$i', ('+', ('var', '$i'), ('int-lit', 1)))]
                    dStmts += desugarStmts([('while', ('<', ('var', '$i'), ('len', s[2])), body)])
            else:
                dStmts.append(s)
        return dStmts
    return desugarStmts(stmts)

# Global environment.  Persists across invocations of the ExecGlobal function
globEnv = {'__up__':None}
retVCL = None

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

def ExecGlobal(ast):
    bc = bytecode(desugar(ast))[1]
    tcall_opt(bc)
    #print_bytecode(bc)
    Resume(State(bc, globEnv))
