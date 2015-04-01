import util.Digraph;
public class Search {
    private boolean[] marked;
    private int count;

    public Search(Digraph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    public Search(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        for (int v : sources) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(Digraph G, int v) {
        count++;
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G,w);
            }
        }
    }

    public boolean marked(int v) {
        return marked[v];
    }

    public int count() {
        return count;
    }

}
