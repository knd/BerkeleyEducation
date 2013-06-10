contains(A,[A|R]).
contains(A,[B|R]) :- contains(A,R).
remove(A,[A|R],R).
remove(A,[F|R],[F|S]) :- remove(A,R,S).
append([A|B],Z,[A|W]) :- append(B,Z,W).
append([],A,A).
remove(A,[1,2,3,4],B)?
