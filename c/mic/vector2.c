#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <immintrin.h>

// 打印二进制
 void print_binary(uint64_t t, int bit_len) {
	short buffer[bit_len];
	int i;
	for(i = 0; i < bit_len; i++) {
		buffer[i] = 0;
	}

	for (i = 0; i < bit_len; i++) {
		if (t == 0)
			break;
		if (t % 2 == 0) {
			buffer[i] = 0;
		} else {
			buffer[i] = 1;
		}
		t = t / 2;
	}
	for (i = bit_len - 1; i >= 0; i--) {
		printf("%hd", buffer[i]);
	}
}


/**
 * 向量加法, 这里以32位整型为例
 */
void mic_add() {
    uint32_t *arr_a, *arr_b, *arr_c;
    int i = 0, n = 16;

    arr_a = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_b = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_c = _mm_malloc(sizeof(uint32_t) * n, 64);

    for(i = 0; i < n; i++) {
        arr_a[i] = i;
        arr_b[i] = n + i; 
    }

#pragma offload target(mic) in(arr_a:length(n)) in(arr_b:length(n)) out(arr_c:length(n))
    {
        __m512i m_a, m_b, m_c;
        m_a = _mm512_load_epi32(arr_a);
        m_b = _mm512_load_epi32(arr_b);
        m_c = _mm512_add_epi32(m_a, m_b);
        _mm512_store_epi32(arr_c, m_c);
    }
    
    for(i = 0; i < n; i++) {
        printf("arr_a[%2d] is: %2d \t  arr_b[%2d] is: %2d \t  arr_c[%2d] is : %2d\n", i, arr_a[i], i, arr_b[i], i, arr_c[i]);
    }
    _mm_free(arr_a);
    _mm_free(arr_b);
    _mm_free(arr_c);
}

/**
 * 向量减法, 这里以32位整型为例
 */
void mic_sub() {
    uint32_t *arr_a, *arr_b, *arr_c;
    int i = 0, n = 16;

    arr_a = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_b = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_c = _mm_malloc(sizeof(uint32_t) * n, 64);

    for(i = 0; i < n; i++) {
        arr_a[i] = n + i;
        arr_b[i] = i; 
    }

#pragma offload target(mic) in(arr_a:length(n)) in(arr_b:length(n)) out(arr_c:length(n))
    {
        __m512i m_a, m_b, m_c;
        m_a = _mm512_load_epi32(arr_a);
        m_b = _mm512_load_epi32(arr_b);
        m_c = _mm512_sub_epi32(m_a, m_b);
        _mm512_store_epi32(arr_c, m_c);
    }
    
    for(i = 0; i < n; i++) {
        printf("arr_a[%2d] is: %2d \t  arr_b[%2d] is: %2d \t  arr_c[%2d] is : %2d\n", i, arr_a[i], i, arr_b[i], i, arr_c[i]);
    }
    _mm_free(arr_a);
    _mm_free(arr_b);
    _mm_free(arr_c);
}

/**
 * 向量乘法, 这里以32位整型为例
 */
void mic_mul() {
    uint32_t *arr_a, *arr_b, *arr_c;
    int i = 0, n = 16;

    arr_a = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_b = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_c = _mm_malloc(sizeof(uint32_t) * n, 64);

    for(i = 0; i < n; i++) {
        arr_a[i] = i;
        arr_b[i] = 2; 
    }

#pragma offload target(mic) in(arr_a:length(n)) in(arr_b:length(n)) out(arr_c:length(n))
    {
        __m512i m_a, m_b, m_c;
        m_a = _mm512_load_epi32(arr_a);
        m_b = _mm512_load_epi32(arr_b);
        // _mm512_mullo_epi32 保留乘法结果的低32位, _mm512_mulhi_epi32保存结果的高32位
        m_c = _mm512_mullo_epi32(m_a, m_b);
        _mm512_store_epi32(arr_c, m_c);
    }
    
    for(i = 0; i < n; i++) {
        printf("arr_a[%2d] is: %2d \t  arr_b[%2d] is: %2d \t  arr_c[%2d] is : %2d\n", i, arr_a[i], i, arr_b[i], i, arr_c[i]);
    }
    _mm_free(arr_a);
    _mm_free(arr_b);
    _mm_free(arr_c);
}

/**
 * mask加法
 */
void mic_mask_add() {
    uint32_t *arr_a, *arr_b, *arr_c, *arr_old;
    int i = 0, n = 16;

    arr_a = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_b = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_c = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_old = _mm_malloc(sizeof(uint32_t) * n, 64);

    for(i = 0; i < n; i++) {
        arr_a[i] = i;
        arr_b[i] = n + i; 
        arr_old[i] = 10000;
    }

#pragma offload target(mic) in(arr_a:length(n)) in(arr_b:length(n)) in(arr_old:length(n)) out(arr_c:length(n))
    {
        __m512i m_a, m_b, m_c, m_old;
        // 11换成二进制就是0000000000001011
        __mmask16 k1 = 11;
        m_a = _mm512_load_epi32(arr_a);
        m_b = _mm512_load_epi32(arr_b);
        m_old = _mm512_load_epi32(arr_old);
        // 根据k1的值只有1,2,4位为1 所以只有m_c中只有第1,2,4个元素为m_a 和m_b中1,2,4个元素的和 剩余元素使用arr_old对应元素的值
        m_c = _mm512_mask_add_epi32(m_old, k1, m_a, m_b);
        _mm512_store_epi32(arr_c, m_c);
    }
    
    for(i = 0; i < n; i++) {
        printf("arr_a[%2d] is: %2d \t  arr_b[%2d] is: %2d \t arr_old[%2d] is: %d \t  arr_c[%2d] is : %2d\n", i, arr_a[i], i, arr_b[i], i, arr_old[i], i, arr_c[i]);
    }
    _mm_free(arr_a);
    _mm_free(arr_b);
    _mm_free(arr_c);
    _mm_free(arr_old);
}

/**
 * and操作
 */
void mic_and() {
    uint32_t *arr_a, *arr_b, *arr_c;
    int i = 0, n = 16;

    arr_a = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_b = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_c = _mm_malloc(sizeof(uint32_t) * n, 64);

    for(i = 0; i < n; i++) {
        arr_a[i] = i ;
        arr_b[i] = n + i; 
    }

#pragma offload target(mic) in(arr_a:length(n)) in(arr_b:length(n)) out(arr_c:length(n))
    {
        __m512i m_a, m_b, m_c;
        m_a = _mm512_load_epi32(arr_a);
        m_b = _mm512_load_epi32(arr_b);
        m_c = _mm512_and_epi32(m_a, m_b);
        _mm512_store_epi32(arr_c, m_c);
    }
    
    for(i = 0; i < n; i++) {
        //printf("arr_a[%2d] is: %2d \t  arr_b[%2d] is: %2d \t  arr_c[%2d] is : %2d\n", i, arr_a[i], i, arr_b[i], i, arr_c[i]);
        print_binary (arr_a[i], 8);
        printf( " & ");
        print_binary(arr_b[i], 8);
        printf (" = ");
        print_binary(arr_c[i], 8);
        printf("\n");

    }
    _mm_free(arr_a);
    _mm_free(arr_b);
    _mm_free(arr_c);
}

/**
 * or操作
 */
void mic_or() {
    uint32_t *arr_a, *arr_b, *arr_c;
    int i = 0, n = 16;

    arr_a = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_b = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_c = _mm_malloc(sizeof(uint32_t) * n, 64);

    for(i = 0; i < n; i++) {
        arr_a[i] = i ;
        arr_b[i] = n; 
    }

#pragma offload target(mic) in(arr_a:length(n)) in(arr_b:length(n)) out(arr_c:length(n))
    {
        __m512i m_a, m_b, m_c;
        m_a = _mm512_load_epi32(arr_a);
        m_b = _mm512_load_epi32(arr_b);
        m_c = _mm512_or_epi32(m_a, m_b);
        _mm512_store_epi32(arr_c, m_c);
    }
    
    for(i = 0; i < n; i++) {
        //printf("arr_a[%2d] is: %2d \t  arr_b[%2d] is: %2d \t  arr_c[%2d] is : %2d\n", i, arr_a[i], i, arr_b[i], i, arr_c[i]);
        print_binary (arr_a[i], 8);
        printf( " | ");
        print_binary(arr_b[i], 8);
        printf (" = ");
        print_binary(arr_c[i], 8);
        printf("\n");

    }
    _mm_free(arr_a);
    _mm_free(arr_b);
    _mm_free(arr_c);
}

/**
 * 异或
 */
void mic_xor() {
    uint32_t *arr_a, *arr_b, *arr_c;
    int i = 0, n = 16;

    arr_a = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_b = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_c = _mm_malloc(sizeof(uint32_t) * n, 64);

    for(i = 0; i < n; i++) {
        arr_a[i] = i ;
        arr_b[i] = rand()%n; 
    }

#pragma offload target(mic) in(arr_a:length(n)) in(arr_b:length(n)) out(arr_c:length(n))
    {
        __m512i m_a, m_b, m_c;
        m_a = _mm512_load_epi32(arr_a);
        m_b = _mm512_load_epi32(arr_b);
        m_c = _mm512_xor_epi32(m_a, m_b);
        _mm512_store_epi32(arr_c, m_c);
    }
    
    for(i = 0; i < n; i++) {
        //printf("arr_a[%2d] is: %2d \t  arr_b[%2d] is: %2d \t  arr_c[%2d] is : %2d\n", i, arr_a[i], i, arr_b[i], i, arr_c[i]);
        print_binary (arr_a[i], 8);
        printf( " ^ ");
        print_binary(arr_b[i], 8);
        printf (" = ");
        print_binary(arr_c[i], 8);
        printf("\n");

    }
    _mm_free(arr_a);
    _mm_free(arr_b);
    _mm_free(arr_c);
}

/**
 * 取反
 */
void mic_not() {
    uint32_t *arr_a, *arr_c;
    int i = 0, n = 16;

    arr_a = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_c = _mm_malloc(sizeof(uint32_t) * n, 64);

    for(i = 0; i < n; i++) {
        arr_a[i] = i ;
    }

#pragma offload target(mic) in(arr_a:length(n)) out(arr_c:length(n))
    {
        __m512i m_a, m_b, m_c;
        int32_t all_one = 0xffffffff;
        // _mm512_set1_epi32 : 将向量中的16个整型都设为all_one
        m_b = _mm512_set1_epi32(all_one);
        m_a = _mm512_load_epi32(arr_a);
        m_c = _mm512_xor_epi32(m_a, m_b);
        _mm512_store_epi32(arr_c, m_c);
    }
    
    for(i = 0; i < n; i++) {
        //printf("arr_a[%2d] is: %2d \t  arr_b[%2d] is: %2d \t  arr_c[%2d] is : %2d\n", i, arr_a[i], i, arr_b[i], i, arr_c[i]);
        printf("~ ");
        print_binary (arr_a[i], 8);
        printf( " = ");
        print_binary(arr_c[i], 8);
        printf("\n");

    }
    _mm_free(arr_a);
    _mm_free(arr_c);
}

/**
 * 左移, 按指定的整型大小移位 
 */
void mic_lshift() {
    uint32_t *arr_a, *arr_c;
    int i = 0, n = 16;

    arr_a = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_c = _mm_malloc(sizeof(uint32_t) * n, 64);

    for(i = 0; i < n; i++) {
        arr_a[i] = i ;
    }

#pragma offload target(mic) in(arr_a:length(n)) out(arr_c:length(n))
    {
        __m512i m_a, m_c;
        m_a = _mm512_load_epi32(arr_a);
        // 向量中的每个整型都左移一位 ,逻辑右移 _mm512_srli_epi32
        m_c = _mm512_slli_epi32 (m_a, 1);
        _mm512_store_epi32(arr_c, m_c);
    }
    
    for(i = 0; i < n; i++) {
        print_binary (arr_a[i], 8);
        printf( " \t  ");
        print_binary(arr_c[i], 8);
        printf("\n");

    }
    _mm_free(arr_a);
    _mm_free(arr_c);
}

void mic_lshift_v() {
    uint32_t *arr_a, *arr_c;
    int i = 0, n = 16;

    arr_a = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_c = _mm_malloc(sizeof(uint32_t) * n, 64);

    for(i = 0; i < n; i++) {
        arr_a[i] = i+1 ;
    }

#pragma offload target(mic) in(arr_a:length(n)) out(arr_c:length(n))
    {
        __m512i m_a, m_b, m_c;
        // _mm512_set_epi32(int e15, int e14, int e13, int e12, int e11, int e10, int e9, int e8, int e7, int e6, int e5, int e4, int e3, int e2, int e1, int e0);
        // _mm512_set_epi32 按从高到低的顺序, 第一个参数设为向量中第16个整型的值, 最后一个参数设为第1个整型的值
        m_b = _mm512_set_epi32(1,2,3,4,1,2,3,4,1,2,3,4,1,2,3,4);
        m_a = _mm512_load_epi32(arr_a);
        //  逻辑右移 _mm512_srlv_epi32
        m_c = _mm512_sllv_epi32 (m_a, m_b);
        _mm512_store_epi32(arr_c, m_c);
    }
    
    for(i = 0; i < n; i++) {
        print_binary (arr_a[i], 8);
        printf( " \t  ");
        print_binary(arr_c[i], 8);
        printf("\n");

    }
    _mm_free(arr_a);
    _mm_free(arr_c);
}

/**
 * 算术右移
 */
void mic_arshift() {
    uint32_t *arr_a, *arr_b, *arr_c, *arr_d;
    int i = 0, n = 16;

    arr_a = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_b = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_c = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_d = _mm_malloc(sizeof(uint32_t) * n, 64);

    uint32_t high_one = 1 << 31;
    for(i = 0; i < n; i++) {
        arr_a[i] = i+1;
        arr_b[i] = high_one | (i + 1); 
    }

#pragma offload target(mic) in(arr_a:length(n)) in(arr_b:length(n)) out(arr_c:length(n)) out(arr_d:length(n))
    {
        __m512i m_a,m_b, m_c, m_d;
        m_a = _mm512_load_epi32(arr_a);
        //  算术右移, 符号为0, 补0；符号位为1, 补1
        m_c = _mm512_srai_epi32 (m_a, 2);
        _mm512_store_epi32(arr_c, m_c);
        m_b = _mm512_load_epi32(arr_b);
        m_d = _mm512_srai_epi32(m_b, 2);
        _mm512_store_epi32(arr_d, m_d);
    }
    
    printf("符号位为0: \n");
    for(i = 0; i < n; i++) {
        print_binary (arr_a[i],32);
        printf( " \t  ");
        print_binary(arr_c[i], 32);
        printf("\n");

    }

    printf("符号位为1: \n");
    for(i = 0; i < n; i++) {
        print_binary (arr_b[i],32);
        printf( " \t  ");
        print_binary(arr_d[i], 32);
        printf("\n");
    }

    _mm_free(arr_a);
    _mm_free(arr_b);
    _mm_free(arr_c);
    _mm_free(arr_d);
}

/**
 * 将v2和v3拼接起来, v2在前, v3在后, 然后循环左移count个元素, 然后取最右侧的16个元素 
 */
void mic_alignr() {
    uint32_t *arr_a, *arr_b, *arr_c, *arr_d;
    int i = 0, n = 16;

    arr_a = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_b = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_c = _mm_malloc(sizeof(uint32_t) * n, 64);
    arr_d = _mm_malloc(sizeof(uint32_t) * n, 64);

    for(i = 0; i < n; i++) {
        arr_a[i] = i+1;
        arr_b[i] = n + i + 1; 
    }

#pragma offload target(mic) in(arr_a:length(n)) in(arr_b:length(n)) out(arr_c:length(n)) out(arr_d:length(n))
    {
        __m512i m_a,m_b, m_c, m_d;
        m_a = _mm512_load_epi32(arr_a);
        m_b = _mm512_load_epi32(arr_b);
        //  算术右移, 符号为0, 补0；符号位为1, 补1
        m_c = _mm512_alignr_epi32 (m_a, m_b, 3);
        _mm512_store_epi32(arr_c, m_c);
        m_d = _mm512_alignr_epi32(m_a, m_b, 8);
        _mm512_store_epi32(arr_d, m_d);
    }
    
    printf("arr_a: ");
    for(i = 0; i < n; i++) {
        printf("%2u ", arr_a[i]);
    }
    printf(" \narr_b: ");
    for(i = 0; i < n; i++) {
        printf("%2u ", arr_b[i]);
    }
    printf("\n\n");
    printf("count = 3  arr_c: ");
    for(i = 0; i < n; i++) {
        printf("%2u ", arr_c[i]);
    }
    printf("\n");
    
    printf("count = 8  arr_c: ");
    for(i = 0; i < n; i++) {
        printf("%2u ", arr_d[i]);
    }

    printf("\n");

    _mm_free(arr_a);
    _mm_free(arr_b);
    _mm_free(arr_c);
    _mm_free(arr_d);
}

int main() {
    // mic_add();
    // mic_sub();
    // mic_mul();
    // mic_mask_add();
    // mic_and();
    // mic_or();
    // mic_xor();
    // mic_not();
    // mic_lshift();
    // mic_lshift_v();
    // mic_arshift();
    mic_alignr();
}

