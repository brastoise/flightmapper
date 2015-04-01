public class Airport {
    private String iata;
    private int id;

    public Airport(String name, int id) {
        iata = name;
        this.id = id;
    }

    public int getID() {
        return id;
    }



}
