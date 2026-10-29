/*
 https://codewitharyan.com/tech-blogs/fizz-buzz-multithreaded
 */

import java.util.concurrent.Semaphore;

class FizzBuzzLogic{
    private int n;
    private Semaphore numberSemaphore = new Semaphore(1);
    private Semaphore fizzSemaphore = new Semaphore(0);
    private Semaphore buzzSemaphore = new Semaphore(0);
    private Semaphore fizzBuzzSemaphore = new Semaphore(0);

    public FizzBuzzLogic(int n){
        this.n = n;
    }    

    public void number() throws InterruptedException {
        for(int i = 1; i <= n; i++){
            numberSemaphore.acquire();

            if(i % 3 == 0 && i % 5 == 0){
                fizzBuzzSemaphore.release();
            }
            else if(i % 3 == 0){
                fizzSemaphore.release();
            }
            else if(i % 5 == 0){
                buzzSemaphore.release();
            }
            else{
                System.out.print(i + " ");
                numberSemaphore.release();
            }
        }
    }

    public void fizz() throws InterruptedException {
        for(int i = 1; i <= n; i++){
            if(i % 3 == 0 && i % 5 != 0){
                fizzSemaphore.acquire();
                System.out.print("Fizz ");
                numberSemaphore.release();
            }
        }
    }

    public void buzz() throws InterruptedException {
        for(int i = 1; i <= n; i++){
            if(i % 5 == 0 && i % 3 != 0){
                buzzSemaphore.acquire();
                System.out.print("Buzz ");
                numberSemaphore.release();
            }
        }
    }

    public void fizzBuzz() throws InterruptedException {
        for(int i = 1; i <= n; i++){
            if(i % 5 == 0 && i % 3 == 0){
                fizzBuzzSemaphore.acquire();
                System.out.print("FizzBuzz ");
                numberSemaphore.release();
            }
        }
    }

}


public class FizzBuzz {
    public static void main(String[] args) {
        FizzBuzzLogic obj = new FizzBuzzLogic(16);

        Thread t1 = new Thread(() -> {
            try {
                obj.number();
            } catch (Exception e) {

            }
        });

        Thread t2 = new Thread(() -> {
            try {
                obj.fizz();
            } catch (Exception e) {

            }
        });

        Thread t3 = new Thread(() -> {
            try {
                obj.buzz();
            } catch (Exception e) {

            }
        });

        Thread t4 = new Thread(() -> {
            try {
                obj.fizzBuzz();
            } catch (Exception e) {

            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
