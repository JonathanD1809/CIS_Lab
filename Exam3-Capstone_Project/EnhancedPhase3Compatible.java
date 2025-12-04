

import java.util.ArrayList;
import java.util.List;

public class EnhancedPhase3Compatible {

    public static void main(String[] args) {
        // Create MaxHeap scheduler
        MaxHeap scheduler = new MaxHeap(20);

        // Insert tasks individually
        scheduler.insert(new Task(1, "Process VIP loan", 10));
        scheduler.insert(new Task(2, "Standard loan", 3));
        scheduler.insert(new Task(3, "Emergency patient", 9));
        scheduler.insert(new Task(4, "Routine maintenance", 1));

        // Batch insert
        List<Task> batchTasks = new ArrayList<>();
        batchTasks.add(new Task(5, "Follow-up patient", 5));
        batchTasks.add(new Task(6, "Update records", 2));
        insertBatch(scheduler, batchTasks);

        // Peek highest-priority task
        System.out.println("Peek highest-priority task:");
        System.out.println(scheduler.peek());

        // Poll all tasks in priority order
        System.out.println("\nPolling tasks in priority order:");
        while (!scheduler.isEmpty()) {
            Task t = scheduler.poll();
            System.out.println(t);
        }

        // Re-insert tasks for search demonstration
        scheduler.insert(new Task(1, "Process VIP loan", 10));
        scheduler.insert(new Task(2, "Standard loan", 3));
        scheduler.insert(new Task(3, "Emergency patient", 9));
        scheduler.insert(new Task(4, "Routine maintenance", 1));

        // Demonstrate search
        System.out.println("\nSearch result for task ID 2:");
        Task found = scheduler.findTaskById(2);
        if (found != null) {
            System.out.println(found);
        } else {
            System.out.println("Task not found");
        }

        System.out.println("\nSearch result for task ID 99:");
        Task notFound = scheduler.findTaskById(99);
        if (notFound != null) {
            System.out.println(notFound);
        } else {
            System.out.println("Task not found");
        }

        // Simple analytics: count tasks with priority >= 5
        System.out.println("\nNumber of tasks with priority >=5:");
        int count = countHighPriority(scheduler, 5);
        System.out.println(count);
    }

    // Batch insert helper
    public static void insertBatch(MaxHeap heap, List<Task> tasks) {
        for (Task t : tasks) {
            heap.insert(t);
        }
    }

    // Simple analytics helper
    public static int countHighPriority(MaxHeap heap, int threshold) {
        int count = 0;
        for (int i = 0; i < heapSize(heap); i++) {
            Task t = getTaskAt(heap, i);
            if (t != null && t.getPriority() >= threshold) {
                count++;
            }
        }
        return count;
    }

    // Access size of heap via polling temporary copy (since size is private)
    private static int heapSize(MaxHeap heap) {
        // Unsafe but works for demo: poll all tasks temporarily and restore
        List<Task> temp = new ArrayList<>();
        int count = 0;
        while (!heap.isEmpty()) {
            temp.add(heap.poll());
            count++;
        }
        // Restore tasks
        for (Task t : temp) {
            heap.insert(t);
        }
        return count;
    }

    // Access task at index via polling (since no getter)
    private static Task getTaskAt(MaxHeap heap, int index) {
        // Unsafe but works for demo: poll tasks until index, then restore
        List<Task> temp = new ArrayList<>();
        Task result = null;
        for (int i = 0; i <= index && !heap.isEmpty(); i++) {
            Task t = heap.poll();
            temp.add(t);
            if (i == index) result = t;
        }
        // Restore tasks
        for (Task t : temp) {
            heap.insert(t);
        }
        return result;
    }
}
