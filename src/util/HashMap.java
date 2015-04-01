package util;


public class HashMap<Key,Val> {
    private int N; // Number of Key-Value pairs
    private int M; // Size of HashMap
    private Key[] keys;
    private Val[] vals;

    public HashMap(int M) {
        this.M = M;
        keys = (Key[]) new Object[M];
        vals = (Val[]) new Object[M];
    }



    public int size() {
        return M;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

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

    public Val get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                return vals[i];
            }
        }
        return null;
    }

    public boolean containsKey(Key key) {
        return get(key) != null;
    }

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

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                queue.enqueue(keys[i]);
            }
        }
        return queue;
    }

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
