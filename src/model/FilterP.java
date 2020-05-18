package model;

import java.util.ArrayList;

public class FilterP implements Filter{

    private ArrayList<ChemicalSubstance> chemicalSubstances = new ArrayList<>();
    private final double kt;
    private final double kw = 0.5;
    private final double v;

    public FilterP(double ktCelsius, double v) {
        this.kt = convertToKelvins(ktCelsius);
        this.v = v;
    }

    @Override
    public ArrayList<ChemicalSubstance> filter(ArrayList<ChemicalSubstance> x) {
        chemicalSubstances = x;
        x.forEach(chemicalSubstance -> {
            chemicalSubstance.setEmission((getKt(kt) * chemicalSubstance.getEmission()) / (kw * v));
        });
        return x;
    }

    private double convertToKelvins(double celsius) {
        return celsius + 273;
    }

    private double getKt(double kt){
        return kt / 293;
    }

}
