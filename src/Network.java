import util.HashMap;
import util.ReadCSV;
import java.util.ArrayList;
public class Network {
    private static ReadCSV input = new ReadCSV("data/StarAlliance.csv");
    private static HashMap<Integer, String> HM_idAirport = new HashMap<Integer, String>(100);
    private static HashMap<String, Integer> HM_AirportID = new HashMap<String, Integer>(100);

    public static void init() {
        ArrayList<String> airports = input.readColumn(0);
        ArrayList<String> seen = new ArrayList<String>();
        int counter = 0;
        for (String s : airports) {
            if (!seen.contains(s)) {
                HM_idAirport.put(counter, s);
                HM_AirportID.put(s, counter);
                seen.add(s);
                counter++;
            }
        }
    }

    public static void main(String[] args) {
        init();
        for (String s : HM_AirportID.keys()) {
            System.out.println(s);
        }
    }

}
