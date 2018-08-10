#include <stdio.h>
#include <stdlib.h>

#define ALLOC alloc_if(1)
#define FREE free_if(1)
#define RETAIN free_if(0)
#define REUSE alloc_if(0)

/**
 * 使用alloc_if 和free_if控制是否为指针在MIC上分配新的内存以及是否释放掉内存
 * alloc_if(1): 为指针在mic上分配新的内存
 * alloc_if(0): 不为指针分配新的内存, 使用上次未释放的内存
 * free_if(1): 当离开mic时是放掉为该指针分配的内存
 * free_if(0): 当离开mic时不释放指针内存 
 * 
 * 默认值为: alloc_if(1) free_if(1)
 */

void init_array(int* arr, int n, int start_num){
	int i;
	for(i = 0; i < n; i++) {
		arr[i] = start_num + i;
	}
}

// 当mic上没有未释放的内存时, 使用alloc_if(0)会报错
void reuse_before_alloc() {
	int n = 10;
	int *p =(int*) calloc(n, sizeof(int));
	int i;
	init_array(p, n, 0);	
	//当然这是错的offload error: cannot find data associated with pointer variable 0x15e2c60
	//因为没有已有的内存
#pragma offload target(mic) in(p:length(10) REUSE)
	{
		for(i = 0; i < n; i++) {
			printf("the p[%d] id %d\n", i, p[i]);
		}
	}
	free(p);
}

//这里保存内存, 在下面执行reuse, reuse2 之前都应该先执行该函数在MIC上保存内存.
void retian() {

	int n = 10;
	int *p =(int*) calloc(n, sizeof(int));
	int i;
	init_array(p, n, 0);	
#pragma offload target(mic) in(p:length(n) RETAIN)
	{
		for(i = 0; i < n; i++) {
			printf("retian: the p[%d] id %d\n", i, p[i]);
		}
	}
	free(p);
}

//这里使用上面保存的内存空间
void reuse() {
	int n = 10;
	int *p =(int*) calloc(n, sizeof(int));
	int i;
	init_array(p, n, 0);
	//如果不加retain会默认释放掉该内存	
#pragma offload target(mic) in(p:length(n) REUSE)
	{
		for(i = 0; i < n; i++) {
			printf("reuse: the p[%d] id %d\n", i, p[i]);
		}
	}
	free(p);
}

// 重用的内存不可以大于MIC上已保存的内存, 小于是可以的
void reuse2() {
	// 如果n=11就会报错
	int n = 9;
	int *p =(int*) calloc(n, sizeof(int));
	int i;
	init_array(p, n, 0);
#pragma offload target(mic) in(p:length(n) REUSE)
	{
		for(i = 0; i < n; i++) {
			printf("reuse: the p[%d] is %d\n", i, p[i]);
		}
	}
	free(p);
}


int main(){

//	reuse_before_alloc();

	retian();
	reuse();

//	retian();
//	reuse2();


	return 0;
}
