package oose.GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import oose.data.itemsData;
import oose.data.oneItem;
import oose.data.saleLineItems;
import oose.logic.member;
import oose.logic.report;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class mainController {
    saleLineItems lineItems = new saleLineItems();
    @FXML
    GridPane paymentRoot;
    @FXML
    TabPane mainTabPane;
    @FXML
    TextField member_name, member_address, member_contact;
    @FXML
    TextField itemName, itemQuantity, additem_name, additem_quantity, additem_price;
    @FXML
    TableView<oneItem> cartTable;
    @FXML
    TableView allItemsTable;
    @FXML
    TableColumn<oneItem, String> item_Id,item_Name;
   @FXML
    TableColumn<oneItem, String> item_Price, tableItem, tableQuantity, tablePrice, tableName,item_Quantity;
    @FXML
    Button addItemToCartBtn, recordTransactionBtn;
    @FXML
    FontAwesomeIcon closeBtn;

    public void saleBtnClicked() {
        mainTabPane.getSelectionModel().select(0);
    }

    public void returnBtnClicked() {
        mainTabPane.getSelectionModel().select(1);
    }

    public void membershipBtnClicked() {
        mainTabPane.getSelectionModel().select(2);
    }

    public void reportBtnClicked() {
        mainTabPane.getSelectionModel().select(3);
    }

    public void createMember() {
        try {
            String name = member_name.getText();
            String address = member_address.getText();
            String contact = member_contact.getText();
            if (name.equals("") || address.equals("") || contact.equals("")) {
                JOptionPane.showMessageDialog(null, "1..* fields missing.");
            } else {
                (new member()).createMember(name, (address.replace(",", " ")), contact);
                JOptionPane.showMessageDialog(null, "Member Stored Successfully!");
                member_contact.setText("");
                member_address.setText("");
                member_name.setText("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void logout(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public void addItemToCart(ActionEvent event) throws SQLException {
        try {
            if (!checkItemInput()) {
                itemName.setText("");
                itemQuantity.setText("");
                return;
            }
            String item = itemName.getText();
            int itemQty = Integer.parseInt(itemQuantity.getText());
            String[] itemDetails = (new itemsData()).getItemDetails(item);
            ObservableList<oneItem> itemData = FXCollections.observableArrayList(new oneItem(itemDetails[0], itemDetails[1], itemDetails[2], itemDetails[3]));
            item_Id.setCellValueFactory(new PropertyValueFactory<oneItem, String>("id"));
            item_Name.setCellValueFactory(new PropertyValueFactory<oneItem, String>("name"));
            item_Quantity.setCellValueFactory(new PropertyValueFactory<oneItem, String>("quantity"));
            item_Price.setCellValueFactory(new PropertyValueFactory<oneItem, String>("price"));
            ObservableList<oneItem> observableList = cartTable.getItems();
            observableList.add(new oneItem(itemDetails[0], itemDetails[1], itemQuantity.getText().trim(), String.valueOf(Integer.parseInt(itemDetails[3]) * Integer.parseInt(itemQuantity.getText().trim()))));
            cartTable.setItems(observableList);
            lineItems.setItems(item);
            lineItems.setItemsQty(itemQty);
            itemName.setText("");
            itemQuantity.setText("");

        } catch (Exception e) {
            itemName.setText("");
            itemQuantity.setText("");
            JOptionPane.showMessageDialog(null, "Error! in Input.");
        }
    }

    public boolean checkItemInput() {
        boolean cond = false;
        try {
            if (itemName.getText().trim().equals("") || itemQuantity.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Valid Input Required!");
                itemName.requestFocus();
                return false;
            } else {
                cond = true;
            }
            int qty = Integer.parseInt(itemQuantity.getText().trim());
            if (qty < 1) {
                JOptionPane.showMessageDialog(null, "Quantity can't be less than 0!");
                return false;
            } else {
                cond = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return cond;
    }

    public void proceedToPayment(ActionEvent event) throws IOException {
        if (cartTable.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fist Add some Items!!");
            return;
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        stage = new Stage();
        paymentRoot = FXMLLoader.load(getClass().getResource("payment.fxml"));
        paymentRoot.add(cartTable, 1, 1);
        Scene scene = new Scene(paymentRoot);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setResizable(false);
        stage.show();
    }

    public void closePayment(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.show();
    }

    public void recordTransaction(ActionEvent event) throws IOException {
        lineItems.insertData();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.show();
    }

    public void exportAllItems(ActionEvent event) throws IOException {
        (new report()).generateFullData(1);
    }

    public void addItemToDB(ActionEvent event) throws SQLException {
        int itemQ_int, itemP_int;
        String itemN = additem_name.getText();
        String itemQ = additem_quantity.getText();
        String itemP = additem_price.getText();
        if (itemN.trim().equals("") || itemQ.trim().equals("") || itemP.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "1..* fields missing!");
            additem_name.setText("");
            additem_price.setText("");
            additem_quantity.setText("");
            return;
        }
        try {
            itemQ_int = Integer.parseInt(itemQ);
            itemP_int = Integer.parseInt(itemP);
        } catch (Exception e) {
            additem_name.setText("");
            additem_price.setText("");
            additem_quantity.setText("");
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }
        if (itemP_int < 1 || itemQ_int < 1) {
            JOptionPane.showMessageDialog(null, "Input < 1 is not allowed!");
            additem_name.setText("");
            additem_price.setText("");
            additem_quantity.setText("");
            return;
        }
        itemsData itemsData = new itemsData();
        itemsData.insertItem(itemN, itemQ_int, itemP_int);
        additem_name.setText("");
        additem_price.setText("");
        additem_quantity.setText("");
    }

    public void exportAllTransactions(ActionEvent event) {
        (new report()).generateFullData(3);
    }

    public void exportAllMembers(ActionEvent event) {
        (new report()).generateFullData(2);
    }

    public void viewAllItems(ActionEvent event) throws SQLException {
        mainTabPane.getSelectionModel().select(4);
        ResultSet resultSet=(new itemsData()).getAllItems();
        ObservableList<oneItem> list=FXCollections.observableArrayList();
        while (resultSet.next()){
            list.add(new oneItem(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)));
        }
        tableItem.setCellValueFactory(new PropertyValueFactory<oneItem,String>("id"));
        tableName.setCellValueFactory(new PropertyValueFactory<oneItem,String>("name"));
        tableQuantity.setCellValueFactory(new PropertyValueFactory<oneItem,String>("quantity"));
        tablePrice.setCellValueFactory(new PropertyValueFactory<oneItem,String>("price"));
        allItemsTable.setItems(list);
    }
}
