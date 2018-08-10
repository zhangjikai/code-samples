#include <stdio.h>
#include <stdlib.h>
/**
 * 传输一维静态数组到MIC上
 */
void offload_one_dim_array(int n) {
    int arr[n];
    int arr2[n];
    int arr3[n];
    int i;
    
    for(i = 0; i < n; i++) {
        arr[i] = i;
        arr2[i] = n + i;
        arr3[i] = 2 * n + i;
    }
    
    //上传arr的全部元素,上传arr2的前0-4共5(长度为5)个元素,上传arr3的从索引2开始的5个元素(即索引2-6)到mic上
    #pragma offload target(mic) in(arr) in(arr2:length(5)) in(arr3[1:5])
    {
        for(i = 0; i < n; i++) {
            printf(" arr[%d] is %d\n", i, arr[i]);
        }
        
        printf("==========================\n");
        
        for(i = 0; i < n; i++) {
            printf("arr2[%d] is %d\n", i, arr2[i]);
        }
        
        printf("==========================\n");
        
        for(i = 0; i < n; i++) {
            printf("arr3[%d] is %d\n", i, arr3[i]);
        }
    }
}

int main() {
    offload_one_dim_array(10);
    return 0;
} 
