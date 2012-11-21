/*  Name: Khanh Dao
	Login: cs61c-ef
    Partner name: Phuc-Hai Huynh
	Login: cs61c-cd
*/ 

#include <stdio.h>
#include <emmintrin.h> 

void square_sgemm (int n, float* A, float* B, float* C)
{
	int i, j, k;
	if (n == 64)
	{
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
			for (k = 0; k < n; k=k+4)
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
	} // end of if (n == 64)
	else 
	{
 		int bound = n / 4 * 4;
		int chunks = n / 4;
		__m128 c[chunks], b, a;

		for (j = 0; j < n; j++)
		{
			for (i = 0; i < chunks; i++) 
			{
				c[i] = _mm_setzero_ps();
			}

			for (k = 0; k < n; k++) 
			{
				b = _mm_load1_ps(B+k+j*n);
				for (i = 0; i < chunks; i++)
				{
					a = _mm_loadu_ps(A+i*4+k*n);
					c[i] = _mm_add_ps( c[i], _mm_mul_ps( a, b ) );
				}
			}
			
			for (i = 0; i < chunks; i++)
			{
				_mm_storeu_ps( C+i*4+j*n, c[i] );
			}
		}
		
		for (i = bound; i < n; i++)
			for (j = 0; j < n; j++)
			{
				for (k = 0; k < n; k++)
					C[i+j*n] += A[i+k*n] * B[k+j*n];
			}
  }
}

