package DMweKa.Application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import DMweKa.DataSet;



public class DynamicTableFx {

    //TABLE VIEW AND DATA


    public static TableView dataSetToTableView(DataSet dataSet , TableView tableview){

        ObservableList<ObservableList> data = FXCollections.observableArrayList();


        /**********************************
         * TABLE COLUMN ADDED DYNAMICALLY *
         **********************************/
        for(int i=0 ; i< dataSet.nbAttributs(); i++){
            final int j = i;
            TableColumn col = new TableColumn(dataSet.getAttribut(i).name());
            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            tableview.getColumns().addAll(col);
            System.out.println("Column ["+i+"] ");
        }

        /********************************
         * Data added to ObservableList *
         ********************************/
        for(int i=0;i< dataSet.nbInstances();i++){
            //Iterate Row
            ObservableList<String> row = FXCollections.observableArrayList();
            for(int j=0 ; j < dataSet.nbAttributs(); j++){
                //Iterate Column
                row.add(dataSet.getAttribut(j).getvalue(i));
            }
            System.out.println("Row [1] added "+row );
            data.add(row);
        }

        //FINALLY ADDED TO TableView
        tableview.setItems(data);
        return tableview;
    }


}