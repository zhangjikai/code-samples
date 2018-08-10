#include <stdio.h>
/**
 * 在共享内存模式下使用二维指针
 */

int **_Cilk_shared p;
_Cilk_shared int n = 3, m = 3;

void init_p() {
	int index = 0, i, j;
	for(i = 0; i < n; i++) {
		for( j = 0; j < m; j++) {
			p[i][j] = index++;
		}
	}
}

_Cilk_shared void print_p() {
	int i, j;
	for(i = 0; i < n; i++) {
		for(j = 0; j < n; j++) {
			printf("print_p: p[%d][%d] is %d\n", i, j, p[i][j]);
		}
	}
}

int main() {
	int i;
	p = (int **) _Offload_shared_malloc(sizeof(int *) * n);
	for( i = 0; i < n ;i++) {
		p[i] =(int *) _Offload_shared_malloc(sizeof(int) * m);
	}

	init_p();
	_Cilk_offload print_p();
	for(i = 0; i < n; i++) {
		_Offload_shared_free(p[i]);
	}

	_Offload_shared_free(p);
}
