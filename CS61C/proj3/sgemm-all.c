/*  Name: Khanh Dao
	Login: cs61c-ef
    Partner name: Phuc-Hai Huynh
	Login: cs61c-cd
*/ 

#include <stdio.h>
#include <string.h>
#include <emmintrin.h> 

inline void square_sgemm64x64(int n, float* A, float* B, float* C)
{
	int i, j, k;
	__m128 c0[16], c1[16], c2[16], c3[16];
	__m128 b00, b10, b20, b30;
	__m128 b01, b11, b21, b31;
	__m128 b02, b12, b22, b32;
	__m128 b03, b13, b23, b33;
	__m128 a0, a1, a2, a3;

	for (i = 0; i < 16; i=i++)
	{
		c0[i] = _mm_setzero_ps();
		c1[i] = _mm_setzero_ps();
		c2[i] = _mm_setzero_ps();
		c3[i] = _mm_setzero_ps();
	}

	for (j = 0; j < 32; j=j+4)
	{

		///////
		for (k = 0; k < 64; k=k+4)
		{
			b00 = _mm_load1_ps(B+k+j*n);
			b01 = _mm_load1_ps(B+(k+1)+j*n);
			b02 = _mm_load1_ps(B+(k+2)+j*n);
			b03 = _mm_load1_ps(B+(k+3)+j*n);

			b10 = _mm_load1_ps(B+k+(j+1)*n);
			b11 = _mm_load1_ps(B+(k+1)+(j+1)*n);
			b12 = _mm_load1_ps(B+(k+2)+(j+1)*n);
			b13 = _mm_load1_ps(B+(k+3)+(j+1)*n);

			b20 = _mm_load1_ps(B+k+(j+2)*n);
			b21 = _mm_load1_ps(B+(k+1)+(j+2)*n);
			b22 = _mm_load1_ps(B+(k+2)+(j+2)*n);
			b23 = _mm_load1_ps(B+(k+3)+(j+2)*n);

			b30 = _mm_load1_ps(B+k+(j+3)*n);
			b31 = _mm_load1_ps(B+(k+1)+(j+3)*n);
			b32 = _mm_load1_ps(B+(k+2)+(j+3)*n);
			b33 = _mm_load1_ps(B+(k+3)+(j+3)*n);


			for (i = 0; i < 16; i+=8)
			{
				a3 = _mm_loadu_ps(A+i*4+(k+3)*n);
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a3, b33 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a3, b23 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a3, b13 ) );
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+i*4+(k+2)*n);	
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a2, b32 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a2, b22 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a2, b12 ) );
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+i*4+(k+1)*n);					
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a1, b31 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a1, b21 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a1, b11 ) );
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+i*4+k*n);
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a0, b30 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a0, b20 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a0, b10 ) );
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a0, b00 ) );
				
				///////
				a3 = _mm_loadu_ps(A+(i+1)*4+(k+3)*n);
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a3, b33 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a3, b23 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a3, b13 ) );
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+(i+1)*4+(k+2)*n);	
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a2, b32 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a2, b22 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a2, b12 ) );
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+(i+1)*4+(k+1)*n);					
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a1, b31 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a1, b21 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a1, b11 ) );
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+(i+1)*4+k*n);
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a0, b30 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a0, b20 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a0, b10 ) );
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a0, b00 ) );
				
				////////
				a3 = _mm_loadu_ps(A+(i+2)*4+(k+3)*n);
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a3, b33 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a3, b23 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a3, b13 ) );
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+(i+2)*4+(k+2)*n);	
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a2, b32 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a2, b22 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a2, b12 ) );
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+(i+2)*4+(k+1)*n);					
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a1, b31 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a1, b21 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a1, b11 ) );
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+(i+2)*4+k*n);
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a0, b30 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a0, b20 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a0, b10 ) );
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a0, b00 ) );

				////////
				a3 = _mm_loadu_ps(A+(i+3)*4+(k+3)*n);
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a3, b33 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a3, b23 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a3, b13 ) );
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+(i+3)*4+(k+2)*n);	
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a2, b32 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a2, b22 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a2, b12 ) );
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+(i+3)*4+(k+1)*n);					
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a1, b31 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a1, b21 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a1, b11 ) );
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+(i+3)*4+k*n);
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a0, b30 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a0, b20 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a0, b10 ) );
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a0, b00 ) );
			
				/////////
				a3 = _mm_loadu_ps(A+(i+4)*4+(k+3)*n);
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a3, b33 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a3, b23 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a3, b13 ) );
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+(i+4)*4+(k+2)*n);	
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a2, b32 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a2, b22 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a2, b12 ) );
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+(i+4)*4+(k+1)*n);					
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a1, b31 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a1, b21 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a1, b11 ) );
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+(i+4)*4+k*n);
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a0, b30 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a0, b20 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a0, b10 ) );
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a0, b00 ) );
				
				///////
				a3 = _mm_loadu_ps(A+(i+5)*4+(k+3)*n);
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a3, b33 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a3, b23 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a3, b13 ) );
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+(i+5)*4+(k+2)*n);	
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a2, b32 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a2, b22 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a2, b12 ) );
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+(i+5)*4+(k+1)*n);					
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a1, b31 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a1, b21 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a1, b11 ) );
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+(i+5)*4+k*n);
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a0, b30 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a0, b20 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a0, b10 ) );
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a0, b00 ) );
				
				////////
				a3 = _mm_loadu_ps(A+(i+6)*4+(k+3)*n);
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a3, b33 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a3, b23 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a3, b13 ) );
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+(i+6)*4+(k+2)*n);	
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a2, b32 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a2, b22 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a2, b12 ) );
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+(i+6)*4+(k+1)*n);					
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a1, b31 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a1, b21 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a1, b11 ) );
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+(i+6)*4+k*n);
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a0, b30 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a0, b20 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a0, b10 ) );
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a0, b00 ) );

				////////
				a3 = _mm_loadu_ps(A+(i+7)*4+(k+3)*n);
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a3, b33 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a3, b23 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a3, b13 ) );
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+(i+7)*4+(k+2)*n);	
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a2, b32 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a2, b22 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a2, b12 ) );
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+(i+7)*4+(k+1)*n);					
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a1, b31 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a1, b21 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a1, b11 ) );
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+(i+7)*4+k*n);
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a0, b30 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a0, b20 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a0, b10 ) );
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a0, b00 ) );
						
			}
		}


		//////////
		for (i = 0; i < 16; i++) 
		{
			_mm_storeu_ps( C+i*4+j*n, c0[i] );
			_mm_storeu_ps( C+i*4+(j+1)*n, c1[i] );
			_mm_storeu_ps( C+i*4+(j+2)*n, c2[i] );
			_mm_storeu_ps( C+i*4+(j+3)*n, c3[i] );
			c0[i] = _mm_setzero_ps();
			c1[i] = _mm_setzero_ps();
			c2[i] = _mm_setzero_ps();
			c3[i] = _mm_setzero_ps();
		}

		
	} // end of for

	for (j = 32; j < 64; j=j+4)
	{

		//////
		for (k = 0; k < 64; k=k+4)
		{
			b00 = _mm_load1_ps(B+k+j*n);
			b01 = _mm_load1_ps(B+(k+1)+j*n);
			b02 = _mm_load1_ps(B+(k+2)+j*n);
			b03 = _mm_load1_ps(B+(k+3)+j*n);

			b10 = _mm_load1_ps(B+k+(j+1)*n);
			b11 = _mm_load1_ps(B+(k+1)+(j+1)*n);
			b12 = _mm_load1_ps(B+(k+2)+(j+1)*n);
			b13 = _mm_load1_ps(B+(k+3)+(j+1)*n);

			b20 = _mm_load1_ps(B+k+(j+2)*n);
			b21 = _mm_load1_ps(B+(k+1)+(j+2)*n);
			b22 = _mm_load1_ps(B+(k+2)+(j+2)*n);
			b23 = _mm_load1_ps(B+(k+3)+(j+2)*n);

			b30 = _mm_load1_ps(B+k+(j+3)*n);
			b31 = _mm_load1_ps(B+(k+1)+(j+3)*n);
			b32 = _mm_load1_ps(B+(k+2)+(j+3)*n);
			b33 = _mm_load1_ps(B+(k+3)+(j+3)*n);


			for (i = 0; i < 16; i+=8)
			{
				a3 = _mm_loadu_ps(A+i*4+(k+3)*n);
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a3, b33 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a3, b23 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a3, b13 ) );
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+i*4+(k+2)*n);	
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a2, b32 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a2, b22 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a2, b12 ) );
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+i*4+(k+1)*n);					
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a1, b31 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a1, b21 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a1, b11 ) );
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+i*4+k*n);
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a0, b30 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a0, b20 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a0, b10 ) );
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a0, b00 ) );
				
				///////
				a3 = _mm_loadu_ps(A+(i+1)*4+(k+3)*n);
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a3, b33 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a3, b23 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a3, b13 ) );
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+(i+1)*4+(k+2)*n);	
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a2, b32 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a2, b22 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a2, b12 ) );
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+(i+1)*4+(k+1)*n);					
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a1, b31 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a1, b21 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a1, b11 ) );
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+(i+1)*4+k*n);
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a0, b30 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a0, b20 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a0, b10 ) );
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a0, b00 ) );
				
				////////
				a3 = _mm_loadu_ps(A+(i+2)*4+(k+3)*n);
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a3, b33 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a3, b23 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a3, b13 ) );
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+(i+2)*4+(k+2)*n);	
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a2, b32 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a2, b22 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a2, b12 ) );
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+(i+2)*4+(k+1)*n);					
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a1, b31 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a1, b21 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a1, b11 ) );
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+(i+2)*4+k*n);
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a0, b30 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a0, b20 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a0, b10 ) );
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a0, b00 ) );

				////////
				a3 = _mm_loadu_ps(A+(i+3)*4+(k+3)*n);
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a3, b33 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a3, b23 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a3, b13 ) );
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+(i+3)*4+(k+2)*n);	
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a2, b32 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a2, b22 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a2, b12 ) );
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+(i+3)*4+(k+1)*n);					
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a1, b31 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a1, b21 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a1, b11 ) );
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+(i+3)*4+k*n);
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a0, b30 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a0, b20 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a0, b10 ) );
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a0, b00 ) );
			
				/////////
				a3 = _mm_loadu_ps(A+(i+4)*4+(k+3)*n);
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a3, b33 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a3, b23 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a3, b13 ) );
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+(i+4)*4+(k+2)*n);	
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a2, b32 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a2, b22 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a2, b12 ) );
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+(i+4)*4+(k+1)*n);					
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a1, b31 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a1, b21 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a1, b11 ) );
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+(i+4)*4+k*n);
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a0, b30 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a0, b20 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a0, b10 ) );
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a0, b00 ) );
				
				///////
				a3 = _mm_loadu_ps(A+(i+5)*4+(k+3)*n);
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a3, b33 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a3, b23 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a3, b13 ) );
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+(i+5)*4+(k+2)*n);	
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a2, b32 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a2, b22 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a2, b12 ) );
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+(i+5)*4+(k+1)*n);					
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a1, b31 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a1, b21 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a1, b11 ) );
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+(i+5)*4+k*n);
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a0, b30 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a0, b20 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a0, b10 ) );
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a0, b00 ) );
				
				////////
				a3 = _mm_loadu_ps(A+(i+6)*4+(k+3)*n);
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a3, b33 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a3, b23 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a3, b13 ) );
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+(i+6)*4+(k+2)*n);	
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a2, b32 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a2, b22 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a2, b12 ) );
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+(i+6)*4+(k+1)*n);					
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a1, b31 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a1, b21 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a1, b11 ) );
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+(i+6)*4+k*n);
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a0, b30 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a0, b20 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a0, b10 ) );
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a0, b00 ) );

				////////
				a3 = _mm_loadu_ps(A+(i+7)*4+(k+3)*n);
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a3, b33 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a3, b23 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a3, b13 ) );
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a3, b03 ) );
			
				a2 = _mm_loadu_ps(A+(i+7)*4+(k+2)*n);	
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a2, b32 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a2, b22 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a2, b12 ) );
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a2, b02 ) );

				a1 = _mm_loadu_ps(A+(i+7)*4+(k+1)*n);					
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a1, b31 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a1, b21 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a1, b11 ) );
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a1, b01 ) );

				a0 = _mm_loadu_ps(A+(i+7)*4+k*n);
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a0, b30 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a0, b20 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a0, b10 ) );
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a0, b00 ) );
						
			}
		}

		//////////
		for (i = 0; i < 16; i++) 
		{
			_mm_storeu_ps( C+i*4+j*n, c0[i] );
			_mm_storeu_ps( C+i*4+(j+1)*n, c1[i] );
			_mm_storeu_ps( C+i*4+(j+2)*n, c2[i] );
			_mm_storeu_ps( C+i*4+(j+3)*n, c3[i] );
			c0[i] = _mm_setzero_ps();
			c1[i] = _mm_setzero_ps();
			c2[i] = _mm_setzero_ps();
			c3[i] = _mm_setzero_ps();
		}
	} // end of for

}

inline void square_sgemm64xn(int bound, int n, float* A, float* B, float* C)
{
	int i, j, k;
	__m128 c0[16], c1[16], c2[16], c3[16];
	__m128 b00, b10, b20, b30;
	__m128 b01, b11, b21, b31;
	__m128 b02, b12, b22, b32;
	__m128 b03, b13, b23, b33;
	__m128 a0, a1, a2, a3, a;

	for (i = 0; i < 16; i=i++)
	{
		c0[i] = _mm_setzero_ps();
		c1[i] = _mm_setzero_ps();
		c2[i] = _mm_setzero_ps();
		c3[i] = _mm_setzero_ps();
	}

	for (j = 0; j < 32; j=j+4)
	{
		
		///////
		for (k = 0; k < bound; k=k+4)
		{
			b00 = _mm_load1_ps(B+k+j*n);
			b01 = _mm_load1_ps(B+(k+1)+j*n);
			b02 = _mm_load1_ps(B+(k+2)+j*n);
			b03 = _mm_load1_ps(B+(k+3)+j*n);

			b10 = _mm_load1_ps(B+k+(j+1)*n);
			b11 = _mm_load1_ps(B+(k+1)+(j+1)*n);
			b12 = _mm_load1_ps(B+(k+2)+(j+1)*n);
			b13 = _mm_load1_ps(B+(k+3)+(j+1)*n);
	
			b20 = _mm_load1_ps(B+k+(j+2)*n);
			b21 = _mm_load1_ps(B+(k+1)+(j+2)*n);
			b22 = _mm_load1_ps(B+(k+2)+(j+2)*n);
			b23 = _mm_load1_ps(B+(k+3)+(j+2)*n);

			b30 = _mm_load1_ps(B+k+(j+3)*n);
			b31 = _mm_load1_ps(B+(k+1)+(j+3)*n);
			b32 = _mm_load1_ps(B+(k+2)+(j+3)*n);
			b33 = _mm_load1_ps(B+(k+3)+(j+3)*n);

			for (i = 0; i < 16; i=i+8)
			{
				/// i = 0
				a0 = _mm_loadu_ps(A+i*4+k*64);
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a0, b00 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a0, b10 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a0, b20 ) );
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a0, b30 ) );
				
				a1 = _mm_loadu_ps(A+i*4+(k+1)*64);
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a1, b01 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a1, b11 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a1, b21 ) );
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+i*4+(k+2)*64);
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a2, b02 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a2, b12 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a2, b22 ) );
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+i*4+(k+3)*64);
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a3, b03 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a3, b13 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a3, b23 ) );
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a3, b33 ) );


				/// i = 1
				a0 = _mm_loadu_ps(A+(i+1)*4+k*64);
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a0, b00 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a0, b10 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a0, b20 ) );
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a0, b30 ) );

				a1 = _mm_loadu_ps(A+(i+1)*4+(k+1)*64);
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a1, b01 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a1, b11 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a1, b21 ) );
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+(i+1)*4+(k+2)*64);
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a2, b02 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a2, b12 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a2, b22 ) );
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+(i+1)*4+(k+3)*64);
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a3, b03 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a3, b13 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a3, b23 ) );
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a3, b33 ) );


				/// i = 2
				a0 = _mm_loadu_ps(A+(i+2)*4+k*64);
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a0, b00 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a0, b10 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a0, b20 ) );
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a0, b30 ) );

				a1 = _mm_loadu_ps(A+(i+2)*4+(k+1)*64);
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a1, b01 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a1, b11 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a1, b21 ) );
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+(i+2)*4+(k+2)*64);
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a2, b02 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a2, b12 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a2, b22 ) );
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+(i+2)*4+(k+3)*64);
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a3, b03 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a3, b13 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a3, b23 ) );
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a3, b33 ) );

				/// i = 3
				a0 = _mm_loadu_ps(A+(i+3)*4+k*64);
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a0, b00 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a0, b10 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a0, b20 ) );
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a0, b30 ) );

				a1 = _mm_loadu_ps(A+(i+3)*4+(k+1)*64);
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a1, b01 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a1, b11 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a1, b21 ) );
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+(i+3)*4+(k+2)*64);
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a2, b02 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a2, b12 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a2, b22 ) );
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+(i+3)*4+(k+3)*64);
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a3, b03 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a3, b13 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a3, b23 ) );
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a3, b33 ) );


				/// i = 4
				a0 = _mm_loadu_ps(A+(i+4)*4+k*64);
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a0, b00 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a0, b10 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a0, b20 ) );
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a0, b30 ) );

				a1 = _mm_loadu_ps(A+(i+4)*4+(k+1)*64);
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a1, b01 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a1, b11 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a1, b21 ) );
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+(i+4)*4+(k+2)*64);
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a2, b02 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a2, b12 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a2, b22 ) );
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+(i+4)*4+(k+3)*64);
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a3, b03 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a3, b13 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a3, b23 ) );
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a3, b33 ) );

				/// i = 5
				a0 = _mm_loadu_ps(A+(i+5)*4+k*64);
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a0, b00 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a0, b10 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a0, b20 ) );
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a0, b30 ) );

				a1 = _mm_loadu_ps(A+(i+5)*4+(k+1)*64);
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a1, b01 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a1, b11 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a1, b21 ) );
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+(i+5)*4+(k+2)*64);
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a2, b02 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a2, b12 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a2, b22 ) );
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+(i+5)*4+(k+3)*64);
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a3, b03 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a3, b13 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a3, b23 ) );
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a3, b33 ) );

				/// i = 6
				a0 = _mm_loadu_ps(A+(i+6)*4+k*64);
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a0, b00 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a0, b10 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a0, b20 ) );
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a0, b30 ) );

				a1 = _mm_loadu_ps(A+(i+6)*4+(k+1)*64);
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a1, b01 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a1, b11 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a1, b21 ) );
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+(i+6)*4+(k+2)*64);
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a2, b02 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a2, b12 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a2, b22 ) );
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+(i+6)*4+(k+3)*64);
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a3, b03 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a3, b13 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a3, b23 ) );
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a3, b33 ) );


				/// i = 7
				a0 = _mm_loadu_ps(A+(i+7)*4+k*64);
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a0, b00 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a0, b10 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a0, b20 ) );
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a0, b30 ) );

				a1 = _mm_loadu_ps(A+(i+7)*4+(k+1)*64);
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a1, b01 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a1, b11 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a1, b21 ) );
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+(i+7)*4+(k+2)*64);
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a2, b02 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a2, b12 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a2, b22 ) );
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+(i+7)*4+(k+3)*64);
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a3, b03 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a3, b13 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a3, b23 ) );
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a3, b33 ) );

			}
		}

		for (; k < n; k=k++)
		{
			b00 = _mm_load1_ps(B+k+j*n);
			b10 = _mm_load1_ps(B+k+(j+1)*n);
			b20 = _mm_load1_ps(B+k+(j+2)*n);
			b30 = _mm_load1_ps(B+k+(j+3)*n);

			for (i = 0; i < 16; i=i+8)
			{
				a = _mm_loadu_ps(A+i*4+k*64);
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a, b00 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a, b10 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a, b20 ) );
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a, b30 ) );

				a = _mm_loadu_ps(A+(i+1)*4+k*64);
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a, b00 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a, b10 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a, b20 ) );
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a, b30 ) );

				a = _mm_loadu_ps(A+(i+2)*4+k*64);
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a, b00 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a, b10 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a, b20 ) );
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a, b30 ) );

				a = _mm_loadu_ps(A+(i+3)*4+k*64);
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a, b00 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a, b10 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a, b20 ) );
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a, b30 ) );

				a = _mm_loadu_ps(A+(i+4)*4+k*64);
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a, b00 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a, b10 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a, b20 ) );
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a, b30 ) );

				a = _mm_loadu_ps(A+(i+5)*4+k*64);
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a, b00 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a, b10 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a, b20 ) );
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a, b30 ) );

				a = _mm_loadu_ps(A+(i+6)*4+k*64);
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a, b00 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a, b10 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a, b20 ) );
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a, b30 ) );

				a = _mm_loadu_ps(A+(i+7)*4+k*64);
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a, b00 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a, b10 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a, b20 ) );
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a, b30 ) );

			}
		}



		//////////
		for (i = 0; i < 16; i++) 
		{
			_mm_storeu_ps( C+i*4+j*64, c0[i] );
			_mm_storeu_ps( C+i*4+(j+1)*64, c1[i] );
			_mm_storeu_ps( C+i*4+(j+2)*64, c2[i] );
			_mm_storeu_ps( C+i*4+(j+3)*64, c3[i] );
			c0[i] = _mm_setzero_ps();
			c1[i] = _mm_setzero_ps();
			c2[i] = _mm_setzero_ps();
			c3[i] = _mm_setzero_ps();
		}

		
	} // end of for

	for (j = 32; j < 64; j=j+4)
	{
		
		///////
		for (k = 0; k < bound; k=k+4)
		{
			b00 = _mm_load1_ps(B+k+j*n);
			b01 = _mm_load1_ps(B+(k+1)+j*n);
			b02 = _mm_load1_ps(B+(k+2)+j*n);
			b03 = _mm_load1_ps(B+(k+3)+j*n);

			b10 = _mm_load1_ps(B+k+(j+1)*n);
			b11 = _mm_load1_ps(B+(k+1)+(j+1)*n);
			b12 = _mm_load1_ps(B+(k+2)+(j+1)*n);
			b13 = _mm_load1_ps(B+(k+3)+(j+1)*n);
	
			b20 = _mm_load1_ps(B+k+(j+2)*n);
			b21 = _mm_load1_ps(B+(k+1)+(j+2)*n);
			b22 = _mm_load1_ps(B+(k+2)+(j+2)*n);
			b23 = _mm_load1_ps(B+(k+3)+(j+2)*n);

			b30 = _mm_load1_ps(B+k+(j+3)*n);
			b31 = _mm_load1_ps(B+(k+1)+(j+3)*n);
			b32 = _mm_load1_ps(B+(k+2)+(j+3)*n);
			b33 = _mm_load1_ps(B+(k+3)+(j+3)*n);

			for (i = 0; i < 16; i=i+8)
			{
				/// i = 0
				a0 = _mm_loadu_ps(A+i*4+k*64);
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a0, b00 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a0, b10 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a0, b20 ) );
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a0, b30 ) );
				
				a1 = _mm_loadu_ps(A+i*4+(k+1)*64);
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a1, b01 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a1, b11 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a1, b21 ) );
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+i*4+(k+2)*64);
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a2, b02 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a2, b12 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a2, b22 ) );
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+i*4+(k+3)*64);
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a3, b03 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a3, b13 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a3, b23 ) );
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a3, b33 ) );


				/// i = 1
				a0 = _mm_loadu_ps(A+(i+1)*4+k*64);
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a0, b00 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a0, b10 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a0, b20 ) );
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a0, b30 ) );

				a1 = _mm_loadu_ps(A+(i+1)*4+(k+1)*64);
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a1, b01 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a1, b11 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a1, b21 ) );
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+(i+1)*4+(k+2)*64);
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a2, b02 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a2, b12 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a2, b22 ) );
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+(i+1)*4+(k+3)*64);
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a3, b03 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a3, b13 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a3, b23 ) );
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a3, b33 ) );


				/// i = 2
				a0 = _mm_loadu_ps(A+(i+2)*4+k*64);
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a0, b00 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a0, b10 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a0, b20 ) );
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a0, b30 ) );

				a1 = _mm_loadu_ps(A+(i+2)*4+(k+1)*64);
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a1, b01 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a1, b11 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a1, b21 ) );
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+(i+2)*4+(k+2)*64);
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a2, b02 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a2, b12 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a2, b22 ) );
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+(i+2)*4+(k+3)*64);
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a3, b03 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a3, b13 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a3, b23 ) );
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a3, b33 ) );

				/// i = 3
				a0 = _mm_loadu_ps(A+(i+3)*4+k*64);
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a0, b00 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a0, b10 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a0, b20 ) );
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a0, b30 ) );

				a1 = _mm_loadu_ps(A+(i+3)*4+(k+1)*64);
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a1, b01 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a1, b11 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a1, b21 ) );
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+(i+3)*4+(k+2)*64);
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a2, b02 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a2, b12 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a2, b22 ) );
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+(i+3)*4+(k+3)*64);
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a3, b03 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a3, b13 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a3, b23 ) );
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a3, b33 ) );


				/// i = 4
				a0 = _mm_loadu_ps(A+(i+4)*4+k*64);
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a0, b00 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a0, b10 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a0, b20 ) );
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a0, b30 ) );

				a1 = _mm_loadu_ps(A+(i+4)*4+(k+1)*64);
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a1, b01 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a1, b11 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a1, b21 ) );
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+(i+4)*4+(k+2)*64);
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a2, b02 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a2, b12 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a2, b22 ) );
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+(i+4)*4+(k+3)*64);
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a3, b03 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a3, b13 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a3, b23 ) );
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a3, b33 ) );

				/// i = 5
				a0 = _mm_loadu_ps(A+(i+5)*4+k*64);
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a0, b00 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a0, b10 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a0, b20 ) );
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a0, b30 ) );

				a1 = _mm_loadu_ps(A+(i+5)*4+(k+1)*64);
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a1, b01 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a1, b11 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a1, b21 ) );
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+(i+5)*4+(k+2)*64);
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a2, b02 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a2, b12 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a2, b22 ) );
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+(i+5)*4+(k+3)*64);
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a3, b03 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a3, b13 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a3, b23 ) );
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a3, b33 ) );

				/// i = 6
				a0 = _mm_loadu_ps(A+(i+6)*4+k*64);
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a0, b00 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a0, b10 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a0, b20 ) );
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a0, b30 ) );

				a1 = _mm_loadu_ps(A+(i+6)*4+(k+1)*64);
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a1, b01 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a1, b11 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a1, b21 ) );
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+(i+6)*4+(k+2)*64);
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a2, b02 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a2, b12 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a2, b22 ) );
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+(i+6)*4+(k+3)*64);
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a3, b03 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a3, b13 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a3, b23 ) );
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a3, b33 ) );


				/// i = 7
				a0 = _mm_loadu_ps(A+(i+7)*4+k*64);
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a0, b00 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a0, b10 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a0, b20 ) );
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a0, b30 ) );

				a1 = _mm_loadu_ps(A+(i+7)*4+(k+1)*64);
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a1, b01 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a1, b11 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a1, b21 ) );
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a1, b31 ) );

				a2 = _mm_loadu_ps(A+(i+7)*4+(k+2)*64);
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a2, b02 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a2, b12 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a2, b22 ) );
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a2, b32 ) );

				a3 = _mm_loadu_ps(A+(i+7)*4+(k+3)*64);
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a3, b03 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a3, b13 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a3, b23 ) );
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a3, b33 ) );

			}
		}

		for (; k < n; k=k++)
		{
			b00 = _mm_load1_ps(B+k+j*n);
			b10 = _mm_load1_ps(B+k+(j+1)*n);
			b20 = _mm_load1_ps(B+k+(j+2)*n);
			b30 = _mm_load1_ps(B+k+(j+3)*n);

			for (i = 0; i < 16; i=i+8)
			{
				a = _mm_loadu_ps(A+i*4+k*64);
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a, b00 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a, b10 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a, b20 ) );
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a, b30 ) );

				a = _mm_loadu_ps(A+(i+1)*4+k*64);
				c0[i+1] = _mm_add_ps( c0[i+1], _mm_mul_ps( a, b00 ) );
				c1[i+1] = _mm_add_ps( c1[i+1], _mm_mul_ps( a, b10 ) );
				c2[i+1] = _mm_add_ps( c2[i+1], _mm_mul_ps( a, b20 ) );
				c3[i+1] = _mm_add_ps( c3[i+1], _mm_mul_ps( a, b30 ) );

				a = _mm_loadu_ps(A+(i+2)*4+k*64);
				c0[i+2] = _mm_add_ps( c0[i+2], _mm_mul_ps( a, b00 ) );
				c1[i+2] = _mm_add_ps( c1[i+2], _mm_mul_ps( a, b10 ) );
				c2[i+2] = _mm_add_ps( c2[i+2], _mm_mul_ps( a, b20 ) );
				c3[i+2] = _mm_add_ps( c3[i+2], _mm_mul_ps( a, b30 ) );

				a = _mm_loadu_ps(A+(i+3)*4+k*64);
				c0[i+3] = _mm_add_ps( c0[i+3], _mm_mul_ps( a, b00 ) );
				c1[i+3] = _mm_add_ps( c1[i+3], _mm_mul_ps( a, b10 ) );
				c2[i+3] = _mm_add_ps( c2[i+3], _mm_mul_ps( a, b20 ) );
				c3[i+3] = _mm_add_ps( c3[i+3], _mm_mul_ps( a, b30 ) );

				a = _mm_loadu_ps(A+(i+4)*4+k*64);
				c0[i+4] = _mm_add_ps( c0[i+4], _mm_mul_ps( a, b00 ) );
				c1[i+4] = _mm_add_ps( c1[i+4], _mm_mul_ps( a, b10 ) );
				c2[i+4] = _mm_add_ps( c2[i+4], _mm_mul_ps( a, b20 ) );
				c3[i+4] = _mm_add_ps( c3[i+4], _mm_mul_ps( a, b30 ) );

				a = _mm_loadu_ps(A+(i+5)*4+k*64);
				c0[i+5] = _mm_add_ps( c0[i+5], _mm_mul_ps( a, b00 ) );
				c1[i+5] = _mm_add_ps( c1[i+5], _mm_mul_ps( a, b10 ) );
				c2[i+5] = _mm_add_ps( c2[i+5], _mm_mul_ps( a, b20 ) );
				c3[i+5] = _mm_add_ps( c3[i+5], _mm_mul_ps( a, b30 ) );

				a = _mm_loadu_ps(A+(i+6)*4+k*64);
				c0[i+6] = _mm_add_ps( c0[i+6], _mm_mul_ps( a, b00 ) );
				c1[i+6] = _mm_add_ps( c1[i+6], _mm_mul_ps( a, b10 ) );
				c2[i+6] = _mm_add_ps( c2[i+6], _mm_mul_ps( a, b20 ) );
				c3[i+6] = _mm_add_ps( c3[i+6], _mm_mul_ps( a, b30 ) );

				a = _mm_loadu_ps(A+(i+7)*4+k*64);
				c0[i+7] = _mm_add_ps( c0[i+7], _mm_mul_ps( a, b00 ) );
				c1[i+7] = _mm_add_ps( c1[i+7], _mm_mul_ps( a, b10 ) );
				c2[i+7] = _mm_add_ps( c2[i+7], _mm_mul_ps( a, b20 ) );
				c3[i+7] = _mm_add_ps( c3[i+7], _mm_mul_ps( a, b30 ) );

			}
		}



		//////////
		for (i = 0; i < 16; i++) 
		{
			_mm_storeu_ps( C+i*4+j*64, c0[i] );
			_mm_storeu_ps( C+i*4+(j+1)*64, c1[i] );
			_mm_storeu_ps( C+i*4+(j+2)*64, c2[i] );
			_mm_storeu_ps( C+i*4+(j+3)*64, c3[i] );
			c0[i] = _mm_setzero_ps();
			c1[i] = _mm_setzero_ps();
			c2[i] = _mm_setzero_ps();
			c3[i] = _mm_setzero_ps();
		}

		
	} // end of for


}

inline void square_sgemm32xn(int n, float* A, float* B, float* C)
{
	int i, j, k;
	__m128 c0[8], c1[8], c2[8], c3[8];
	__m128 c4[8], c5[8], c6[8], c7[8];
	__m128 b00, b10, b20, b30;
	__m128 a;
	
	for (i = 0; i < 8; i++)
	{
		c0[i] = _mm_setzero_ps();
		c1[i] = _mm_setzero_ps();
		c2[i] = _mm_setzero_ps();
		c3[i] = _mm_setzero_ps();
		c4[i] = _mm_setzero_ps();
		c5[i] = _mm_setzero_ps();
		c6[i] = _mm_setzero_ps();
		c7[i] = _mm_setzero_ps();

	}	
	for (j = 0; j < 32; j+=4)
	{
		for (k = 0; k < n; k++)
		{
			b00 = _mm_load1_ps( B+k+j*n );
			b10 = _mm_load1_ps( B+k+(j+1)*n );
			b20 = _mm_load1_ps( B+k+(j+2)*n );
			b30 = _mm_load1_ps( B+k+(j+3)*n );

				i = 0;
				a = _mm_loadu_ps(A+i*4+k*32);
				c0[i] = _mm_add_ps( c0[i], _mm_mul_ps( a, b00 ) );
				c1[i] = _mm_add_ps( c1[i], _mm_mul_ps( a, b10 ) );
				c2[i] = _mm_add_ps( c2[i], _mm_mul_ps( a, b20 ) );
				c3[i] = _mm_add_ps( c3[i], _mm_mul_ps( a, b30 ) );
				
				/// i = 1
				a = _mm_loadu_ps(A+(i+1)*4+k*32);
				c0[(i+1)] = _mm_add_ps( c0[(i+1)], _mm_mul_ps( a, b00 ) );
				c1[(i+1)] = _mm_add_ps( c1[(i+1)], _mm_mul_ps( a, b10 ) );
				c2[(i+1)] = _mm_add_ps( c2[(i+1)], _mm_mul_ps( a, b20 ) );
				c3[(i+1)] = _mm_add_ps( c3[(i+1)], _mm_mul_ps( a, b30 ) ); 
			
				/// i = 2
				a = _mm_loadu_ps(A+(i+2)*4+k*32);
				c0[(i+2)] = _mm_add_ps( c0[(i+2)], _mm_mul_ps( a, b00 ) );
				c1[(i+2)] = _mm_add_ps( c1[(i+2)], _mm_mul_ps( a, b10 ) );
				c2[(i+2)] = _mm_add_ps( c2[(i+2)], _mm_mul_ps( a, b20 ) );
				c3[(i+2)] = _mm_add_ps( c3[(i+2)], _mm_mul_ps( a, b30 ) ); 

				/// i = 3
				a = _mm_loadu_ps(A+(i+3)*4+k*32);
				c0[(i+3)] = _mm_add_ps( c0[(i+3)], _mm_mul_ps( a, b00 ) );
				c1[(i+3)] = _mm_add_ps( c1[(i+3)], _mm_mul_ps( a, b10 ) );
				c2[(i+3)] = _mm_add_ps( c2[(i+3)], _mm_mul_ps( a, b20 ) );
				c3[(i+3)] = _mm_add_ps( c3[(i+3)], _mm_mul_ps( a, b30 ) ); 

				/// i = 4
				a = _mm_loadu_ps(A+(i+4)*4+k*32);
				c0[(i+4)] = _mm_add_ps( c0[(i+4)], _mm_mul_ps( a, b00 ) );
				c1[(i+4)] = _mm_add_ps( c1[(i+4)], _mm_mul_ps( a, b10 ) );
				c2[(i+4)] = _mm_add_ps( c2[(i+4)], _mm_mul_ps( a, b20 ) );
				c3[(i+4)] = _mm_add_ps( c3[(i+4)], _mm_mul_ps( a, b30 ) );

				/// i = 5
				a = _mm_loadu_ps(A+(i+5)*4+k*32);
				c0[(i+5)] = _mm_add_ps( c0[(i+5)], _mm_mul_ps( a, b00 ) );
				c1[(i+5)] = _mm_add_ps( c1[(i+5)], _mm_mul_ps( a, b10 ) );
				c2[(i+5)] = _mm_add_ps( c2[(i+5)], _mm_mul_ps( a, b20 ) );
				c3[(i+5)] = _mm_add_ps( c3[(i+5)], _mm_mul_ps( a, b30 ) );

				/// i = 6
				a = _mm_loadu_ps(A+(i+6)*4+k*32);
				c0[(i+6)] = _mm_add_ps( c0[(i+6)], _mm_mul_ps( a, b00 ) );
				c1[(i+6)] = _mm_add_ps( c1[(i+6)], _mm_mul_ps( a, b10 ) );
				c2[(i+6)] = _mm_add_ps( c2[(i+6)], _mm_mul_ps( a, b20 ) );
				c3[(i+6)] = _mm_add_ps( c3[(i+6)], _mm_mul_ps( a, b30 ) );

				/// i = 7
				a = _mm_loadu_ps(A+(i+7)*4+k*32);
				c0[(i+7)] = _mm_add_ps( c0[(i+7)], _mm_mul_ps( a, b00 ) );
				c1[(i+7)] = _mm_add_ps( c1[(i+7)], _mm_mul_ps( a, b10 ) );
				c2[(i+7)] = _mm_add_ps( c2[(i+7)], _mm_mul_ps( a, b20 ) );
				c3[(i+7)] = _mm_add_ps( c3[(i+7)], _mm_mul_ps( a, b30 ) );
		}


		for ( i = 0; i < 8; i++)
		{
			_mm_storeu_ps( C+i*4+j*32, c0[i] );
			_mm_storeu_ps( C+i*4+(j+1)*32, c1[i] );
			_mm_storeu_ps( C+i*4+(j+2)*32, c2[i] );
			_mm_storeu_ps( C+i*4+(j+3)*32, c3[i] );
			c0[i] = _mm_setzero_ps();
			c1[i] = _mm_setzero_ps();
			c2[i] = _mm_setzero_ps();
			c3[i] = _mm_setzero_ps();

		}
	}

	


}


void square_sgemm(int n, float* A, float* B, float* C)
{
	register const int bound = n / 64 * 64;
	int fringe = n - bound;
	int pad = 64 - fringe;
	int size_floatx64 = 64*sizeof(float);
	int size_floatxfringe = fringe*sizeof(float);
	int size_floatxpad = pad*sizeof(float);
	float* A_64_64 = calloc(64*64, sizeof(float));
	float* B_64_64 = calloc(64*64, sizeof(float));
	float* C_64_64 = calloc(64*64, sizeof(float));
	int i, j, k, l, p;
	// make sure matrix C is all zero out
	memset(C, 0.0, n*n*sizeof(float));
	
	if (n == 64)
	{
		square_sgemm64x64(n, A, B, C);
	}
	else if (fringe == 0)
	{
		// 1st level loop
		for (j = 0; j < n; j=j+64)
		{
			// 2nd level loop
			for (i = 0; i < n; i=i+64)
			{
				// load into B_64_64
				for (l = 0; l < 64; l+=4)
				{
					memcpy(B_64_64+l*64, B+i+(j+l)*n, size_floatx64);
					memcpy(B_64_64+(l+1)*64, B+i+(j+l+1)*n, size_floatx64);
					memcpy(B_64_64+(l+2)*64, B+i+(j+l+2)*n, size_floatx64);
					memcpy(B_64_64+(l+3)*64, B+i+(j+l+3)*n, size_floatx64);
				}	
				
				// 3rd level loop
				for (p = 0; p < n; p=p+64)
				{
					// load into A_64_64
					for (l = 0; l < 64; l+=4)
					{
						memcpy(A_64_64+(l)*64, A+p+(i+l)*n, size_floatx64);
						memcpy(A_64_64+(l+1)*64, A+p+(i+l+1)*n, size_floatx64);
						memcpy(A_64_64+(l+2)*64, A+p+(i+l+2)*n, size_floatx64);
						memcpy(A_64_64+(l+3)*64, A+p+(i+l+3)*n, size_floatx64);
					}

					// do the multiplication to get C_64_64
					square_sgemm64x64(64, A_64_64, B_64_64, C_64_64);

					// store back into C
					__m128 temp_c, buff_c;
					for (l = 0; l < 32; l+=4)
					{
						for (k = 0; k < 64; k+=8)
						{
							/// l = 0
							temp_c = _mm_loadu_ps(C+(k+p)+(j+l)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+l*64);
							_mm_storeu_ps( C+(k+p)+(j+l)*n, _mm_add_ps( temp_c, buff_c ) );	

							temp_c = _mm_loadu_ps(C+(k+4+p)+(j+l)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+4+l*64);
							_mm_storeu_ps( C+(k+4+p)+(j+l)*n, _mm_add_ps( temp_c, buff_c ) );	
		
							/// l = 1
							temp_c = _mm_loadu_ps(C+(k+p)+(j+l+1)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+(l+1)*64);
							_mm_storeu_ps( C+(k+p)+(j+l+1)*n, _mm_add_ps( temp_c, buff_c ) );	

							temp_c = _mm_loadu_ps(C+(k+4+p)+(j+l+1)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+4+(l+1)*64);
							_mm_storeu_ps( C+(k+4+p)+(j+l+1)*n, _mm_add_ps( temp_c, buff_c ) );	

							/// l = 2
							temp_c = _mm_loadu_ps(C+(k+p)+(j+l+2)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+(l+2)*64);
							_mm_storeu_ps( C+(k+p)+(j+l+2)*n, _mm_add_ps( temp_c, buff_c ) );	

							temp_c = _mm_loadu_ps(C+(k+4+p)+(j+l+2)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+4+(l+2)*64);
							_mm_storeu_ps( C+(k+4+p)+(j+l+2)*n, _mm_add_ps( temp_c, buff_c ) );	

							///l = 3
							temp_c = _mm_loadu_ps(C+(k+p)+(j+l+3)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+(l+3)*64);
							_mm_storeu_ps( C+(k+p)+(j+l+3)*n, _mm_add_ps( temp_c, buff_c ) );	

							temp_c = _mm_loadu_ps(C+(k+4+p)+(j+l+3)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+4+(l+3)*64);
							_mm_storeu_ps( C+(k+4+p)+(j+l+3)*n, _mm_add_ps( temp_c, buff_c ) );	
						}

					}	

					for (l = 32; l < 64; l+=4)
					{
						for (k = 0; k < 64; k+=8)
						{
							/// l = 0
							temp_c = _mm_loadu_ps(C+(k+p)+(j+l)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+l*64);
							_mm_storeu_ps( C+(k+p)+(j+l)*n, _mm_add_ps( temp_c, buff_c ) );	

							temp_c = _mm_loadu_ps(C+(k+4+p)+(j+l)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+4+l*64);
							_mm_storeu_ps( C+(k+4+p)+(j+l)*n, _mm_add_ps( temp_c, buff_c ) );	
		
							/// l = 1
							temp_c = _mm_loadu_ps(C+(k+p)+(j+l+1)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+(l+1)*64);
							_mm_storeu_ps( C+(k+p)+(j+l+1)*n, _mm_add_ps( temp_c, buff_c ) );	

							temp_c = _mm_loadu_ps(C+(k+4+p)+(j+l+1)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+4+(l+1)*64);
							_mm_storeu_ps( C+(k+4+p)+(j+l+1)*n, _mm_add_ps( temp_c, buff_c ) );	

							/// l = 2
							temp_c = _mm_loadu_ps(C+(k+p)+(j+l+2)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+(l+2)*64);
							_mm_storeu_ps( C+(k+p)+(j+l+2)*n, _mm_add_ps( temp_c, buff_c ) );	

							temp_c = _mm_loadu_ps(C+(k+4+p)+(j+l+2)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+4+(l+2)*64);
							_mm_storeu_ps( C+(k+4+p)+(j+l+2)*n, _mm_add_ps( temp_c, buff_c ) );	

							///l = 3
							temp_c = _mm_loadu_ps(C+(k+p)+(j+l+3)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+(l+3)*64);
							_mm_storeu_ps( C+(k+p)+(j+l+3)*n, _mm_add_ps( temp_c, buff_c ) );	

							temp_c = _mm_loadu_ps(C+(k+4+p)+(j+l+3)*n);
							buff_c = _mm_loadu_ps(C_64_64+k+4+(l+3)*64);
							_mm_storeu_ps( C+(k+4+p)+(j+l+3)*n, _mm_add_ps( temp_c, buff_c ) );	
						}

					}	

				} // end of 3rd level loop

			} // end of 2nd level loop

		} // end of 1st level loop

	} // end of fringe == 0

	else if (fringe == 32)
	{
		float A_64_n[64*n];
		float B_n_64[n*64];
		float C_64_64[64*64];

		for (j = 0; j < bound; j+=64)
		{
			// copy to B_n_64, fringe of C on right edge
			for (l = 0; l < 64; l+=4)
			{
				memcpy(B_n_64+l*n, B+(j+l)*n, n*sizeof(float));
				memcpy(B_n_64+(l+1)*n, B+(j+l+1)*n, n*sizeof(float));
				memcpy(B_n_64+(l+2)*n, B+(j+l+2)*n, n*sizeof(float));
				memcpy(B_n_64+(l+3)*n, B+(j+l+3)*n, n*sizeof(float));
			}

			for (i = 0; i < bound; i+=64)
			{
				
				// copy to A_64_n, fringe of C bottom edge
				for (l = 0; l < n; l+=4)
				{
					memcpy(A_64_n+l*64, A+i+l*n, 64*sizeof(float));
					memcpy(A_64_n+(l+1)*64, A+i+(l+1)*n, 64*sizeof(float));
					memcpy(A_64_n+(l+2)*64, A+i+(l+2)*n, 64*sizeof(float));
					memcpy(A_64_n+(l+3)*64, A+i+(l+3)*n, 64*sizeof(float));
				}
				
				// do multiplication
				square_sgemm64xn(bound, n, A_64_n, B_n_64, C_64_64);

				
				// copy result back to C
				for (l = 0; l < 64; l+=4)
				{
					memcpy(C+i+(l+j)*n, C_64_64+l*64, 64*sizeof(float));
					memcpy(C+i+(l+1+j)*n, C_64_64+(l+1)*64, 64*sizeof(float));
					memcpy(C+i+(l+2+j)*n, C_64_64+(l+2)*64, 64*sizeof(float));
					memcpy(C+i+(l+3+j)*n, C_64_64+(l+3)*64, 64*sizeof(float));
				}
				
			} // for i
		} // for j 

		float A_32_n[32*n], B_n_32[n*32], C_32_32[32*32];
		// copy to A_32_n, fringe of C bottom edge
		for (l = 0; l < n; l+=4)
		{
			memcpy(A_32_n+l*32, A+bound+l*n, 32*sizeof(float));
			memcpy(A_32_n+(l+1)*32, A+bound+(l+1)*n, 32*sizeof(float));
			memcpy(A_32_n+(l+2)*32, A+bound+(l+2)*n, 32*sizeof(float));
			memcpy(A_32_n+(l+3)*32, A+bound+(l+3)*n, 32*sizeof(float));
		}

		for (j = 0; j < n; j+=32)
		{
			// copy to B_n_32, fringe of C on right edge
			for (l = 0; l < 32; l+=4)
			{
				memcpy(B_n_32+l*n, B+(j+l)*n, n*sizeof(float));
				memcpy(B_n_32+(l+1)*n, B+(j+l+1)*n, n*sizeof(float));
				memcpy(B_n_32+(l+2)*n, B+(j+l+2)*n, n*sizeof(float));
				memcpy(B_n_32+(l+3)*n, B+(j+l+3)*n, n*sizeof(float));
			}
				
			// do multiplication
			square_sgemm32xn(n, A_32_n, B_n_32, C_32_32);

			
			// copy result back to C
			for (l = 0; l < 32; l+=4)
			{
				memcpy(C+bound+(l+j)*n, C_32_32+l*32, 32*sizeof(float));
				memcpy(C+bound+(l+1+j)*n, C_32_32+(l+1)*32, 32*sizeof(float));
				memcpy(C+bound+(l+2+j)*n, C_32_32+(l+2)*32, 32*sizeof(float));
				memcpy(C+bound+(l+3+j)*n, C_32_32+(l+3)*32, 32*sizeof(float));
			}
			
		} // for j


		for (i = 0; i < bound; i+=32)
		{
			// copy to A_32_n, fringe of C bottom edge
			for (l = 0; l < n; l+=4)
			{
				memcpy(A_32_n+l*32, A+i+l*n, 32*sizeof(float));
				memcpy(A_32_n+(l+1)*32, A+i+(l+1)*n, 32*sizeof(float));
				memcpy(A_32_n+(l+2)*32, A+i+(l+2)*n, 32*sizeof(float));
				memcpy(A_32_n+(l+3)*32, A+i+(l+3)*n, 32*sizeof(float));
			}
			
			// do multiplication
			square_sgemm32xn(n, A_32_n, B_n_32, C_32_32);

			
			// copy result back to C
			for (l = 0; l < 32; l+=4)
			{
				memcpy(C+i+(l+bound)*n, C_32_32+l*32, 32*sizeof(float));
				memcpy(C+i+(l+1+bound)*n, C_32_32+(l+1)*32, 32*sizeof(float));
				memcpy(C+i+(l+2+bound)*n, C_32_32+(l+2)*32, 32*sizeof(float));
				memcpy(C+i+(l+3+bound)*n, C_32_32+(l+3)*32, 32*sizeof(float));
			}
			
		} // for i


	} // end of fringe == 32

	else if (fringe == 33)
	{
		float A_64_n[64*n];
		float B_n_64[n*64];
		float C_64_64[64*64];
		for (j = 0; j < bound; j+=64)
		{
			// copy to B_n_64, fringe of C on right edge
			for (l = 0; l < 64; l+=4)
			{
				memcpy(B_n_64+l*n, B+(j+l)*n, n*sizeof(float));
				memcpy(B_n_64+(l+1)*n, B+(j+l+1)*n, n*sizeof(float));
				memcpy(B_n_64+(l+2)*n, B+(j+l+2)*n, n*sizeof(float));
				memcpy(B_n_64+(l+3)*n, B+(j+l+3)*n, n*sizeof(float));
			}

			for (i = 0; i < bound; i+=64)
			{
				
				// copy to A_64_n, fringe of C bottom edge
				for (l = 0; l < n; l++)
					memcpy(A_64_n+l*64, A+i+l*n, 64*sizeof(float));
			
	
				// do multiplication
				square_sgemm64xn(bound, n, A_64_n, B_n_64, C_64_64);

				
				// copy result back to C
				for (l = 0; l < 64; l++)
				{
					memcpy(C+i+(l+j)*n, C_64_64+l*64, 64*sizeof(float));
					memcpy(C+i+(l+1+j)*n, C_64_64+(l+1)*64, 64*sizeof(float));
					memcpy(C+i+(l+2+j)*n, C_64_64+(l+2)*64, 64*sizeof(float));
					memcpy(C+i+(l+3+j)*n, C_64_64+(l+3)*64, 64*sizeof(float));
				}
				
			} // for i
		} // for j

		int next_bound = bound + 32;
		float A_32_n[32*n], B_n_32[n*32], C_32_32[32*32];
		// copy to A_32_n, fringe of C bottom edge
		for (l = 0; l < n; l++)
			memcpy(A_32_n+l*32, A+bound+l*n, 32*sizeof(float));

		for (j = 0; j < next_bound; j+=32)
		{
			// copy to B_n_32, fringe of C on right edge
			for (l = 0; l < 32; l+=4)
			{
				memcpy(B_n_32+l*n, B+(j+l)*n, n*sizeof(float));
				memcpy(B_n_32+(l+1)*n, B+(j+l+1)*n, n*sizeof(float));
				memcpy(B_n_32+(l+2)*n, B+(j+l+2)*n, n*sizeof(float));
				memcpy(B_n_32+(l+3)*n, B+(j+l+3)*n, n*sizeof(float));
			}
				
			// do multiplication
			square_sgemm32xn(n, A_32_n, B_n_32, C_32_32);

			
			// copy result back to C
			for (l = 0; l < 32; l+=4)
			{
				memcpy(C+bound+(l+j)*n, C_32_32+l*32, 32*sizeof(float));
				memcpy(C+bound+(l+1+j)*n, C_32_32+(l+1)*32, 32*sizeof(float));
				memcpy(C+bound+(l+2+j)*n, C_32_32+(l+2)*32, 32*sizeof(float));
				memcpy(C+bound+(l+3+j)*n, C_32_32+(l+3)*32, 32*sizeof(float));
			}
			
		} // for j


		for (i = 0; i < bound; i+=32)
		{
			
			// copy to A_32_n, fringe of C bottom edge
			for (l = 0; l < n; l++)
				memcpy(A_32_n+l*32, A+i+l*n, 32*sizeof(float));
			
			// do multiplication
			square_sgemm32xn(n, A_32_n, B_n_32, C_32_32);

			
			// copy result back to C
			for (l = 0; l < 32; l+=4)
			{
				memcpy(C+i+(l+bound)*n, C_32_32+l*32, 32*sizeof(float));
				memcpy(C+i+(l+1+bound)*n, C_32_32+(l+1)*32, 32*sizeof(float));
				memcpy(C+i+(l+2+bound)*n, C_32_32+(l+2)*32, 32*sizeof(float));
				memcpy(C+i+(l+3+bound)*n, C_32_32+(l+3)*32, 32*sizeof(float));
			}
			
		} // for i

		__m128 a, b, c0, c1, c2, c3;	
		// right fringe
		c0 = _mm_setzero_ps();
		c1 = _mm_setzero_ps();
		c2 = _mm_setzero_ps();
		c3 = _mm_setzero_ps();
		for (j = next_bound; j < n; j++)
		{
			for (i = 0; i < next_bound; i+=16)
			{
				for (k = 0; k < n; k++)
				{
					b = _mm_load1_ps( B+j*n+k );
					a = _mm_loadu_ps( A+k*n+i );
					c0 = _mm_add_ps( c0, _mm_mul_ps( a, b ) );
					a = _mm_loadu_ps( A+k*n+i+4 );
					c1 = _mm_add_ps (c1, _mm_mul_ps( a, b ) );
					a = _mm_loadu_ps( A+k*n+i+8 );
					c2 = _mm_add_ps (c2, _mm_mul_ps( a, b ) );
					a = _mm_loadu_ps( A+k*n+i+12 );
					c3 = _mm_add_ps (c3, _mm_mul_ps( a, b ) );
				}
				_mm_storeu_ps( C+j*n+i, c0 );
				_mm_storeu_ps( C+j*n+i+4, c1 );
				_mm_storeu_ps( C+j*n+i+8, c2 );
				_mm_storeu_ps( C+j*n+i+12, c3 );
				c0 = _mm_setzero_ps();
				c1 = _mm_setzero_ps();
				c2 = _mm_setzero_ps();
				c3 = _mm_setzero_ps();
			}
		}

		// bottom fringe
		i = next_bound;
			for (j = 0; j < n; j++)
				for (k = 0; k < n; k++)
					C[i+j*n] += A[i+k*n] * B[k+j*n];


	}  // end of fringe == 33

	
	else if (fringe == 31) // fringe == 31
	{
		float A_64_n[64*n];
		float B_n_64[n*64];
		float C_64_64[64*64];
		int multiple4 = n / 4 * 4;
		for (j = 0; j < bound; j+=64)
		{
			// copy to B_n_64, fringe of C on right edge
			for (l = 0; l < 64; l++)
				memcpy(B_n_64+l*n, B+(j+l)*n, n*sizeof(float));

			for (i = 0; i < bound; i+=64)
			{
				
				// copy to A_64_n, fringe of C bottom edge
				for (l = 0; l < n; l++)
					memcpy(A_64_n+l*64, A+i+l*n, 64*sizeof(float));
				
				// do multiplication
				square_sgemm64xn(bound, n, A_64_n, B_n_64, C_64_64);

				
				// copy result back to C
				for (l = 0; l < 64; l=l++)
					memcpy(C+i+(l+j)*n, C_64_64+l*64, 64*sizeof(float));
				
			} // for i
		} // for j


		// bottom fringe
		float A_32_n[32*n], B_n_32[n*32], C_32_32[32*32];
		// copy to A_32_n, fringe of C bottom edge
		for (l = 0; l < n; l++)
		{
			memcpy(A_32_n+l*32, A+bound+l*n, 31*sizeof(float)); 
			*(A_32_n+31+l*32) = 0.0;
		}

		for (j = 0; j < n; j+=32)
		{
			// copy to B_n_32, fringe of C on right edge
			if (j != bound)
			{
				for (l = 0; l < 32; l++)
					memcpy(B_n_32+l*n, B+(j+l)*n, n*sizeof(float));
			}
			else // j == bound
			{
				for (l = 0; l < 31; l++)
					memcpy(B_n_32+l*n, B+(j+l)*n, n*sizeof(float));
				memset(B_n_32+31*n, 0.0, n*sizeof(float)); 
			}
				
			// do multiplication
			square_sgemm32xn(n, A_32_n, B_n_32, C_32_32);

			
			// copy result back to C
			if (j != bound) 
			{
				for (l = 0; l < 32; l++)
					memcpy(C+bound+(l+j)*n, C_32_32+l*32, 31*sizeof(float)); 
			}
			else // j == bound
			{
				for (l = 0; l < 31; l++)
					memcpy(C+bound+(l+j)*n, C_32_32+l*32, 31*sizeof(float)); 
			}
			
		} // for j


		// right fringe
		for (i = 0; i < bound; i+=32)
		{
			
			// copy to A_32_n, fringe of C bottom edge
			for (l = 0; l < n; l++)
				memcpy(A_32_n+l*32, A+i+l*n, 32*sizeof(float));
			
			// do multiplication
			square_sgemm32xn(n, A_32_n, B_n_32, C_32_32);

			
			// copy result back to C
			for (l = 0; l < 31; l++)
				memcpy(C+i+(l+bound)*n, C_32_32+l*32, 32*sizeof(float));
			
		} // for i


	} // end of fringe == 31 

	else if (fringe != 1) // fringe == 63
	{
		// 1st level loop
		for (j = 0; j < n; j=j+64)
		{
			// 2nd level loop
			for (i = 0; i < n; i=i+64)
			{
				// load into B_64_64
				if (i != bound && j != bound)
				{
					for (l = 0; l < 64; l+=4)
					{
						memcpy(B_64_64+l*64, B+i+(j+l)*n, size_floatx64);
						memcpy(B_64_64+(l+1)*64, B+i+(j+l+1)*n, size_floatx64);
						memcpy(B_64_64+(l+2)*64, B+i+(j+l+2)*n, size_floatx64);
						memcpy(B_64_64+(l+3)*64, B+i+(j+l+3)*n, size_floatx64);
					}	
				}
				else if (j != bound) // i == bound
				{

					for (l = 0; l < 64; l+=4)
					{
						memcpy(B_64_64+l*64, B+i+(j+l)*n, size_floatxfringe);
						memset(B_64_64+fringe+l*64, 0.0, size_floatxpad);
						memcpy(B_64_64+(l+1)*64, B+i+(j+l+1)*n, size_floatxfringe);
						memset(B_64_64+fringe+(l+1)*64, 0.0, size_floatxpad);
						memcpy(B_64_64+(l+2)*64, B+i+(j+l+2)*n, size_floatxfringe);
						memset(B_64_64+fringe+(l+2)*64, 0.0, size_floatxpad);
						memcpy(B_64_64+(l+3)*64, B+i+(j+l+3)*n, size_floatxfringe);
						memset(B_64_64+fringe+(l+3)*64, 0.0, size_floatxpad);
					}


				}
				else if (i != bound) // j == bound
				{	
					for (l = 0; l < fringe; l++)
						memcpy(B_64_64+l*64, B+i+(j+l)*n, size_floatx64);
					memset(B_64_64+l*64, 0.0, size_floatx64);

				}
				else  // i == j == bound
				{
					for (l = 0; l < fringe; l++)
					{
						memcpy(B_64_64+l*64, B+i+(j+l)*n, size_floatxfringe);
						memset(B_64_64+fringe+l*64, 0.0, size_floatxpad);
					}
					memset(B_64_64+l*64, 0.0, pad*size_floatx64);
				}

				// 3rd level loop
				for (p = 0; p < n; p=p+64)
				{
					if (p != bound && i != bound)
					{
						// load into A_64_64
						for (l = 0; l < 64; l+=4)
						{
							memcpy(A_64_64+(l)*64, A+p+(i+l)*n, size_floatx64);
							memcpy(A_64_64+(l+1)*64, A+p+(i+l+1)*n, size_floatx64);
							memcpy(A_64_64+(l+2)*64, A+p+(i+l+2)*n, size_floatx64);
							memcpy(A_64_64+(l+3)*64, A+p+(i+l+3)*n, size_floatx64);
						}
					}
					else if (i != bound) // p == bound
					{
						for (l = 0; l < 64; l+=4)
						{
							memcpy(A_64_64+(l)*64, A+p+(i+l)*n, size_floatxfringe);
							memcpy(A_64_64+(l+1)*64, A+p+(i+l+1)*n, size_floatxfringe);
							memcpy(A_64_64+(l+2)*64, A+p+(i+l+2)*n, size_floatxfringe);
							memcpy(A_64_64+(l+3)*64, A+p+(i+l+3)*n, size_floatxfringe);
						}
					}
					else if (p != bound) // i == bound
					{
						for (l = 0; l < fringe; l++)
							memcpy(A_64_64+l*64, A+p+(i+l)*n, size_floatx64);
					}
					else // i == p == bound
					{
						for (l = 0; l < fringe; l++)
							memcpy(A_64_64+l*64, A+p+(i+l)*n, size_floatxfringe);
					}

					// do the multiplication to get C_64_64
					square_sgemm64x64(64, A_64_64, B_64_64, C_64_64);

					// store back into C
					if (p != bound && j != bound)
					{
						__m128 temp_c, buff_c;
						for (l = 0; l < 64; l+=4)
						{
							for (k = 0; k < 64; k+=8)
							{
								/// l = 0
								temp_c = _mm_loadu_ps(C+(k+p)+(j+l)*n);
								buff_c = _mm_loadu_ps(C_64_64+k+l*64);
								_mm_storeu_ps( C+(k+p)+(j+l)*n, _mm_add_ps( temp_c, buff_c ) );	

								temp_c = _mm_loadu_ps(C+(k+4+p)+(j+l)*n);
								buff_c = _mm_loadu_ps(C_64_64+k+4+l*64);
								_mm_storeu_ps( C+(k+4+p)+(j+l)*n, _mm_add_ps( temp_c, buff_c ) );	
			
								/// l = 1
								temp_c = _mm_loadu_ps(C+(k+p)+(j+l+1)*n);
								buff_c = _mm_loadu_ps(C_64_64+k+(l+1)*64);
								_mm_storeu_ps( C+(k+p)+(j+l+1)*n, _mm_add_ps( temp_c, buff_c ) );	

								temp_c = _mm_loadu_ps(C+(k+4+p)+(j+l+1)*n);
								buff_c = _mm_loadu_ps(C_64_64+k+4+(l+1)*64);
								_mm_storeu_ps( C+(k+4+p)+(j+l+1)*n, _mm_add_ps( temp_c, buff_c ) );	

								/// l = 2
								temp_c = _mm_loadu_ps(C+(k+p)+(j+l+2)*n);
								buff_c = _mm_loadu_ps(C_64_64+k+(l+2)*64);
								_mm_storeu_ps( C+(k+p)+(j+l+2)*n, _mm_add_ps( temp_c, buff_c ) );	

								temp_c = _mm_loadu_ps(C+(k+4+p)+(j+l+2)*n);
								buff_c = _mm_loadu_ps(C_64_64+k+4+(l+2)*64);
								_mm_storeu_ps( C+(k+4+p)+(j+l+2)*n, _mm_add_ps( temp_c, buff_c ) );	

								///l = 3
								temp_c = _mm_loadu_ps(C+(k+p)+(j+l+3)*n);
								buff_c = _mm_loadu_ps(C_64_64+k+(l+3)*64);
								_mm_storeu_ps( C+(k+p)+(j+l+3)*n, _mm_add_ps( temp_c, buff_c ) );	

								temp_c = _mm_loadu_ps(C+(k+4+p)+(j+l+3)*n);
								buff_c = _mm_loadu_ps(C_64_64+k+4+(l+3)*64);
								_mm_storeu_ps( C+(k+4+p)+(j+l+3)*n, _mm_add_ps( temp_c, buff_c ) );	
							}
						}

					}
					else if (j != bound) // p == bound
					{
						for (l = 0; l < 64; l+=4)
						{
							for (k = 0; k < fringe; k++)
							{
								*(C+(k+p)+(j+l)*n) += *(C_64_64+k+l*64);
								*(C+(k+p)+(j+l+1)*n) += *(C_64_64+k+(l+1)*64);
								*(C+(k+p)+(j+l+2)*n) += *(C_64_64+k+(l+2)*64);
								*(C+(k+p)+(j+l+3)*n) += *(C_64_64+k+(l+3)*64);
							}
						}

					}
					else if (p != bound) // j == bound
					{
						__m128 temp_c, buff_c;
						for (l = 0; l < fringe; l++)
						{
							for (k = 0; k < 64; k+=16)
							{
								temp_c = _mm_loadu_ps(C+(k+p)+(j+l)*n);
								buff_c = _mm_loadu_ps(C_64_64+k+l*64);
								_mm_storeu_ps( C+(k+p)+(j+l)*n, _mm_add_ps( temp_c, buff_c ) );	

								temp_c = _mm_loadu_ps(C+(k+4+p)+(j+l)*n);
								buff_c = _mm_loadu_ps(C_64_64+k+4+l*64);
								_mm_storeu_ps( C+(k+4+p)+(j+l)*n, _mm_add_ps( temp_c, buff_c ) );	

								temp_c = _mm_loadu_ps(C+(k+8+p)+(j+l)*n);
								buff_c = _mm_loadu_ps(C_64_64+k+8+l*64);
								_mm_storeu_ps( C+(k+8+p)+(j+l)*n, _mm_add_ps( temp_c, buff_c ) );	

								temp_c = _mm_loadu_ps(C+(k+12+p)+(j+l)*n);
								buff_c = _mm_loadu_ps(C_64_64+k+12+l*64);
								_mm_storeu_ps( C+(k+12+p)+(j+l)*n, _mm_add_ps( temp_c, buff_c ) );	

							}
						}

					}
					else  // p == j == bound
					{
						for (l = 0; l < fringe; l++)
							for (k = 0; k < fringe; k++)
								*(C+(k+p)+(j+l)*n) += *(C_64_64+k+l*64);

					}

			
				} // end of 3rd level loop

			} // end of 2nd level loop

		} // end of 1st level loop

	}
		
	else  // fringe == 1
	{
		float A_64_n[64*n];
		float B_n_64[n*64];
		float C_64_64[64*64];
		for (j = 0; j < bound; j+=64)
		{
			// copy to B_n_64, fringe of C on right edge
			for (l = 0; l < 64; l+=4)
			{
				memcpy(B_n_64+l*n, B+(j+l)*n, n*sizeof(float));
				memcpy(B_n_64+(l+1)*n, B+(j+l+1)*n, n*sizeof(float));
				memcpy(B_n_64+(l+2)*n, B+(j+l+2)*n, n*sizeof(float));
				memcpy(B_n_64+(l+3)*n, B+(j+l+3)*n, n*sizeof(float));
			}
				
			for (i = 0; i < bound; i+=64)
			{
				
				// copy to A_64_n, fringe of C bottom edge
				for (l = 0; l < n; l++)
					memcpy(A_64_n+l*64, A+i+l*n, 64*sizeof(float));
				
				// do multiplication
				square_sgemm64xn(bound, n, A_64_n, B_n_64, C_64_64);

				
				// copy result back to C
				for (l = 0; l < 64; l+=4)
				{
					memcpy(C+i+(l+j)*n, C_64_64+l*64, 64*sizeof(float));
					memcpy(C+i+(l+1+j)*n, C_64_64+(l+1)*64, 64*sizeof(float));
					memcpy(C+i+(l+2+j)*n, C_64_64+(l+2)*64, 64*sizeof(float));
					memcpy(C+i+(l+3+j)*n, C_64_64+(l+3)*64, 64*sizeof(float));
				}
				
			} // for i
		} // for j

		__m128 a, b, c0, c1, c2, c3;	
		// right fringe
		c0 = _mm_setzero_ps();
		c1 = _mm_setzero_ps();
		c2 = _mm_setzero_ps();
		c3 = _mm_setzero_ps();
		for (j = bound; j < n; j++)
		{
			for (i = 0; i < bound; i+=16)
			{
				for (k = 0; k < n; k++)
				{
					b = _mm_load1_ps( B+j*n+k );
					a = _mm_loadu_ps( A+k*n+i );
					c0 = _mm_add_ps( c0, _mm_mul_ps( a, b ) );
					a = _mm_loadu_ps( A+k*n+i+4 );
					c1 = _mm_add_ps (c1, _mm_mul_ps( a, b ) );
					a = _mm_loadu_ps( A+k*n+i+8 );
					c2 = _mm_add_ps (c2, _mm_mul_ps( a, b ) );
					a = _mm_loadu_ps( A+k*n+i+12 );
					c3 = _mm_add_ps (c3, _mm_mul_ps( a, b ) );
				}
				_mm_storeu_ps( C+j*n+i, c0 );
				_mm_storeu_ps( C+j*n+i+4, c1 );
				_mm_storeu_ps( C+j*n+i+8, c2 );
				_mm_storeu_ps( C+j*n+i+12, c3 );
				c0 = _mm_setzero_ps();
				c1 = _mm_setzero_ps();
				c2 = _mm_setzero_ps();
				c3 = _mm_setzero_ps();
			}
		}

		// bottom fringe
		i = bound;
			for (j = 0; j < n; j++)
				for (k = 0; k < n; k++)
					C[i+j*n] += A[i+k*n] * B[k+j*n];


	} // end of fringe ==1 
	


	free ( A_64_64 );
	free ( B_64_64 );
	free ( C_64_64 );


}
