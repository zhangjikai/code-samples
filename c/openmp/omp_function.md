```C
// 设置线程数量
omp_set_num_threads(number)

// 获得运行的线程数量, 在并行域外执行返回1 
omp_get_num_threads()

// 获得可以使用的最大线程数量, 数量是确定的, 与在串行域还是并行域调用无关
omp_get_max_threads()

// 获得当前线程编号
omp_get_thread_num()

// 设置是否可以动态调整线程数量
omp_set_dynamic(0或1)

// 获得当前运行环境是否可以动态调整线程数量, 返回0或1
omp_get_dynamic()

// 设置是否允许嵌套并行
omp_set_nested(0或1)

// 获得当前运行环境是否允许嵌套并行, 返回0或1
omp_get_nested()

// 获得可以使用的处理器数量
omp_get_num_procs()

// 判断是否在一个活跃的并行域内, 返回0或1
omp_in_parallel()
```
