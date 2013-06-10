% Read about this puzzle at
% http://cseweb.ucsd.edu/classes/sp11/cse130-a/static/goat_etc.html
% Prolog encoding adapted from
% http://cseweb.ucsd.edu/classes/sp11/cse130-a/static/goat.prolog

change(e,w).
change(w,e).
move([X,X,Goat,Cabbage],wolf,[Y,Y,Goat,Cabbage]) :- change(X,Y).
move([X,Wolf,X,Cabbage],goat,[Y,Wolf,Y,Cabbage]) :- change(X,Y).
move([X,Wolf,Goat,X],cabbage,[Y,Wolf,Goat,Y]) :- change(X,Y).
move([X,Wolf,Goat,Cabbage],nothing,[Y,Wolf,Goat,Cabbage]) :- change(X,Y).

oneeq(X,X,WW).
oneeq(X,WWW,X).

safe([Man,Wolf,Goat,Cabbage]) :-
	oneeq(Man,Goat,Wolf),
	oneeq(Man,Goat,Cabbage).

solution([e,e,e,e],[]).

solution(Config,[FirstMove|OtherMoves]) :-
	move(Config,FirstMove,NextConfig),
	safe(NextConfig),
	solution(NextConfig,OtherMoves).

% We give the beginning of the solution to speed up the search.
solution([w,w,w,w], [goat, nothing, wolf, P4, P5, P6, P7])?
