import map.Reachable;
import util.*;

import java.util.ArrayList;

import static util.StdOut.*;
import static util.StdIn.*;

public class FlightMapper {
    //private static ReadCSV input = new ReadCSV("data/StarAlliance.csv");
    private static ReadCSV input = new ReadCSV("data/AirCanada.csv");
    private static HashMap<Integer, String> HM_idAirport = new HashMap<Integer, String>(100);
    private static Digraph network;

    public static void init() {
        // Creates a list of airports
        ArrayList<String> departures = input.readColumn(0);
        println(departures);
        ArrayList<String> arrivals = input.readColumn(1);
        println(arrivals);
        ArrayList<String> seen = new ArrayList<String>();
        int counter = 0;

        // Check departure airports
        for (String s : departures) {
            if (!seen.contains(s)) {
                HM_idAirport.put(counter, s);
                seen.add(s);
                counter++;
            }
        }
//        println(HM_idAirport.keys());
//        println(HM_idAirport.values());

        // Check arrival airports
        for (String s : arrivals) {
            if (!seen.contains(s)) {
                HM_idAirport.put(counter, s);
                seen.add(s);
                counter++;
            }
        }

        println(HM_idAirport.keys());
        println(HM_idAirport.values());

        // Create a list of connections
        ArrayList<Tuple<String,String>> connections = input.parseTuple(0, 1);
        ArrayList<Airport> airports = new ArrayList<Airport>();
        // println(connections.keys());

        for (Integer i : HM_idAirport.keys()) {
            airports.add(new Airport(i, HM_idAirport.get(i)));
        }

        println(airports);

        network = new Digraph(HM_idAirport.size());
        for (Tuple<String,String> t : connections) {
            network.addEdge(HM_idAirport.getKey(t.x), HM_idAirport.getKey(t.y));
        }
    }

    public static void search() {
        println("Search!");
    }

    public static void map() {
        mapIntro();
        mapMenu();
        int returnCode = -1;
        while(returnCode != 0) {
            mapMenu();
            returnCode = mapInput();
        }
    }

    public static void mapIntro() {
        println("Mapping");
    }

    public static void mapMenu() {
        println("Select an option:");
        println("1. Departures");
        println("2. Arrivals");
        println("3. Shortest Path");
        println("4. Connectivity");
        println("5. Return");
        println();
    }

    public static int mapInput() {
        print("Input: ");
        String inputSelection = readString();
        switch(inputSelection.charAt(0)) {
            case '1':
                mapDepartures();
                return 1;
            case '2':
                // TODO
                return 1;
            case '3':
                // TODO
                return 1;
            case '4':
                // TODO
                return 1;
            case '5':
                return 0;
            default:
                println("Input mismatch");
                return -1;
        }
    }

    public static void mapDepartures() {
        print("Input Airport: ");
        String inputAirport = readString();
        if (inputAirport.length() == 3) {
            String airportCode = inputAirport;
            int airportID = HM_idAirport.getKey(airportCode);
            Bag<Integer> source = new Bag<Integer>();
            source.add(airportID);
            Reachable search = new Reachable(network, source);
            for (int v = 0; v < network.V(); v++) {
                if (search.marked(v)) {
                    print(HM_idAirport.get(v) + " ");
                }
            }
            println();
        } else {
            println("Input mismatch");
        }
    }

    // Interface code

    public static int menuInput() {
        print("Input: ");
        String inputSelection = readString();
        switch(inputSelection.charAt(0)) {
            case '1':
                search();
                return 0;
            case '2':
                map();
                return 0;
            case '3':
                return 1;
            default:
                println("Input mismatch");
                return -1;
        }
    }

    public static void intro() {
        println("Welcome to Flight Mapper");
        println();
    }

    public static void printMenu() {
        println("Select an option:");
        println("1. Search");
        println("2. Map Network");
        println("3. Exit");
        println();
    }

    public static void exit() {
        println();
        println("Thank you for using Flight Mapper");
    }

    public static void main(String[] args) {
        init();
        intro();
        int returnCode = -1;
        while(returnCode != 1) {
            printMenu();
            returnCode = menuInput();
        }
        exit();

    }
}
