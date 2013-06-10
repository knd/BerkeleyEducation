require "prolog_ast"

-- Should debug output be printed to stdout. Setting this to true will print a
-- trace similar to the one in the handout.
debug = false

-------------------------------------------------------------------------------
--                       Useful Library Functions                            --
-------------------------------------------------------------------------------

-- Return iterater function for list
-- Example,
-- x = {1,2,3,4,5}
-- for ele in values(x) do print(ele) end
function values(t)
  local i = 0
  return function() i = i + 1; return t[i] end
end

-- Return iterater function for list starting after index i.
function values_after_index(t,i)
  return function() i = i + 1; return t[i] end
end

-- Create a new list whose content is identical to list a.
function copy(a)
  local res = {}
  for j,x in ipairs(a) do 
    res[j] = x 
  end
  return res
end

-------------------------------------------------------------------------------
--                            Pretty Printing                                --
-------------------------------------------------------------------------------

-- Pretty print a term
function pp_term(term)
  if is_var(term) or is_cst(term) then
    return term.name
  else
    return pp_clause(term.ref)
  end
end

-- Pretty print a substitution list
function pp_subst(slist)
  if slist ~= nil then
    local s = "{"
    for ele in values(slist) do
      s = s .. ele.from .. "->" .. pp_term(ele.to)
      s = s .. ", "
    end
    s = s .. "}"
    return s
  end
  return "null"
end

-- Pretty print a clause
function pp_clause(clause)
  if clause.name == 'Nil' then
    return "[]"
  elseif clause.name == 'Cons' then
    return "[" .. pp_term(clause[1]) .. "|" .. pp_term(clause[2]) .. "]"
  else
    local txt = clause.name .. "("
    if #clause > 0 then
      txt = txt .. pp_term(clause[1])
      for t in values_after_index(clause,1) do
        txt = txt .. ", " .. pp_term(t)
      end
    end
    txt = txt .. ")"
    if clause.negated then
      return "~" .. txt
    end
    return txt
  end
end

-- Pretty print out a solution
function print_solutions(solution)
  function lookup_subst(var, slist)
    local res = nil
    for s in values(slist) do
      if s.from == var then
        res = s.to
      end
    end
    return res
  end

  if solution ~= nil then
    local vars = get_vars(query)
    for v in values(vars) do
      print(v.name .. " = " .. pp_term(lookup_subst(v.name, solution)))
    end
    if #vars == 0 then
      print("Yes")
    end
  else
    print("None")
  end
end

-------------------------------------------------------------------------------
--           Complimentary Helper Functions For Your Convenience             --
-------------------------------------------------------------------------------

-- Returns whether a term is a variable
function is_var(t)
  return t.typ == "var"
end

-- Returns whether a term is a constant
function is_cst(t)
  return t.typ == "cst"
end

-- Returns whether a term is a clause
function is_clause(t)
  return t.typ == "cls"
end

-- Returns whether var is used anywhere inside the term
function contains(term, var)
  -- Check for var inside a clause
  function contains_inner(clause, var)
    local res = false
    for term in values(clause) do
      res = res or contains(term, var)
    end
    return res
  end

  if is_cst(term) then
    return false
  elseif is_var(term) then
    return term.name == var
  elseif is_clause(term) then
    return contains_inner(term.ref, var)
  else
    error("contains: Unknown term type: " .. term.typ)
  end
end

-- Returns a list of variables used inside the clause
function get_vars(clause)
  function isIn(list, var)
    local res = false
    for x in values(list) do
      res = res or x.name == var.name
    end
    return res
  end

  local res = {}
  -- Check inside each clause
  pp_clause(clause)
  for term in values(clause) do
    if term.typ == 'var' and not(isIn(res, term)) then
      table.insert(res, term)
    elseif term.typ == 'cls' then
      local terms = get_vars(term.ref)
      for t in values(terms) do
        if not(isIn(res, t)) then
          table.insert(res, t)
        end
      end
    end
  end
  return res
end

-- Renames each variable in the clause by appending "_{cnt}" to the end
function freshen_clause(clause, cnt)
  local vars = get_vars(clause)
  -- Create a mgu which converts each variable to its new name
  local subst = {}
  for v in values(vars) do
    table.insert(subst, {from=v.name, to={typ='var', name=v.name .. "_" .. cnt}})
  end
  -- substitute the variables in clause
  return subst_clause(clause, subst)
end

-- Counter for creating unique variables names
cnt = 0

-- For recursive invocations of the same rule, we need to rename the variables
-- so that our unification algorithm does not have clashes with variable names
-- freshen appends "_{cnt}" to each variable in the rule
function freshen(rule)
  cnt = cnt + 1

  local new_rule = {}
  -- Update the head
  new_rule.head = freshen_clause(rule.head, cnt)

  -- update the body
  if rule.body then
    local new_body = {}
    for clause in values(rule.body) do
      table.insert(new_body, freshen_clause(clause, cnt))
    end
    new_rule.body = new_body
  end
  return new_rule
end


-- Replace variables in term using the substitution list
function subst_term(term, slist)
  local newterm = {typ = term.typ}
  if is_var(term) then
    newterm.name = term.name
    -- check if variable exists in slist
    for s in values(slist) do
      if s.from == term.name then
        newterm = s.to
      end
    end
  elseif is_clause(term) then
    -- replace variables in clause
    newterm.ref = subst_clause(term.ref, slist)
  elseif is_cst(term) then
    newterm = term
  else
    error("subst: Unknown term type: " .. term.typ)
  end
  return newterm
end

-- Replace variables in clause using the substitution list
function subst_clause(clause, slist)
  local newclause = {name = clause.name, negated = clause.negated}

  -- Replace variables in each term
  for term in values(clause) do
    pp_term(term)
    table.insert(newclause, subst_term(term, slist))
  end
  return newclause
end

-- Replace variables in clauses using the substitution list
function subst(clauses, slist)
  local newclist = {}

  -- Replace variables in each clause
  for clause in values(clauses) do
    table.insert(newclist, subst_clause(clause, slist))
  end
  return newclist
end

-- Replace the subsitution subst onto the substitution list
function subst_subst(slist, subst)
  local newslist = {}

  -- Create a mgu from subst
  local subst_list = {}
  subst_list[1] = subst

  -- replace variables in each substitution
  for s in values(slist) do
    table.insert(newslist, {from = s.from, to = subst_term(s.to, subst_list)})
  end
  return newslist
end

-- Merge two MGUs to get one MGU. it is assumed that substitutions defined in
-- mgu1 do not use any of the variables being substituted in mgu2.
function merge(mgu1, mgu2)
  if mgu1 == nil or mgu2 == nil then
    return nil
  else
    local res = copy(mgu1)
    -- update the substitions in mgu2 so that do not use any variables found in mgu1
    for s in values(mgu2) do
      local s2 = {from= s.from, to=subst_term(s.to, mgu1)}
      table.insert(res, s2)
    end

    return res
  end
end

-------------------------------------------------------------------------------
--               Robinson's First Order Unification Algorithm                --
-------------------------------------------------------------------------------

-- Try to unify clause1 with clause2, should return a MGU, as a list of
-- substitions (represented as a table with a from and to field
function unify(clause1, clause2)
  if debug then
    print("Unify: " .. pp_clause(clause1) .. " and " .. pp_clause(clause2))
  end

  local res = {}
  
  -- If names of the clauses or number of terms are different, return right away.
  if clause1.name ~= clause2.name or #clause1 ~= #clause2 then
	if debug then
      print("    Unifier: null")
    end
    return nil
  else
    local i = 1
    while i <= #clause1 do
      local t1 = subst_term(clause1[i], res)
      local t2 = subst_term(clause2[i], res)

      -- if both have the same name, then do nothing
      local const_match = is_cst(t1) and is_cst(t2) and (t1.name == t2.name)
      local var_match = is_var(t1) and is_var(t2) and t1.name == t2.name

      if const_match or var_match then
        -- Nothing to do here
      elseif is_var(t1) and not(contains(t2, t1.name)) then
        local s = {from = t1.name, to = t2}
        -- Need to update res using new substition to maintain invariant
        res = subst_subst(res, s)
        table.insert(res, s)
      elseif is_var(t2) and not(contains(t1, t2.name)) then
        local s = {from = t2.name, to = t1}
        -- Need to update res using new substition to maintain invariant
        res = subst_subst(res, s)
        table.insert(res, s)
      elseif is_clause(t1) and is_clause(t2) then
        local innermgu = unify(t1.ref, t2.ref)
        if innermgu then
          res = merge(innermgu, res)
        else
          i = #clause1
          res = innermgu
        end
      else
        i = #clause1
        res = nil
      end -- End If
      i = i + 1
    end -- End while
    
    if debug then
      print("    Unifier: " .. pp_subst(res))
    end
    return res
  end
end


-------------------------------------------------------------------------------
--                           Prolog Interpreter Core                         --
-------------------------------------------------------------------------------

-- Try to satisfy the goal, should yield a MGU for every solution found and null
-- at the end. 
-- FIXME: The current skeleton is nowhere near complete. You will need to
-- modify its control structure. You MUST use coroutine to enumerate solutions.
function process(goal)
  if debug then
    print("Goal: " .. pp_clause(goal))
  end 
  for ele in values(prog) do 
	local rule = freshen(ele) -- Think about why renaming is necessary!
	local mgu = unify(rule.head, goal)

	if mgu then
		if rule.body then
			local i = 1
			local co_lst = {}
			for rule in values(rule.body) do
				co_lst[i] = coroutine.create(process)
				i = i + 1
			end

			local mgu_lst = {}
			local j = 1
			while j ~= 0 do
				if j == i then
					if mgu_lst[j-1] then
						coroutine.yield(mgu_lst[j-1])
					end
					j = j - 1
				else
					if j == 1 then
						status, solut = coroutine.resume(co_lst[j], subst_clause(rule.body[j], mgu))
					else
						status, solut = coroutine.resume(co_lst[j], subst_clause(rule.body[j], mgu_lst[j-1]))
					end
					
					if solut ~= nil and rule.body[j].negated == false then
						if j == 1 then
							mgu_lst[j] = merge(solut, mgu)
						else
							mgu_lst[j] = merge(solut, mgu_lst[j-1] )
						end
						j = j + 1
					else
                        if rule.body[j].negated and solut == nil then
                            if j == 1 then
                                mgu_lst[j] = mgu
                            else
                                mgu_lst[j] = mgu_lst[j-1]
                            end
                            j = j + 1
                        else
						    co_lst[j] = coroutine.create(process)
						    j = j - 1
                        end
					end
				end
			end
		else
			coroutine.yield(mgu)
		end
    end
  end
end

-- Main loop
co = coroutine.create(process)
i = 1
solution = true

while solution ~= nil do
  print("Asking for solution " .. i)
  status, solution = coroutine.resume(co,query)
  print_solutions(solution)
  i = i + 1
  --if i == 3 then break end
end
  
