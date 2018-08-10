#include <stdio.h>
#include <stdlib.h>
/**
 * 传输包含指针变量的struct
 */

struct my_struct {
    int y;
    int *a;
};

void offload_struct() {
    struct my_struct m;
    m.y = 10;
    m.a =(int*) calloc(10, sizeof(int));
    
    int i;
    for(i=0; i < 10; i++) {
        m.a[i] = i;
    }
    
    //struct中有指针变量时要单独传指针变量
    #pragma offload target(mic) in(m) in(m.a:length(10))
    {
        printf("offload_struct: the struct.y is %d\n", m.y);
        printf("offload_struct: the struct.a[1] is %d\n", m.a[1]);
    }
    free(m.a);
}

int main() {
    offload_struct();
    return 0;
}
