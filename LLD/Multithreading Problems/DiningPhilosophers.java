
/*
 https://codewitharyan.com/tech-blogs/the-dining-philosophers
*/


import java.util.concurrent.Semaphore;

class DiningPhilosophersLogic {

    Semaphore semaphore;
    Semaphore[] forkSemaphores;
    int n;

    public DiningPhilosophersLogic(int n) {
        this.n = n;
        semaphore = new Semaphore(n - 1, true);
        forkSemaphores = new Semaphore[n];
        for(int i = 0; i < n; i++){
            forkSemaphores[i] = new Semaphore(1);
        }
    }

    public void wantsToEat(int philosopher, Runnable pickLeftFork, Runnable pickRightFork, Runnable eat, Runnable putLeftFork, Runnable putRightFork) throws InterruptedException{
        semaphore.acquire();

        int left = philosopher;
        int right = (philosopher + 1) % n;

        Semaphore leftForkSemaphore = forkSemaphores[left];
        Semaphore rightForkSemaphore = forkSemaphores[right];

        leftForkSemaphore.acquire();
        pickLeftFork.run();

        rightForkSemaphore.acquire();
        pickRightFork.run();

        try {
            eat.run();
        } finally {
            putLeftFork.run();
            leftForkSemaphore.release();
            putRightFork.run();
            rightForkSemaphore.release();
        }


        semaphore.release();
    }

    

}


public class DiningPhilosophers {

    public static void main(String[] args) {

        Runnable pickLeftFork = () -> {
            System.out.println("Picked Left Fork");
        };

        Runnable pickRightFork = () -> {
            System.out.println("Picked Right Fork");
        };

        Runnable putLeftFork = () -> {
            System.out.println("Put Left Fork");
        };

        Runnable putRightFork = () -> {
            System.out.println("Put Right Fork");
        };

        Runnable eat = () -> {
            try {
                Thread.sleep(500);
                System.out.println("Done eating!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        DiningPhilosophersLogic obj = new DiningPhilosophersLogic(5);

        Thread t0 = new Thread(() -> {
            try {
                obj.wantsToEat(0, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork);
            } catch (Exception e) {
            }
        });

        Thread t1 = new Thread(() -> {
            try {
                obj.wantsToEat(1, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork);
            } catch (Exception e) {
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                obj.wantsToEat(2, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork);
            } catch (Exception e) {
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                obj.wantsToEat(3, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork);
            } catch (Exception e) {
            }
        });

        Thread t4 = new Thread(() -> {
            try {
                obj.wantsToEat(4, pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork);
            } catch (Exception e) {
            }
        });

        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        
    }
    
}
