/*  Name: Khanh Dao
	Login: cs61c-ef
    Partner name: Phuc-Hai Huynh
	Login: cs61c-cd
*/ 

#include <stdio.h>
#include <string.h>
#include <omp.h>
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


void square_sgemm(int n, float* A, float* B, float* C)
{
	register const int bound = n / 64 * 64;
	int fringe = n - bound;
	int pad = 64 - fringe;
	const int size_floatx64 = 64*sizeof(float);
	const int size_floatxfringe = fringe*sizeof(float);
	const int size_floatxpad = pad*sizeof(float);/*
	float* A_64_64 = calloc(64*64, sizeof(float));
	float* B_64_64 = calloc(64*64, sizeof(float));
	float* C_64_64 = calloc(64*64, sizeof(float));*/
	int i, j, k, l, p;
	//omp_set_num_threads(16);
	// make sure C is zeroed out
	memset(C, 0.0, n*n*sizeof(float));
	

		omp_set_num_threads(n/64);
		# pragma omp parallel private (j, i, k, l, p)
		{
			float* A_64_64 = calloc(64*64, sizeof(float));
			float* B_64_64 = calloc(64*64, sizeof(float));
			float* C_64_64 = calloc(64*64, sizeof(float));
			# pragma omp for schedule(dynamic) nowait
			// 1st level loop
			for (j = 0; j < n; j=j+64)
			{
				// 2nd level loop
				for (i = 0; i < n; i=i+64)
				{
					// load into B_64_64
					if (i != bound && j != bound)
					{
						for (l = 0; l < 64; l++)
						{
							memcpy(B_64_64+l*64, B+i+(j+l)*n, size_floatx64);
						}	
					}
					else if (j != bound) // i == bound
					{

						for (l = 0; l < 64; l++)
						{
							memcpy(B_64_64+l*64, B+i+(j+l)*n, size_floatxfringe);
							memset(B_64_64+fringe+l*64, 0.0, size_floatxpad);
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
							for (l = 0; l < 64; l++)
							{
								memcpy(A_64_64+(l)*64, A+p+(i+l)*n, size_floatx64);
							}
						}
						else if (i != bound) // p == bound
						{
							for (l = 0; l < 64; l++)
							{
								memcpy(A_64_64+(l)*64, A+p+(i+l)*n, size_floatxfringe);
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
							for (l = 0; l < 64; l++)
							{
								for (k = 0; k < 64; k+=4)
								{
									/// l = 0
									temp_c = _mm_loadu_ps(C+(k+p)+(j+l)*n);
									buff_c = _mm_loadu_ps(C_64_64+k+l*64);
									_mm_storeu_ps( C+(k+p)+(j+l)*n, _mm_add_ps( temp_c, buff_c ) );	
								}
							}

						}
						else if (j != bound) // p == bound
						{
							for (l = 0; l < 64; l++)
							{
								for (k = 0; k < fringe; k++)
								{
									*(C+(k+p)+(j+l)*n) += *(C_64_64+k+l*64);
								}
							}

						}
						else if (p != bound) // j == bound
						{
							__m128 temp_c, buff_c;
							for (l = 0; l < fringe; l++)
							{
								for (k = 0; k < 64; k+=4)
								{
									temp_c = _mm_loadu_ps(C+(k+p)+(j+l)*n);
									buff_c = _mm_loadu_ps(C_64_64+k+l*64);
									_mm_storeu_ps( C+(k+p)+(j+l)*n, _mm_add_ps( temp_c, buff_c ) );	

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
			free ( A_64_64 );
			free ( B_64_64 );
			free ( C_64_64 );

		} // prgama parallel


}
