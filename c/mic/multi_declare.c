/**
 * 下面同时声明两个可以在mic上执行的函数.
 * 
 * #pragma offload_attribute(push, target(mic))
 * ...
 * #pragma offload_attribute(pop)
 */
#pragma offload_attribute(push, target(mic))
#include <stdio.h>
#include <stdlib.h>

void test1();
void test2();
#pragma offload_attribute(pop)

/*
 共享内存声明
#pragma offload_attribute(push, _Cilk_shared)
...
#pragma offload_attribute(pop)
*/

int main() {
#pragma offload target(mic)
	test1();

#pragma offload target(mic)
	test2();
}

void test1() {
	printf("this is test1\n");
}

void test2() {
	printf("this is test2\n");
} 
