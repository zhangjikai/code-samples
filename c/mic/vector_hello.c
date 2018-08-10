#include <stdio.h>
#include <stdlib.h>
#include <immintrin.h>
#include <stdint.h>

#define __ONMIC__ __attribute__((target(mic)))

/**
 * 在mic上使用向量化示例, mic的向量处理器可以同时处理16个32位或者8个64位
 */

__ONMIC__ void test() {
	int a[16] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
	int b[16] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
	int c[16];
	__m512i m1 = _mm512_load_epi32(a);
	__m512i m2 = _mm512_load_epi32(b);
	__m512i m3; 
	m3 = _mm512_add_epi32(m1,m2);
	_mm512_store_epi32(c, m3);

	int i;
	for(i = 0; i < 16; i++) {
		printf("test: c[%d] is %d\n", i, c[i]);
	}
}


// 向量加法
void add() {
	int *a, *b, *c;
	int i, n = 32;
	// 这里要按照64位对齐
	a =(int *) _mm_malloc(sizeof(int) * n, 64);
	b =(int *) _mm_malloc(sizeof(int) * n, 64);
	c =(int *)  _mm_malloc(sizeof(int) * n, 64);
	//
	for(i = 0; i < n; i++) {
		a[i] = i;
		b[i] = n+i;
		c[i] = 0;
	}

#pragma offload target(mic) in(a:length(n)) in(b:length(n)) out(c:length(n)) 
	{
		__m512i m1, m2, m3;

		for(i = 0; i < n; i+=16) {
			m1 = _mm512_load_epi32(a + i);
			m2 = _mm512_load_epi32(b + i);
			m3 = _mm512_add_epi32(m1, m2);
			_mm512_store_epi32(c+i, m3);
		}
	}

	for(i = 0; i < n; i++) {
		printf("add: c[%d] is %d\n", i, c[i]);
	}

	_mm_free(a)	;
	_mm_free(b);
	_mm_free(c);
}

int main() {

#pragma offload target(mic)
	{
		test();
	}

	add();
}
