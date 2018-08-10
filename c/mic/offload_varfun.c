#include <stdio.h>

#define __ONMIC__ __attribute__((target(mic)))

/**
 *  声明MIC上可以使用的全局变量和函数, 声明方式如下
 *
 *	__declspec( target (mic)) function-declaration
 *  __declspec( target (mic)) variable-declaration
 *  __attribute__ (( target (mic))) function-declaration
 *  __attribute__ (( target (mic))) variable-declaration
 * 
 *  其中__declspec可以用于windows或者linux系统, 而_attribute__只能用于linux.
 *
 */

__ONMIC__ int i;

__ONMIC__ void f(int n) {
    printf("n*n is %d\n", n*n);
}

int main() {
    
    #pragma offload target(mic)
    {
        i = 100;
        f(i);
    }
    
    printf("i is %d\n", i);
}
