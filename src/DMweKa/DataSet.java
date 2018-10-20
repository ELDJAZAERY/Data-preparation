package DMweKa;

import java.util.ArrayList;

import weka.core.*;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Standardize;
import weka.filters.unsupervised.attribute.NominalToBinary;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import DMweKa.Application.ListAttributTable;


public class DataSet {

    // List of Attribut's names
    private ArrayList<String> listAttributs = new ArrayList<>();
    // List of Attributs <Class> of DataSet
    private ArrayList<AttributDataSet> attributs = new ArrayList<>();
    private ArrayList<Instance> instances = new ArrayList<>();

    private String relation;
    private int nbInstances ;
    private int nbAttributs ;

    public DataSet(Instances data) {
        relation = data.relationName();
        try {
            data = preProcessData(data,true,false,false);
            data.setRelationName(relation);
        } catch (Exception e) {
            e.printStackTrace();
        }

        nbInstances = data.numInstances();
        this.instances = new ArrayList<>(data);

        nbAttributs = data.numAttributes() ;
        for(int i=0;i<data.numAttributes();i++){
            attributs.add(new AttributDataSet(data.attribute(i),data.attributeStats(i),data));
            listAttributs.add(data.attribute(i).name());
        }
    }

    private Instances normalization(Instances data){
        return null;
    }

    public ArrayList<AttributDataSet> getAttributs() {
        return attributs;
    }

    public AttributDataSet getAttribut(int i){
        i = Math.abs(i);
        if(i<attributs.size()) return attributs.get(i);
        return null;
    }

    public AttributDataSet getAttribut(String name){
       for(AttributDataSet atr:attributs){
           if(atr.name().equalsIgnoreCase(name)) return atr;
       }
       return null;
    }


    public String relation() {
        return relation;
    }

    public int nbInstances() {
        return nbInstances;
    }

    public int nbAttributs() {
        return nbAttributs;
    }

    public ArrayList<String> listAttributs() {
        return listAttributs;
    }


    public ObservableList<ListAttributTable> listAttributsTableItems(){
        // Reinsialize the static counter
        ListAttributTable.reInisialize();
        ObservableList<ListAttributTable> items = FXCollections.observableArrayList();
        for(AttributDataSet attribut:attributs){
            items.add(new ListAttributTable(attribut.name(),attribut.type()));
        }
        return items;
    }

    public void toTableFx(){
        // TODO
    }

    public static Instances preProcessData(Instances data, boolean shouldImpute,
                                           boolean shouldStandardize,boolean shouldBinarize) throws Exception {

        if( shouldImpute ) {
            Filter impute = new ReplaceMissingValues();
            impute.setInputFormat(data);
            data = Filter.useFilter(data, impute);
        }
        if( shouldStandardize ) {
            Filter standardize = new Standardize();
            standardize.setInputFormat(data);
            data = Filter.useFilter(data, standardize);
        }
        if( shouldBinarize ) {
            Filter binarize = new NominalToBinary();
            binarize.setInputFormat(data);
            // make resulting binary attrs nominal, not numeric
            binarize.setOptions(new String[] { "-N" } );
            data = Filter.useFilter(data, binarize);
        }
        return data;
    }

}
