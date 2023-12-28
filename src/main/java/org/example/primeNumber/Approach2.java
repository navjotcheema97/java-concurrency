package org.example.primeNumber;


import java.util.concurrent.atomic.AtomicInteger;

/**
 In this approach, We will divide the numbers equally per concurrency, and process them on separate threads.
 This is multithreaded approach, but not fairly tuned as different threads are completed differently because the thread with higher
 number will take more time to calculate


 result:

 Time taken by thread 15 : 2
 Time taken by thread 16 : 3
 Time taken by thread 17 : 3
 Time taken by thread 18 : 4
 Time taken by thread 19 : 4
 Time taken by thread 20 : 5
 Time taken by thread 21 : 5
 Time taken by thread 22 : 5
 Time taken by thread 23 : 6
 Time taken by thread 24 : 6
 Total prime numbers found: 5761455
 Total time taken: 6 seconds

 If we see the time taken by each thread is uneven, this can be more fine-tuned. Have concurrency least equal to
 number of cors
 **/

public class Approach2 {

    private static AtomicInteger totalPrimes = new AtomicInteger(0);
    private static final int INT_MAX = 100000000;

    private static final int CONCURRENCY = 10;

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

    private static void calculatePrimeCount() throws InterruptedException {
        Thread[] threads = new Thread[CONCURRENCY];
        for(int i=0; i< CONCURRENCY ; i++){
            int factor = INT_MAX/CONCURRENCY;
            int start = i*factor;
            int end = (i+1) * factor;
            threads[i] = new Thread(new PrimeCounter(start, end));
            threads[i].start();
        }
        for(Thread thread: threads)
            thread.join();
    }

    private static class PrimeCounter implements Runnable{

        private final int start;

        private final int end;

        public PrimeCounter(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            long threadStartTime = System.currentTimeMillis();
            int localPrimes = 0;
            for(int i = start; i<end; i++){
                if(Approach2.isPrime(i))
                    localPrimes++;
            }
            totalPrimes.addAndGet(localPrimes);
            long threadEndTime = System.currentTimeMillis();
            long totalTimeTaken = threadEndTime - threadStartTime;
            System.out.println("Time taken by thread " + Thread.currentThread().getId() + " : " + totalTimeTaken/1000);
        }
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
