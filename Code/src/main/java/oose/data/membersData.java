package oose.data;

import oose.DB.DBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class membersData {
    public static ArrayList<String> getMember_names() {
        return member_names;
    }

    public static ArrayList<String> getMember_address() {
        return member_address;
    }

    public static ArrayList<String> getMember_contact() {
        return member_contact;
    }

    private static ArrayList<String> member_names = new ArrayList<>(5);
    private static ArrayList<String> member_address = new ArrayList<>(5);
    private static ArrayList<String> member_contact = new ArrayList<>(5);

    public membersData() throws SQLException {
        DBC mySqlJDBC = new DBC();
        ResultSet resultSet;
        resultSet = mySqlJDBC.retrieveData("Select name from members;");
        retrieveMember_names(resultSet);
        resultSet = mySqlJDBC.retrieveData("Select address from members;");
        retrieveMember_address(resultSet);
        resultSet = mySqlJDBC.retrieveData("Select contact from members;");
        retrieveMember_contact(resultSet);
    }

    public void retrieveMember_names(ResultSet set) throws SQLException {
        while (set.next()) {
            this.member_names.add(set.getString(1));
        }
    }

    public void retrieveMember_address(ResultSet set) throws SQLException {
        while (set.next()) {
            this.member_address.add(set.getString(1));
        }
    }

    public void retrieveMember_contact(ResultSet set) throws SQLException {
        while (set.next()) {
            this.member_contact.add(set.getString(1));
        }
    }
    public String[] getMemberDetails(String name) {
        String[] details = {" "," "," "};
        for (int i = 0; i < getMember_names().size(); i++) {
            if (name.equals(getMember_names().get(i))) {
                details[0] = getMember_names().get(i);
                details[1] = getMember_address().get(i);
                details[2] = getMember_contact().get(i);
                break;
            }
        }
        return details;
    }
}
