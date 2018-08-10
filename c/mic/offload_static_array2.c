#include <stdio.h>
#include <stdlib.h>

/**
 * 上传二维静态数组
 *
 */

void offload_two_dim_array(int n) {
    int arr[n][n];
    int arr2[n][n];
    int arr3[n][n];
    int arr4[n][n];
    int i, j, index = 0;
    for(i = 0; i < n; i++) {
        for(j = 0; j < n; j++) {
            arr[i][j] = index;
            arr2[i][j] = n * n + index;
            arr3[i][j] = 2 * n * n + index;
            arr4[i][j] = 3 * n * n + index;
            index++;
        }
    }
    
    //上传arr的全部值,上传arr2的前5个值(整体看为长度为n*n的一维数组,取前5个值),上传arr3中[0-1][0-(n-1)]的值,
    //不加后面的y的维度,默认y的是1-(n-1), 上传arr4中[0-1][0-1]的值
    #pragma offload target(mic) in(arr) in(arr2:length(5)) in(arr3[0:2]) in(arr4[0:2][0:2])
    {
        for(i = 0; i < n; i++) {
            for(j = 0; j < n; j++) {
                printf(" arr[%d][%d] is %d\n", i, j, arr[i][j]);
            }
        }
        
        printf("==========================\n");
        
        for(i = 0; i < n; i++) {
            for(j = 0; j < n; j++) {
                printf("arr2[%d][%d] is %d\n", i, j, arr2[i][j]);
            }
        }
        
        printf("==========================\n");
        
        for(i = 0; i < n; i++) {
            for(j = 0; j < n; j++) {
                printf("arr3[%d][%d] is %d\n", i, j,  arr3[i][j]);
            }
        }
        
        printf("==========================\n");
        
        for(i = 0; i < n; i++) {
            for(j = 0; j < n; j++) {
                printf("arr4[%d][%d] is %d\n", i, j, arr4[i][j]);
            }
        }
    }
}

int main() {
    offload_two_dim_array(3);
    return 0;
}
