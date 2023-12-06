package com.igornoroc.kotlin_investigation

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

/**
 * A trivial performance test
 */

suspend fun performTaskV1(num: Long): Long {
    var count = num
    repeat(1000) {
        count += 3 * 10 / 2 + 612 * 23
    }
    logger.info("finish ${Thread.currentThread().name}")
    return count
}


suspend fun main() = runBlocking {

    repeat(1000) {
        val startTime = System.nanoTime()
        var re: Long = 0;
        val result1 = async { performTaskV1(5465) }
        val result2 = async { performTaskV1(545846) }
        val result3 = async { performTaskV1(54568454346544) }
        val commonResult = awaitAll(result1, result2, result3)
        commonResult.forEach { re += it }
        val finishTime = System.nanoTime()
        val totalTime = finishTime - startTime;


        logger.info("Completed in $totalTime ms")


    }
}



private static ExecutorService executorService = reindexEverythingTaskExecutor();

public static CompletableFuture<Long> performTask(Long num) {
    return CompletableFuture.supplyAsync(() -> {
        log.info("in thread {}", Thread.currentThread().getName());
        long count = num;
        for (int i = 0; i < 1000; i++) {
        count += 3 * 10 / 2 + 612 * 23;
    }
        log.info("Finish iterations in thread {}, count : {}", Thread.currentThread().getName(), count);
        return count;
    }, executorService);
}

public static void main(String[] args) throws ExecutionException, InterruptedException {
    long result = 0;
    for (int i = 0; i < 1000; i++) {
        long startTime =  System.nanoTime();
        log.info("start free memory {}", startTime);
        CompletableFuture<Long> result1 = performTask(5465L);
        CompletableFuture<Long> result2 = performTask(545846L);
        CompletableFuture<Long> result3 = performTask(54568454346544L);
        CompletableFuture.allOf(result1, result2, result3).get();
        result += result1.get();
        result += result2.get();
        result += result3.get();
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;


        log.info("Completed in : " + elapsedTime );
    }
    executorService.shutdown();
}

