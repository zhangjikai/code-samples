#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <getopt.h>

/**
 * 解析命令行参数 getopt 和 getopt_long 用法
 *
 * 详细可查看 http://blog.zhangjikai.com/2016/03/05/%E3%80%90C%E3%80%91%E8%A7%A3%E6%9E%90%E5%91%BD%E4%BB%A4%E8%A1%8C%E5%8F%82%E6%95%B0--getopt%E5%92%8Cgetopt_long/
 */

// 解析短选项
void use_getopt(int argc, char **argv) {
    int ch;
    opterr = 0;
    while((ch = getopt(argc, argv, "a:b")) != -1) {
        switch(ch) {
            case 'a':
                printf("option a: %s\n", optarg);
                break;
            case 'b':
                printf("option b \n");
                break;
            case '?': // 输入为定义的选项, 都会将该选项的值变为 ?
                printf("unknown option \n");
                break;
            default:
                printf("default \n");
        }
    }

}

// 解析长选项
void use_getpot_long(int argc, char *argv[]) {
    const char *optstring = "vn:h";
    int c;
    struct option opts[] = {
        {"version", 0, NULL, 'v'},
        {"name", 1, NULL, 'n'},
        {"help", 0, NULL, 'h'}
    };

    while((c = getopt_long(argc, argv, optstring, opts, NULL)) != -1) {
        switch(c) {
            case 'n':
                printf("username is %s\n", optarg);
                break;
            case 'v':
                printf("version is 0.0.1\n");
                break;
            case 'h':
                printf("this is help\n");
                break;
            case '?':
                printf("unknown option\n");
                break;
            case 0 :
                printf("the return val is 0\n");
                break;
            default:
                printf("------\n");

        }
    }
}


void use_getpot_long2(int argc, char *argv[]) {
    const char *optstring = "vn:h";
    int c;

    int f_v = -1, f_n = -1, f_h = -1, opt_index = -1; 
    struct option opts[] = {
        {"version", 0, &f_v, 'v'},
        {"name", 1, &f_n, 'n'},
        {"help", 0, &f_h, 'h'}
    };

    while((c = getopt_long(argc, argv, optstring, opts, &opt_index)) != -1) {
        switch(c) {
            case 'n':
                printf("username is %s\n", optarg);
                break;
            case 'v':
                printf("version is 0.0.1\n");
                break;
            case 'h':
                printf("this is help\n");
                break;
            case '?':
                printf("unknown option\n");
                break;
            case 0 :
                printf("f_v is %d \n", f_v);
                printf("f_n is %d \n", f_n);
                printf("f_h is %d \n", f_h);
                break;
            default:
                printf("------\n");
        }
        printf("opt_index is %d\n\n", opt_index);
    }
}

// 和getopt_long类似, 不同的是getopt_long_only 可以接受长选项前面跟 - 
void use_getpot_long_only(int argc, char *argv[]) {
    const char *optstring = "vn:h";
    int c;
    struct option opts[] = {
        {"version", 0, NULL, 'v'},
        {"name", 1, NULL, 'n'},
        {"help", 0, NULL, 'h'}
    };

    while((c = getopt_long_only(argc, argv, optstring, opts, NULL)) != -1) {
        switch(c) {
            case 'n':
                printf("username is %s\n", optarg);
                break;
            case 'v':
                printf("version is 0.0.1\n");
                break;
            case 'h':
                printf("this is help\n");
                break;
            case '?':
                printf("unknown option\n");
                break;
            case 0 :
                printf("the return val is 0\n");
                break;
            default:
                printf("------\n");

        }
    }
}


int main(int argc, char **argv) {
    //use_getpot_long(argc, argv);
    use_getopt(argc, argv)
}
