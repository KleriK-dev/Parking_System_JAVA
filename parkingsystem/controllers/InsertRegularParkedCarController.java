package parkingsystem.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import parkingsystem.Common;
import parkingsystem.SpecialAlert;
import parkingsystem.database.mysqlConnection;
import parkingsystem.objects.RegularParkings;

public class InsertRegularParkedCarController implements Initializable {

    @FXML
    private ComboBox<String> parkings;
    @FXML
    private ComboBox<String> pricelists;
    @FXML
    private ComboBox<String> customers;
    @FXML
    private ComboBox<String> cars;
    @FXML
    private Button cancel;
    
    ObservableList<String> oblist = FXCollections.observableArrayList();
    SpecialAlert alert = new SpecialAlert();
    Common common = new Common();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDataToCustomersChoiceBox();
        loadDataToCarsChoiceBox();
        loadDataToParkingsChoiceBox();
        loadDataToPricelistsChoiceBox();
    }    

    @FXML
    private void insert(ActionEvent event) {
        
        long MONTH = (long) (2.62974383 * Math.pow(10, 9));
        
        Date currentDateTime = new Date();
        
        Date estimatedTime = new Date(currentDateTime.getTime() + 1 * MONTH);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        String entranceTime = dateFormat.format(currentDateTime);
        String exitTime = dateFormat.format(estimatedTime);
        
        try { 
            String customer = customers.getSelectionModel().getSelectedItem().toString(); 
            String car = cars.getSelectionModel().getSelectedItem().toString(); 
            String parking = parkings.getSelectionModel().getSelectedItem().toString(); 
            String pricelist = pricelists.getSelectionModel().getSelectedItem().toString();
                
                RegularParkings newParked = new RegularParkings(null, customer, car, pricelist, parking, entranceTime, exitTime);

                newParked.insertRegularParking();

                parkings.getSelectionModel().clearSelection();
                pricelists.getSelectionModel().clearSelection();
                cars.getSelectionModel().clearSelection();
                customers.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) { 
            alert.show("Warning", "Empty fields, please fill them all!", Alert.AlertType.WARNING);
        }
        
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow(); 
        stage.close(); 
    }
    
    public void loadDataToCustomersChoiceBox() {

        oblist.removeAll(oblist);

        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT full_name FROM customers";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                oblist.add(rs.getString("full_name"));
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }

        customers.getItems().addAll(oblist);

    }
    
    public void loadDataToCarsChoiceBox() {

        oblist.removeAll(oblist);

        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT car_plate FROM cars";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                oblist.add(rs.getString("car_plate"));
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }

        cars.getItems().addAll(oblist);

    }
    
    public void loadDataToParkingsChoiceBox() {

        oblist.removeAll(oblist);

        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT description FROM parking_spaces";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                oblist.add(rs.getString("description"));
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }

        parkings.getItems().addAll(oblist);

    }
    
    public void loadDataToPricelistsChoiceBox() {

        oblist.removeAll(oblist);

        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT description FROM price_list";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                oblist.add(rs.getString("description"));
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }

        pricelists.getItems().addAll(oblist);

    }
    
}
