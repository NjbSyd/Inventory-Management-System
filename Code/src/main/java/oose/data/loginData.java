package oose.data;

import oose.DB.DBC;

import java.sql.ResultSet;
import java.sql.SQLException;

public class loginData {
    public static String[] getUsername() {
        return username;
    }

    public static String[] getPassword() {
        return password;
    }

    private static String[] username=new String[10];
    private static String[] password=new String[10];

    public loginData() throws SQLException {
        DBC mySqlJDBC = new DBC();
        ResultSet resultSet;
        resultSet = mySqlJDBC.retrieveData("Select username from login;");
        retrieveUsername(resultSet);
        resultSet = mySqlJDBC.retrieveData("Select password from login;");
        retrievePassword(resultSet);
    }

    public void retrieveUsername(ResultSet set) throws SQLException {
        int i = 0;
        while (set.next()) {
            this.username[i] = set.getString(1);
            i++;
        }
    }


    public void retrievePassword(ResultSet set) throws SQLException {
        int i = 0;
        while (set.next()) {
            this.password[i] = set.getString(1);
            i++;
        }
    }
}
