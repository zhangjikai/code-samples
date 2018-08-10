#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

/**
 * 记录openmp中子句的用法
 *
 * 包括shared, private, default, firstprivate, lastprivate, nowait, schedule, if, reduction, copyin, copyprivate, num_threads
 */

void omp_shared_private_default() {
    int i, n = 10;
#pragma omp parallel for default(none) \
    private(i) shared(n)
    for(i = 0; i < n; i++) {
        printf("thread %d execute loop %d\n",  omp_get_thread_num(), i);
    }

}

void omp_firstprivate() {
    int n = 8;
    int i=0, a[n];

    for(i = 0; i < n ;i++) {
        a[i] = i+1;
    }
#pragma omp parallel for private(i) firstprivate(a)
    for ( i = 0; i<n; i++)
    {
        printf("thread %d: a[%d] is %d\n", omp_get_thread_num(), i, a[i]);
    }
}

void omp_lastprivate() {
    int n = 8;
    int i, a = 3;
    // lastprivate 将for中最后一次循环(i == n-1) a 的值赋给a    
#pragma omp parallel for private(i) lastprivate(a)
    for ( i = 0; i<n; i++)
    {
        a = i+1;
        printf("In for: thread %d has a value of a = %d for i = %d\n", omp_get_thread_num(),a,i);
    }

    printf("\n");
    printf("Out for: thread %d has a value of a = %d\n", omp_get_thread_num(),a);

    printf("\n");
    // lastprivate 将最后一个包含a的section的中a 的值赋给a    
#pragma omp parallel sections private(i) lastprivate(a)
    {
        #pragma omp section
        {
            a = 1;
        }
        #pragma omp section
        {
            a = 5;
        }
    }
    printf("after section: thread  %d has a value of a = %d\n", omp_get_thread_num(),a);
}

void omp_nowait() {
    int i, n =6;
    #pragma omp parallel 
    {
        #pragma omp for nowait
        for(i = 0; i < n; i++) {
            printf("thread %d: ++++\n", omp_get_thread_num());
        }

        #pragma omp for
        for(i = 0; i < n; i++) {
            printf("thread %d: ----\n", omp_get_thread_num());
        }
    }
}

void omp_schedule() {
    int i, n = 10;

    // schedule 取值 static, dynamic, guided, auto, runtime
#pragma omp parallel for default(none) schedule(static, 2) \
    private(i) shared(n)
    for(i = 0; i < n; i++) {
        printf("Iteration %d executed by thread %d\n", i, omp_get_thread_num());
    }
}

void omp_if() {
    int n = 1, tid;
    printf("n = 1\n");
#pragma omp parallel if(n>5) default(none) \
    private(tid) shared(n)
    {
        tid = omp_get_thread_num();
        printf("thread %d is running\n", tid);
    }

    printf("\n");

    n = 10;
    printf("n = 10\n");

#pragma omp parallel if(n>5) default(none) \
    private(tid) shared(n)
    {
        tid = omp_get_thread_num();
        printf("thread %d is running\n", tid);
    }
}

void omp_reduction() {
    int sum, i;
    int n = 10;
    int a[n];
    for(i = 0; i < n; i++) {
        a[i] = (i+1);
    }

#pragma omp parallel for default(none) \
    private(i) shared(a,n) reduction(+:sum)
    for(i = 0; i < n; i++) {
        sum += a[i];
    }
    
    printf("sum is %d\n", sum);
}

int counter = 10;
#pragma omp threadprivate(counter)

void omp_copyin() {
    printf("counter is %d\n", counter);
    #pragma omp parallel copyin(counter) 
    {
          
        counter = omp_get_thread_num() + counter + 1;
        printf("thread %d : counter is %d\n", omp_get_thread_num(), counter);
    }
    printf("counter is %d\n", counter);
}

void omp_copyprivate() {
    int i;
#pragma omp parallel private(i)
    {
        #pragma omp single copyprivate(i, counter) 
        {
            i = 50;
            counter = 100;
            printf("thread %d execute single\n", omp_get_thread_num());
        }
        printf("thread %d: i is %d and counter is %d\n",omp_get_thread_num(), i, counter);
    }
}

void omp_num_threads() {
#pragma omp parallel num_threads(2)
    {
        printf("thread %d is running\n", omp_get_thread_num());
    }
}

int main() {
    // omp_shared_private_default();
    // omp_firstprivate();
    // omp_lastprivate();
    // omp_nowait();
    // omp_schedule();
    // omp_if();
    // omp_reduction();
    // omp_copyin();
    // omp_copyprivate();
    omp_num_threads();
}
