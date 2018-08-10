#include <stdio.h>

/**
 * 使用offload时, host和target看为两块独立的内存, 使用in, out, inout传递数据
 *
 * in: 指定一个变量从host端复制到target端(作为target的输入), 但是不从target端复制回host端
 * out: 指定一个变量从target端复制回host端(作为target的输出), 但是不从host段复制到target端
 * inout: 指定一个变量即从host端复制到target端, 也从target段复制回host端(即是输入又是输出).
 *
 */

int main() {
    int inVar = 10;
    int outVar = 20;
    int inoutVar = 30;
    
    #pragma offload target(mic) in(inVar) out(outVar)
    {
        printf("inVar in MIC is %d\n", inVar);
        printf("outVar in MIC is %d\n", outVar);
        // 这里用到了inoutVar, 但是offload时并没有指定它的说明符, 则用默认的inout
        printf("inoutVar in MIC is %d\n", inoutVar);
        
        inVar = 100;
        outVar = 200;
        inoutVar = 300;
    }
    
    printf("inVar in CPU is %d\n", inVar);
    printf("outVar in CPU is %d\n", outVar);
    printf("inoutVar in CPU is %d\n", inoutVar);
}
