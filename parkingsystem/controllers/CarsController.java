package parkingsystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import parkingsystem.objects.Car;
import parkingsystem.SpecialAlert;
import parkingsystem.database.mysqlConnection;

public class CarsController implements Initializable {

    @FXML
    private TextField carReg;
    @FXML
    private TextField txt_id;
    @FXML
    private Button closeCarTab;
    @FXML
    private TableView<Car> table;
    @FXML
    private TableColumn<Car, String> col_id;
    @FXML
    private TableColumn<Car, String> col_plate;
    @FXML
    private TableColumn<Car, String> col_brand;
    @FXML
    private TableColumn<Car, String> col_hidden; //the id of the owner
    @FXML
    private Label id_label;
    @FXML
    private Label name_label;
    @FXML
    private Label vat_label;
    @FXML
    private Label adress_label;
    @FXML
    private Label zip_label;

    ObservableList<Car> oblist = FXCollections.observableArrayList();
    SpecialAlert alert = new SpecialAlert();
    int index = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        countData(); //count records
        loadData(); //load the data when the stage opens
    }

    @FXML
    public void openAddCarTab(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../resources/addCar.fxml"));
        Stage addCarStage = new Stage();
        Scene addCarScene = new Scene(root);
        addCarStage.setScene(addCarScene);
        addCarStage.setTitle("Add Car");
        addCarStage.setResizable(false);
        addCarStage.show();

    }

    @FXML
    public void deleteCar(ActionEvent event) {

        if ((txt_id.getText()).equals("")) {
            alert.show("Warning", "Select a car!", Alert.AlertType.WARNING);
        } else {

            String car_id = txt_id.getText(); //get the id from the selected row
            Car car = new Car(car_id, null, null, null);
            car.deleteCar(); //call deleteCustomer method from customer class
            loadData(); //load data again after the deletion
            countData(); //and count records again
            txt_id.clear();

        }

    }

    @FXML
    public void refreshCarTab(ActionEvent event) {
        countData();
        loadData();
        txt_id.clear();
    }

    @FXML
    public void closeCarTab(ActionEvent event) {

        Stage stage = (Stage) closeCarTab.getScene().getWindow();
        stage.close();

    }

    @FXML //after selecting on mouseclicked a record get the id of the car and display all the info of the owner of this car
    public void handleMouseClickedonTable(MouseEvent event) {

        index = table.getSelectionModel().getSelectedIndex();

        if (index <= -1) { //check if user clicked on nothing
            return; //return so the program doesn't crash
        } else { //else set the data to the textfields
            txt_id.setText(col_id.getCellData(index).toString());
            displayCustomerInfo();
        }

    }

    public void displayCustomerInfo() { //select the info of the owner of the car and display

        index = table.getSelectionModel().getSelectedIndex();
        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT * FROM customers WHERE customer_id = ?";
        PreparedStatement prst;
        ResultSet rs;
        String CustomerID = col_hidden.getCellData(index).toString();

        String fullName = null;
        String vatNumber = null;
        String addresA = null;
        String zipCode = null;

        try {
            prst = conn.prepareStatement(query);
            prst.setString(1, CustomerID);
            rs = prst.executeQuery();
            while (rs.next()) {
                fullName = rs.getString("full_name");
                vatNumber = rs.getString("VAT_number");
                addresA = rs.getString("adress");
                zipCode = rs.getString("zip_code");
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }

        id_label.setText(CustomerID);
        name_label.setText(fullName);
        vat_label.setText(vatNumber);
        adress_label.setText(addresA);
        zip_label.setText(zipCode);

    }

    public void countData() { //count all the records from customers table and display

        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT COUNT(*) FROM cars";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                carReg.setText(rs.getString("COUNT(*)"));
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }
    }

    public void loadData() { //select all the data from customers on database and display them
        //clear car table
        oblist.clear();
        //clear customer info table
        id_label.setText("");
        name_label.setText("");
        vat_label.setText("");
        adress_label.setText("");
        zip_label.setText("");

        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT * FROM cars";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                oblist.add(new Car(rs.getString("car_id"), rs.getString("car_plate"),
                        rs.getString("car_brand"), rs.getString("customer_id")));
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_plate.setCellValueFactory(new PropertyValueFactory<>("plate"));
        col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        col_hidden.setCellValueFactory(new PropertyValueFactory<>("owner_id"));

        table.setItems(oblist);
    }

}
