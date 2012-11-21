	.file	"sgemm-small.c"
	.text
	.p2align 4,,15
	.globl	square_sgemm
	.type	square_sgemm, @function
square_sgemm:
.LFB531:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	pushq	%r15
	pushq	%r14
	pushq	%r13
	pushq	%r12
	pushq	%rbx
	subq	$1208, %rsp
	movq	%rsi, -1224(%rbp)
	movq	%rdx, -1232(%rbp)
	movq	%fs:40, %rax
	movq	%rax, -56(%rbp)
	xorl	%eax, %eax
	cmpl	$64, %edi
	movq	%rcx, -1240(%rbp)
	jne	.L2
	.cfi_offset 3, -56
	.cfi_offset 12, -48
	.cfi_offset 13, -40
	.cfi_offset 14, -32
	.cfi_offset 15, -24
	xorps	%xmm0, %xmm0
	.p2align 4,,10
	.p2align 3
.L3:
	movaps	%xmm0, -1088(%rbp,%rax)
	movaps	%xmm0, -832(%rbp,%rax)
	movaps	%xmm0, -576(%rbp,%rax)
	movaps	%xmm0, -320(%rbp,%rax)
	addq	$16, %rax
	cmpq	$256, %rax
	jne	.L3
	movq	-1240(%rbp), %rdi
	movq	-1232(%rbp), %rdx
	movq	-1240(%rbp), %rax
	addq	$8192, %rdi
	movq	%rdx, -1216(%rbp)
	movq	%rdi, -1208(%rbp)
	movq	%rax, -1200(%rbp)
.L4:
	movq	-1216(%rbp), %r15
	movq	-1216(%rbp), %r14
	movq	-1216(%rbp), %r13
	movq	-1224(%rbp), %r12
	movq	-1216(%rbp), %rbx
	movl	$16, -1188(%rbp)
	addq	$256, %r15
	addq	$512, %r14
	addq	$768, %r13
	.p2align 4,,10
	.p2align 3
.L6:
	movss	8(%r14), %xmm0
	leaq	768(%r12), %r10
	movaps	%xmm0, %xmm1
	leaq	512(%r12), %r9
	movss	12(%r14), %xmm0
	leaq	256(%r12), %r8
	shufps	$0, %xmm1, %xmm1
	leaq	-1088(%rbp), %rsi
	movss	(%rbx), %xmm13
	leaq	-832(%rbp), %rcx
	movss	4(%rbx), %xmm12
	leaq	-576(%rbp), %rdx
	movaps	%xmm1, -1104(%rbp)
	movaps	%xmm0, %xmm1
	leaq	-320(%rbp), %rax
	shufps	$0, %xmm13, %xmm13
	movq	%r12, %rdi
	shufps	$0, %xmm1, %xmm1
	movl	$2, %r11d
	movss	0(%r13), %xmm0
	movss	8(%rbx), %xmm11
	shufps	$0, %xmm12, %xmm12
	movaps	%xmm1, -1120(%rbp)
	movaps	%xmm0, %xmm1
	shufps	$0, %xmm11, %xmm11
	shufps	$0, %xmm1, %xmm1
	movss	4(%r13), %xmm0
	movss	12(%rbx), %xmm10
	movaps	%xmm1, -1136(%rbp)
	movaps	%xmm0, %xmm1
	shufps	$0, %xmm10, %xmm10
	shufps	$0, %xmm1, %xmm1
	movss	8(%r13), %xmm0
	movss	(%r15), %xmm9
	movaps	%xmm1, -1152(%rbp)
	movaps	%xmm0, %xmm1
	shufps	$0, %xmm9, %xmm9
	shufps	$0, %xmm1, %xmm1
	movss	12(%r13), %xmm0
	movss	4(%r15), %xmm8
	movaps	%xmm1, -1168(%rbp)
	movaps	%xmm0, %xmm1
	shufps	$0, %xmm8, %xmm8
	shufps	$0, %xmm1, %xmm1
	movss	8(%r15), %xmm7
	movss	12(%r15), %xmm6
	shufps	$0, %xmm7, %xmm7
	movss	(%r14), %xmm5
	movss	4(%r14), %xmm4
	shufps	$0, %xmm6, %xmm6
	shufps	$0, %xmm5, %xmm5
	shufps	$0, %xmm4, %xmm4
	movaps	%xmm1, -1184(%rbp)
.L5:
	movups	(%r10), %xmm0
	movaps	-1184(%rbp), %xmm3
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm3
	movups	(%r9), %xmm14
	movaps	-1120(%rbp), %xmm2
	mulps	%xmm6, %xmm1
	movaps	-1168(%rbp), %xmm15
	addps	(%rax), %xmm3
	mulps	%xmm0, %xmm2
	mulps	%xmm14, %xmm15
	addps	(%rcx), %xmm1
	mulps	%xmm10, %xmm0
	addps	(%rdx), %xmm2
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	(%r8), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	(%rdi), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	-1168(%rbp), %xmm15
	movaps	%xmm3, (%rax)
	movaps	%xmm14, %xmm3
	mulps	%xmm5, %xmm3
	addps	%xmm3, %xmm2
	movaps	-1184(%rbp), %xmm3
	movaps	%xmm2, (%rdx)
	movaps	%xmm14, %xmm2
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	addps	%xmm14, %xmm0
	addps	%xmm2, %xmm1
	movaps	-1120(%rbp), %xmm2
	movaps	%xmm0, (%rsi)
	movaps	%xmm1, (%rcx)
	movups	16(%r10), %xmm0
	mulps	%xmm0, %xmm3
	movups	16(%r9), %xmm14
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm2
	mulps	%xmm14, %xmm15
	addps	16(%rax), %xmm3
	mulps	%xmm6, %xmm1
	addps	16(%rdx), %xmm2
	mulps	%xmm10, %xmm0
	addps	16(%rcx), %xmm1
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	16(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	16(%r8), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	16(%rdi), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm3, 16(%rax)
	movaps	%xmm14, %xmm3
	mulps	%xmm5, %xmm3
	addps	%xmm3, %xmm2
	movaps	%xmm2, 16(%rdx)
	movaps	%xmm14, %xmm2
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	movaps	-1184(%rbp), %xmm3
	addps	%xmm14, %xmm0
	movaps	-1168(%rbp), %xmm15
	addps	%xmm2, %xmm1
	movaps	-1120(%rbp), %xmm2
	movaps	%xmm0, 16(%rsi)
	movaps	%xmm1, 16(%rcx)
	movups	32(%r10), %xmm0
	mulps	%xmm0, %xmm3
	movups	32(%r9), %xmm14
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm2
	mulps	%xmm14, %xmm15
	addps	32(%rax), %xmm3
	mulps	%xmm6, %xmm1
	addps	32(%rdx), %xmm2
	mulps	%xmm10, %xmm0
	addps	32(%rcx), %xmm1
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	32(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	32(%r8), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	32(%rdi), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	-1168(%rbp), %xmm15
	movaps	%xmm3, 32(%rax)
	movaps	%xmm14, %xmm3
	mulps	%xmm5, %xmm3
	addps	%xmm3, %xmm2
	movaps	-1184(%rbp), %xmm3
	movaps	%xmm2, 32(%rdx)
	movaps	%xmm14, %xmm2
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	addps	%xmm14, %xmm0
	addps	%xmm2, %xmm1
	movaps	-1120(%rbp), %xmm2
	movaps	%xmm0, 32(%rsi)
	movaps	%xmm1, 32(%rcx)
	movups	48(%r10), %xmm0
	mulps	%xmm0, %xmm3
	movups	48(%r9), %xmm14
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm2
	mulps	%xmm14, %xmm15
	addps	48(%rax), %xmm3
	mulps	%xmm6, %xmm1
	addps	48(%rdx), %xmm2
	mulps	%xmm10, %xmm0
	addps	48(%rcx), %xmm1
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	48(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	48(%r8), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	48(%rdi), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm3, 48(%rax)
	movaps	%xmm14, %xmm3
	mulps	%xmm5, %xmm3
	movaps	-1168(%rbp), %xmm15
	addps	%xmm3, %xmm2
	movaps	-1184(%rbp), %xmm3
	movaps	%xmm2, 48(%rdx)
	movaps	%xmm14, %xmm2
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	addps	%xmm14, %xmm0
	addps	%xmm2, %xmm1
	movaps	-1120(%rbp), %xmm2
	movaps	%xmm0, 48(%rsi)
	movaps	%xmm1, 48(%rcx)
	movups	64(%r10), %xmm0
	mulps	%xmm0, %xmm3
	movups	64(%r9), %xmm14
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm2
	mulps	%xmm14, %xmm15
	addps	64(%rax), %xmm3
	mulps	%xmm6, %xmm1
	addps	64(%rdx), %xmm2
	mulps	%xmm10, %xmm0
	addps	64(%rcx), %xmm1
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	64(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	64(%r8), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	64(%rdi), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	-1168(%rbp), %xmm15
	movaps	%xmm3, 64(%rax)
	movaps	%xmm14, %xmm3
	mulps	%xmm5, %xmm3
	addps	%xmm3, %xmm2
	movaps	-1184(%rbp), %xmm3
	movaps	%xmm2, 64(%rdx)
	movaps	%xmm14, %xmm2
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	addps	%xmm14, %xmm0
	addps	%xmm2, %xmm1
	movaps	-1120(%rbp), %xmm2
	movaps	%xmm0, 64(%rsi)
	movaps	%xmm1, 64(%rcx)
	movups	80(%r10), %xmm0
	mulps	%xmm0, %xmm3
	movups	80(%r9), %xmm14
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm2
	mulps	%xmm14, %xmm15
	addps	80(%rax), %xmm3
	mulps	%xmm6, %xmm1
	addps	80(%rdx), %xmm2
	mulps	%xmm10, %xmm0
	addps	80(%rcx), %xmm1
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	80(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	80(%r8), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	80(%rdi), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm3, 80(%rax)
	movaps	%xmm14, %xmm3
	mulps	%xmm5, %xmm3
	movaps	-1168(%rbp), %xmm15
	addps	%xmm3, %xmm2
	movaps	-1184(%rbp), %xmm3
	movaps	%xmm2, 80(%rdx)
	movaps	%xmm14, %xmm2
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	addps	%xmm14, %xmm0
	addps	%xmm2, %xmm1
	movaps	-1120(%rbp), %xmm2
	movaps	%xmm0, 80(%rsi)
	movaps	%xmm1, 80(%rcx)
	movups	96(%r10), %xmm0
	mulps	%xmm0, %xmm3
	movups	96(%r9), %xmm14
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm2
	mulps	%xmm14, %xmm15
	addps	96(%rax), %xmm3
	mulps	%xmm6, %xmm1
	addps	96(%rdx), %xmm2
	mulps	%xmm10, %xmm0
	addps	96(%rcx), %xmm1
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	96(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	96(%r8), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	96(%rdi), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	-1168(%rbp), %xmm15
	movaps	%xmm3, 96(%rax)
	movaps	%xmm14, %xmm3
	mulps	%xmm5, %xmm3
	addps	%xmm3, %xmm2
	movaps	-1184(%rbp), %xmm3
	movaps	%xmm2, 96(%rdx)
	movaps	%xmm14, %xmm2
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	addps	%xmm14, %xmm0
	addps	%xmm2, %xmm1
	movaps	-1120(%rbp), %xmm2
	movaps	%xmm0, 96(%rsi)
	movaps	%xmm1, 96(%rcx)
	movups	112(%r10), %xmm0
	subq	$-128, %r10
	mulps	%xmm0, %xmm3
	movups	112(%r9), %xmm14
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm2
	subq	$-128, %r9
	mulps	%xmm14, %xmm15
	addps	112(%rax), %xmm3
	mulps	%xmm6, %xmm1
	addps	112(%rdx), %xmm2
	mulps	%xmm10, %xmm0
	addps	112(%rcx), %xmm1
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	112(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	112(%r8), %xmm14
	subq	$-128, %r8
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	112(%rdi), %xmm14
	subq	$-128, %rdi
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm3, 112(%rax)
	movaps	%xmm14, %xmm3
	subq	$-128, %rax
	mulps	%xmm5, %xmm3
	addps	%xmm3, %xmm2
	movaps	%xmm2, 112(%rdx)
	movaps	%xmm14, %xmm2
	subq	$-128, %rdx
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	addps	%xmm14, %xmm0
	addps	%xmm2, %xmm1
	movaps	%xmm0, 112(%rsi)
	subq	$-128, %rsi
	movaps	%xmm1, 112(%rcx)
	subq	$-128, %rcx
	subl	$1, %r11d
	jne	.L5
	addq	$16, %rbx
	addq	$16, %r15
	addq	$16, %r14
	addq	$16, %r13
	addq	$1024, %r12
	subl	$1, -1188(%rbp)
	jne	.L6
	movq	-1200(%rbp), %rdx
	movq	-1200(%rbp), %rdi
	xorl	%eax, %eax
	leaq	256(%rdx), %rsi
	leaq	512(%rdx), %rcx
	addq	$768, %rdx
	.p2align 4,,10
	.p2align 3
.L7:
	movaps	-1088(%rbp,%rax), %xmm0
	movups	%xmm0, (%rax,%rdi)
	movaps	-832(%rbp,%rax), %xmm0
	movups	%xmm0, (%rsi,%rax)
	movaps	-576(%rbp,%rax), %xmm0
	movups	%xmm0, (%rcx,%rax)
	movaps	-320(%rbp,%rax), %xmm0
	movups	%xmm0, (%rdx,%rax)
	xorps	%xmm0, %xmm0
	movaps	%xmm0, -1088(%rbp,%rax)
	movaps	%xmm0, -832(%rbp,%rax)
	movaps	%xmm0, -576(%rbp,%rax)
	movaps	%xmm0, -320(%rbp,%rax)
	addq	$16, %rax
	cmpq	$256, %rax
	jne	.L7
	addq	$1024, -1200(%rbp)
	addq	$1024, -1216(%rbp)
	movq	-1208(%rbp), %rax
	cmpq	%rax, -1200(%rbp)
	jne	.L4
	movq	-1232(%rbp), %rdx
	movq	-1240(%rbp), %rdi
	addq	$8192, %rdx
	addq	$16384, %rdi
	movq	%rdx, -1200(%rbp)
	movq	%rdi, -1216(%rbp)
.L8:
	movq	-1200(%rbp), %r15
	movq	-1200(%rbp), %r14
	movq	-1200(%rbp), %r13
	movq	-1224(%rbp), %r12
	movq	-1200(%rbp), %rbx
	movl	$16, -1188(%rbp)
	addq	$256, %r15
	addq	$512, %r14
	addq	$768, %r13
	.p2align 4,,10
	.p2align 3
.L10:
	movss	8(%r14), %xmm0
	leaq	768(%r12), %r10
	movaps	%xmm0, %xmm1
	leaq	512(%r12), %r9
	movss	12(%r14), %xmm0
	leaq	256(%r12), %r8
	shufps	$0, %xmm1, %xmm1
	leaq	-1088(%rbp), %rsi
	movss	(%rbx), %xmm13
	leaq	-832(%rbp), %rcx
	movss	4(%rbx), %xmm12
	leaq	-576(%rbp), %rdx
	movaps	%xmm1, -1104(%rbp)
	movaps	%xmm0, %xmm1
	leaq	-320(%rbp), %rax
	shufps	$0, %xmm13, %xmm13
	movq	%r12, %rdi
	shufps	$0, %xmm1, %xmm1
	movl	$2, %r11d
	movss	0(%r13), %xmm0
	movss	8(%rbx), %xmm11
	shufps	$0, %xmm12, %xmm12
	movaps	%xmm1, -1120(%rbp)
	movaps	%xmm0, %xmm1
	shufps	$0, %xmm11, %xmm11
	shufps	$0, %xmm1, %xmm1
	movss	4(%r13), %xmm0
	movss	12(%rbx), %xmm10
	movaps	%xmm1, -1136(%rbp)
	movaps	%xmm0, %xmm1
	shufps	$0, %xmm10, %xmm10
	shufps	$0, %xmm1, %xmm1
	movss	8(%r13), %xmm0
	movss	(%r15), %xmm9
	movaps	%xmm1, -1152(%rbp)
	movaps	%xmm0, %xmm1
	shufps	$0, %xmm9, %xmm9
	shufps	$0, %xmm1, %xmm1
	movss	12(%r13), %xmm0
	movss	4(%r15), %xmm8
	movaps	%xmm1, -1168(%rbp)
	movaps	%xmm0, %xmm1
	shufps	$0, %xmm8, %xmm8
	shufps	$0, %xmm1, %xmm1
	movss	8(%r15), %xmm7
	movss	12(%r15), %xmm6
	shufps	$0, %xmm7, %xmm7
	movss	(%r14), %xmm5
	movss	4(%r14), %xmm4
	shufps	$0, %xmm6, %xmm6
	shufps	$0, %xmm5, %xmm5
	shufps	$0, %xmm4, %xmm4
	movaps	%xmm1, -1184(%rbp)
.L9:
	movups	(%r10), %xmm0
	movaps	-1184(%rbp), %xmm3
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm3
	movups	(%r9), %xmm14
	movaps	-1120(%rbp), %xmm2
	mulps	%xmm6, %xmm1
	movaps	-1168(%rbp), %xmm15
	addps	(%rax), %xmm3
	mulps	%xmm0, %xmm2
	mulps	%xmm14, %xmm15
	addps	(%rcx), %xmm1
	mulps	%xmm10, %xmm0
	addps	(%rdx), %xmm2
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	(%r8), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	(%rdi), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	-1168(%rbp), %xmm15
	movaps	%xmm3, (%rax)
	movaps	%xmm14, %xmm3
	mulps	%xmm5, %xmm3
	addps	%xmm3, %xmm2
	movaps	-1184(%rbp), %xmm3
	movaps	%xmm2, (%rdx)
	movaps	%xmm14, %xmm2
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	addps	%xmm14, %xmm0
	addps	%xmm2, %xmm1
	movaps	-1120(%rbp), %xmm2
	movaps	%xmm0, (%rsi)
	movaps	%xmm1, (%rcx)
	movups	16(%r10), %xmm0
	mulps	%xmm0, %xmm3
	movups	16(%r9), %xmm14
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm2
	mulps	%xmm14, %xmm15
	addps	16(%rax), %xmm3
	mulps	%xmm6, %xmm1
	addps	16(%rdx), %xmm2
	mulps	%xmm10, %xmm0
	addps	16(%rcx), %xmm1
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	16(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	16(%r8), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	16(%rdi), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm3, 16(%rax)
	movaps	%xmm14, %xmm3
	mulps	%xmm5, %xmm3
	addps	%xmm3, %xmm2
	movaps	%xmm2, 16(%rdx)
	movaps	%xmm14, %xmm2
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	movaps	-1184(%rbp), %xmm3
	addps	%xmm14, %xmm0
	movaps	-1168(%rbp), %xmm15
	addps	%xmm2, %xmm1
	movaps	-1120(%rbp), %xmm2
	movaps	%xmm0, 16(%rsi)
	movaps	%xmm1, 16(%rcx)
	movups	32(%r10), %xmm0
	mulps	%xmm0, %xmm3
	movups	32(%r9), %xmm14
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm2
	mulps	%xmm14, %xmm15
	addps	32(%rax), %xmm3
	mulps	%xmm6, %xmm1
	addps	32(%rdx), %xmm2
	mulps	%xmm10, %xmm0
	addps	32(%rcx), %xmm1
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	32(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	32(%r8), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	32(%rdi), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	-1168(%rbp), %xmm15
	movaps	%xmm3, 32(%rax)
	movaps	%xmm14, %xmm3
	mulps	%xmm5, %xmm3
	addps	%xmm3, %xmm2
	movaps	-1184(%rbp), %xmm3
	movaps	%xmm2, 32(%rdx)
	movaps	%xmm14, %xmm2
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	addps	%xmm14, %xmm0
	addps	%xmm2, %xmm1
	movaps	-1120(%rbp), %xmm2
	movaps	%xmm0, 32(%rsi)
	movaps	%xmm1, 32(%rcx)
	movups	48(%r10), %xmm0
	mulps	%xmm0, %xmm3
	movups	48(%r9), %xmm14
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm2
	mulps	%xmm14, %xmm15
	addps	48(%rax), %xmm3
	mulps	%xmm6, %xmm1
	addps	48(%rdx), %xmm2
	mulps	%xmm10, %xmm0
	addps	48(%rcx), %xmm1
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	48(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	48(%r8), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	48(%rdi), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm3, 48(%rax)
	movaps	%xmm14, %xmm3
	mulps	%xmm5, %xmm3
	movaps	-1168(%rbp), %xmm15
	addps	%xmm3, %xmm2
	movaps	-1184(%rbp), %xmm3
	movaps	%xmm2, 48(%rdx)
	movaps	%xmm14, %xmm2
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	addps	%xmm14, %xmm0
	addps	%xmm2, %xmm1
	movaps	-1120(%rbp), %xmm2
	movaps	%xmm0, 48(%rsi)
	movaps	%xmm1, 48(%rcx)
	movups	64(%r10), %xmm0
	mulps	%xmm0, %xmm3
	movups	64(%r9), %xmm14
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm2
	mulps	%xmm14, %xmm15
	addps	64(%rax), %xmm3
	mulps	%xmm6, %xmm1
	addps	64(%rdx), %xmm2
	mulps	%xmm10, %xmm0
	addps	64(%rcx), %xmm1
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	64(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	64(%r8), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	64(%rdi), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	-1168(%rbp), %xmm15
	movaps	%xmm3, 64(%rax)
	movaps	%xmm14, %xmm3
	mulps	%xmm5, %xmm3
	addps	%xmm3, %xmm2
	movaps	-1184(%rbp), %xmm3
	movaps	%xmm2, 64(%rdx)
	movaps	%xmm14, %xmm2
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	addps	%xmm14, %xmm0
	addps	%xmm2, %xmm1
	movaps	-1120(%rbp), %xmm2
	movaps	%xmm0, 64(%rsi)
	movaps	%xmm1, 64(%rcx)
	movups	80(%r10), %xmm0
	mulps	%xmm0, %xmm3
	movups	80(%r9), %xmm14
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm2
	mulps	%xmm14, %xmm15
	addps	80(%rax), %xmm3
	mulps	%xmm6, %xmm1
	addps	80(%rdx), %xmm2
	mulps	%xmm10, %xmm0
	addps	80(%rcx), %xmm1
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	80(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	80(%r8), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	80(%rdi), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm3, 80(%rax)
	movaps	%xmm14, %xmm3
	mulps	%xmm5, %xmm3
	movaps	-1168(%rbp), %xmm15
	addps	%xmm3, %xmm2
	movaps	-1184(%rbp), %xmm3
	movaps	%xmm2, 80(%rdx)
	movaps	%xmm14, %xmm2
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	addps	%xmm14, %xmm0
	addps	%xmm2, %xmm1
	movaps	-1120(%rbp), %xmm2
	movaps	%xmm0, 80(%rsi)
	movaps	%xmm1, 80(%rcx)
	movups	96(%r10), %xmm0
	mulps	%xmm0, %xmm3
	movups	96(%r9), %xmm14
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm2
	mulps	%xmm14, %xmm15
	addps	96(%rax), %xmm3
	mulps	%xmm6, %xmm1
	addps	96(%rdx), %xmm2
	mulps	%xmm10, %xmm0
	addps	96(%rcx), %xmm1
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	96(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	96(%r8), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	96(%rdi), %xmm14
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	-1168(%rbp), %xmm15
	movaps	%xmm3, 96(%rax)
	movaps	%xmm14, %xmm3
	mulps	%xmm5, %xmm3
	addps	%xmm3, %xmm2
	movaps	-1184(%rbp), %xmm3
	movaps	%xmm2, 96(%rdx)
	movaps	%xmm14, %xmm2
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	addps	%xmm14, %xmm0
	addps	%xmm2, %xmm1
	movaps	-1120(%rbp), %xmm2
	movaps	%xmm0, 96(%rsi)
	movaps	%xmm1, 96(%rcx)
	movups	112(%r10), %xmm0
	subq	$-128, %r10
	mulps	%xmm0, %xmm3
	movups	112(%r9), %xmm14
	movaps	%xmm0, %xmm1
	mulps	%xmm0, %xmm2
	subq	$-128, %r9
	mulps	%xmm14, %xmm15
	addps	112(%rax), %xmm3
	mulps	%xmm6, %xmm1
	addps	112(%rdx), %xmm2
	mulps	%xmm10, %xmm0
	addps	112(%rcx), %xmm1
	addps	%xmm15, %xmm3
	movaps	-1104(%rbp), %xmm15
	addps	112(%rsi), %xmm0
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm11, %xmm14
	mulps	%xmm7, %xmm15
	addps	%xmm14, %xmm0
	movups	112(%r8), %xmm14
	subq	$-128, %r8
	addps	%xmm15, %xmm1
	movaps	-1152(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm14, %xmm15
	mulps	%xmm4, %xmm15
	addps	%xmm15, %xmm2
	movaps	%xmm14, %xmm15
	mulps	%xmm12, %xmm14
	mulps	%xmm8, %xmm15
	addps	%xmm14, %xmm0
	movups	112(%rdi), %xmm14
	subq	$-128, %rdi
	addps	%xmm15, %xmm1
	movaps	-1136(%rbp), %xmm15
	mulps	%xmm14, %xmm15
	addps	%xmm15, %xmm3
	movaps	%xmm3, 112(%rax)
	movaps	%xmm14, %xmm3
	subq	$-128, %rax
	mulps	%xmm5, %xmm3
	addps	%xmm3, %xmm2
	movaps	%xmm2, 112(%rdx)
	movaps	%xmm14, %xmm2
	subq	$-128, %rdx
	mulps	%xmm13, %xmm14
	mulps	%xmm9, %xmm2
	addps	%xmm14, %xmm0
	addps	%xmm2, %xmm1
	movaps	%xmm0, 112(%rsi)
	subq	$-128, %rsi
	movaps	%xmm1, 112(%rcx)
	subq	$-128, %rcx
	subl	$1, %r11d
	jne	.L9
	addq	$16, %rbx
	addq	$16, %r15
	addq	$16, %r14
	addq	$16, %r13
	addq	$1024, %r12
	subl	$1, -1188(%rbp)
	jne	.L10
	movq	-1208(%rbp), %rdx
	movq	-1208(%rbp), %rdi
	xorl	%eax, %eax
	leaq	256(%rdx), %rsi
	leaq	512(%rdx), %rcx
	addq	$768, %rdx
	.p2align 4,,10
	.p2align 3
.L11:
	movaps	-1088(%rbp,%rax), %xmm0
	movups	%xmm0, (%rdi,%rax)
	movaps	-832(%rbp,%rax), %xmm0
	movups	%xmm0, (%rsi,%rax)
	movaps	-576(%rbp,%rax), %xmm0
	movups	%xmm0, (%rcx,%rax)
	movaps	-320(%rbp,%rax), %xmm0
	movups	%xmm0, (%rdx,%rax)
	xorps	%xmm0, %xmm0
	movaps	%xmm0, -1088(%rbp,%rax)
	movaps	%xmm0, -832(%rbp,%rax)
	movaps	%xmm0, -576(%rbp,%rax)
	movaps	%xmm0, -320(%rbp,%rax)
	addq	$16, %rax
	cmpq	$256, %rax
	jne	.L11
	addq	$1024, -1208(%rbp)
	addq	$1024, -1200(%rbp)
	movq	-1216(%rbp), %rax
	cmpq	%rax, -1208(%rbp)
	jne	.L8
.L1:
	movq	-56(%rbp), %rdi
	xorq	%fs:40, %rdi
	jne	.L44
	leaq	-40(%rbp), %rsp
	popq	%rbx
	popq	%r12
	popq	%r13
	popq	%r14
	popq	%r15
	popq	%rbp
	.cfi_remember_state
	.cfi_def_cfa 7, 8
	ret
.L2:
	.cfi_restore_state
	leal	3(%rdi), %r10d
	testl	%edi, %edi
	movq	%rsp, %r14
	cmovns	%edi, %r10d
	sarl	$2, %r10d
	movslq	%r10d, %rax
	leal	0(,%r10,4), %r13d
	salq	$4, %rax
	addq	$16, %rax
	subq	%rax, %rsp
	leaq	15(%rsp), %rdx
	andq	$-16, %rdx
	testl	%edi, %edi
	jle	.L13
	movq	-1232(%rbp), %r9
	leal	-1(%r10), %esi
	movq	-1240(%rbp), %r12
	xorps	%xmm2, %xmm2
	movq	-1224(%rbp), %r15
	movslq	%edi, %r11
	addq	$1, %rsi
	xorl	%ebx, %ebx
	salq	$2, %r11
	salq	$4, %rsi
	subq	%r9, %r12
.L14:
	xorl	%eax, %eax
	testl	%r10d, %r10d
	jle	.L21
	.p2align 4,,10
	.p2align 3
.L15:
	movaps	%xmm2, (%rdx,%rax)
	addq	$16, %rax
	cmpq	%rsi, %rax
	jne	.L15
.L21:
	movq	%r15, %rcx
	xorl	%r8d, %r8d
	.p2align 4,,10
	.p2align 3
.L18:
	movss	(%r9,%r8,4), %xmm1
	testl	%r10d, %r10d
	shufps	$0, %xmm1, %xmm1
	jle	.L16
	xorl	%eax, %eax
	.p2align 4,,10
	.p2align 3
.L17:
	movups	(%rax,%rcx), %xmm0
	mulps	%xmm1, %xmm0
	addps	(%rdx,%rax), %xmm0
	movaps	%xmm0, (%rdx,%rax)
	addq	$16, %rax
	cmpq	%rsi, %rax
	jne	.L17
.L16:
	addq	$1, %r8
	addq	%r11, %rcx
	cmpl	%r8d, %edi
	jg	.L18
	testl	%r10d, %r10d
	jle	.L19
	leaq	(%r12,%r9), %rcx
	xorl	%eax, %eax
	.p2align 4,,10
	.p2align 3
.L20:
	movaps	(%rdx,%rax), %xmm0
	movups	%xmm0, (%rcx,%rax)
	addq	$16, %rax
	cmpq	%rsi, %rax
	jne	.L20
.L19:
	addl	$1, %ebx
	addq	%r11, %r9
	cmpl	%edi, %ebx
	jne	.L14
.L13:
	cmpl	%r13d, %edi
	jle	.L22
	movq	-1224(%rbp), %rdx
	movq	-1240(%rbp), %r11
	movslq	%r13d, %rax
	movq	-1232(%rbp), %rbx
	movslq	%edi, %r8
	salq	$2, %r8
	leaq	(%rdx,%rax,4), %r10
	subq	%rdx, %r11
.L23:
	testl	%edi, %edi
	jle	.L25
	leaq	(%r11,%r10), %rcx
	movq	%rbx, %rsi
	xorl	%r9d, %r9d
	.p2align 4,,10
	.p2align 3
.L26:
	movss	(%rcx), %xmm1
	movq	%r10, %rdx
	xorl	%eax, %eax
	.p2align 4,,10
	.p2align 3
.L24:
	movss	(%rdx), %xmm0
	addq	%r8, %rdx
	mulss	(%rsi,%rax,4), %xmm0
	addq	$1, %rax
	cmpl	%eax, %edi
	addss	%xmm0, %xmm1
	movss	%xmm1, (%rcx)
	jg	.L24
	addl	$1, %r9d
	addq	%r8, %rcx
	addq	%r8, %rsi
	cmpl	%edi, %r9d
	jne	.L26
.L25:
	addl	$1, %r13d
	addq	$4, %r10
	cmpl	%edi, %r13d
	jne	.L23
.L22:
	movq	%r14, %rsp
	jmp	.L1
.L44:
	call	__stack_chk_fail
	.cfi_endproc
.LFE531:
	.size	square_sgemm, .-square_sgemm
	.ident	"GCC: (Ubuntu/Linaro 4.6.1-9ubuntu3) 4.6.1"
	.section	.note.GNU-stack,"",@progbits
