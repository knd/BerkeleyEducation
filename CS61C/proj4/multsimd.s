multsimd:	andi $r1, $r1, 0
			lw	$r2, 0($r1)			
			andi $r3, $r3, 0
			addi $r3, $r3, 8 
			sllv $r2, $r2, $r3
			lw	$r3, 1($r1)		
			or $r2, $r2, $r3	
			lw $r3, 2($r1)	
			
			andi $r0, $r0, 0
L:			beq $r1, $r3, L1
			addp8 $r0, $r0, $r2		
			addi $r1, $r1, 1		
			j L	

L1:	    	andi $r1, $r1, 0
			andi $r3, $r3, 0
			addi $r3, $r3, 8
			srlv $r2, $r0, $r3		
			sw $r2, 3($r1)			
			andi $r2, $r0, 255 	
			sw $r2, 4($r1)	
			
			lui $r1, 51
      		ori $r1, $r1, 68
      		lui $r2, 51
      		ori $r2, $r2, 68

self: 		beq $r1, $r2, self
