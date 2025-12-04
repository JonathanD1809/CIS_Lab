import java.util.ArrayList;

// --- 1. Entry ADT ---
class Entry<K, V> {
    private final K key;   // key không được thay đổi// key is immutable
    private V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }
    public K getKey() { return key; }
    public V getValue() { return value; }
    public V setValue(V value) { 
        V old = this.value;
        this.value = value;
        return old;
    }
}

// --- 2. Common Map Interface ---
interface MapADT<K, V> {
    V get(K key);
    V put(K key, V value);
    V remove(K key);
    int size();
    boolean isEmpty();
}

// --- 3. Implementation: Unsorted List Map ---
class UnsortedListMap<K, V> implements MapADT<K, V> {
    private ArrayList<Entry<K, V>> list = new ArrayList<>();

    // Helper: tìm index của entry với key cho trước (O(n))
    // helper: find the index of the entry with the given key (O(n))
    private int findEntryIndex(K key) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public int size() { return list.size(); }
    public boolean isEmpty() { return list.isEmpty(); }
    
    public V get(K key) {
        int i = findEntryIndex(key);
        return (i != -1) ? list.get(i).getValue() : null;
    }
    
    public V remove(K key) {
        int i = findEntryIndex(key);
        if (i != -1) {
            V oldValue = list.get(i).getValue();
            list.remove(i); // O(n) do ArrayList shift
            return oldValue;
        }
        return null;
    }
    
    // TODO: Complete this method (O(n))
    public V put(K key, V value) {
        // 1. Search for existing key (linear scan)
        int i = findEntryIndex(key);
        
        // 2. If found, update the value and return the old value
        if (i != -1) {
            return list.get(i).setValue(value);
        }
        
        // 3. If not found, add a new entry and return null
        list.add(new Entry<>(key, value)); // append at end, O(1)
        return null;
    }
}

// --- 4. Driver / Testing ---
public class UnsortedListMapDriver {
    public static void main(String[] args) {
        UnsortedListMap<Integer, String> map = new UnsortedListMap<>();
        
        // Test put, get, remove
        System.out.println("put(5, A): " + map.put(5, "A")); // null
        System.out.println("put(7, B): " + map.put(7, "B")); // null
        System.out.println("put(2, C): " + map.put(2, "C")); // null
        System.out.println("put(2, E): " + map.put(2, "E")); // C (value replaced)
        System.out.println("get(7): " + map.get(7));         // B
        System.out.println("remove(5): " + map.remove(5));   // A
    }
}
