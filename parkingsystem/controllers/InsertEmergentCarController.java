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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import parkingsystem.Common;
import parkingsystem.SpecialAlert;
import parkingsystem.database.mysqlConnection;
import parkingsystem.objects.Emergents;

public class InsertEmergentCarController implements Initializable {

    @FXML
    private TextField plateField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField adressField;
    @FXML
    private ComboBox<String> parkings;
    @FXML
    private ComboBox<String> pricelists;
    @FXML
    private Button cancel;
    
    ObservableList<String> oblist = FXCollections.observableArrayList();
    SpecialAlert alert = new SpecialAlert();
    Common common = new Common();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDataToParkingsChoiceBox();
        loadDataToPricelistsChoiceBox();
    }    

    @FXML
    private void insert(ActionEvent event) {
        
        long HOUR = 3600*1000; // create hour in milli-seconds
        
        Date currentDateTime = new Date(); //get current date and time
        Date estimatedTime = new Date(currentDateTime.getTime() + 1 * HOUR); //get current time and add 1 hour in milliseconds

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy \tHH:mm:ss"); //create a format for date and time
        SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss"); //create format for time
        
        //store them in string
        String entranceTime = dateTimeFormat.format(currentDateTime);
        String exitTime = TimeFormat.format(estimatedTime);
        
        
        try { 
            String parking = parkings.getSelectionModel().getSelectedItem().toString(); 
            String pricelist = pricelists.getSelectionModel().getSelectedItem().toString(); 
            String plate = plateField.getText(); 
            String owner = nameField.getText(); 
            String adress = adressField.getText(); 

            if (plate.equals("") || owner.equals("") || adress.equals("")) { 
                alert.show("Warning", "Empty fields! Please fill them all!", Alert.AlertType.WARNING); 
            } else {
                
                Emergents newCustomer = new Emergents(null, plate, owner, adress, entranceTime, exitTime, pricelist, parking);

                newCustomer.insertEmergent(); 

                parkings.getSelectionModel().clearSelection();
                pricelists.getSelectionModel().clearSelection();
                plateField.clear();
                nameField.clear();
                adressField.clear();

            }
        } catch (NullPointerException ex) { 
            alert.show("Warning", "Please select a pariking and a pricelist!", Alert.AlertType.WARNING);
        }
        
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow(); 
        stage.close(); 
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
        String query = "SELECT description FROM emergent_pricelist";
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
