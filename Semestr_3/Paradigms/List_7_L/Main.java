class SharedCounter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}


class IncrementTask implements Runnable {
    private final SharedCounter counter;

    public IncrementTask(SharedCounter counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter.increment();
        }
    }
}


public class Main {
    public static void main(String[] args) throws InterruptedException {
        SharedCounter counter = new SharedCounter();

        Thread t1 = new Thread(new IncrementTask(counter));
        Thread t2 = new Thread(new IncrementTask(counter));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final count: " + counter.getCount());
    }
}