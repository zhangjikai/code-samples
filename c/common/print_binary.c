#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
 * 打印二进制
 * t: 要打印的数字
 * bit_len: 二进制的长度,不要超过64位
 */
void print_binary(uint64_t t, int bit_len) {
	short buffer[bit_len];
	int i;
	
	for(i = 0; i < bit_len; i++) {
		buffer[i] = 0;
	}
	
	for (i = 0; i < bit_len; i++) {
		if (t == 0)
			break;
		if (t % 2 == 0) {
			buffer[i] = 0;
		} else {
			buffer[i] = 1;
		}
		t = t / 2;
	}
	for (i = bit_len - 1; i >= 0; i--) {
		printf("%hd", buffer[i]);
	}
}

void main() {
	print_binary(1234, 8);
	printf("\n");
	print_binary(1234, 16);
	printf("\n");
	print_binary(1234, 32);
	printf("\n");
	print_binary(1234, 64);
	printf("\n");
}

