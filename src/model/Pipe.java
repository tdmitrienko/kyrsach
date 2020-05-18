package model;
import javafx.collections.ObservableList;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pipe {

    private List<Filter> filters = new ArrayList<>();
    private ArrayList<ArrayList<ChemicalSubstance>> tables = new ArrayList<>();
    private ArrayList<ChemicalSubstance> chemicalSubstances;

    public Pipe(ArrayList<ChemicalSubstance> chemicalSubstances, Filter... filters) { //Передача фильтров автоматический массив [] (insertion - входное число)
        this.chemicalSubstances = chemicalSubstances;
        this.filters.addAll(Arrays.asList(filters));
    }

    public ObservableList<String> calculate(ObservableList<String> massiv) {
        DecimalFormat df = new DecimalFormat("#.000");
        if (filters.size() == 3) {
            chemicalSubstances = filters.get(0).filter(chemicalSubstances);
            chemicalSubstances.forEach(chemicalSubstance -> {
                massiv.add("Конструкция: " + chemicalSubstance.getConstruction() + ", Материал: " + chemicalSubstance.getMaterial() + ", Химический элемент: " + chemicalSubstance.getName() + ", Q: " +df.format(chemicalSubstance.getEmission()) + ";");
            });
            massiv.add("");
            chemicalSubstances = filters.get(1).filter(chemicalSubstances);
            chemicalSubstances.forEach(chemicalSubstance -> {
                massiv.add("Конструкция: " + chemicalSubstance.getConstruction() + ", Материал: " + chemicalSubstance.getMaterial() + ", Химический элемент: " + chemicalSubstance.getName() + ", P: " +df.format(chemicalSubstance.getEmission()) + ";");
            });
            massiv.add("");
            chemicalSubstances = filters.get(2).filter(chemicalSubstances);
            chemicalSubstances.forEach(chemicalSubstance -> {
                massiv.add("Конструкция: " + chemicalSubstance.getConstruction() + ", Материал: " + chemicalSubstance.getMaterial() + ", Химический элемент: " + chemicalSubstance.getName() + ", SUMM: " +df.format(chemicalSubstance.getEmission())+ ", PDK: " + chemicalSubstance.getPdk() + ";");
            });
            massiv.add("");
        }
        else {
            filters.forEach(filter -> {
                chemicalSubstances = new ArrayList<>(filter.filter(chemicalSubstances));
                chemicalSubstances.forEach(chemicalSubstance -> {
                    massiv.add("Конструкция: " + chemicalSubstance.getConstruction() + ", Материал: " + chemicalSubstance.getMaterial() + ", Химический элемент: " + chemicalSubstance.getName() + ", E: " + chemicalSubstance.getEmission() + ", PDK: " + chemicalSubstance.getPdk() + ";");
                });
                tables.add(chemicalSubstances);
            });
        }
        return massiv;
    }
}