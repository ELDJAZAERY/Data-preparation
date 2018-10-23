package Application;

import DMweKa.*;
import DMweKa.Application.ListAttributTable;
import DMweKa.Calculateur;
import DMweKa.Instac;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.net.URL;
import javafx.scene.control.TableColumn;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;


public class Controller {

    private final String path = "data/";
    private DataSet dataSet;

    private ObservableList<ListAttributTable> attributslist = FXCollections.observableArrayList();


    /** File Chooser */
    @FXML
    private ComboBox<String> combobox;
    @FXML
    private TextArea cotent;


    /** Text Feild --- !! **/
    @FXML
    private TextField relation;

    @FXML
    private TextField attributes;

    @FXML
    private TextField instances;

    @FXML
    private TextField Nom;

    @FXML
    private TextField Type;

    @FXML
    private TextField distincts;



    /** Attribut's Names and Types !! Table !! **/
    @FXML
    private TableView<ListAttributTable> table;

    @FXML
    private TableColumn<ListAttributTable,Integer> num;

    @FXML
    private TableColumn<ListAttributTable,String> name;

    @FXML
    private TableColumn<ListAttributTable,String> type;







    // -------------- old ------------- //






    @FXML
    private BarChart barchart;

    @FXML
    private TableColumn<Calculateur,Integer> nume;

    @FXML
    private TableColumn<Calculateur,String> label;

    @FXML
    private TableColumn<Calculateur,Integer> count;

    @FXML
    private TableColumn<Calculateur,String> weight;


    @FXML
    public void initialize() {
        File repo = new File(path);
        if (repo.isDirectory()) {
            File[] fileList = repo.listFiles();
            for (File f : fileList) {
                combobox.getItems().add(f.getName());
            }
        }

        num.setCellValueFactory(new PropertyValueFactory<>("num"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        type.setCellValueFactory(new PropertyValueFactory<>("Type"));

        nume.setCellValueFactory(new PropertyValueFactory<>("num"));
        label.setCellValueFactory(new PropertyValueFactory<>("label"));
        count.setCellValueFactory(new PropertyValueFactory<>("count"));
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));

     }


    @FXML
    void afficheInstance() {
        clearForInstance();
        String fileName = combobox.getValue();
        DataSource dataSource;
        try {
            dataSource = new DataSource(path+fileName);
            dataSet = new DataSet(dataSource.getDataSet());

            // TODO : affiche content of file in table element
            cotent.appendText(dataSource.getDataSet().toSummaryString());
            Instances inst = PreProcessing.preProcessData(dataSource.getDataSet());
            cotent.appendText(inst.toSummaryString());

            /** Instance  Proprities */
            relation.appendText(dataSet.relation());
            instances.appendText(Integer.toString(dataSet.nbInstances()));
            attributes.appendText(String.valueOf(dataSet.nbAttributs()));

            /** Show Attribut's Names and types in the Table Fx */
            showAttributsNames();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML //en cliquant sur un attribut
    private void showAttribut(){ }


    public void clearForInstance(){
        cotent.clear();relation.clear();instances.clear();attributes.clear();
    }

    public void clearForAttribut(){
        Nom.clear();Type.clear();distincts.clear();
        barchart.getData().clear();barchart.layout();
    }


    /** Show Attribut's Names and Types in the Table Fx */
    public void showAttributsNames(){
        table.setItems(dataSet.listAttributsTableItems());
        table.sort();
    }

}




