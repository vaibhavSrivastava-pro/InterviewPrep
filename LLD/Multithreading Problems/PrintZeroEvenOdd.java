/*
https://codewitharyan.com/tech-blogs/print-zero-even-odd

For n = 5, output should be:
0 1 0 2 0 3 0 4 0 5
 */


import java.util.concurrent.Semaphore;

class ZeroEvenOdd {
    private int n;
    private Semaphore zero = new Semaphore(1);
    private Semaphore odd = new Semaphore(0);
    private Semaphore even = new Semaphore(0);

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero() throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            zero.acquire();
            System.out.print(0);

            if (i % 2 == 1) {
                odd.release();
            } else {
                even.release();
            }
        }
    }

    public void odd() throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            odd.acquire();
            System.out.print(i);
            zero.release();
        }
    }

    public void even() throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            even.acquire();
            System.out.print(i);
            zero.release();
        }
    }
}

public class PrintZeroEvenOdd {
    public static void main(String[] args) {
        ZeroEvenOdd obj = new ZeroEvenOdd(5);

        Thread t1 = new Thread(() -> {
            try { obj.zero(); } catch (Exception e) {}
        });

        Thread t2 = new Thread(() -> {
            try { obj.odd(); } catch (Exception e) {}
        });

        Thread t3 = new Thread(() -> {
            try { obj.even(); } catch (Exception e) {}
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
