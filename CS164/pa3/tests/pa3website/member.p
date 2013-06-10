member(T,[T|Q]).
member(X,[T|Q]) :- member(X,Q).

member(1, [0|[1|[]]])?
