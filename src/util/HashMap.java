package util;


public class HashMap<Key,Val> {
    private int N; // Number of Key-Value pairs
    private int M; // Size of HashMap
    private SymbolTable<Key,Val>[] st; // Array of linked-list symbol tables

    public HashMap(int M) {
        this.M = M;
        st = (SymbolTable<Key,Val>[]) new SymbolTable[M];
        for (int i = 0; i < M; i++) {
            st[i] = new SymbolTable<Key,Val>();
        }
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public void resize(int chains) {
        HashMap<Key, Val> temp = new HashMap<Key, Val>(chains);
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.M = temp.M;
        this.N = temp.N;
        this.st = temp.st;
    }

    public Val get(Key key) {
        int i = hash(key);
        return st[i].get(key);
    }

    public Key getKey(Val val) {
        Iterable<Key> list = keys();
        for (Key k : list) {
            if (get(k).equals(val)) {
                return k;
            }
        }
        return null;
    }

    public boolean containsKey(Key key) {
        return get(key) != null;
    }

    public boolean containsVal(Val val) {
        return getKey(val) != null;
    }

    public void put(Key key, Val val) {
        if (val == null) {
            delete(key);
            return;
        }

        if (N >= 10*M) {
            resize(2*M);
        }

        int i = hash(key);
        if (!st[i].contains(key)) {
            N++;
        }
        st[i].put(key,val);
    }

    public void delete(Key key) {
        int i = hash(key);
        if (st[i].contains(key)) {
            N--;
        }
        st[i].delete(key);

        if (M > 10 && N <= 2*M) {
            resize(M/2);
        }
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i++) {
            for (Key key : st[i].keys()) {
                queue.enqueue(key);
            }
        }
        return queue;
    }

//    public Iterable<Val> values() {
//        Queue<Val> queue = new Queue<Val>();
//        for (int i = 0; i < M; i++) {
//            if (vals[i] != null) {
//                queue.enqueue(vals[i]);
//            }
//        }
//        return queue;
//    }

//    private boolean check() {
//        if (M < 2*N) {
//            System.err.println("Hash table size M = " + M + "; array size N = " + N);
//            return false;
//        }
//
//        for (int i = 0; i < M; i++) {
//            if (keys[i] == null) {
//                continue;
//            } else if (get(keys[i]) != vals[i]) {
//                System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i]);
//                return false;
//            }
//        }
//        return true;
//    }

    public static void main(String[] args) {
        HashMap<Integer, String> test = new HashMap<Integer, String>(10);
        test.put(0, "a");
        test.put(1, "b");
        System.out.println(test.containsVal("z"));
    }

}
