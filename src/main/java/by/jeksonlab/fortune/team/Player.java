package by.jeksonlab.fortune.team;

public class Player {

    private String name;
    private double rating;

    public Player(String name, double rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }
}
