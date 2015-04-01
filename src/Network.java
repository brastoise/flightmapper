import util.HashMap;
import util.ReadCSV;
public class Network {
    private ReadCSV input = new ReadCSV("data/StarAlliance.csv");
    private HashMap<Integer, String> HM_idAirport = new HashMap<Integer, String>(100);
    private HashMap<String, Integer> HM_AirportID = new HashMap<String, Integer>(100);

    public static void init() {
        HashMap<String, String> temp = ReadCSV.parse(0,1);




    }

}
