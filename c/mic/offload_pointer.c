#include <stdio.h>
#include <stdlib.h>

/**
 * 上传指针
 *
 */

void offload_point() {
    int n = 10;
    int *arr =(int*) calloc(n, sizeof(int));
    int *arr2 = (int*) calloc(n, sizeof(int));
    int *arr3 = (int*) calloc(n, sizeof(int));
    int i;
    
    for(i = 0; i < n; i++) {
        arr[i] = i;
        arr2[i] = n + i;
        arr3[i] = n * 2 + i;
    }
    
    //需要注意:上传指针定义的数组时 1:要指定length或者[start:length]属性 2:要显示用in
    #pragma offload target(mic) in(arr:length(n)) in (arr2[2:3]) in (arr3:length(3))
    {
        for(i = 0; i < n; i++) {
            printf(" arr[%d] is  %d\n",i, arr[i]);
        }
        
        printf("==========================\n");
        
        for(i = 0; i < n; i++) {
            printf("arr2[%d] is %d \n",i, arr2[i]);
        }
        
        printf("==========================\n");
        
        for(i = 0; i < n; i++) {
            printf("arr3[%d] is %d \n", i, arr3[i]);
        }
    }
    
    free(arr);
    free(arr2);
    free(arr3);
}

int main() {
    offload_point();
    return 0;
}
