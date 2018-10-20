package DMweKa;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

//va etre utilisee pour l'affichage de la deuxieme table
public class Calculateur {
    SimpleIntegerProperty nume;
    SimpleIntegerProperty count;
    SimpleStringProperty label;
    SimpleStringProperty statistic;
    SimpleStringProperty weight;
    SimpleFloatProperty value;
    
//pour Nominale attribute
    public Calculateur(Integer nume,  String label,Integer count, String weight) {
        this.nume = new SimpleIntegerProperty(nume);
        this.count = new SimpleIntegerProperty(count);
        this.label = new SimpleStringProperty(label);
        this.weight =new SimpleStringProperty(weight) ;
    }

    public Calculateur() {
    }

    //for numeric attribute
    public Calculateur(String statistic, Float value) {
        this.statistic =new SimpleStringProperty(statistic);
        this.value =new SimpleFloatProperty(value);
    }

    public Integer getNume() {
        return nume.get();
    }

    public Integer getCount() {
        return count.get();
    }

    public String getLabel() {
        return label.get();
    }

    public String getStatistic() {
        return statistic.get();
    }

    public String getWeight() {
        return weight.get();
    }

    public Float getValue() {
        return value.get();
    }


}
