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
import parkingsystem.SpecialAlert;
import parkingsystem.objects.EmergentPriceList;

public class AddEmergentPriceListController implements Initializable {

    @FXML
    private TextField descriptionField;
    @FXML
    private TextField entrancePriceField;
    @FXML
    private TextField priceHfield;
    @FXML
    private Button cancelAddPrice;
    
    SpecialAlert alert = new SpecialAlert();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addPriceButton(ActionEvent event) {
        
        String description = descriptionField.getText();
        String price = entrancePriceField.getText();
        String priceH = priceHfield.getText();
        double price_double = 0; //we will insert double variable on db
        double priceH_double = 0; //we will insert double variable on db
        
        try{ 
            /*
                try parsing to double, the input from the price field as it comes as a string
                if price_double variable gets string or nothing, catch the NumberFormatException
            */
            price_double = Double.parseDouble(price); 
            priceH_double = Double.parseDouble(priceH); 
            if(description.equals("")){ // we need to check only the description as the price field is handled by the try catch block
            alert.show("Error", "Empty fields! Please fill them all!", Alert.AlertType.ERROR);
        } else{
            EmergentPriceList pricelist = new EmergentPriceList(null, description, price_double, priceH_double); 
        
            pricelist.addEmergentPriceList();
        
            descriptionField.clear();
            entrancePriceField.clear();
            priceHfield.clear();
            
        }
        } catch (NumberFormatException ex){
            alert.show("Error", "Price entrance price and price/hour fields accepts only decimal numbers!", Alert.AlertType.ERROR);
        }     
        
    }

    @FXML
    private void cancelAddPriceButton(ActionEvent event) {
        
        Stage stage = (Stage) cancelAddPrice.getScene().getWindow(); 
        stage.close();
        
    }
    
}
