package Application;

import DMweKa.Application.ListAttributTable;
import DMweKa.Calculateur;
import DMweKa.Instac;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Controller2 {

    private final String path = "data/";


    private Instances data;
    private Instac ins;


    private ArrayList <String> array = new ArrayList<>();
    private ArrayList <Integer> valdistinctes = new ArrayList<>();
    private Map<String, Integer> mapOfRepeatedWord = new HashMap<>();


    private ObservableList<Calculateur> liste1;

    private ObservableList<ListAttributTable> liste = FXCollections.observableArrayList();

    private XYChart.Series series = new XYChart.Series();


    @FXML
    private TextArea cotent;

    @FXML
    private ComboBox<String> combobox;

    @FXML
    private TextField attributes;

    @FXML
    private TextField Sw;

    @FXML
    private TextField relation;

    @FXML
    private TextField instances;

    @FXML
    private TextField Nom;

    @FXML
    private TextField Type;

    @FXML
    private TextField missing;

    @FXML
    private TextField distincts;

    @FXML
    private TableView<ListAttributTable> table;
    
    @FXML
    private TableColumn<ListAttributTable,Integer> num;
    
    @FXML
    private TableColumn<ListAttributTable,String> name;
    
    @FXML
    private TableColumn<ListAttributTable,String> typ;

    @FXML
    private BarChart barchart;

    @FXML
    private TableView<Calculateur> table1;

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
        typ.setCellValueFactory(new PropertyValueFactory<>("Type"));
        nume.setCellValueFactory(new PropertyValueFactory<>("num"));
        label.setCellValueFactory(new PropertyValueFactory<>("label"));
        count.setCellValueFactory(new PropertyValueFactory<>("count"));
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));

     }


    @FXML
    void afficheInstance() {
        ClearForInstance();
        String var = combobox.getValue();
        DataSource p;
        try {
            p = new DataSource(path+var);
            data = p.getDataSet();

            // TODO : affiche content of file in table element
            cotent.appendText(String.valueOf(data));

            /** Instance  Proprities */
            relation.appendText(data.relationName());
            instances.appendText(Integer.toString(data.numInstances()));
            attributes.appendText(String.valueOf(data.numAttributes()));

            int i = 0;
            while( i < data.numAttributes()){
              // liste.add(new ListAttributTable(data.attribute(i).name(),""));
               if(data.attribute(i).isNominal()) {
                   array.add("Nominale");
               }else if (data.attribute(i).isNumeric()){
                   array.add("Numerique");
               }else if(data.attribute(i).isDate()){
                   array.add("Date");
               }
               valdistinctes.add(data.attribute(i).numValues());
               //table.setItems(liste);
               table.sort();
               i++;
          }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML //en cliquant sur un attribut
    private void showAttribut(){
//        ClearForAttribut();
//        TableAtt ligne = table.getSelectionModel().getSelectedItem();
//        Nom.appendText(ligne.name.get());
//        Type.appendText(array.get(ligne.num.get()-1));
//
//        int i = 0;
//        while ( i < data.numAttributes() ) {
//            if (data.attribute(i).name().equals(ligne.name.get())) {
//                ins = new Instac();
//
//                System.out.println("nouveau" + data.attribute(i).name());
//                if (data.attribute(i).isNumeric()) {
//                    for (int j = 0; j < data.numInstances(); j++) {
//                        String str = new String("" + data.get(j));
//                        ins.attribute = data.attribute(i).name();
//                        List<String> lign = Arrays.asList(str.split(","));
//                        ins.lign.add(Float.parseFloat(lign.get(i)));
//                    }
//                    distincts.appendText(String.valueOf(ins.distinctValues()));
//                    nume.setVisible(false);
//                    count.setVisible(false);
//                    label.setText("Statistic");
//                    label.setMinWidth(150);
//                    weight.setText("Value");
//                    weight.setMinWidth(500);
//
//                    liste1 = FXCollections.observableArrayList(
//                        new Calculateur(new Integer(1), "Minimum", new Integer(502), new Float(ins.Min()).toString()),
//                        new Calculateur(new Integer(1), "Maximum", new Integer(502), new Float(ins.Max()).toString()),
//                        new Calculateur(new Integer(1), "Moyenne", new Integer(502),new Float(ins.Mean()).toString()),
//                        new Calculateur(new Integer(1), "Médianne", new Integer(502),new Float(ins.calculMedian()).toString() ),
//                        new Calculateur(new Integer(1), "1er quartile", new Integer(502), new Float(ins.calculQ1()).toString()),
//                        new Calculateur(new Integer(1), "3eme quartile", new Integer(502), new Float(ins.calculQ3()).toString()),
//                        new Calculateur(new Integer(1), "Mode", new Integer(502),ins.Mode()),
//                        new Calculateur(new Integer(1), "MidRange", new Integer(502), new Float(ins.calculMidRange()).toString())
//                    );
//
//                    table1.setItems(liste1);
//                    System.out.println("Mean"+ins.Mean());
//                }else{
//                    nume.setVisible(true);
//                    count.setVisible(true);
//                    label.setText("Label");
//                    label.setMinWidth(200);
//                    weight.setText("weight");
//                    weight.setMinWidth(500);
//                    for (int j = 0; j < data.numInstances(); j++) {
//                        String str = new String("" + data.get(j));
//                        //mettre chaque instance d'un même attribut dans un tableau
//                        ins.attribute = data.attribute(i).name();
//                        //ArrayList<String> lign = Arrays.asList(str.split(","));
//                        List<String> lign = Arrays.asList(str.split(","));
//                        // System.out.println("hna"+lign.get(i));
//                        ins.ligne.add(lign.get(i));
//                    }
//                    mapOfRepeatedWord=ins.calculfreq();
//                    distincts.appendText(String.valueOf(mapOfRepeatedWord.size()));
//                    Integer k=1;
//
//                    for(Map.Entry<String, Integer> entry : mapOfRepeatedWord.entrySet()){
//                        System.out.println(entry.getKey() + "\t\t" + entry.getValue());
//
//                        liste1 = FXCollections.observableArrayList(new Calculateur(
//                                        new Integer(1),
//                                        entry.getKey(),
//                                        entry.getValue(),
//                                        new Float(entry.getValue()).toString())
//                        );
//
//                        table1.setItems(liste1);k=k+1;
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
//                                series.getData().add(new XYChart.Data<String, Integer>(entry.getKey(), entry.getValue()));
//                                barchart.getData().add(series);
//
//                            }
//                        });
//                        mapOfRepeatedWord.remove(entry.getKey());
//
//                        break;
//                    }
//                    for(Map.Entry<String, Integer> entry : mapOfRepeatedWord.entrySet()){
//                        System.out.println(entry.getKey() + "\t\t" + entry.getValue());
//
//
//                        liste1.add(new Calculateur(k, entry.getKey(), entry.getValue(), new Float(entry.getValue()).toString()));
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
//                                series.getData().add(new XYChart.Data<String, Integer>(entry.getKey(), entry.getValue()));
//                                barchart.getData().add(series);
//
//                            }
//                        });
//
//                        k++;
//                    }
//                   table1.setItems(liste1);
//                    }
//
//                }
//            }

        }


    public void ClearForInstance(){
        cotent.clear();relation.clear();instances.clear();attributes.clear();
    }

    public void ClearForAttribut(){
        Nom.clear();Type.clear();distincts.clear();
        barchart.getData().clear();barchart.layout();
    }

}




