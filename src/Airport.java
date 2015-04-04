import java.util.ArrayList;
public class Airport {
    private int id;
    private String name;
    private ArrayList<String> departures = new ArrayList<String>();
    private ArrayList<String> arrivals = new ArrayList<String>();

    public Airport(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addArrival(String airport) {
        arrivals.add(airport);
    }

    public void addDeparture(String airport) {
        departures.add(airport);
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getArrivals() {
        return arrivals;
    }

    public ArrayList<String> getDepartures() {
        return departures;
    }

    public int getID() {
        return id;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("ID: " + id + " - Name: " + name);
        return s.toString();
    }


}
