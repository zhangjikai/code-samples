#include <stdio.h>
#include <stdlib.h>
#define __ONMIC__ __attribute__((target(mic)))
/**
 * 异步传输数据 offload_transfer
 */

__ONMIC__ void add_inputs(int n, float *f1, float *f2){
	int i;
	for( i =0; i < n; i++) {
		f2[i] += f1[i];
	}
}

void display_vals( int id, int n,  float *f2) {
	printf("\nResults after Offload #%d:\n",id);

	int i;
	for ( i = 0; i < n; i++) {
		printf("f2[%d] is %f\n", i, f2[i] );
	} 
	printf("====================\n");
}
void test() {
	float *f1 , *f2;
	int n = 10000;
	int i, j;
	f1 = (float*) malloc(sizeof(float) * n);
	f2 = (float*)malloc(sizeof(float) * n);

	for(i = 0; i < n; i++) {
		f1[i] = i+1;
		f2[i] = 0.0;
	}
	// 这里只上传数据
#pragma offload_transfer target(mic:0) signal(f1) \
	in (f1:length(n) alloc_if(1) free_if(0))\
	in (f2:length(n) alloc_if(1) free_if(0))

	// wait(f1)等待上面的数据传输完毕, 再执行该操作
#pragma offload target(mic:0) wait(f1) signal(f2) \
	in(n) \
	nocopy(f1:alloc_if (0) free_if(1))\
	out(f2:length(n) alloc_if(0) free_if(1))
	add_inputs(n, f1, f2);

	// 等该f2执行完
#pragma offload_wait target(mic:0) wait(f2)

	// 如果不加wait, 就会全部打印出0
	display_vals(1, 10, f2);

	// 多个数据异步上传
#pragma offload_transfer target(mic:0) signal(f1) \
	in(f1:length(n) alloc_if(1) free_if(0))

#pragma offload_transfer target(mic:0) signal(f2) \
	in(f2:length(n) alloc_if(1) free_if(0))

	// 同时等待两个信号量
#pragma offload target(mic:0) wait(f1, f2) \
	in (n) \
	nocopy (f1:alloc_if(0) free_if(1)) \
	out (f2:length(n) alloc_if(0) free_if(1))
	add_inputs(n, f1, f2);

	display_vals(2, 10, f2);

	// 异步传输和同步传输结合
#pragma offload_transfer target(mic:0) signal(f2)\
	in(f2:length(n) alloc_if(1) free_if(0))

#pragma offload target(mic:0) wait(f2) \
	in(n) \
	in(f1:length(n) alloc_if(1) free_if(0))\
	nocopy(f2)
	add_inputs(n ,f1, f2);

#pragma offload_transfer target(mic:0) signal(f2) \
	out(f2:length(n) alloc_if(0) free_if(1))

#pragma offload_wait target(mic:0) wait(f2)

	display_vals(3, 10, f2);

	free(f1);
	free(f2);
}

int main() {
	test();	
}

