package DMweKa.Application;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AttributPropertiesTable {

    private static int id = 0;

    SimpleIntegerProperty nume;
    SimpleIntegerProperty count;
    SimpleStringProperty label;
    SimpleStringProperty statistic;
    SimpleStringProperty weight;
    SimpleFloatProperty value;

    public AttributPropertiesTable(String label,Integer count, String weight) {
        id++;
        this.nume = new SimpleIntegerProperty(id);
        this.count = new SimpleIntegerProperty(count);
        this.label = new SimpleStringProperty(label);
        this.weight =new SimpleStringProperty(weight) ;
    }

    //for numeric attribute
    public AttributPropertiesTable(String statistic, Float value) {
        id++;
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


    public static void reInisialize(){ id = 0; }


}
