/**
 * C格式化输出
 */

#include <stdio.h>

int main() {
    int i = 10;
    float f = 10.0F;
    char c = 'D';
    char s[] = "DDDD";
    char *s1 = "CCCC";
    long k = 100L;
    long long kk = 100L;
    long double ld = 100.0;


    //输出有符号十进制整数
    printf("**%d**\n", i);
    printf("**%i**\n", i);

    //输出无符号十进制整数
    printf("**%u**\n", i);

    //输出无符号八进制整数
    printf("**%o**\n", i);

    //输出无符号十六进制整数(x-小写字符/X-大写字符)
    printf("**%x**\n", i);
    printf("**%X**\n", i);

    //输出浮点数,十进制计数法
    printf("**%f**\n", f);

    // %.nf n保留n位小数
    printf("**%.2f**\n", f);

    //输出浮点数 e--e计数法/E--E计数法
    printf("**%e**\n", f);
    printf("**%E**\n", f);

    //输出字符
    printf("**%c**\n", c);

    //输出字符串
    printf("**%s**\n", s);

    //输出指针
    printf("**%p**\n",s1);

    //打印一个百分号
    printf("**%%**\n");

    // h 和整数转换符一起使用,表示是一个short int 或者unsigned short int
    printf("**%hd**\n", i);

    // l 和整数转换说明符一起使用,说明是一个long int或者 unsigned long int
    printf("**%ld**\n", k);

    // ll 和整数转换说明符一起使用,说明是一个long long int 或者 unsigned long long int
    printf("**%lld**\n", kk);

    // L 和浮点转换说明符一起使用,表示一个long double值
    printf("**%Lf**\n", ld);

    // z 和整数转换说明符一起使用,表示一个size_t值(sizeof返回的类型)
    printf("**%zd**\n", sizeof(int));

    // %n(d/f/...)  n表示输出的最小宽度,默认是右对齐,不足补空格
    printf("**%4d**\n", i);

    // %n.ms n表示最小宽度,m表示字符串输出的字符数目
    printf("**%4.1s**\n", s);

    // %-n(d/f/...) -表示左对齐
    printf("**%-4d**\n", i);

    // %+(d/f/...) +在有符号的数字输出时加上 +/- 号
    printf("**%+d**\n", i);

    // % (d/f/...)  ' '(空格)对于有符号数, 符号为+显示前导空格,符号为负显示-
    printf("**% d**\n", i);

    // %#(o/x/...)使用转化说明的可选形式,如果8进制,则以0开头,16进制以0x开头
    printf("**%#x**\n", i);

    // %0n(d/f/...) n前面的0表示使用空格填充剩余位
    printf("**%04d**\n", i);

}
