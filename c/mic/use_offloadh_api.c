#include <stdio.h>
#include <stdlib.h>
#include <offload.h>

//使用offload.h中的api
int main() {
	
	//获得系统中协处理器的数量,不需要offload到mic上
	int i = _Offload_number_of_devices();
    printf("the devices of mic is %d\n", i);

	// mic:1 执行mic卡的编号, 不指定则使用0号
#pragma offload target(mic:1) 
	{
		//获得当前使用的协处理器编号,如果不offload到mic上,而是在cpu上执行,会返回-1.0代表第一块卡
		int j = _Offload_get_device_number();
		printf("the index of mic is %d\n", j);	
	}
}
