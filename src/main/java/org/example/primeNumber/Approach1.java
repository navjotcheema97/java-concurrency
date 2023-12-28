package org.example.primeNumber;


import java.sql.Timestamp;
import java.util.Date;

/**
    This is single threaded approach
    result:

     Total prime numbers found: 5761455
     Total time taken: 40 seconds
 **/

public class Approach1 {

    private static Integer totalPrimes = 0;

    private static boolean isPrime( int num ){
        for(int i= 2; i*i<= num; i++){
            if(num%i == 0){
                return false;
            }
        }
        return true;
    }

    private static void calculatePrimeCount() {
        int INT_MAX = 100000000;
        for(int i = 2; i< INT_MAX; i++){
            if( isPrime(i))
                totalPrimes++;
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        calculatePrimeCount();

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Total prime numbers found: " + totalPrimes);
        System.out.println("Total time taken: " + totalTime/1000 + " seconds");
    }
}
