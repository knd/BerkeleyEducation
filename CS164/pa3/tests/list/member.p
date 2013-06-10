member(T,[T|Q]).
member(X,[T|Q]) :- member(X,Q).
member(A,[0, 1])?
