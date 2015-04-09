package util;

/**
 * Linear probing hash table
 * Implemented according to the specifications in the Algorithms textbook
 *
 * @param <Key> - data type for the key
 * @param <Val> - data type for the value
 */
public class HashMap<Key,Val> {
    private int N; // Number of Key-Value pairs
    private int M; // Size of HashMap
    private Key[] keys; // An array of keys
    private Val[] vals; // An array of values

    /**
     * Initialises a HashMap with size M
     * 
     * @param M - initial size of HashMap
     */
    public HashMap(int M) {
        this.M = M;
        keys = (Key[]) new Object[M];
        vals = (Val[]) new Object[M];
    }

    /**
     * Returns the size of the HashMap
     * Note: Not number of key-value pairs, calculates the null values of HashMap as well
     */
    public int size() {
        return M;
    }

    /**
     * Returns true if the HashMap has a size of 0
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Hashing Function
     * 
     * @param key - Key to be hashed
     */
    public int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    /**
     * Resizes the HashMap to the given size
     * 
     * @param newM - desired size of HashMap
     */
    public void resize(int newM) {
        HashMap<Key, Val> temp = new HashMap<Key, Val>(newM);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        M = temp.M;
    }

    /**
     * Given a key, find the matching value
     * 
     * @param key - Search for value associated with this key
     */
    public Val get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                return vals[i];
            }
        }
        return null;
    }

    /**
     * Find the key for a given value
     * 
     * @param val - value to search key for
     */
    public Key getKey(Val val) {
        Iterable<Key> list = keys();
        for (Key k : list) {
            if (get(k).equals(val)) {
                return k;
            }
        }
        return null;
    }

    /**
     * Checks the HashMap if a key-value pair exists
     * 
     * @param key
     */
    public boolean containsKey(Key key) {
        return get(key) != null;
    }

    /**
     * Checks if key-value pair exists given value
     * 
     * @param val
     */
    public boolean containsVal(Val val) {
        return getKey(val) != null;
    }

    /**
     * Inserts key-pair value into HashMap
     * If value given is null, delete given key
     * 
     * @param key
     * @param val
     */
    public void put(Key key, Val val) {
        if (val == null) {
            delete(key);
            return;
        }

        if (N >= M/2) {
            resize(2*M);
        }

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    /**
     * Deletes the key-value pair
     * 
     * @param key
     */
    public void delete(Key key) {
        if (!containsKey(key)) {
            return;
        }

        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % M;
        }

        keys[i] = null;
        vals[i] = null;

        i = (i + 1) % M;
        while (keys[i] != null) {
            Key rehashKey = keys[i];
            Val rehashVal = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(rehashKey, rehashVal);
            i = (i + 1) % M;
        }

        N--;

        if (N > 0 && N <= M/8) {
            resize(M/2);
        }

        assert check();
    }

    /**
     * Returns a list of keys in the HashMap
     */
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                queue.enqueue(keys[i]);
            }
        }
        return queue;
    }

    /**
     * Returns a list of values in the HashMap
     */
    public Iterable<Val> values() {
        Queue<Val> queue = new Queue<Val>();
        for (int i = 0; i < M; i++) {
            if (vals[i] != null) {
                queue.enqueue(vals[i]);
            }
        }
        return queue;
    }

    /**
     * Integrity check
     */
    private boolean check() {
        if (M < 2*N) {
            System.err.println("Hash table size M = " + M + "; array size N = " + N);
            return false;
        }

        for (int i = 0; i < M; i++) {
            if (keys[i] == null) {
                continue;
            } else if (get(keys[i]) != vals[i]) {
                System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i]);
                return false;
            }
        }
        return true;
    }
}
