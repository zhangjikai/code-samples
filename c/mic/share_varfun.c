#include <stdio.h>
#include <stdlib.h>

/**
 * 使用共享虚拟内存. 在这种模式下, cpu和mic共享同一块虚拟内存, 在cpu和mic上变量的地址是相同的.
 * 在这种模式下, 使用_Cilk_shared声明变量和方法, 使用_Cilk_offload 分载到mic上运行.
 * 共享虚拟内存的效率要低于#pragma offload, 但是可以处理复杂的数据结构, 例如树,指针数组等
 *
 * The keyword _Cilk_shared enables you to use this shared memory as follows:
 *   - It places variables in this shared memory address range.
 *   - It specifies that a function is defined on both the CPU and the coprocessor.
 *
 *  The compiler allocates shared variables such that:
 *   - their virtual addresses are the same on the CPU and the coprocessor
 *   - their values are synchronized between the CPU and the coprocessor at a predefined point
 *
 */


// 声明CPU和MIC共享的变量
_Cilk_shared int x = 1;

// 声明CPU和MIC共享的函数
_Cilk_shared void run_onmic() {
	x = 3;
	printf("mic: the value of x is %d and the address of mic is %p\n", x, &x);
	
	// 确认是否在mic上执行
#ifdef __MIC__
	printf("this is onmic\n");
#endif
}

void run_oncpu() {
	printf("cpu: the value of x is %d and the address of mic is %p\n", x, &x);
}

int main() {
	// 使用_Cilk_offload 代替#pragma offload target(mic)
	_Cilk_offload run_onmic();
	run_oncpu();
}

