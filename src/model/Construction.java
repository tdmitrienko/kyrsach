package model;

import java.util.ArrayList;

public class Construction {

    private final String name;
    private final ArrayList<Material> materials;

    public Construction(String name, ArrayList<Material> materials) {
        this.name = name;
        this.materials = materials;
    }

    public void add(Material material) {
        materials.add(material);
    }

    public void addAll(ArrayList<Material> materials) {
        this.materials.addAll(materials);
    }

    public void remove(Material material) {
        materials.remove(material);
    }

    public void removeAll(ArrayList<Material> materials) {
        this.materials.removeAll(materials);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Material> getMaterials() {
        return materials;
    }
}

