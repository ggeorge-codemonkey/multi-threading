public class MultiThreadingDemo {

    // A simple thread that prints numbers from 1 to 5
    static class NumberPrinter extends Thread {
        private final String threadName;

        NumberPrinter(String name) {
            this.threadName = name;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                System.out.println(threadName + " - Number: " + i);
                try {
                    Thread.sleep(500); // Sleep for 500 milliseconds
                } catch (InterruptedException e) {
                    System.out.println(threadName + " interrupted.");
                }
            }
        }
    }

    // A thread that prints numbers from 1 to 5 using Runnable
    static class NumberPrinterRunnable implements Runnable {
        private final String threadName;

        NumberPrinterRunnable(String name) {
            this.threadName = name;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                System.out.println(threadName + " - Number: " + i);
                try {
                    Thread.sleep(500); // Sleep for 500 milliseconds
                } catch (InterruptedException e) {
                    System.out.println(threadName + " interrupted.");
                }
            }
        }
    }

    // A thread-safe class to demonstrate synchronization
    static class Counter {
        private int count = 0;

        synchronized void increment() {
            count++;
            System.out.println("Count: " + count);
        }
    }

    static class CounterThread extends Thread {
        private final Counter counter;

        CounterThread(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                counter.increment();
                try {
                    Thread.sleep(100); // Sleep for 100 milliseconds
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted.");
                }
            }
        }
    }

    public static void main(String[] args) {
        // Creating and starting threads using Thread class
        NumberPrinter thread1 = new NumberPrinter("Thread1");
        NumberPrinter thread2 = new NumberPrinter("Thread2");

        thread1.start();
        thread2.start();

        // Creating and starting threads using Runnable interface
        Thread runnableThread1 = new Thread(new NumberPrinterRunnable("RunnableThread1"));
        Thread runnableThread2 = new Thread(new NumberPrinterRunnable("RunnableThread2"));

        runnableThread1.start();
        runnableThread2.start();

        // Demonstrating thread-safe operations with synchronization
        Counter counter = new Counter();
        CounterThread counterThread1 = new CounterThread(counter);
        CounterThread counterThread2 = new CounterThread(counter);

        counterThread1.start();
        counterThread2.start();

        // Wait for all threads to finish
        try {
            thread1.join();
            thread2.join();
            runnableThread1.join();
            runnableThread2.join();
            counterThread1.join();
            counterThread2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        System.out.println("All threads have finished execution.");
    }
}
