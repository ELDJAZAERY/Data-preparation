package DMweKa;

import java.util.ArrayList;

import weka.core.*;
import DMweKa.Application.ListAttributTable;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


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
        nbInstances = data.numInstances();
        this.instances = new ArrayList<>(data);
        nbAttributs = data.numAttributes() ;
        data.setClassIndex(data.numAttributes() - 1);

        // Replace Missing values !!
        data = PreProcessing.preProcessData(data);

        for(int i=0;i<data.numAttributes();i++){
            attributs.add(new AttributDataSet(data.attribute(i),data.attributeStats(i),data));
            listAttributs.add(data.attribute(i).name());
        }
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


}
