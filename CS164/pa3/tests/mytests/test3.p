reverse([A|B],Z,W) :- reverse(B,[A|Z],W).
reverse([],A,A).
reverse([1,2,3,4,5],[],X)?
