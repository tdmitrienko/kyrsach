package model;

import java.util.ArrayList;

public interface Filter {
    ArrayList<ChemicalSubstance> filter(ArrayList<ChemicalSubstance> x);
}
