parent(john, ed).
parent(bob, john).
gp(X,Y) :- parent(X,Z), parent(Z,Y).

gp(W, ed)?
