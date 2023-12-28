package org.example.primeNumber;

import java.util.concurrent.atomic.AtomicInteger;

/**

 In this each thread runs infinitely and picks the next available number
 This is fine-tuned multithreaded approach. In this all threads are doing the same work.

 Time taken by thread 18 : 6
 Time taken by thread 23 : 6
 Time taken by thread 16 : 6
 Time taken by thread 19 : 6
 Time taken by thread 22 : 6
 Time taken by thread 15 : 6
 Time taken by thread 21 : 6
 Time taken by thread 20 : 6
 Time taken by thread 24 : 6
 Time taken by thread 17 : 6
 Total prime numbers found: 5761455
 Total time taken: 6 seconds

 **/
public class Approach3 {

    private static final int INT_MAX = 100000000;
    private static final int CONCURRENCY = 10;

    private static AtomicInteger currentNumber = new AtomicInteger(2);

    protected static AtomicInteger totalPrimes = new AtomicInteger(0);

    private static boolean isPrime( int num ){
        if( num <= 1)
            return false;
        for(int i= 2; i*i<= num; i++){
            if(num%i == 0){
                return false;
            }
        }
        return true;
    }

    private static class PrimeCounter implements Runnable{

        @Override
        public void run() {
            long threadStartTime = System.currentTimeMillis();
            while (true){
                int number = currentNumber.getAndIncrement();
                if(number > INT_MAX)
                    break;
                if(isPrime(number)){
                    totalPrimes.incrementAndGet();
                }
            }

            long threadEndTime = System.currentTimeMillis();
            long totalTimeTaken = threadEndTime - threadStartTime;
            System.out.println("Time taken by thread " + Thread.currentThread().getId() + " : " + totalTimeTaken/1000);
        }
    }

    // This takes 38 seconds
//    private static void calculatePrimeCount2() {
//        ExecutorService executorService = Executors.newFixedThreadPool(CONCURRENCY);
//        while (currentNumber.get() <= INT_MAX){
//            int finalLocal = currentNumber.get();
//            executorService.execute(() -> {
//                if(isPrime(finalLocal))
//                    totalPrimes.incrementAndGet();
//            });
//            currentNumber.incrementAndGet();
//        }
//        executorService.shutdown();
//        try {
//            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
//        } catch (InterruptedException e){
//            Thread.currentThread().interrupt();
//        }
//    }

    private static void calculatePrimeCount() throws InterruptedException {
        Thread[] threads = new Thread[CONCURRENCY];

        for(int i=0; i<CONCURRENCY; i++){
            threads[i] = new Thread(new PrimeCounter());
            threads[i].start();
        }
        for (Thread thread: threads)
            thread.join();
    }

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        calculatePrimeCount();

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Total prime numbers found: " + totalPrimes);
        System.out.println("Total time taken: " + totalTime/1000 + " seconds");
    }

}
