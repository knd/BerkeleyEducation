% fill the country map so that no 2 regions have same color

adj(1,2).  adj(2,1). 
adj(1,3).  adj(3,1). 
adj(1,4).  adj(4,1). 
adj(1,5).  adj(5,1). 
adj(2,3).  adj(3,2). 
adj(2,4).  adj(4,2). 
adj(3,4).  adj(4,3). 
adj(4,5).  adj(5,4). 
fill(1,red,a).     fill(1,red,b). 
fill(2,blue,a).    fill(2,blue,b). 
fill(3,green,a).   fill(3,green,b). 
fill(4,yellow,a).  fill(4,blue,b). 
fill(5,blue,a).    fill(5,green,b).
oops(C) :- adj(X,Y), fill(X,Color,C), fill(Y,Color,C). 
oops(A,B,C) :- adj(A,B), fill(A,Color,C), fill(B,Color,C). 
oops(A,B,b)?
