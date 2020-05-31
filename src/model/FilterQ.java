package model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс для расчета выделения химического вещества
 */
public class FilterQ implements Filter {

    /**
     * Переменная: список строительных конструкций
     */
    private final ArrayList<Construction> constructions;
    /**
     * Переменная: список химических элементов
     */
    private ArrayList<ChemicalSubstance> chemicalSubstances = new ArrayList<>();
    /**
     * Переменная: для работы с базой данных
     */
    private DAO dataBase;

    /**
     * Конструктор класса
     * @param constructions
     * @param dataBase
     */
    public FilterQ(ArrayList<Construction> constructions, DAO dataBase) {
        this.constructions = constructions;
        this.dataBase = dataBase;
    }

    /**
     * Фильтр для расчета выделения химического вещества
     * @param x
     * @return
     */
    @Override
    public ArrayList<ChemicalSubstance> filter(ArrayList<ChemicalSubstance> x) {
        constructions.forEach(construction -> {
            construction.getMaterials().forEach(material -> {
                ResultSet resultSet = null;
                try {
                    resultSet = dataBase.dbExecuteQueryDao(
                            "SELECT chemicalsubstance.name, chemicalsubstance.Emission, chemicalsubstance.PDK, builmaterial.name, builmaterial.Diffusion, buildconstruction.name FROM chemicalsubstance " +
                                    "JOIN chemicalsubstance_buildmaterial ON (chemicalsubstance.ID = chemicalsubstance_buildmaterial.ID_ChemicalSubstance) " +
                                    "JOIN builmaterial ON (builmaterial.ID = chemicalsubstance_buildmaterial.ID_BuildMaterial) " +
                                    "JOIN buildconstruction ON (buildconstruction.ID = builmaterial.Id_buildconstruction) " +
                                    "JOIN mark ON (mark.ID = builmaterial.ID_MARK) " +
                                    "WHERE builmaterial.name = '" + material.getName() + "' AND mark.NAME = '" + material.getMark() + "';");
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        if (!resultSet.next())
                            break;
                        else {
                            chemicalSubstances.add(new ChemicalSubstance(resultSet.getString(1), Double.parseDouble(resultSet.getString(2)), Double.parseDouble(resultSet.getString(3)), resultSet.getString(4), resultSet.getString(6)));
                            x.add(new ChemicalSubstance(resultSet.getString(1), Double.parseDouble(resultSet.getString(2)) * material.getSquare() * Double.parseDouble(resultSet.getString(5)), Double.parseDouble(resultSet.getString(3)), resultSet.getString(4), resultSet.getString(6)));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        return x;
    }

}
