package DMweKa;

import DMweKa.Application.TableFx;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;

/**
 * Weka bibs
 **/



public class DataSet {

    public ArrayList<Instance> instances ;

    // List of Attribut's names
    private ArrayList<String> listAttributs = new ArrayList<>();
    // List of Attributs <Class> of DataSet
    private ArrayList<AttributDataSet> attributs = new ArrayList<>();


    private String relation;
    private int nbInstances ;
    private int nbAttributs ;

    public DataSet(Instances data) {

        // Replace Missing values !!
        data = PreProcessing.preProcessData(data);

        instances = new ArrayList<>(data);
        relation = data.relationName();
        nbInstances = data.numInstances();
        nbAttributs = data.numAttributes() ;
        data.setClassIndex(data.numAttributes() - 1);


        for(int i=0;i<data.numAttributes();i++){
            attributs.add(new AttributDataSet(data.attribute(i),data));
            listAttributs.add(data.attribute(i).name());
        }

        PreProcessing.normaizeNumeric(this);

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


    public String getAttVal(Instance inst , Attribute att){
        return getAttribut(att.index()).getvalue(instances.indexOf(inst));
    }

    public ObservableList<TableFx> listAttributsTableItems(){
        // Reinsialize the static counter
        TableFx.reInisialize();
        ObservableList<TableFx> items = FXCollections.observableArrayList();
        for(AttributDataSet attribut:attributs){
            items.add(new TableFx(attribut.name(),attribut.type()));
        }
        return items;
    }


    public void toTableFx(){
        // TODO
    }


}
