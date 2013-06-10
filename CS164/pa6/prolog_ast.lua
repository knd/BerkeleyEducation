term1 = {}
term1.typ = "var"
term1.str = "A"
term1.name = "A"

term2 = {}
term2.typ = "var"
term2.str = "A"
term2.name = "A"

clause1 = {}
clause1.name = "equal"
clause1.negated = false
clause1.str = "equal(A, A)"
clause1[1] = term1
clause1[2] = term2

axiom1 = {}
axiom1.head = clause1
axiom1.body = null

term3 = {}
term3.typ = "cst"
term3.str = "agatha"
term3.name = "agatha"

clause2 = {}
clause2.name = "lives"
clause2.negated = false
clause2.str = "lives(agatha)"
clause2[1] = term3

axiom2 = {}
axiom2.head = clause2
axiom2.body = null

term4 = {}
term4.typ = "cst"
term4.str = "charles"
term4.name = "charles"

clause3 = {}
clause3.name = "lives"
clause3.negated = false
clause3.str = "lives(charles)"
clause3[1] = term4

axiom3 = {}
axiom3.head = clause3
axiom3.body = null

term5 = {}
term5.typ = "cst"
term5.str = "butler"
term5.name = "butler"

clause4 = {}
clause4.name = "lives"
clause4.negated = false
clause4.str = "lives(butler)"
clause4[1] = term5

axiom4 = {}
axiom4.head = clause4
axiom4.body = null

term6 = {}
term6.typ = "var"
term6.str = "X"
term6.name = "X"

term7 = {}
term7.typ = "var"
term7.str = "Y"
term7.name = "Y"

clause5 = {}
clause5.name = "kill"
clause5.negated = false
clause5.str = "kill(X, Y)"
clause5[1] = term6
clause5[2] = term7

term8 = {}
term8.typ = "var"
term8.str = "X"
term8.name = "X"

clause6 = {}
clause6.name = "lives"
clause6.negated = false
clause6.str = "lives(X)"
clause6[1] = term8

term9 = {}
term9.typ = "var"
term9.str = "Y"
term9.name = "Y"

clause7 = {}
clause7.name = "lives"
clause7.negated = false
clause7.str = "lives(Y)"
clause7[1] = term9

term10 = {}
term10.typ = "var"
term10.str = "X"
term10.name = "X"

term11 = {}
term11.typ = "var"
term11.str = "Y"
term11.name = "Y"

clause8 = {}
clause8.name = "hates"
clause8.negated = false
clause8.str = "hates(X, Y)"
clause8[1] = term10
clause8[2] = term11

term12 = {}
term12.typ = "var"
term12.str = "X"
term12.name = "X"

term13 = {}
term13.typ = "var"
term13.str = "Y"
term13.name = "Y"

clause9 = {}
clause9.name = "richer"
clause9.negated = true
clause9.str = "~richer(X, Y)"
clause9[1] = term12
clause9[2] = term13

clauseset1 = {}
clauseset1[1] = clause6
clauseset1[2] = clause7
clauseset1[3] = clause8
clauseset1[4] = clause9

rule1 = {}
rule1.head = clause5
rule1.body = clauseset1

term14 = {}
term14.typ = "cst"
term14.str = "charles"
term14.name = "charles"

term15 = {}
term15.typ = "var"
term15.str = "X"
term15.name = "X"

clause10 = {}
clause10.name = "hates"
clause10.negated = false
clause10.str = "hates(charles, X)"
clause10[1] = term14
clause10[2] = term15

term16 = {}
term16.typ = "var"
term16.str = "X"
term16.name = "X"

clause11 = {}
clause11.name = "lives"
clause11.negated = false
clause11.str = "lives(X)"
clause11[1] = term16

term17 = {}
term17.typ = "cst"
term17.str = "agatha"
term17.name = "agatha"

term18 = {}
term18.typ = "var"
term18.str = "X"
term18.name = "X"

clause12 = {}
clause12.name = "hates"
clause12.negated = true
clause12.str = "~hates(agatha, X)"
clause12[1] = term17
clause12[2] = term18

term19 = {}
term19.typ = "var"
term19.str = "X"
term19.name = "X"

term20 = {}
term20.typ = "cst"
term20.str = "charles"
term20.name = "charles"

clause13 = {}
clause13.name = "equal"
clause13.negated = true
clause13.str = "~equal(X, charles)"
clause13[1] = term19
clause13[2] = term20

clauseset2 = {}
clauseset2[1] = clause11
clauseset2[2] = clause12
clauseset2[3] = clause13

rule2 = {}
rule2.head = clause10
rule2.body = clauseset2

term21 = {}
term21.typ = "cst"
term21.str = "agatha"
term21.name = "agatha"

term22 = {}
term22.typ = "var"
term22.str = "X"
term22.name = "X"

clause14 = {}
clause14.name = "hates"
clause14.negated = false
clause14.str = "hates(agatha, X)"
clause14[1] = term21
clause14[2] = term22

term23 = {}
term23.typ = "var"
term23.str = "X"
term23.name = "X"

clause15 = {}
clause15.name = "lives"
clause15.negated = false
clause15.str = "lives(X)"
clause15[1] = term23

term24 = {}
term24.typ = "cst"
term24.str = "butler"
term24.name = "butler"

term25 = {}
term25.typ = "var"
term25.str = "X"
term25.name = "X"

clause16 = {}
clause16.name = "equal"
clause16.negated = true
clause16.str = "~equal(butler, X)"
clause16[1] = term24
clause16[2] = term25

term26 = {}
term26.typ = "cst"
term26.str = "agatha"
term26.name = "agatha"

term27 = {}
term27.typ = "var"
term27.str = "X"
term27.name = "X"

clause17 = {}
clause17.name = "equal"
clause17.negated = true
clause17.str = "~equal(agatha, X)"
clause17[1] = term26
clause17[2] = term27

clauseset3 = {}
clauseset3[1] = clause15
clauseset3[2] = clause16
clauseset3[3] = clause17

rule3 = {}
rule3.head = clause14
rule3.body = clauseset3

term28 = {}
term28.typ = "cst"
term28.str = "butler"
term28.name = "butler"

term29 = {}
term29.typ = "var"
term29.str = "X"
term29.name = "X"

clause18 = {}
clause18.name = "hates"
clause18.negated = false
clause18.str = "hates(butler, X)"
clause18[1] = term28
clause18[2] = term29

term30 = {}
term30.typ = "var"
term30.str = "X"
term30.name = "X"

clause19 = {}
clause19.name = "lives"
clause19.negated = false
clause19.str = "lives(X)"
clause19[1] = term30

term31 = {}
term31.typ = "cst"
term31.str = "agatha"
term31.name = "agatha"

term32 = {}
term32.typ = "var"
term32.str = "X"
term32.name = "X"

clause20 = {}
clause20.name = "richer"
clause20.negated = true
clause20.str = "~richer(agatha, X)"
clause20[1] = term31
clause20[2] = term32

term33 = {}
term33.typ = "cst"
term33.str = "agatha"
term33.name = "agatha"

term34 = {}
term34.typ = "var"
term34.str = "X"
term34.name = "X"

clause21 = {}
clause21.name = "hates"
clause21.negated = false
clause21.str = "hates(agatha, X)"
clause21[1] = term33
clause21[2] = term34

term35 = {}
term35.typ = "cst"
term35.str = "butler"
term35.name = "butler"

term36 = {}
term36.typ = "var"
term36.str = "X"
term36.name = "X"

clause22 = {}
clause22.name = "equal"
clause22.negated = true
clause22.str = "~equal(butler, X)"
clause22[1] = term35
clause22[2] = term36

clauseset4 = {}
clauseset4[1] = clause19
clauseset4[2] = clause20
clauseset4[3] = clause21
clauseset4[4] = clause22

rule4 = {}
rule4.head = clause18
rule4.body = clauseset4

term37 = {}
term37.typ = "var"
term37.str = "X"
term37.name = "X"

term38 = {}
term38.typ = "var"
term38.str = "Y"
term38.name = "Y"

clause23 = {}
clause23.name = "hates"
clause23.negated = false
clause23.str = "hates(X, Y)"
clause23[1] = term37
clause23[2] = term38

term39 = {}
term39.typ = "var"
term39.str = "X"
term39.name = "X"

clause24 = {}
clause24.name = "lives"
clause24.negated = false
clause24.str = "lives(X)"
clause24[1] = term39

term40 = {}
term40.typ = "var"
term40.str = "Y"
term40.name = "Y"

clause25 = {}
clause25.name = "lives"
clause25.negated = false
clause25.str = "lives(Y)"
clause25[1] = term40

term41 = {}
term41.typ = "var"
term41.str = "Z"
term41.name = "Z"

term42 = {}
term42.typ = "var"
term42.str = "Y"
term42.name = "Y"

clause26 = {}
clause26.name = "equal"
clause26.negated = true
clause26.str = "~equal(Z, Y)"
clause26[1] = term41
clause26[2] = term42

term43 = {}
term43.typ = "var"
term43.str = "X"
term43.name = "X"

term44 = {}
term44.typ = "var"
term44.str = "Z"
term44.name = "Z"

clause27 = {}
clause27.name = "hates"
clause27.negated = true
clause27.str = "~hates(X, Z)"
clause27[1] = term43
clause27[2] = term44

clauseset5 = {}
clauseset5[1] = clause24
clauseset5[2] = clause25
clauseset5[3] = clause26
clauseset5[4] = clause27

rule5 = {}
rule5.head = clause23
rule5.body = clauseset5

prog  = {}
prog[1] = axiom1
prog[2] = axiom2
prog[3] = axiom3
prog[4] = axiom4
prog[5] = rule1
prog[6] = rule2
prog[7] = rule3
prog[8] = rule4
prog[9] = rule5
term45 = {}
term45.typ = "var"
term45.str = "X"
term45.name = "X"

term46 = {}
term46.typ = "cst"
term46.str = "agatha"
term46.name = "agatha"

query = {}
query.name = "kill"
query.negated = false
query.str = "kill(X, agatha)"
query[1] = term45
query[2] = term46


