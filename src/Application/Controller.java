package Application;


import DMweKa.Application.DynamicTableFx;
import DMweKa.Application.TableFx;
import DMweKa.AttributDataSet;
import DMweKa.DataSet;
import DMweKa.PreProcessing;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

/**
 * Weka bibs
 **/



public class Controller {

    private final String path = "data/";
    private DataSet dataSet;

    private ObservableList<TableFx> attributslist = FXCollections.observableArrayList();


    /** File Chooser  - Data Set Properties */
    @FXML
    private ComboBox<String> combobox;

    @FXML
    private TableView<TableFx> fileContentTable;

    @FXML
    private TextField relation;

    @FXML
    private TextField attributes;

    @FXML
    private TextField nbinstances;



    /** Attribut's Names and Types !! Table !! **/
    @FXML
    private TableView<TableFx> attributsTable;

    @FXML
    private TableColumn<TableFx,Integer> num;

    @FXML
    private TableColumn<TableFx,String> name;

    @FXML
    private TableColumn<TableFx,String> type;


    /** Name , Type , Distainct , mode , Num , Lable , weight ---> for each Attribut **/
    /** and Max Min Mean Q1 Q3 midRange sum  ---> for each Numeric Attribut **/
    @FXML
    private TableView<TableFx> selectedAttributsTable;

    @FXML
    private TextField Nom;

    @FXML
    private TextField Type;

    @FXML
    private TextField distincts;

    @FXML
    private TextField mode;

    @FXML
    private TableColumn<TableFx,Integer> nume;

    @FXML
    private TableColumn<TableFx,String> label;

    @FXML
    private TableColumn<TableFx,String> weight;

    /** Max Min Mean Q1 Q3 midRange sum  ---> for each Numeric Attribut **/

    @FXML
    private TableView<TableFx> numericAttributsTable;

    @FXML
    private TableColumn<TableFx,String> max;

    @FXML
    private TableColumn<TableFx,String> min;

    @FXML
    private TableColumn<TableFx,String> mean;

    @FXML
    private TableColumn<TableFx,Integer> Q1;

    @FXML
    private TableColumn<TableFx,String> Q3;

    @FXML
    private TableColumn<TableFx,String> midRange;

    @FXML
    private TableColumn<TableFx,String> sum;


    // Histogramme Barchart
    @FXML
    private BarChart barchart;


    @FXML
    public void initialize() {

        // init the colums

        num.setCellValueFactory(new PropertyValueFactory<>("num"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        type.setCellValueFactory(new PropertyValueFactory<>("Type"));

        nume.setCellValueFactory(new PropertyValueFactory<>("num"));
        label.setCellValueFactory(new PropertyValueFactory<>("label"));
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));

        /** Max Min Mean Q1 Q3 midRange sum  ---> for each Numeric Attribut **/
        numericAttributsTable.setVisible(false);
        max.setCellValueFactory(new PropertyValueFactory<>("max"));
        min.setCellValueFactory(new PropertyValueFactory<>("min"));
        mean.setCellValueFactory(new PropertyValueFactory<>("mean"));
        Q1.setCellValueFactory(new PropertyValueFactory<>("Q1"));
        Q3.setCellValueFactory(new PropertyValueFactory<>("Q3"));
        midRange.setCellValueFactory(new PropertyValueFactory<>("midRange"));
        sum.setCellValueFactory(new PropertyValueFactory<>("sum"));

        File repo = new File(path);
        if (repo.isDirectory()) {
            File[] fileList = repo.listFiles();
            for (File f : fileList) {
                combobox.getItems().add(f.getName());
            }
            if(fileList.length > 0) { combobox.setValue(fileList[0].getName()); afficheInstance();}
        }


    }


    @FXML
    void afficheInstance() {
        clearForInstance();
        String fileName = combobox.getValue();
        DataSource dataSource;
        try {
            dataSource = new DataSource(path+fileName);
            Instances instances = dataSource.getDataSet();

            // Replace Missing Values !!
            instances = PreProcessing.preProcessData(instances);

            dataSet = new DataSet(instances);

            // display --->
            // TODO : poster the content in table element
            afficheFileContent();
            //cotent.appendText(instances.toSummaryString());

            /** Instance  Proprities */
            relation.appendText(dataSet.relation());
            nbinstances.appendText(Integer.toString(dataSet.nbInstances()));
            attributes.appendText(String.valueOf(dataSet.nbAttributs()));

            /** Show Attribut's Names and types in the Table Fx */
            showAttributsNames();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML //en cliquant sur un attribut
    private void showSelectedAttribut(){
        clearForAttribut();
        TableFx item = attributsTable.getSelectionModel().getSelectedItem();
        if(item == null) return;
        AttributDataSet attribut = dataSet.getAttribut(item.getName());

        Nom.appendText(attribut.name());
        Type.appendText(attribut.type());
        distincts.appendText(attribut.distinct());
        mode.appendText(attribut.mode());

        selectedAttributsTable.setItems(attribut.tableFxItems());
        selectedAttributsTable.sort();

        if(attribut.isNumeric()){
            numericAttributsTable.setVisible(true);
            numericAttributsTable.setItems(attribut.tableFxItemsNumiric());
        }else{
            numericAttributsTable.setVisible(false);
        }

        if( Integer.valueOf(attribut.distinct()) < 20 ) {
            barchar(attribut);
        }
    }


    /** Show Attribut's Names and Types in the Table Fx */
    public void showAttributsNames(){
        attributsTable.setItems(dataSet.listAttributsTableItems());
        attributsTable.sort();
    }


    public void clearForInstance(){
        relation.clear();nbinstances.clear();attributes.clear(); mode.clear();
    }


    public void clearForAttribut(){
        Nom.clear();Type.clear();distincts.clear();
        barchart.getData().clear();barchart.layout();
        mode.clear();
    }

    public void afficheFileContent(){
        try {
            fileContentTable = DynamicTableFx.dataSetToTableView(dataSet,fileContentTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void barchar(AttributDataSet attribut){
        TreeMap<Double,Integer> labelWeight = attribut.getLabelsAndWeightNum();
        TreeMap<String,Integer> labelWeightNom = attribut.getLabelsAndWeightNom();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(attribut.isNumeric()){
                    for(Map.Entry<Double,Integer> entry : labelWeight.entrySet()){
                        XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
                        series.getData().add(new XYChart.Data<String, Integer>(Double.toString(entry.getKey()), entry.getValue()));
                        barchart.getData().add(series);
                    }
                }else{
                    for(Map.Entry<String,Integer> entry : labelWeightNom.entrySet()){
                        XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
                        series.getData().add(new XYChart.Data<String, Integer>(entry.getKey(), entry.getValue()));
                        barchart.getData().add(series);
                    }
                }
            }
        });

        barchart.setTitle(attribut.name().toUpperCase());
        barchart.autosize();

    }

}




