package model;

/**
 * Класс для описания строительного материала
 */
public class Material {

    /**
     * Переменная: название строительного материала
     */
    private String name;
    /**
     * Переменная: название марки строительного материала
     */
    private String mark;
    /**
     * Переменная: площадь строительного материала
     */
    private double square;

    /**
     * Конструктор класса
     * @param name
     * @param mark
     * @param square
     */
    public Material (String name, String mark, double square) {
        this.name = name;
        this.mark = mark;
        this.square = square;
    }

    /**
     * метод для получения названия строительного материала
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * метод для получения марки строительного материала
     * @return
     */
    public String getMark() {
        return mark;
    }

    /**
     * метод для получения площади строительного материала
     * @return
     */
    public double getSquare() {
        return square;
    }
}
