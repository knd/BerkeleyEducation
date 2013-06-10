parent(bob,john).
parent(alice, john).
parent(john,ed).
gp(X, Y) :- parent(X, Z), parent(Z, Y).
gp(tim, ed).
gp(X, ed)?
