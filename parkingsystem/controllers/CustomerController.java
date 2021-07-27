package parkingsystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import parkingsystem.objects.Customer;
import parkingsystem.SpecialAlert;
import parkingsystem.database.mysqlConnection;

public class CustomerController implements Initializable {

    @FXML
    private TextField customerReg;
    @FXML
    private TableView<Customer> table;
    @FXML
    private TableColumn<Customer, String> col_id;
    @FXML
    private TableColumn<Customer, String> col_name;
    @FXML
    private TableColumn<Customer, String> col_vat;
    @FXML
    private TableColumn<Customer, String> col_adress;
    @FXML
    private TableColumn<Customer, String> col_zip;
    @FXML
    private Button closeCustomerTab;
    @FXML
    private TextField txt_id;

    ObservableList<Customer> oblist = FXCollections.observableArrayList();
    SpecialAlert alert = new SpecialAlert();
    int index = -1;

    @FXML
    public void handleMouseClickedonTable(MouseEvent event) { //set data to a textfield when its selected by click

        index = table.getSelectionModel().getSelectedIndex();

        if (index <= -1) { //check if user clicked on nothing
            return; //return so the program doesn't crash
        } else { //else set the data to the textfields
            txt_id.setText(col_id.getCellData(index).toString());
        }

    }

    @FXML
    public void openAddCustomerTab(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../resources/addCustomer.fxml"));
        Stage customerStage = new Stage();
        Scene customerScene = new Scene(root);
        customerStage.setScene(customerScene);
        customerStage.setTitle("Add Customer");
        customerStage.setResizable(false);
        customerStage.show();

    }

    @FXML
    public void deleteCustomer(ActionEvent event) { //delete the customer that was selected

        if ((txt_id.getText()).equals("")) { //check if user has selected a customer
            alert.show("Warning", "Select a customer!", Alert.AlertType.WARNING);
        } else {

            String customer_id = txt_id.getText(); //get the id from the selected row
            Customer customer = new Customer(customer_id, null, null, null, null);
            customer.deleteCustomer(); //call deleteCustomer method from customer class
            loadData(); //load data again after the deletion
            countData(); //and count records again
            txt_id.clear();
            
        }

    }

    @FXML
    public void editCustomer(ActionEvent event) throws IOException {

        try {
            //create scene for editing customer
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../resources/editCustomer.fxml"));
            Parent root = loader.load();

            //pass selected data from this controller to the editCustomerController
            EditCustomerController controller = loader.getController();
            controller.getData(table.getSelectionModel().getSelectedItem());

            //create stage
            Stage editStage = new Stage();
            Scene editScene = new Scene(root);
            editStage.setScene(editScene);
            editStage.setTitle("Edit Customer");
            editStage.setResizable(false);
            editStage.show();

        } catch (Exception ex) { //catch the exception of the user dont select a customer
            alert.show("Warning", "Select a customer!", Alert.AlertType.WARNING);
        }

    }

    @FXML
    public void refreshCustomerTab(ActionEvent event) { //refresh listener to bring the new data after insertion

        countData();
        loadData();
        
        txt_id.clear();

    }

    @FXML
    public void closeCustomerTab(ActionEvent event) { //close stage when x button is pressed

        Stage stage = (Stage) closeCustomerTab.getScene().getWindow();
        stage.close();

    }

    public void countData() { //count all the records from customers table and display

        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT COUNT(*) FROM customers";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                customerReg.setText(rs.getString("COUNT(*)"));
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }
    }

    public void loadData() { //select all the data from customers on database and display them

        oblist.clear();

        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT * FROM customers";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                oblist.add(new Customer(rs.getString("customer_id"), rs.getString("full_name"),
                        rs.getString("VAT_number"), rs.getString("adress"), rs.getString("zip_code")));
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_vat.setCellValueFactory(new PropertyValueFactory<>("VAT"));
        col_adress.setCellValueFactory(new PropertyValueFactory<>("adress"));
        col_zip.setCellValueFactory(new PropertyValueFactory<>("ZIP"));

        table.setItems(oblist);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        countData(); //count records
        loadData(); //load the data when the stage opens

    }

}
