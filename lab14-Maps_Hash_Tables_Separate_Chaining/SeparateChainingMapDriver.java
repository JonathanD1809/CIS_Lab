import java.util.ArrayList;
import java.util.LinkedList;

// --- 1. Entry ADT ---
class Entry<K, V> {
    private final K key;  // Key không thể thay đổi
    private V value;      // Value có thể cập nhật

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }

    // Cập nhật value, trả về giá trị cũ
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }

    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}

// --- 2. Map Interface ---
interface MapADT<K, V> {
    V get(K key);
    V put(K key, V value);
    V remove(K key);
    int size();
    boolean isEmpty();
}

// --- 3. Separate Chaining Hash Map Implementation ---
class SeparateChainingMap<K, V> implements MapADT<K, V> {
    private ArrayList<LinkedList<Entry<K, V>>> table; // Mảng các bucket // array of buckets
    private int size = 0;                              // Số lượng phần tử// number of entries
    private final int N = 11;                          // Prime number for table capacity

    public SeparateChainingMap() {
        table = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            table.add(new LinkedList<Entry<K, V>>()); // // initialize empty buckets
        }
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % N);
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public V get(K key) {
        int h = hash(key);
        LinkedList<Entry<K, V>> bucket = table.get(h);

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry.getValue(); // Key found
            }
        }
        return null; // Key not found
    }

    public V put(K key, V value) {
        int h = hash(key);
        LinkedList<Entry<K, V>> bucket = table.get(h);

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry.setValue(value); // Key exists: update value
            }
        }

        // Key mới: thêm vào đầu bucket // key not found, add new entry at the front
        bucket.addFirst(new Entry<>(key, value));
        size++;
        return null; // New key added, return null
    }

    public V remove(K key) {
        int h = hash(key);
        LinkedList<Entry<K, V>> bucket = table.get(h);

        Entry<K, V> toRemove = null;
        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                toRemove = entry;
                break;
            }
        }

        if (toRemove != null) {
            V oldValue = toRemove.getValue();
            bucket.remove(toRemove);
            size--;
            return oldValue;
        }
        return null; // Key not found
    }
}

// --- 4. Test 
public class SeparateChainingMapDriver {
    public static void main(String[] args) {
        MapADT<String, Integer> map = new SeparateChainingMap<>();

        // --- New simple test examples ---
        System.out.println("put(\"dog\", 5) -> " + map.put("dog", 5));       // null
        System.out.println("put(\"cat\", 3) -> " + map.put("cat", 3));       // null
        System.out.println("put(\"bird\", 8) -> " + map.put("bird", 8));     // null
        System.out.println("put(\"cat\", 7) -> " + map.put("cat", 7));       // 3 (value replaced)

        System.out.println("\nRetrieve values:");
        System.out.println("get(\"dog\") -> " + map.get("dog"));             // 5
        System.out.println("get(\"cat\") -> " + map.get("cat"));             // 7
        System.out.println("get(\"bird\") -> " + map.get("bird"));           // 8
        System.out.println("get(\"fish\") -> " + map.get("fish"));           // null

        System.out.println("\nRemove keys:");
        System.out.println("remove(\"bird\") -> " + map.remove("bird"));     // 8
        System.out.println("remove(\"fish\") -> " + map.remove("fish"));     // null

        System.out.println("\nMap status:");
        System.out.println("Current size -> " + map.size());                 // 2
        System.out.println("Is empty? -> " + map.isEmpty());                 // false
    }
}
