package parkingsystem.controllers;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import parkingsystem.SpecialAlert;
import parkingsystem.database.mysqlConnection;
import parkingsystem.objects.Car;

public class AddCarController implements Initializable {

    @FXML
    private TextField car_plate;
    @FXML
    private TextField car_brand;
    @FXML
    private ComboBox<String> owners;
    @FXML
    private Button cancelAddCarButton;

    ObservableList<String> oblist = FXCollections.observableArrayList();
    SpecialAlert alert = new SpecialAlert();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDataToChoiceBox();
    }

    @FXML
    public void addCar(ActionEvent event) {

        try { //use try catch block just to catch the null exception that the getSelectionModel().getSelectedItem() might show
            String owner = owners.getSelectionModel().getSelectedItem().toString(); //get the name of owner on combobox
            String plate = car_plate.getText(); //get plate from textfield
            String brand = car_brand.getText(); //get brand from textfield

            if (plate.equals("") || brand.equals("")) { //check if the 2 textfields are empty 
                alert.show("Warning", "Empty fields! Please fill them all!", Alert.AlertType.WARNING); //display warming
            } else {
                
                Car car = new Car(null, plate, brand, owner);

                car.addCar(); //add car with the plate brand and owners id (customer_id is the foreign key on database)
                
                //clear screen after insertion
                owners.getSelectionModel().clearSelection();
                car_plate.clear();
                car_brand.clear();

            }
        } catch (NullPointerException ex) { //catch null exception and display warning that the combobox is empty
            alert.show("Warning", "Please select the owner!", Alert.AlertType.WARNING);
        }

    }

    @FXML
    public void cancelAddCar(ActionEvent event) {

        Stage stage = (Stage) cancelAddCarButton.getScene().getWindow(); //get the stage
        stage.close(); //and close it
    }

    public void loadDataToChoiceBox() {

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

        owners.getItems().addAll(oblist);

    }

}
