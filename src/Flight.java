import util.Time;

import java.util.Comparator;

public class Flight {
    private String source;
    private String dest;
    private int miles;
    private Time leave;
    private Time arrive;
    private String carrier;
    private int flightNo;
    private String plane;
    private int days;
    private int stops;
    private String meal;
    private String notes;

    public Flight(String input) {
        String[] info = input.split(",");
        source = info[0];
        dest = info[1];
        miles = Integer.parseInt(info[2]);
        leave = new Time(info[3]);
        arrive = new Time(info[4]);
        carrier = info[5];
        flightNo = Integer.parseInt(info[6]);
        plane = info[7];
        days = Integer.parseInt(info[8]);
        stops = Integer.parseInt(info[9]);
        meal = info[10];
        notes = info[11];
    }

    public static class SourceComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            return f1.getSource().compareTo(f2.getSource());
        }
    }

    public static class DestComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            return f1.getDest().compareTo(f2.getDest());
        }
    }

    public static class MilesComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            return f1.getMiles() - f2.getMiles();
        }
    }

    public static class LeaveComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            return f1.getLeave().compareTo(f2.getLeave());
        }
    }

    public static class ArriveComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            return f1.getArrive().compareTo(f2.getArrive());
        }
    }

    public static class CarrierComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            return f1.getCarrier().compareTo(f2.getCarrier());
        }
    }

    public static class PlaneComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            return f1.getPlane().compareTo(f2.getPlane());
        }
    }

    public static class DaysComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            return f1.getDays() - f2.getDays();
        }
    }

    public static class StopsComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            return f1.getStops() - f2.getStops();
        }
    }

    public static class MealComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            return f1.getMeal().compareTo(f2.getMeal());
        }
    }

    public String getSource() {
        return source;
    }

    public String getDest() {
        return dest;
    }

    public int getMiles() {
        return miles;
    }

    public Time getLeave() {
        return leave;
    }

    public Time getArrive() {
        return arrive;
    }

    public String getCarrier() {
        return carrier;
    }

    public int getFlightNo() {
        return flightNo;
    }

    public String getPlane() {
        return plane;
    }

    public int getDays() {
        return days;
    }

    public int getStops() {
        return stops;
    }

    public String getMeal() {
        return meal;
    }

    public String getNotes() {
        return notes;
    }
}
