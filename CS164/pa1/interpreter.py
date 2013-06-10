#!/usr/bin/env python

###### knd ######
import sys
####### end #####

class Closure:
    def __init__(self, body, args, parent):
        self.body = body
        self.args = args
        self.parentEnv = parent

def callError():
    print ("Error")
    sys.exit(-1)

def Exec(stmts):
    def evalExp(e,env):
        # TODO: Lookup the value of "name" in "env" in the current and previous frames! Fix me.
        ######### DUNG PHAM ###########
        def lookup(name,env):
            #print(name)
            #print(env)
            if name in env:
                return env[name]
            if env["__up__"] is not None:
                return lookup(name, env["__up__"])
            #print (env)
            #print ((env['for']).parentEnv)
            callError()
        ########## END #################

        if (e[0] == 'int-lit'):   return e[1]
        if (e[0] == 'var'):       return lookup(e[1],env)
        if (e[0] == '+'):         return evalExp(e[1], env) + evalExp(e[2], env)
        if (e[0] == '-'):         return evalExp(e[1], env) - evalExp(e[2], env)
        if (e[0] == '*'):         return evalExp(e[1], env) * evalExp(e[2], env)
        if (e[0] == '/'): 
            ############ knd ########  
            try:
                return evalExp(e[1], env) / evalExp(e[2], env)
            except ZeroDivisionError:
                callError()
            ############# end #######
         ######### Phuc-Hai Huynh #########
        if (e[0] == '>'):
            if evalExp(e[1], env) > evalExp(e[2], env):
                return 1
            return 0 
        if (e[0] == '>='):  
            if evalExp(e[1], env) >= evalExp(e[2], env):
                return 1
            return 0
        if (e[0] == '<'):
            if evalExp(e[1], env) < evalExp(e[2], env):
                return 1
            return 0
        if (e[0] == '<='):
            if evalExp(e[1], env) <= evalExp(e[2], env):
                return 1
            return 0
        if (e[0] == '=='):        
            if evalExp(e[1], env) == evalExp(e[2], env):
                return 1
            return 0
        if (e[0] == '!='):       
            if evalExp(e[1], env) != evalExp(e[2], env):
                return 1
            return 0
        ######### end ###################
        # This only works if functions have no arguments. 
        # It is your job to add argument support to it  
        if (e[0] == 'call'):
        ########### DUNG PHAM ###########
            args = []
            for i in e[2]:
                args.append(evalExp(i, env))
            return doCall(evalExp(e[1], env), args)
        # This does not handle closure. What else do you need to return?
        ########## END ##################
        if (e[0] == 'lambda'):    return Closure(e[2], e[1], env)
        if (e[0] == 'ite'):       
            if evalExp(e[1], env) == 'null':
                return evalExp(e[3], env)
            return [evalExp(e[2],env),evalExp(e[3],env)][not evalExp(e[1],env)]
        if (e[0] == 'null'):      return 'null'
        raise SyntaxError("Illegal or Unimplemented AST node: " + str(e))
    
    def evalStmt(stmts,env):
        # TODO: Update the environment: variable "name" must be bound to "val"
        ############## DUNG PHAM ###########
        def update(name,env,val):
            if name in env.keys():
                env[name] = val
            elif (env["__up__"] is not None):
                update(name, env["__up__"], val)
            else:
                raise NameError(val,"Name is not in dictionary")
        ############### END ################

        val = None
        for s in stmts:
            if   s[0] == 'exp'  : 
                val = evalExp(s[1],env)
            elif s[0] == 'def'  :
                if (env.has_key(s[1])): 
                    callError()
                val = evalExp(s[2],env); env[s[1]] = val
            elif s[0] == 'asgn' : 
                update(s[1],env,evalExp(s[2], env))
                val = 'null'
            elif s[0] == 'print': 
                print(evalExp(s[1],env))
                val = 'null'
            elif s[0] == 'error':
                print(evalExp(s[1], env))
                sys.exit(-1)
        return val
        
    # TODO: Perform a call. The crucial steps are:
    # 1. Create a new frame
    # 2. Link the new frame and the old one.
    # 3. Add argument bindings to the new frame.
    ################## DUNG PHAM ################
    def doCall(closure, args):
        newenv = {"__up__": closure.parentEnv}
        for i in range(len(closure.args)):
            newenv[closure.args[i]] = args[i]
        return evalStmt(closure.body, newenv)
    ################## END ######################
          
    return evalStmt(stmts, { "__up__": None})

# Rewrite complex AST nodes in terms of simpler ones here
def Desugar(ast):
    # We need to desugar any statements contained in the expression
    def desugarExp(e):
        if ((e[0] == '+') or (e[0] == '-') or (e[0] == '*') or (e[0] == '/')
            or (e[0] == '==') or (e[0] == '!=') or (e[0] == '<=')
            or (e[0] == '>=') or (e[0] == '<') or (e[0] == '>')):
            e1 = desugarExp(e[1])
            e2 = desugarExp(e[2])
            return (e[0], e1, e2)
        elif (e[0] == 'call'):
            e1 = desugarExp(e[1])
            dArgs = []
            for arg in e[2]:
                dArgs.append(desugarExp(arg))
            return (e[0], e1, dArgs)
        # Lambda expressions contain statements
        elif (e[0] == 'lambda'):
            e2 = desugarStmts(e[2])
            return (e[0], e[1], e2)
        elif (e[0] == 'ite'):
            e1 = desugarExp(e[1])
            e2 = desugarExp(e[2])
            e3 = desugarExp(e[3])
            return (e[0], e1, e2, e3)
        else:
            return e
    # TODO: Desugar the if statement
    # TODO: Desugar the while statement
    # TODO: Desugar the for statement
    def desugarStmts(stmts):
        dStmts = []
        for s in stmts:                 
            if s[0] == 'exp' or s[0] == 'print' or s[0] == 'error':
                s1 = desugarExp(s[1])
                dStmts.append((s[0], s1))
            elif s[0] == 'asgn' or s[0] == 'def':
                s2 = desugarExp(s[2])
                dStmts.append((s[0], s[1], s2))
            # This case is already done for you. It desugars the body of the function from
            # ('fdef', id, args, body) to ('def', id, ('lambda', args, body))
            elif s[0] == 'fdef':
                s3 = desugarStmts(s[3])
                body = ('lambda', s[2], s3)
                dStmts.append(('def', s[1], body))
            elif s[0] == "if":
                s2 = ('lambda', [], desugarStmts(s[2]))
                s3 = ('lambda', [], desugarStmts(s[3]))
                dStmts.append(('exp', ('call', ('ite', desugarExp(s[1]), s2, s3), [])))
            elif s[0] == "while":
                s2 = ('lambda', [], desugarStmts(s[2]))
                s3 = ('lambda', [], [('exp', ('null',))])
                s4 = ('lambda', [], [('exp',('call', ('var', 'while'), [('var', 'e'), ('var','body')]))])
                innerStmts = []
                innerStmts.append(('def', 'x', ('call', ('var', 'e'), [])))
                innerStmts.append(('exp', ('call', ('ite', ('var', 'x'), ('var', 'body'), s3), [])))
                innerStmts.append(('exp', ('call', ('ite', ('var', 'x'), s4, s3), [])))
                dStmts.append(('def', 'while', ('lambda', ['e', 'body'], innerStmts)))
                dStmts.append(('exp', ('call', ('var', 'while'), [('lambda', [], [('exp', desugarExp(s[1]))]), s2])))
            elif s[0] == "for":
                s2 = ('lambda', [], [('exp', ('call', ('var', 's'), [])), ('exp', ('call', ('var', 'for'), [('var','e'), ('var','s')]))])
                dStmts.append(('def', 'e', s[2]))
                dStmts.append(('def', 's', ('lambda', [], desugarStmts(s[3]))))
                innerStmts = [('asgn', s[1], ('call', ('var', 'e'),[]))]
                innerStmts.append(('exp', ('call', ('ite', ('var', s[1]), s2, ('lambda', [], [])), [])))
                dStmts.append(('def', s[1], ('int-lit', 1)))
                dStmts.append(('def', 'for', ('lambda', ['e', 's'], innerStmts)))
                dStmts.append(('exp', ('call', ('var', 'for'), [('var','e'), ('var','s')])))
##                #print desugarStmts(s[3])
##                s3 = ('lambda', [], desugarStmts(s[3])) #body of for loop
##                innerStmts = []
##                #innerStmts.append(('def', 'ID', ('var', 'e')))
##                innerStmts.append(('def', s[1], ('call', ('var', 'e'), [])))
##                whileloop = [('while', ('var', s[1]),
##                              [('exp', ('call', ('var', '_body_'), [])),
##                               ('asgn', s[1], ('call', ('var', 'e'), []))]
##                              )
##                             ]
##                print desugarStmts(whileloop)
##                innerStmts = innerStmts + desugarStmts(whileloop)
##                #print s[2]
##                dStmts.append(('def', 'for', ('lambda', ['e','body'], innerStmts)))
##                dStmts.append(('exp', ('call', ('var', 'for'), [desugarExp(s[2]), s3])))
##                #print dStmts
            else:
                dStmts.append(s)
        return dStmts
    return desugarStmts(ast)
    
# This class is already complete.
class Frame:
    def __init__(self, name, parent):
        self.name = name        # name of the frame
        self.parent = parent    # reference to the parent's frame
        self.sval = {}          # dict variables to values
        self.cval = {}          # dict variable to (closure's frame, closure's args)
    def add_members(self, sval, cval):
        self.cval = cval
        self.sval = sval
    def encode_vertex(self):
        label = '<head> %s ' % self.name
        for k, v in self.sval.items():
            label += '| %s = %s' % (k, str(v))
        for k, (f, args) in self.cval.items():
            label += '| <%s> %s = lambda\\n%s' % (k, k, str(args)) 
        return '\t%s [label=\"%s\"];\n' % (self.name, label)
    def encode_edges(self):
        code = ''
        if self.parent is not None:
            code += '\t%s:head -> %s:head [label=\"__up__\"];\n' % (self.name, self.parent.name)
        for k, (f, args) in self.cval.items():
            code += '\t%s:%s -> %s:head [weight=0.1 color="blue"];\n' % (self.name, k, f.name)
        return code
    
frameCounter = 0
def snapshot(env, filename):
    def fresh(prefix):
        global frameCounter
        frameCounter = frameCounter + 1
        return prefix + str(frameCounter)
    # You need to finish this function. 
    # The code navigating the environment has been removed
    def walk(env):
        envs = []
        frames = []
        def walk0(env):
            if env in envs:
                return frames[envs.index(env)]
            else:
                # TODO: Find the parent's frame and store it in parentf
                parentf = None    
                frame = Frame(fresh('frame'), parentf)
                envs.append(env)
                frames.append(frame)
                
                sval = {}  # Dict of variables to simple values            
                for k, v in env.items():
                    if k is '__up__':
                        pass
                    elif not isinstance(v, Closure):
                        sval[k] = v
                        
                cval = {}  # Dict of variables to closures   
                # TODO: explore recursively all closures accessible from the current frame
                # Store a pair (closure frame, closure args) into cval 
                
                frame.add_members(sval, cval)   
                return frame          
        walk0(env)
        return frames
        
    frames = walk(env)
    graph = "digraph G {\n\
        node [shape=record];\n\
        rankdir=RL\n"
    for frame in frames:
        graph += frame.encode_vertex()
    for frame in frames:
        graph += frame.encode_edges()
    graph += "}"
    with open(filename, 'wb+') as dotfile:
            dotfile.write(graph) 
    
def Run(ast):
    #print "The AST is "
    #print ast
    sast = Desugar(ast)
    #print "The simplified AST is "
    #print sast
    Exec(sast)
