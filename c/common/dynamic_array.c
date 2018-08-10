#include <stdio.h>
#include <stdlib.h>

/*
 * 定义一维数组
 */
void defOneDimArray(int n) {
    int *arr = (int*)calloc(n, sizeof(int));
    int i;
    for(i = 0; i < n; i++) {
        arr[i] = i;
    }

    for(i = 0; i < n; i++) {
        printf("i is %d and arr[i] is %d \n", i, arr[i]);
    }
    //需要释放内存
    free(arr);
}

void defTwoDimArray(int m, int n) {
    int **arr = (int **)calloc(m, sizeof(int *));
    int i, j, index = 0;
    for(i = 0; i < m; i++) {
        arr[i] = (int *) (calloc(n, sizeof(int)));
    }

    for(i = 0; i < m; i++) {
        for(j = 0; j < n; j++) {
            arr[i][j] = index;
            index++;
        }
    }

    for(i = 0; i < m; i++) {
        for(j = 0; j < n; j++) {
            printf("i is %d and j is %d and arr[i][j] is %d\n", i, j, arr[i][j]);
        }
    }

    for(i = 0; i < m; i++) {
        free(arr[i]);
    }
    free(arr);
}

int main() {
    defOneDimArray(10);
    defTwoDimArray(2, 3);
}
