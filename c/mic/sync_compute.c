#include <stdio.h>
#include <offload.h>
/*
 * 默认情况下, CPU会等待offload代码块执行完之后再去执行下面的语句, 
 * 这里我们使用异步offload计算, 即不等待offload执行完就继续执行下面的指令,
 * 为了实现异步offload计算, 首先要在offload pragma语句中指定一个signal来初始化计算,
 * 然后使用offload_wait显示指定程程序等待offload完成之后再往下执行 
 * 具体的代码可以看test1(), test2()是同步执行
 *
 * Querying a signal before the signal has been initiated results in undefined behavior,
 *  and a runtime abort of the application. For example, consider a query of a signal (SIG1) 
 *  on target device 0, where the signal was actually initiated for target device 1. 
 *  The signal was initiated for target device 1, so there is no signal (SIG1) associated with target device 0,
 *  and therefore the application aborts.
 */

void test1() {
	char signal_var;
	//需要指定mic卡的编号
#pragma offload target(mic:0)signal(&signal_var)
	{
		long long i;
		long long t;
		for(i = 0; i < 1000000000; i++) {
			t += i;
			t += i * 2;
			t += i * 3;
			t +=i %2;
			t += i %3;
		}
		printf("t is %lld\n", t);
	}

	int j = 0;
	for(j = 0; j < 100000; j++) {}
	printf("j is %d\n", j);

#pragma offload_wait target(mic:0) wait(&signal_var)

	printf("after wait\n");
}

void test2() {

#pragma offload target(mic:0)
	{
	    long long i;
		long long t;
		for(i = 0; i < 1000000000; i++) {
			t += i;
			t += i * 2;
			t += i * 3;
			t +=i %2;
			t += i %3;
		}
		printf("t is %lld\n", t);
	}

	int j = 0;
	for(j = 0; j < 100000; j++) {}
	printf("j is %d\n", j);
	printf("after wait\n");
}

int main() {
	test1();
	//test2();
}
