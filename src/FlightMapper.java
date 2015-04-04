import map.Reachable;
import util.*;

import java.util.ArrayList;

import static util.StdOut.*;
import static util.StdIn.*;

public class FlightMapper {
    //private static ReadCSV input = new ReadCSV("data/StarAlliance.csv");
    private static ReadCSV input = new ReadCSV("data/AirCanada.csv");
    private static HashMap<Integer, String> idAirport = new HashMap<Integer, String>(100);
    private static ArrayList<Airport> airports = new ArrayList<Airport>();
    private static ArrayList<Flight> flights = new ArrayList<Flight>();
    private static Digraph network;
    private static EdgeWeightedDigraph distanceNetwork;

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
                idAirport.put(counter, s);
                seen.add(s);
                counter++;
            }
        }

        // Check arrival airports
        for (String s : arrivals) {
            if (!seen.contains(s)) {
                idAirport.put(counter, s);
                seen.add(s);
                counter++;
            }
        }

        // Prepare a database of airports
        for (Integer i : idAirport.keys()) {
            airports.add(new Airport(i, idAirport.get(i)));
        }

        // Prepare a database of flights
        for (int i = 1; i < 10; i++) {
            flights.add(new Flight(input.readLine(i)));
        }

        // Create a list of connections
        ArrayList<Tuple<String,String>> connections = input.parseTuple(0, 1);
        network = new Digraph(idAirport.size());
        for (Tuple<String,String> t : connections) {
            network.addEdge(idAirport.getKey(t.x), idAirport.getKey(t.y)); // Adds a flight connection
            airports.get(idAirport.getKey(t.x)).addDeparture(t.y); // Adds to the list of places the airport can fly to
            airports.get(idAirport.getKey(t.y)).addArrival(t.x); // Adds to the list of places that travel to this airport
        }

        // Create a network with distances
        distanceNetwork = new EdgeWeightedDigraph(idAirport.size());
        for (Tuple<String,String> t : connections) {
            // TODO
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
                mapArrivals();
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
            int airportID = idAirport.getKey(airportCode);
            Bag<Integer> source = new Bag<Integer>();
            source.add(airportID);
            Reachable search = new Reachable(network, source);
            for (int v = 0; v < network.V(); v++) {
                if (search.marked(v)) {
                    print(idAirport.get(v) + " ");
                }
            }
            println();
        } else {
            println("Input mismatch");
        }
    }

    public static void mapArrivals() {
        print("Input Airport: ");
        String inputAirport = readString();
        if (inputAirport.length() == 3) {
            String airportCode = inputAirport;
            int airportID = idAirport.getKey(airportCode);
            ArrayList<String> arrivals = airports.get(airportID).getArrivals();
            for (String s : arrivals) {
                println(s);
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
