#include <stdio.h>

__attribute__ (( target (mic))) void say_hello() {
    //如果有__MIC__的宏定义, 证明是在MIC端运行的
    #ifdef __MIC__
        printf("Hello from MIC n");
    #else
        printf("Hello from CPU n");
    #endif
}

int main() {
    #pragma offload target(mic)
    say_hello();
}