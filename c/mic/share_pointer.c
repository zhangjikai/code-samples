#include <stdio.h>
#include <stdlib.h>

/**
 * 共享指针内存管理
 */ 

// share_pointer是共享指针, 注意*号是在_Cilk_shared之前, 该指针只能指向共享数据
// 共享内存管理应该使用_Offload_shared_malloc(size_t size) 和 _offload_shared_free(void *p)
// 不过在共享函数中可以使用malloc和free为共享指针分配空间, 参见cilk_malloc()
// 另外在mic上不能使用_Offload_shared_free 释放内存, 需要在host上调用, 参见cilk_sharedfree()
//
// int _Cilk_shared *p 是本地指针, 可以指向共享数据, 如果直接p = _Offload_share_malloc() 会报warning
// 而使用下面的方式定义则没有问题
// typedef int *fp;
// _Cilk_shared fp p;
// p = (fp)_Offload_shared_malloc(sizeof(int) * n);
// _Offload_shared_free(p);
int *_Cilk_shared share_pointer;

_Cilk_shared int n = 8;

// 在共享函数内, 使用malloc和free为共享变量分配和释放内存
_Cilk_shared void cilk_malloc() {

	int i;
	share_pointer = (int *)malloc(sizeof(int) * n);
	for(i = 0; i < n; i++) {
		share_pointer[i] = i;
	}

	for(i = 0; i < n; i++) {
		printf("cilk_malloc: share_pointer[%d] is %d\n", i, share_pointer[i]);
	}
	free(share_pointer);
}

// 在mic上执行下面函数会报错
// CARD--ERROR:1 thread:3 myoArenaFree: It is not supported to free shared memory from the MIC side!
_Cilk_shared void cilk_sharedfree() {
	int i;
	share_pointer = (int *)_Offload_shared_malloc(sizeof(int) * n);
	for(i = 0; i < n; i++) {
		share_pointer[i] = i;
	}

	for(i = 0; i < n; i++) {
		printf("cilk_sharedfree: share_pointer[%d] is %d\n", i, share_pointer[i]);
	}
	_Offload_shared_free(share_pointer);

}


_Cilk_shared void cilk_pointer() {
	int i;
	for(i = 0; i < n; i++) {
		share_pointer[i] = i;
	}

	for(i = 0; i < n; i++) {
		printf("cilk_pointer: share_pointer[%d] is %d\n", i, share_pointer[i]);
	}
}

int main() {

	//_Cilk_offload cilk_malloc();

	//_Cilk_offload cilk_sharedfree();

	// 下面三条语句执行时会错误
	//share_pointer =(int *) malloc(sizeof(int) * n);
	//_Cilk_offload cilk_pointer();
	//free(share_pointer);

	// 下面三条语句可以正常执行
	share_pointer = (int *) _Offload_shared_malloc(sizeof(int) * n);
	_Cilk_offload cilk_pointer();
	_Offload_shared_free(share_pointer);

	return 0;
}
