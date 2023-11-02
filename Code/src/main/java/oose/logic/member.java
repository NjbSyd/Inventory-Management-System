package oose.logic;

import oose.DB.DBC;
import oose.data.membersData;

import java.sql.SQLException;

public class member extends membersData{
    public member() throws SQLException {
        super();
    }

    public void createMember(String name, String address, String contact) {
        DBC dbc = new DBC();
        dbc.insertMemberData(name, address, contact);
    }


}
