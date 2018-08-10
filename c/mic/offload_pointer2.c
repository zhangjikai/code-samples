#include <stdio.h>
#include <stdlib.h>

typedef int ARRAY[5];
/**
 * 指针定义的数组中的每个元素是一个静态数组
 *
 */

//下面相当于上传了一个二维数组
void offload_point2() {
    int n = 3;
    ARRAY *arr = (ARRAY*)calloc(n, sizeof(ARRAY));
    ARRAY *arr2 = (ARRAY*)calloc(n, sizeof(ARRAY));
    int i, j, index = 0;
    for(i = 0; i < n; i++) {
        for(j = 0; j < 5; j++) {
            arr[i][j] = index;
            arr2[i][j] = n *n + index;
            index++;
        }
    }
    
    #pragma offload target(mic) in(arr:length(n))  in (arr2[0:2][0:2])
    {
        for(i = 0; i < n; i++) {
            for(j = 0; j < 5; j++) {
                printf(" arr[%d][%d] is %d \n", i, j , arr[i][j]);
            }
        }
        
        printf("==========================\n");
        
        for(i = 0; i < n; i++) {
            for(j = 0; j < 5; j++) {
                printf("arr2[%d][%d] is %d \n", i, j , arr2[i][j]);
            }
        }
    }
    
    free(arr);
    free(arr2);
}

int main() {
    offload_point2();
    return 0;
}
