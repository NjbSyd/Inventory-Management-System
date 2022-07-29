package oose.data;

import oose.DB.DBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class itemsData {
    private static ArrayList<Integer> itemID = new ArrayList<>(5);
    private static ArrayList<String> name = new ArrayList<>(5);
    private static ArrayList<Integer> quantity = new ArrayList<>(5);
    private static ArrayList<Integer> price = new ArrayList<>(5);

    public itemsData() throws SQLException {
        DBC mySqlJDBC = new DBC();
        ResultSet resultSet;
        resultSet = mySqlJDBC.retrieveData("Select id from items;");
        retrieveItemID(resultSet);
        resultSet = mySqlJDBC.retrieveData("Select name from items;");
        retrieveItemName(resultSet);
        resultSet = mySqlJDBC.retrieveData("Select quantity from items;");
        retrieveItemQuantity(resultSet);
        resultSet = mySqlJDBC.retrieveData("Select price from items;");
        retrieveItemPrice(resultSet);
    }

    private void retrieveItemID(ResultSet resultSet) throws SQLException {
        itemID.clear();
        while (resultSet.next()) {
            itemID.add(resultSet.getInt(1));
        }
    }

    private void retrieveItemQuantity(ResultSet resultSet) throws SQLException {
        quantity.clear();
        while (resultSet.next()) {
            quantity.add(resultSet.getInt(1));
        }
    }

    private void retrieveItemName(ResultSet resultSet) throws SQLException {
        name.clear();
        while (resultSet.next()) {
            name.add(resultSet.getString(1));
        }
    }

    private void retrieveItemPrice(ResultSet resultSet) throws SQLException {
       price.clear();
        while (resultSet.next()) {
            price.add(resultSet.getInt(1));
        }
    }

    public String[] getItemDetails(String name) {
        String[] details = {" ", " ", " ", " "};
        for (int i = 0; i < this.name.size(); i++) {
            if (this.name.get(i).equalsIgnoreCase(name)) {
                details[0] = String.valueOf(itemID.get(i));
                details[1] = this.name.get(i);
                details[2] = String.valueOf(quantity.get(i));
                details[3] = String.valueOf(price.get(i));
                break;
            }
        }
        return details;
    }

    public void insertItem(String itemN, int itemQ_int, int itemP_int){
        DBC dbc=new DBC();
        dbc.insertItemData(itemN,itemQ_int,itemP_int);
    }

    public ResultSet getAllItems(){
        DBC dbc=new DBC();
        return dbc.retrieveData("select * from items;");
    }
}
