contains(A,[A|R]).
contains(A,[B|R]) :- contains(A,R).
remove(A,[A|R],R).
remove(A,[F|R],[F|S]) :- remove(A,R,S).
append([A|B],Z,[A|W]) :- append(B,Z,W).
append([],A,A).
union([A|B],Z,W) :- contains(A,Z),  union(B,Z,W).
union([A|B],Z,[A|W]) :- union(B,Z,W), ~contains(A,Z).
union([],Z,Z).
union([a,b,c,d], [g,f,e,d], A)?
