import map.Reachable;
import util.*;

import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;

import static util.StdOut.*;
import static util.StdIn.*;

public class FlightMapper {
    private static ReadCSV input = new ReadCSV("data/StarAlliance.csv");
    //private static ReadCSV input = new ReadCSV("data/Canada.csv");
    private static HashMap<Integer, String> idAirport = new HashMap<Integer, String>(100);
    private static ArrayList<Airport> airports = new ArrayList<Airport>();
    private static ArrayList<Flight> flights = new ArrayList<Flight>();
    private static Digraph network;
    private static EdgeWeightedDigraph distanceNetwork;

    /**
     * Initialises the application by loading data
     */
    public static void init() throws IOException {
        // Creates a list of airports
        ArrayList<String> departures = input.readColumn(0);
        //println(departures);
        ArrayList<String> arrivals = input.readColumn(1);
        //println(arrivals);
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
        println(airports.size() + " airports loaded");

        // Prepare a database of flights
        // int numberOfLines = input.countLines();
        int numberOfLines = 1000;
        for (int i = 1; i < numberOfLines; i++) {
            flights.add(new Flight(input.readLine(i)));
        }
        println(flights.size() + " flights loaded");

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
        	// Find the flight
        	for (Flight f : flights) {
        		if (f.getSource().equals(t.x) && f.getDest().equals(t.y)) {
                    DirectedEdge connection = new DirectedEdge(idAirport.getKey(t.x), idAirport.getKey(t.y), f.getMiles());
                    distanceNetwork.addEdge(connection);
                }
        	}

        }
        
    }

    public static void search() {
        searchIntro();
        ArrayList<Flight> results = new ArrayList<Flight>();
        print("Departure: ");
        String inputDep = readString();
        if (inputDep.length() != 3) {
            println("Input mismatch");
            return;
        } else {
            results = searchDep(inputDep);
            print("Arrival: ");
            String inputArv = readString();
            if (inputArv.length() != 3) {
                println("Input mismatch");
                return;
            } else {
                results = searchArv(results, inputArv);
                print("Narrow Search? (Y/N): ");
                String inputNarrow = readString();
                switch(inputNarrow.charAt(0)) {
                    case 'Y':
                        searchMore(results);
                        break;
                    case 'N':
                        sort(results);
                        break;
                    default:
                        println("Input mismatch");
                        break;
                }
            }
        }

    }

    private static void searchMore(ArrayList<Flight> oldResults) {
        ArrayList<Flight> results = oldResults;
        int returnCode = -1;
        while (returnCode != 0) {
            println("Search method: ");
            println("1. Airline");
            println("2. Plane");
            println("3. Done");
            print("Input: ");
            String inputSearchMore = readString();
            switch(inputSearchMore.charAt(0)) {
                case '1':
                    print("Enter 2-letter code: ");
                    String inputAirline = readString();
                    results = searchAirline(results, inputAirline);
                    break;
                case '2':
                    print("Enter plane code: ");
                    String inputPlane = readString();
                    results = searchPlane(results, inputPlane);
                    break;
                case '3':
                    returnCode = 0;
                    break;
                default:
                    println("Input mismatch");
                    break;
            }
        }
        sort(results);
    }

    private static ArrayList<Flight> searchAirline(ArrayList<Flight> oldResults, String key) {
        ArrayList<Flight> results = new ArrayList<Flight>();
        for (Flight f : oldResults) {
            if (f.getCarrier().equals(key)) {
                results.add(f);
            }
        }
        println(results.size() + " results found");
        return results;
    }

    private static ArrayList<Flight> searchPlane(ArrayList<Flight> oldResults, String key) {
        ArrayList<Flight> results = new ArrayList<Flight>();
        for (Flight f : oldResults) {
            if (f.getPlane().equals(key)) {
                results.add(f);
            }
        }
        println(results.size() + " results found");
        return results;
    }

    private static void sort(ArrayList<Flight> oldResults) {
        ArrayList<Flight> results = oldResults;
        int returnCode = -1;
        while (returnCode != 0) {
            println("Sort by: ");
            println("1. Departure Time");
            println("2. Arrival Time");
            println("3. Distance");
            print("Input: ");
            String inputSort = readString();
            switch(inputSort.charAt(0)) {
                case '1':
                    Collections.sort(results, new Flight.LeaveComparator());
                    returnCode = 0;
                    break;
                case '2':
                    Collections.sort(results, new Flight.ArriveComparator());
                    returnCode = 0;
                    break;
                case '3':
                    Collections.sort(results, new Flight.MilesComparator());
                    returnCode = 0;
                    break;
                default:
                    println("Input mismatch");
                    break;
            }
        }
        for (Flight f : results) {
            println(f.toString());
        }
    }

    private static ArrayList<Flight> searchDep(String key) {
        ArrayList<Flight> results = new ArrayList<Flight>();
        for (Flight f : flights) {
            if (f.getSource().equals(key)) {
                results.add(f);
            }
        }
        println(results.size() + " results found");
        return results;
    }

    private static ArrayList<Flight> searchArv(ArrayList<Flight> oldResults, String key) {
        ArrayList<Flight> results = new ArrayList<Flight>();
        for (Flight f : oldResults) {
            if (f.getDest().equals(key)) {
                results.add(f);
            }
        }
        println(results.size() + " results found");
        return results;
    }

    public static void searchIntro() {
        println("Search");
    }

    // Mapping Code

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
            StringBuilder s = new StringBuilder();
            int airportID = idAirport.getKey(airportCode);
            Airport airport = airports.get(airportID);
            ArrayList<String> seen = new ArrayList<String>();
            for (String dep : airport.getDepartures()) {
                if (!seen.contains(dep)) {
                    s.append(dep + " ");
                    seen.add(dep);
                }

            }
            s.append("\n");
            print(s.toString());
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
            StringBuilder s = new StringBuilder();
            int airportID = idAirport.getKey(airportCode);
            Airport airport = airports.get(airportID);
            ArrayList<String> seen = new ArrayList<String>();
            for (String arv : airport.getArrivals()) {
                if (!seen.contains(arv)) {
                    s.append(arv + " ");
                    seen.add(arv);
                }
            }
            s.append("\n");
            print(s.toString());
            println();
        } else {
            println("Input mismatch");
        }
    }

    public static void mapSP() {

    }

    public static void mapReach() {
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
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        intro();
        int returnCode = -1;
        while(returnCode != 1) {
            printMenu();
            returnCode = menuInput();
        }
        exit();

    }
}
