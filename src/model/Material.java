package model;

public class Material {

    private String name;
    private String mark;
    private double square;

    public Material (String name, String mark, double square) {
        this.name = name;
        this.mark = mark;
        this.square = square;
    }

    public String getName() {
        return name;
    }

    public String getMark() {
        return mark;
    }

    public double getSquare() {
        return square;
    }
}
