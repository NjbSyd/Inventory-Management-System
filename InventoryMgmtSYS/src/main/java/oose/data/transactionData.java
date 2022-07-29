package oose.data;

import oose.DB.DBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class transactionData {
    public transactionData() throws SQLException {
        DBC dbc=new DBC();
        ResultSet resultSet;
        resultSet= dbc.retrieveData("select transactionId from transaction;");
        retrieveTransactionId(resultSet);
        resultSet= dbc.retrieveData("select item from transaction;");
        retrieveTransactionItem(resultSet);

    }

    private void retrieveTransactionItem(ResultSet set) throws SQLException {
        while (set.next()){
            items.add((set.getString(1)).trim().split("&"));
        }
    }

    private void retrieveTransactionId(ResultSet set) throws SQLException {
        while (set.next()){
            transactionId.add(set.getInt(1));
        }
    }

    public static ArrayList<Integer> getTransactionId() {
        return transactionId;
    }

    public static ArrayList<String[]> getItems() {
        return items;
    }

    private static ArrayList<Integer> transactionId=new ArrayList<>(5);
    private static ArrayList<String[]> items=new ArrayList<>(5);



}
