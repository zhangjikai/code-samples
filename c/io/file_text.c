#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/**
 * c读写普通文件示例
 *
 * 具体介绍可参考 http://blog.zhangjikai.com/2015/12/15/%E3%80%90C%E3%80%91%E6%96%87%E4%BB%B6%E6%93%8D%E4%BD%9C/
 */

/**
 * 向文件中写入单个字符, 使用fputc函数
 */
void w_fputc() {
    FILE *fp;
    fp = fopen("test1.txt", "w+");
    if(fp == NULL) {
        printf("open file fail...\n");
        exit(1);
    }
    char c;
    int num;
    for(c = 'A'; c <= 'Z'; c++) {
       num =  fputc(c, fp);
       printf("num is %d\n", num);
    }
    fclose(fp);
}

/**
 * 向文件中写入一个字符串, 使用fputs函数
 */
void w_fputs() {
    FILE *fp;
    fp = fopen("test.txt", "w+");
    if(fp == NULL) {
        printf("open file fail...\n");
        exit(1);
    }
    char *c = "this is a test...\n";
    char c1[6] = {'A', 'B', 'C', '\0', 'E', 'F'};
    int num;
    num = fputs(c, fp);
    printf("num is %d\n", num);
    num = fputs(c1, fp);
    printf("num is %d\n", num);
    fclose(fp);
}

/**
 * 格式化写入, 使用fprintf函数
 */
void w_fprintf() {
    FILE *fp;
    fp = fopen("test.txt", "w+");
    if(fp == NULL) {
        printf("open file fail...\n");
        exit(1);
    }
    int num;
    int i = 100;
    num = fprintf(fp, "this is a test...\n");
    printf("num is %d\n", num);
    num = fprintf(fp, "i is %d and address of i is %p", i, &i);
    printf("num is %d\n", num);
    fclose(fp);
}

/**
 * 从文件中读取单个字符, 使用fgetc函数
 */
void r_fgetc() {
    FILE *fp;
    fp = fopen("test.txt", "r");
    if(fp == NULL) {
        printf("open file fail...\n");
        exit(1);
    }
    int i, n = 10;
    char c;
    for(i = 0; i < n; i++) {
        c = fgetc(fp);
        printf("%c",c);
    }
    printf("\n");
    fclose(fp);
}

/**
 * 从文件中读取字符串, 使用fgets函数
 */

void r_fgets() {
    FILE *fp;
    fp = fopen("test.txt", "r");
    char c[50];
    fgets(c, 5, fp);
    printf("c is '%s'\n", c);
    printf("c length is %ld\n", strlen(c));
    // 重置文件指针到文件开头
    rewind(fp);
    fgets(c, 20, fp);
    printf("c is '%s'\n", c);
    printf("c length is %ld\n", strlen(c));
    fclose(fp);
}

/**
 * 格式化读入, 使用fscanf函数
 */
void r_fscanf() {
    FILE *fp;
    fp = fopen("test.txt", "r");
    int num[3], i;
    for(i = 0; i < 3; i++) {
        num[i] = 0;
    }
    fscanf(fp, "%d%d", num, num+1);
    for(i = 0; i < 3; i++) {
        printf("num[%d] is %d\n", i, num[i]);
    }
    fclose(fp);
}


int main() {
    //w_fputc();
    //w_fputs();
    //w_fprintf();

    //r_fgetc();
    //r_fgets();
    r_fscanf();
}

