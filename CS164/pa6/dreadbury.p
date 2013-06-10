equal(A, A).
lives(agatha).
lives(charles).
lives(butler).

kill(X, Y):-lives(X), lives(Y), hates(X, Y), ~richer(X, Y).

hates(charles, X) :- lives(X), ~hates(agatha, X), ~equal(X, charles).
hates(agatha, X):-  lives(X), ~equal(butler, X), ~equal(agatha, X).
hates(butler, X):- lives(X), ~richer(agatha, X), hates(agatha, X), ~equal(butler, X).
hates(X, Y):- lives(X), lives(Y), ~equal(Z, Y), ~hates(X, Z).

kill(X, agatha)?