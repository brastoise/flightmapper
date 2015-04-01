import util.HashMap;
import util.ReadCSV;
import util.Digraph;
import java.util.ArrayList;
public class Network {
    private static ReadCSV input = new ReadCSV("data/StarAlliance.csv");
    private static HashMap<Integer, String> HM_idAirport = new HashMap<Integer, String>(100);
    private static Digraph network;

    public static void init() {
        // Creates a list of airports
        ArrayList<String> airports = input.readColumn(0);
        ArrayList<String> seen = new ArrayList<String>();
        int counter = 0;
        for (String s : airports) {
            if (!seen.contains(s)) {
                HM_idAirport.put(counter, s);
                seen.add(s);
                counter++;
            }
        }

        // Create a list of connections
        HashMap<String, String> connections = input.parse(0, 1);
        network = new Digraph(HM_idAirport.size());
        for (String s : connections.keys()) {
            network.addEdge(HM_idAirport.getKey(s), HM_idAirport.getKey(connections.get(s)));
        }
    }

    public static void main(String[] args) {
        init();
        for (String s : HM_idAirport.values()) {
            System.out.println(s);
        }

        System.out.println(network.toString());
        System.out.println(HM_idAirport.get(0));
        System.out.println(HM_idAirport.get(178));
    }

}
