#include <stdio.h>
#include <stdlib.h>

/**
 * 记录fseek, ftell, fread, fwrite, rewind的用法
 *
 * 详细可查看 http://blog.zhangjikai.com/2016/03/04/%E3%80%90C%E3%80%91%E6%96%87%E4%BB%B6%E6%93%8D%E4%BD%9C(%E4%BA%8C)/
 */


// 获得文件大小
long file_size(char *fileName) {
    long size;
    FILE *fp;
    if((fp = fopen(fileName, "rb")) == NULL) {
        printf("can't open file %s\n", fileName);
        exit(EXIT_FAILURE);
    }
    // fseek 移动文件 SEEK_SET 开始位置, SEEK_CUR 当前位置, SEEK_END 结束位置
    fseek (fp, 0 , SEEK_END);
    // ftell 获得当前位置到文件开头的字节数目
    size = ftell(fp);

    double mb;
    mb = size * 1.0 / 1024 / 1024;
    printf("file size is %ldB and %.2fMB \n", size, mb); 
    fclose(fp);
    return size;
}

void file_fread(char *fileName) {
    long count;
    int *buffer;
    FILE *fp;
    if((fp = fopen(fileName, "rb")) == NULL) {
        printf("can't open file %s\n", fileName);
        exit(EXIT_FAILURE);
    }
    fseek (fp, 0 , SEEK_END);
    count = ftell(fp);
    // 重置文件指针到文件开头
    rewind(fp);
    buffer =(int *) malloc(sizeof(int) * count);

    // size_t fread ( void * ptr, size_t size, size_t count, FILE * stream );
    fread(buffer, sizeof(int), count / sizeof(int), fp);
    
    int i;
    for(i = 0; i < count/ sizeof(int); i++) {
        printf("%d\n", buffer[i]);
    }
    fclose(fp);
    free(buffer);
}

void file_fwrite(char *fileName) {
    FILE *fp;
    if((fp = fopen(fileName, "w+b")) == NULL) {
        printf("can't open file %s\n", fileName);
        exit(EXIT_FAILURE);
    }
    int count = 20, i;
    int *buffer;
    buffer = malloc(sizeof(int) * count);
    for(i = 0; i < count; i++) {
        buffer[i] = i;
    }
    
    long success_num = 0;

    // size_t fwrite ( const void * ptr, size_t size, size_t count, FILE * stream );
    success_num = fwrite(buffer, sizeof(int) , count, fp);
    printf("success_num is %ld\n", success_num);

    fclose(fp);
    free(buffer);
}

int main(void) {
    file_size("test.txt");

}
