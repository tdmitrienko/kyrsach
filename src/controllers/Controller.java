package controllers;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public DAO dbConnect = new DBConnect();

    /**
     * лист содержащий список материалов группы стройматериалы
     */
    ArrayList<Material> materials1 = new ArrayList<>(); //лист содержащий список материалов группы стройматериалы
    /**
     * лист содержащий список материалов группы отделочные материалы
     */
    ArrayList<Material> materials2 = new ArrayList<>();//лист содержащий список материалов группы отделочные материалы
    /**
     * лист содержащий список материалов группы мебель
     */
    ArrayList<Material> materials3 = new ArrayList<>();//лист содержащий список материалов группы мебель
    /**
     * лист содержащий все материалы по трем группам
     */
    ArrayList<Material> allMaterials = new ArrayList<>();//лист содержащий все материалы по трем группам
    public ListView listView;
    /**
     * поле для ввода объема
     */
    public TextField V; //поле для ввода объема
    /**
     * поле для ввода температуры
     */
    public TextField t; //поле для ввода температуры
    /**
     * выбор группы строительного материала
     */
    public ComboBox GSM; //выбор группы строительного материала
    /**
     * поле для ввода площади
     */
    public TextField S; //поле для ввода площади
    /**
     * выбор строительного материала
     */
    public ComboBox SM; //выбор строительного материала
    /**
     * выбор марки строительного материала
     */
    public ComboBox mark;//выбор марки строительного материала
    Pipe pipe;

    /**
     * метод срабатывает автоматически при запуске программы, в данном методе производится выборка названий строительных конструкций из базы данных и эти названия записываются в GSM
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ResultSet rez = null;
        try {
            rez = dbConnect.dbExecuteQueryDao("SELECT name FROM buildconstruction");//запрос на выборку имени из таблицы  buildconstruction
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                if (!rez.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                GSM.getItems().add(rez.getString(1)); //добавление в комбобокс результатов выборки
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * метод для добавления в listView  а так же в лист материалов
     */
    public void add() { //метод для добавления в listView  а так же в лист материалов
        try {
            double ss = Double.parseDouble(S.getText());
            if (!GSM.getValue().toString().isEmpty() && !SM.getValue().toString().isEmpty() && !S.getText().isEmpty() && !mark.getValue().toString().isEmpty()) {
                listView.getItems().add(GSM.getValue() + ": " + SM.getValue() + ", площадью S = " + S.getText() + " м²");
                allMaterials.add(new Material(SM.getValue().toString(), mark.getValue().toString(), ss));
                if (GSM.getValue().toString().equals("finish"))
                    materials2.add(new Material(SM.getValue().toString(), mark.getValue().toString(), ss));
                else if (GSM.getValue().toString().equals("furniture"))
                    materials3.add(new Material(SM.getValue().toString(), mark.getValue().toString(), ss));
                else
                    materials1.add(new Material(SM.getValue().toString(), mark.getValue().toString(), ss));
            }
        } catch (NullPointerException | NumberFormatException e) {

        }
    }

    /**
     * метод для отбора строительного материала в зависимости от выбранной группы строительного материала
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void selectMaterials() throws SQLException, ClassNotFoundException { //метод для отбора строительного материала в зависимости от выбранной группы
        SM.getItems().clear();
        ResultSet rez;
        rez = dbConnect.dbExecuteQueryDao("SELECT builmaterial.name FROM builmaterial inner join buildconstruction on(buildconstruction.ID=builmaterial.Id_buildconstruction) where buildconstruction.name='" + GSM.getValue() + "'");
        while (rez.next()) {
            SM.getItems().add(rez.getString(1));
        }
    }

    /**
     * метод для расчета
     */
    public void calculateREZ() {//метод для расчета
        try{
            listView.getItems().clear();
        double temperature = Double.parseDouble(t.getText());
        double volume = Double.parseDouble(V.getText());


        Construction construction1 = new Construction("building materials", materials1);
        Construction construction2 = new Construction("finish", materials2);
        Construction construction3 = new Construction("furniture", materials3);

        ArrayList<Construction> constructions = new ArrayList<>();
        constructions.add(construction1);
        constructions.add(construction2);
        constructions.add(construction3);

        Filter filterQ = new FilterQ(constructions, dbConnect);//фильтр для расчета выделение j -того вредного вещества
        Filter filterP = new FilterP(temperature, volume);//фильтр для расчета концентрация выделения j -го вредного вещества
        Filter filterPDK = new FilterPDK();// фильтр для подсчета конечной концентрации по каждому химическому элементу


        ArrayList<ChemicalSubstance> chemicalSubstances = new ArrayList<>();
        pipe = new Pipe(chemicalSubstances, filterQ, filterP, filterPDK);
        pipe.calculate(listView.getItems());}
        catch (NullPointerException | NumberFormatException e){

        }

    }

    /**
     * удаление элементов из listView, а так же из листа материалов
     */
    public void delete() {//удаление элементов из  listView и листа материалов
        try {
            int x = listView.getSelectionModel().getSelectedIndex();
            listView.getItems().remove(x);

            for (Material material : materials1) {
                if (allMaterials.get(x).getName().equals(material.getName()) && allMaterials.get(x).getMark().equals(material.getMark()) && material.getSquare() == allMaterials.get(x).getSquare()) {
                    materials1.remove(material);
                    break;
                }
            }
            for (Material material : materials2) {
                if (allMaterials.get(x).getName().equals(material.getName()) && allMaterials.get(x).getMark().equals(material.getMark()) && material.getSquare() == allMaterials.get(x).getSquare()) {
                    materials2.remove(material);
                    break;
                }
            }
            for (Material material : materials3) {
                if (allMaterials.get(x).getName().equals(material.getName()) && allMaterials.get(x).getMark().equals(material.getMark()) && material.getSquare() == allMaterials.get(x).getSquare()) {
                    materials3.remove(material);
                    break;
                }
            }
            allMaterials.remove(x);
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    /**
     * метод для отбора марки в зависимости от выбранного строительного материала
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void selectMark() throws SQLException, ClassNotFoundException {
        mark.getItems().clear();
        ResultSet rez;
        rez = dbConnect.dbExecuteQueryDao("SELECT mark.NAME FROM mark inner join builmaterial on(mark.ID=builmaterial.ID_MARK) where builmaterial.name='" + SM.getValue() + "'");
        while (rez.next()) {
            mark.getItems().add(rez.getString(1));
        }
    }

    /**
     * вывод результата в текстовый файл
     */
    public void saveTxt() {//вывод результата в текстовый файл
        try {
            Files.write(Paths.get("demo.txt"), pipe.calculate(listView.getItems()), StandardOpenOption.CREATE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message Here...");
            alert.setHeaderText("Готово");
            alert.setContentText("Отчет успешно сформирован!!!!");
            alert.showAndWait();
        } catch (IOException e) {

        }
    }
}
