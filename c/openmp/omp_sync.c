#include <stdio.h>
#include <omp.h>
#include "timer.h"
/**
 * 记录一些同步相关的指令或者函数, 包括barrier, ordered, critical, atomic, lock
 */

void print_time(int tid, char* s ) {
    int len = 10;
    char buf[len];
    NOW_TIME(buf, len);
    printf("Thread %d %s at %s\n", tid, s, buf);

}
void test_barrier() {
    int tid;
#pragma omp parallel private(tid)
    {
        tid = omp_get_thread_num();
        if(tid < omp_get_num_threads() / 2)
            system("sleep 3");
        print_time(tid, "before barrier ");
        
        #pragma omp barrier

        print_time(tid, "after  barrier ");
    }

}

void test_order() {
    int i, tid, n = 5;
    int a[n];
    for(i = 0; i < n; i++) {
        a[i] = 0;
    }

#pragma omp parallel for default(none) ordered  schedule(dynamic) \
    private (i, tid) shared(n, a)
    for(i = 0; i < n; i++) {
        tid = omp_get_thread_num();
        printf("Thread %d updates a[%d]\n", tid, i);

        a[i] += i;

        #pragma omp ordered
        {
            printf("Thread %d printf value of a[%d] = %d\n", tid, i, a[i]);
        }
    }
}

void test_critical() {
    int n = 100, sum = 0, sumLocal, i, tid;
    int a[n];
    for(i = 0; i < n; i++) {
        a[i] = i;
    }

#pragma omp parallel shared(n, a, sum) private (tid, sumLocal)
    {
        tid = omp_get_thread_num();
        sumLocal = 0;
        #pragma omp for
        for(i = 0; i < n; i++) {
            sumLocal += a[i];
        }

        //#pragma omp critical(update_sum) 
        {
            sum += sumLocal;
            printf("Thread %d: sumLocal = %d sum =%d\n", tid, sumLocal, sum);
        }
    }

    printf("Value of sum after parallel region: %d\n",sum);
}

void test_atomic() {
    int counter=0, n = 1000000, i;

#pragma omp parallel for shared(counter, n)
    for(i = 0; i < n; i++) {
        #pragma omp atomic
        counter += 1;
    }

    printf("counter is %d\n", counter);
}

void test_lock() {
    omp_lock_t lock;
    int i,n = 4;
    omp_init_lock(&lock);
#pragma omp parallel for
    for(i = 0; i < n; i++) {
        omp_set_lock(&lock);
        printf("Thread %d: +\n", omp_get_thread_num());
        system("sleep 0.1");
        printf("Thread %d: -\n", omp_get_thread_num());
        omp_unset_lock(&lock);
    }
    omp_destroy_lock(&lock);
}

void test_master() {
    int a, i, n = 5;
    int b[n];
#pragma omp parallel shared(a, b) private(i)
    {
        #pragma omp master
        {
            a = 10;
            printf("Master construct is executed by thread %d\n", omp_get_thread_num());
        }
        #pragma omp barrier

        #pragma omp for
        for(i = 0; i < n; i++)
            b[i] = a;
    }

    printf("After the parallel region:\n");
    for(i = 0; i < n; i++)
        printf("b[%d] = %d\n", i, b[i]);
}
int main() {
    //test_barrier();
    //test_order();
    //test_critical();
    //test_atomic();
    //test_lock();
    test_master();
    return 0;
}
