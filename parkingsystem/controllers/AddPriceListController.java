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
import parkingsystem.objects.PriceList;

public class AddPriceListController implements Initializable {

    @FXML
    private TextField descriptionField;
    @FXML
    private TextField priceField;
    @FXML
    private Button cancelAddPriceList;
    
    SpecialAlert alert = new SpecialAlert();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AddPriceListButton(ActionEvent event) {
        
        String description = descriptionField.getText();
        String price = priceField.getText();
        double price_double = 0; //we will insert double variable on db
        
        try{ 
            /*
                try parsing to double the input from the price field as it comes as a string
                if price_double variable gets string or nothing, catch the NumberFormatException
            */
            price_double = Double.parseDouble(price); 
            if(description.equals("")){ // we need to check only the description as the price field is handled by the try catch block
            alert.show("Error", "Empty fields! Please fill them all!", Alert.AlertType.ERROR);
        } else{
            PriceList pricelist = new PriceList(null, description, price_double); 
        
            pricelist.addPriceList();
        
            descriptionField.clear();
            priceField.clear();
            
        }
        } catch (NumberFormatException ex){
            alert.show("Error", "Price field accepts only decimal numbers! ", Alert.AlertType.ERROR);
        }          
        
    }

    @FXML
    private void cancelAddPriceList(ActionEvent event) {
        
        Stage stage = (Stage) cancelAddPriceList.getScene().getWindow(); 
        stage.close();
        
    }
    
}
