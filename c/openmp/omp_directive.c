#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

/**
 * 记录openmp制导指令用法
 * parallel, for, sections, single, master, threadprivate
 *
 * 具体介绍可以参考 http://blog.zhangjikai.com/tags/OpenMP/
 */

void omp_parallel() {
    #pragma omp parallel
    {
        printf("The parallel region is executed by thread %d\n",
                omp_get_thread_num());
        if ( omp_get_thread_num() == 2 ) {
            printf(" Thread %d does things differently\n",
                    omp_get_thread_num());
        }
    }
}

void omp_for() {
    int n = 9, i;

    #pragma omp parallel for shared(n) private(i) 
    for(i = 0; i < n; i++) {
        printf("Thread %d executes loop iteration %d\n", omp_get_thread_num(),i);
    }
}

/**
 * 使用#pragma omp sections 和 #pragma omp section, 来使不同的线程执行不同的任务
 * 如果线程数量大于section数量, 那么多余的线程会处于空闲状态(idle)
 * 如果线程数量少于section数量, 那么一个线程会执行多个section代码
 */

void funcA() {
    printf("In funcA: this section is executed by thread %d\n",
            omp_get_thread_num());
}

void funcB() {
    printf("In funcB: this section is executed by thread %d\n",
            omp_get_thread_num());
}

void omp_sections() {
    #pragma omp parallel sections
    {
        #pragma omp section 
        {
            (void)funcA();
        }

        #pragma omp section 
        {
            (void)funcB();
        }
    }
}


void omp_single() {
    #pragma omp parallel 
    {
        // 只有一个线程会执行这段代码, 其他线程会等待该线程执行完毕
        #pragma omp single 
        {
            printf("Single construct executed by thread %d\n\n", omp_get_thread_num());
        }

        // A barrier is automatically inserted here
        printf("thread %d is running\n", omp_get_thread_num()); 
    }
}


void omp_master() {
    #pragma omp parallel 
    {
        // 只有主线程执行, 不会自动插入barrier, 需要手动同步
        #pragma omp master 
        {
            printf("master construct executed by thread %d\n\n", omp_get_thread_num());
        }

        #pragma omp barrier
        
        printf("thread %d is running\n", omp_get_thread_num()); 
    }
}

int counter = 10;
#pragma omp threadprivate(counter)

void omp_threadprivate() {
    printf("counter is %d\n", counter);
    #pragma omp parallel copyin(counter) 
    {
        counter = omp_get_thread_num() + counter + 1;
        printf("thread %d : counter is %d\n", omp_get_thread_num(), counter);
    }
    printf("counter is %d\n", counter);
}

int main() {
    // omp_parallel();
    // omp_for();
    // omp_sections();
    // omp_single(); 
    // omp_master();
    omp_threadprivate();
}
