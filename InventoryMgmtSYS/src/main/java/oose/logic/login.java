package oose.logic;

import oose.DB.DBC;
import oose.data.loginData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class login extends loginData{
    private String[] username;
    private String[] password;

    public login() throws SQLException {
        super();
        username = loginData.getUsername();
        password = loginData.getPassword();
    }

    public boolean verifyCredentials(String username, String password) {
        boolean found = false;
        for (int i = 0; i < this.username.length; i++) {
            if (this.username[i] == null) {
                break;
            }
            if (username.equals(this.username[i])) {
                if (password.equals(this.password[i])) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }
}
