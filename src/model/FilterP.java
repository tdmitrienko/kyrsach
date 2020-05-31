package model;

import java.util.ArrayList;

/**
 * Класс для вычисления расчетной концентрации выделения вредного вещества
 */
public class FilterP implements Filter{

    /**
     * Переменная: список химических элементов
     */
    private ArrayList<ChemicalSubstance> chemicalSubstances = new ArrayList<>();
    /**
     * Переменная: температурный коэффициент
     */
    private final double kt;
    /**
     * Переменная: коэффициент воздухообмена
     */
    private final double kw = 0.5;
    /**
     * Переменная: объем помещения
     */
    private final double v;

    /**
     * Конструктор класса
     * @param ktCelsius
     * @param v
     */
    public FilterP(double ktCelsius, double v) {
        this.kt = convertToKelvins(ktCelsius);
        this.v = v;
    }

    /**
     * Фильтр: расчет концентрации выделения вредного вещества
     * @param x
     * @return
     */
    @Override
    public ArrayList<ChemicalSubstance> filter(ArrayList<ChemicalSubstance> x) {
        chemicalSubstances = x;
        x.forEach(chemicalSubstance -> {
            chemicalSubstance.setEmission((getKt(kt) * chemicalSubstance.getEmission()) / (kw * v));
        });
        return x;
    }

    /**
     * метод для получения температуры в градусах Кельвин
     * @param celsius
     * @return
     */
    private double convertToKelvins(double celsius) {
        return celsius + 273;
    }

    /**
     * метод для колучения температурного коэффициента
     * @param kt
     * @return
     */
    private double getKt(double kt){
        return kt / 293;
    }

}
