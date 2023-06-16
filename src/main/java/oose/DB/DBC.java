package oose.DB;

import javax.swing.*;
import java.sql.*;

public class DBC {
    private final String username = "Admin";
    private final String password = "6246662";
    Connection connection;

    public DBC() {
        setConnection();
    }

    public void setConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/oose", username, password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"DataBase Connection Failed");
        }
    }

    public ResultSet retrieveData(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return null;
    }

    public void insertMemberData(String name, String address, String contact){
        try {
            PreparedStatement statement= connection.prepareStatement("insert into members(name,address,contact) values("+"?,?,?"+");");
            statement.setObject(1,name);
            statement.setObject(2,address);
            statement.setObject(3,contact);
            statement.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    public void recordTransation(String name){
        try {
            System.out.println(name);
            ResultSet resultSet=retrieveData("Select * from transaction;");
            int trID = 0;
            while (resultSet.next()){
                trID=resultSet.getInt(1);
            }
            PreparedStatement statement= connection.prepareStatement("insert into transaction values("+"?,?"+");");
            statement.setObject(1,trID+1);
            statement.setObject(2,name);
            statement.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

    }

    public void insertItemData(String name,int quantity,int price){
        try {
            PreparedStatement statement= connection.prepareStatement("insert into items(name,quantity,price) values ("+"?,?,?"+");");
            statement.setObject(1,name);
            statement.setObject(2,quantity);
            statement.setObject(3,price);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null,"Item Added Successfully");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
}
