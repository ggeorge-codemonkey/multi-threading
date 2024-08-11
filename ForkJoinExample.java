import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinExample {

    // Define a task to compute sum of array elements
    static class SumTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 1000; // Threshold for splitting tasks
        private final int[] arr;
        private final int start;
        private final int end;

        SumTask(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                // Base case: Compute sum directly
                long sum = 0;
                for (int i = start; i <= end; i++) {
                    sum += arr[i];
                }
                return sum;
            } else {
                // Recursive case: Split the task
                int mid = (start + end) / 2;
                SumTask leftTask = new SumTask(arr, start, mid);
                SumTask rightTask = new SumTask(arr, mid + 1, end);

                // Fork tasks
                leftTask.fork();
                rightTask.fork();

                // Join results
                long rightResult = rightTask.join();
                long leftResult = leftTask.join();

                return leftResult + rightResult;
            }
        }
    }

    public static void main(String[] args) {
        // Example array
        int[] array = new int[10000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1; // Initialize array with values 1 to 10000
        }

        // Create a ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();

        // Create and invoke the sum task
        SumTask task = new SumTask(array, 0, array.length - 1);
        long result = pool.invoke(task);

        // Print the result
        System.out.println("The sum of array elements is: " + result);

        // Shutdown the pool
        pool.shutdown();
    }
}
