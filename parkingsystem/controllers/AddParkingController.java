package parkingsystem.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import parkingsystem.Common;
import parkingsystem.SpecialAlert;
import parkingsystem.objects.Parking;

public class AddParkingController implements Initializable {

    @FXML
    private TextField descriptionField;
    @FXML
    private TextField floorField;
    @FXML
    private TextField spaceField;
    @FXML
    private Button cancelAddParking;
    
    SpecialAlert alert = new SpecialAlert();
    Common checkDigits = new Common();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void addParkingButton(ActionEvent event) {

        String description = descriptionField.getText();
        String floor = floorField.getText();
        String space = spaceField.getText();
        
        int floorLen = floor.length(); 
        int spaceLen = space.length();
        
        if(description.equals("") || floor.equals("") || space.equals("")){
            alert.show("Error", "Empty fields! Please fill them all!", Alert.AlertType.ERROR);
        }else if(!checkDigits.onlyDigits(floor, floorLen)){
            alert.show("Error", "Floor field accepts only numbers!", Alert.AlertType.ERROR);
        }else if(!checkDigits.onlyDigits(space, spaceLen)){
            alert.show("Error", "Space field accepts only numbers!", Alert.AlertType.ERROR);
        }else{
            
            Parking parking = new Parking(null, description, floor, space);
            
            parking.createParking();
            
            descriptionField.clear();
            floorField.clear();
            spaceField.clear();
            
        }

    }

    @FXML
    private void cancelAddParking(ActionEvent event) {
        
        Stage stage = (Stage) cancelAddParking.getScene().getWindow(); 
        stage.close(); 
        
    }

}
