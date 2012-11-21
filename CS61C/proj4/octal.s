octal: 	andi $r2, $r2, 0
		lw $r2, 0($r2)
		disp $r2, 1

		andi $r3, $r2, 7 
		or $r0, $r0, $r3 
		andi $r3, $r2, 56 
		addi $r1, $r1, 1 
		sllv $r3, $r3, $r1 
		or $r0, $r0, $r3 
		lui $r1, 1 
		ori $r1, $r1, 192 
		and $r3, $r2, $r1 
		lui $r1, 0
		addi $r1, $r1, 2 
		sllv $r3, $r3, $r1 
		or $r0, $r0, $r3 
		sw $r0, 0($r1) 
		disp $r0, 0
  	  
  	  	lui $r1, 51
      	ori $r1, $r1, 68
      	lui $r2, 51
      	ori $r2, $r2, 68
self: 	beq $r1, $r2, self
