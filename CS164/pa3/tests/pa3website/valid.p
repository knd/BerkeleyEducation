red(1).
red(2).
yellow(3).
yellow(4).
green(5).
green(6).
green(7).

stamp(S) :- red(S).
stamp(S) :- yellow(S).
stamp(S) :- green(S).

samecolor(X,Y) :- red(X), red(Y).
samecolor(X,Y) :- yellow(X), yellow(Y).
samecolor(X,Y) :- green(X), green(Y).

valid(A,B,C) :- stamp(A), stamp(B),
                stamp(C), ~samecolor(A,B),
                ~samecolor(B,C), ~samecolor(C,A).

valid(5, B, C)?