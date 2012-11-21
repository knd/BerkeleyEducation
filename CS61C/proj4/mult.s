mult: 	andi $r3, $r3, 0
		lw $r1, 0($r3)
		lw $r2, 1($r3)
	
		andi $r0, $r0, 0
L:		beq $r3, $r2, L1
		add $r0, $r0, $r1
		addi $r3, $r3, 1
		j L	

L1:	    andi $r3, $r3, 0
		sw $r0, 2($r3)

		lui $r0, 20
      	ori $r0, $r0, 30
      	lui $r1, 20
      	ori $r1, $r1, 30

self: 	beq $r0, $r1, self
