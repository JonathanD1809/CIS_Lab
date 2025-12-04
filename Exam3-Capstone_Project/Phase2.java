// SmartSchedulerPhase2.java
// Phase 2: Backbone Implementation – Max-Heap Smart Scheduler

// Task class represents an individual scheduled task
class Task {
    private int id;
    private String description;
    private int priority;

    public Task(int id, String description, int priority) {
        this.id = id;
        this.description = description;
        this.priority = priority;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public int getPriority() { return priority; }

    @Override
    public String toString() {
        return "Task[ID=" + id + ", Description=" + description + ", Priority=" + priority + "]";
    }
}

// MaxHeap class implements a priority queue using array-based Max-Heap
class MaxHeap {
    private Task[] heapArray;
    private int size;
    private int capacity;

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        heapArray = new Task[capacity];
    }

    // Helper methods to find parent and children indices
    private int parent(int index) { return (index - 1) / 2; }
    private int leftChild(int index) { return 2 * index + 1; }
    private int rightChild(int index) { return 2 * index + 2; }

    // Insert a new task into the heap
    public void insert(Task task) {
        if (size == capacity) {
            System.out.println("Heap is full!");
            return;
        }
        heapArray[size] = task;
        heapifyUp(size);
        size++;
    }

    // Retrieve the highest-priority task without removing it
    public Task peek() {
        if (size == 0) return null;
        return heapArray[0];
    }

    // Remove and return the highest-priority task
    public Task poll() {
        if (size == 0) return null;
        Task root = heapArray[0];
        heapArray[0] = heapArray[size - 1];
        size--;
        heapifyDown(0);
        return root;
    }

    // Restore heap property after insertion
    private void heapifyUp(int index) {
        while (index != 0 && heapArray[parent(index)].getPriority() < heapArray[index].getPriority()) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    // Restore heap property after removal
    private void heapifyDown(int index) {
        int largest = index;
        int left = leftChild(index);
        int right = rightChild(index);

        if (left < size && heapArray[left].getPriority() > heapArray[largest].getPriority())
            largest = left;

        if (right < size && heapArray[right].getPriority() > heapArray[largest].getPriority())
            largest = right;

        if (largest != index) {
            swap(index, largest);
            heapifyDown(largest);
        }
    }

    // Swap two elements in the heap
    private void swap(int i, int j) {
        Task temp = heapArray[i];
        heapArray[i] = heapArray[j];
        heapArray[j] = temp;
    }

    // Check if the heap is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Search for a task by its ID
    public Task findTaskById(int taskId) {
        for (int i = 0; i < size; i++) {
            if (heapArray[i].getId() == taskId) return heapArray[i];
        }
        return null;
    }
}

// Optional main class for testing Phase 2
public class Phase2 {
    public static void main(String[] args) {
        MaxHeap scheduler = new MaxHeap(10);

        scheduler.insert(new Task(1, "Process VIP loan", 10));
        scheduler.insert(new Task(2, "Standard loan", 3));
        scheduler.insert(new Task(3, "Emergency patient", 9));
        scheduler.insert(new Task(4, "Routine maintenance", 1));

        System.out.println("Peek highest-priority task: " + scheduler.peek());

        System.out.println("\nPolling tasks in priority order:");
        while (!scheduler.isEmpty()) {
            System.out.println(scheduler.poll());
        }

        // Demonstrate search
        scheduler.insert(new Task(2, "Standard loan", 3));
        scheduler.insert(new Task(4, "Routine maintenance", 1));

        System.out.println("\nSearch result for task ID 2: " + scheduler.findTaskById(2));
        System.out.println("Search result for task ID 99: " + scheduler.findTaskById(99));
    }
}
