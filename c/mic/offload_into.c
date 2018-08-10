#include <stdio.h>

/**
 * 使用into 将一个变量的值传到另外一个变量中
 * 注意事项: into只能用于in 或者 out中, 不可用于inout或者nocopy中
 */

void init_array(int* arr, int n, int start_num){
	int i;
	for(i = 0; i < n; i++) {
		arr[i] = start_num + i;
	}
}

void use_into() {
	int n = 3, i;
	int p[n], p1[n];

	init_array(p, n, 0);
	init_array(p1, n, n);

	for(i = 0; i < n; i++) {
		printf("before offload:  p[%d] is %d\n", i, p[i]);
	}
	for(i = 0; i < n; i++) {
		printf("before offload: p1[%d] is %d\n", i, p1[i]);
	}

	printf("==============================\n");
//into 将一个变量的值上传到另外一个变量中,如下在mic上p没有值,只有p1有值, 调用out之后原先p1的值会改变
#pragma offload target(mic) in(p[0:n] : into(p1[0:n])) out(p1)
	{
		for(i = 0; i < n; i++) {
			printf("On Mic:  p[%d] is %d\n", i, p[i]);
		}
		for(i = 0; i < n; i++) {
			printf("On Mic: p1[%d] is %d\n", i, p1[i]);
		}
	}

	for(i = 0; i < n; i++) {
		printf("after offload:  p[%d] is %d\n", i, p[i]);
	}
	for(i = 0; i < n; i++) {
		printf("after offload: p1[%d] is %d\n", i, p1[i]);
	}

	printf("==============================\n");
}

void use_into2() {
	int n = 4, i;
	int p[n], p1[n+1], p2[n-1];

	init_array(p, n, 0);
	init_array(p1, n+1, n);
	init_array(p2, n-1, 2*n+1);

	for(i = 0; i < n; i++) {
		printf("before offload:	 p[%d] is %d\n", i, p[i]);
	}
	for(i = 0; i < n+1; i++) {
		printf("before offload: p1[%d] is %d\n", i, p1[i]);
	}
	for(i = 0; i < n-1; i++) {
		printf("before offload: p2[%d] is %d\n", i, p2[i]);
	}
	printf("==============================\n");
	// 当数组长度不一样时, 当length(p) < length(p1)时, p1数组多余的部分会补0
	// 当length(p) > length(p2)时, in的时候需要注意p的长度不可大于p2的长度
#pragma offload target(mic) in(p[0:n]:into(p1[0:n+1])) in(p[0:n-1]:into(p2[0:n-1])) out(p1) out(p2)
	{
		for(i = 0; i < n; i++) {
			printf("on mic:  p[%d] is %d\n", i, p[i]);
		}
		for(i = 0; i < n+1; i++) {
			printf("on mic: p1[%d] is %d\n", i, p1[i]);
		}
		for(i = 0; i < n-1; i++) {
			printf("on mic: p2[%d] is %d\n", i, p2[i]);
		}
	}

	for(i = 0; i < n; i++) {
		printf("after offload:  p[%d] is %d\n", i, p[i]);
	}
	for(i = 0; i < n+1; i++) {
		printf("after offload: p1[%d] is %d\n", i, p1[i]);
	}
	for(i = 0; i < n-1; i++) {
		printf("after offload: p2[%d] is %d\n", i, p2[i]);
	}
	printf("==============================\n");
}

// 将一维数组放到二维数组里以及二维数组放到一维数组,
// 文档中说不可以, 但是这里确实可以使用
void use_into3() {
	int n = 10, i;
	int p[n * n];
	int a[n][n];

	init_array(p, n * n, 0);
#pragma offload target(mic)  in(p:into(a)) out(a:into(p))
	{
		for(i = 0; i < n; i++) {
			printf("on mic: a[%d][0] is %d\n", i, a[i][0]);

		}

		// 相当于p[0]
		a[0][0] = 1000;
		// 相当于p[10]
		a[1][0] = 1000;
	}

	printf("p[0] is %d and p[10] is %d\n", p[0], p[10]);
	printf("==============================\n");
}

int main() {
	use_into();
	//use_into2();
	//use_into3();
}
