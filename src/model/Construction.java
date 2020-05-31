package model;

import java.util.ArrayList;

/**
 * Класс для отписания строительной конструкции
 */
public class Construction {

    /**
     * Переменная: название строительной конструкции
     */
    private final String name;
    /**
     * Переменная: материалы из которых состоит строительная конструкция
     */
    private final ArrayList<Material> materials;

    /**
     * Конструктор класса
     * @param name
     * @param materials
     */
    public Construction(String name, ArrayList<Material> materials) {
        this.name = name;
        this.materials = materials;
    }

    /**
     * метод для добавления строительно материала
     * @param material
     */
    public void add(Material material) {
        materials.add(material);
    }

    /**
     * метод для добавления списка строительных материалов
     * @param materials
     */
    public void addAll(ArrayList<Material> materials) {
        this.materials.addAll(materials);
    }

    /**
     * метод для удаления строительного материала
     * @param material
     */
    public void remove(Material material) {
        materials.remove(material);
    }

    /**
     * метод для удаления списка строительных материалов
     * @param materials
     */
    public void removeAll(ArrayList<Material> materials) {
        this.materials.removeAll(materials);
    }

    /**
     * метод для получения названия строительной конструкции
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * метод для получения строительных материалов
     * @return
     */
    public ArrayList<Material> getMaterials() {
        return materials;
    }
}

