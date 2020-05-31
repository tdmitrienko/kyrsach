package model;

/**
 * Класс для описания химического элемента
 */
public class ChemicalSubstance {
    /**
     * Переменная: название химического элемента
     */
    private String name;
    /**
     * Переменная: эмиссия химического элемента
     */
    private double emission;
    /**
     * Переменная: ПДК химического элемента
     */
    private double pdk;
    /**
     * Переменная: материал к которому относится химических элемент
     */
    private String material;
    /**
     * Переменная: конструкция к которой относится материал
     */
    private String construction;

    /**
     * Конструктор класса
     * @param name
     * @param emission
     * @param pdk
     * @param material
     * @param construction
     */
    public ChemicalSubstance(String name, double emission, double pdk, String material, String construction) {
        this.name = name;
        this.emission = emission;
        this.pdk = pdk;
        this.material = material;
        this.construction = construction;
    }

    /**
     * метод для изменения эмиссии химичского элемента
     * @param emission
     */
    public void setEmission(double emission) {
        this.emission = emission;
    }

    /**
     * метод для получения названия химического элемента
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * метод для получения эмиссии химического элемента
     * @return
     */
    public double getEmission() {
        return emission;
    }

    /**
     * метод для получения ПДК химического элемента
     * @return
     */
    public double getPdk() {
        return pdk;
    }

    /**
     * метод для получения материала к кторому относится химический элемент
     * @return
     */
    public String getMaterial() {
        return material;
    }

    /**
     * метод для получения конструкции к которой относится строительный материал
     * @return
     */
    public String getConstruction() {
        return construction;
    }
}
