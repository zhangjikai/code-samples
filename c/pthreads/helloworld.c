#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

void * hello(void * args) {
    long  rank = (long) args;
    printf("Hello form sub thread %ld\n", rank);
    return NULL;
}
int main() {
    int thread_num = 4;
    long thread_index;
    pthread_t * thread_handles;

    thread_handles =(pthread_t *) malloc(sizeof(pthread_t ) * thread_num);
    
    for(thread_index = 0; thread_index < thread_num; thread_index++) {
        pthread_create(&thread_handles[thread_index], NULL, hello, (void *)thread_index);
    }

    printf("hello from main thread\n");

    for(thread_index = 0; thread_index < thread_num; thread_index++) {
        pthread_join(thread_handles[thread_index], NULL);
    }
    free(thread_handles);
    return 0;
}
