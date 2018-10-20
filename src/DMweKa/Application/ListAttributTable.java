package DMweKa.Application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ListAttributTable {

    private static int id = 0;

    SimpleIntegerProperty num;
    SimpleStringProperty name;
    SimpleStringProperty type;

    public ListAttributTable(String name,String type){
        id++;
        this.num=new SimpleIntegerProperty(id);

        this.name=new SimpleStringProperty(name);
        this.type=new SimpleStringProperty(type);
    }

    public Integer getNum(){
        return num.get();
    }
    public String getName(){
        return name.get();
    }
    public String getType(){
        return type.get();
    }


    public static void reInisialize(){ id = 0; }

}
