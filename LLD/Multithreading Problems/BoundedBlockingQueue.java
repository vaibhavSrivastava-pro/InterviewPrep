/*
 https://codewitharyan.com/tech-blogs/design-bounded-blocking-queue
*/

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

class BoundedBlockingQueueLogic {

    Semaphore empty;
    Semaphore full;
    private ConcurrentLinkedDeque<Integer> deque;

    public BoundedBlockingQueueLogic(int n) {
        empty = new Semaphore(n);
        full = new Semaphore(0);
        deque = new ConcurrentLinkedDeque<>();
    }

    public void enqueue(int e) throws InterruptedException {
        empty.acquire();
        deque.addFirst(e);
        full.release();
    }

    public int deque() throws InterruptedException {
        int result = -1;
        full.acquire();
        result = deque.pollLast();
        empty.release();
        return result;
    }

    public int size() throws InterruptedException {
        int result = 0;
        result = deque.size();
        return result;
    }

}

public class BoundedBlockingQueue {

    public static void main(String[] args) {
        BoundedBlockingQueueLogic bbql = new BoundedBlockingQueueLogic(5);

        Thread t1 = new Thread(() -> {
            try {
                bbql.enqueue(5);
            } catch (Exception e) {
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                bbql.enqueue(6);
            } catch (Exception e) {
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                System.out.println(bbql.deque());
            } catch (Exception e) {
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }

}