#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include <pthread.h>
#include <semaphore.h>


void * p_result(void * arg) {
    char * m = malloc(sizeof(char) * 3);
    m[0] = 'A';
    m[1] = 'B';
    m[2] = 'C';
    return m;
}

// 获得线程执行返回值（正常执行）
void test_get_result() {
    pthread_t thread_id;
    void * exit_status ;
    pthread_create(&thread_id, NULL, p_result, NULL);
    pthread_join(thread_id, & exit_status);
    char * m = (char* ) exit_status;
    printf("m is %s\n", m);
    free(m);
}

void * p_exit_result(void * arg) {
    printf("print before pthread_exit\n");
    pthread_exit((void *)10L);
    printf("print after pthread_exit\n");
    return NULL;
}

// 线程调用pthread_exit结束线程
void test_exit_result() {
    pthread_t thread_id;
    void * exit_status ;
    pthread_create(&thread_id, NULL, p_exit_result, NULL);
    pthread_join(thread_id, & exit_status);
    long m = (long ) exit_status;
    printf("m is %ld\n", m);
}


pthread_mutex_t lock;
int share_data;
void * p_lock(void * arg) {
    int i;
    for(i = 0; i < 1024 * 1024; i++) {
        pthread_mutex_lock(&lock);
        share_data++;
        pthread_mutex_unlock(&lock);
    }
    return NULL;
}

// 使用互斥锁
void test_lock() {
    pthread_t thread_id;
    void *exit_status;
    int i;
    pthread_mutex_init(&lock, NULL);
    pthread_create(&thread_id, NULL, p_lock, NULL);
    for(i = 0; i < 10; i++) {
        //sleep(1);
        pthread_mutex_lock(&lock);
        printf("Shared integer's value = %d\n", share_data);
        pthread_mutex_unlock(&lock);
    }
    printf("\n");
    pthread_join(thread_id, & exit_status);
    pthread_mutex_destroy(&lock);
}

// 设置互斥锁的属性
void test_lock2() {
    pthread_t thread_id;
    void *exit_status;
    int i;
    pthread_mutexattr_t attr;
    pthread_mutexattr_init(&attr);
    pthread_mutexattr_settype(&attr, PTHREAD_MUTEX_TIMED_NP);
    pthread_mutex_init(&lock, &attr);
    pthread_create(&thread_id, NULL, p_lock, NULL);
    for(i = 0; i < 10; i++) {
        //sleep(1);
        pthread_mutex_lock(&lock);
        printf("Shared integer's value = %d\n", share_data);
        pthread_mutex_unlock(&lock);
    }
    printf("\n");
    pthread_join(thread_id, & exit_status);
    pthread_mutexattr_destroy(&attr);
    pthread_mutex_destroy(&lock);
}



pthread_cond_t is_zero;
pthread_mutex_t mutex;
int con_share_data = 32767;

void * p_condition(void * arg) {
    while(con_share_data > 0) {
        pthread_mutex_lock(&mutex);
        con_share_data--;
        pthread_mutex_unlock(&mutex);
    }
    pthread_cond_signal(&is_zero);
}

// 使用条件变量
void test_condition() {
    pthread_t thread_id;
    void *exit_status;
    int i;
    pthread_cond_init(&is_zero, NULL);
    pthread_mutex_init(&mutex, NULL);
    pthread_create(&thread_id, NULL, p_condition, NULL);
    pthread_mutex_lock(&mutex);
    while(con_share_data != 0) {
        pthread_cond_wait(& is_zero, &mutex);
    }
    pthread_mutex_unlock(&mutex);
    pthread_join(thread_id, &exit_status);
    pthread_mutex_destroy(&mutex);
    pthread_cond_destroy(&is_zero);
}

int sem_share_data = 0;
// use like a mutex
sem_t binary_sem; 

void * p_sem(void * arg) {
    sem_wait(&binary_sem);     // 减少信号量
    // 在这里使用共享数据; 
    sem_post(&binary_sem);     // 增加信号量
}

// 使用信号量
void test_sem() {
    sem_init(&binary_sem, 0, 1);   // 信号量初始化为1, 当初互斥锁使用
    // 在这里创建线程
    sem_wait(&binary_sem);
    // 在这里使用共享变量
    sem_post(&binary_sem);
    // 在这里join线程
    sem_destroy(&binary_sem);
}

pthread_rwlock_t rw_lock;

void * p_rwlock(void * arg) {
    pthread_rwlock_rdlock(&rw_lock);
    // 读取共享变量
    pthread_rwlock_unlock(&rw_lock);
    
} 

// 使用读写锁
void  test_rwlock() {
    pthread_rwlock_init(&rw_lock, NULL);
    // 创建线程
    pthread_rwlock_wrlock(&rw_lock);
    // 修改共享变量
    pthread_rwlock_unlock(&rw_lock);
    // join线程
    pthread_rwlock_destroy(&rw_lock);
}

int main() {
    //hello_world();
    //test_get_result();
    //test_lock();
    //test_condition();
    //test_sem();
    //test_rwlock();
   // test_exit_result();
   test_lock2();
    return 0;
}
