package model;

public class ChemicalSubstance {
    private String name;
    private double emission;
    private double pdk;
    private String material;
    private String construction;

    public ChemicalSubstance(String name, double emission, double pdk, String material, String construction) {
        this.name = name;
        this.emission = emission;
        this.pdk = pdk;
        this.material = material;
        this.construction = construction;
    }

    public void setEmission(double emission) {
        this.emission = emission;
    }

    public String getName() {
        return name;
    }

    public double getEmission() {
        return emission;
    }

    public double getPdk() {
        return pdk;
    }

    public String getMaterial() {
        return material;
    }

    public String getConstruction() {
        return construction;
    }
}
