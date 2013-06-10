oppa(a, b). oppa(a, c).
oppa(a, d). oppa(b, e).
oppa(b, f). oppa(c, g).
oppa(d, h). oppa(d, i).
oppa(d, j).
equals(A, A).
contains(Elem, Node) :- equals(Elem, Node).
contains(Elem, Node) :- oppa(Node, X), contains(Elem, X).
contains(i, b)?
