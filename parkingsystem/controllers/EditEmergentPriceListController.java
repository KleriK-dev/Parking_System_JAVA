package parkingsystem.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import parkingsystem.SpecialAlert;
import parkingsystem.objects.EmergentPriceList;
import parkingsystem.objects.PriceList;

public class EditEmergentPriceListController implements Initializable {

    @FXML
    private TextField descriptionField;
    @FXML
    private TextField entrancePriceField;
    @FXML
    private Label editPriceListID;
    @FXML
    private TextField priceHfield;
    @FXML
    private Button updateEditButton;
    @FXML
    private Button cancel;
    
    SpecialAlert alert = new SpecialAlert();
    private EmergentPriceList selectedPriceList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void updateEmergentPriceListButton(ActionEvent event) {
        
        String id = editPriceListID.getText();
        String description = descriptionField.getText();
        String price = entrancePriceField.getText();
        String priceH = priceHfield.getText();
        double price_double = 0;
        double priceH_double = 0;

        try {
            price_double = Double.parseDouble(price);
            priceH_double = Double.parseDouble(priceH);
            EmergentPriceList pricelist = new EmergentPriceList(id, description, price_double, priceH_double);

            pricelist.editEmergentPriceList();

            Stage stage = (Stage) updateEditButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException ex) {
            alert.show("Error", "Price entrance price and price/hour fields accepts only decimal numbers!", Alert.AlertType.ERROR);
        }
        
    }

    @FXML
    private void cancelEditEemergentPriceLlist(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow(); 
        stage.close();
    }
    
    public void getData(EmergentPriceList pricelist) {
        
            selectedPriceList = pricelist;
        
            editPriceListID.setText(selectedPriceList.getId()); 
            descriptionField.setText(selectedPriceList.getDescription());
            entrancePriceField.setText(selectedPriceList.getEntrancePriceString());
            priceHfield.setText(selectedPriceList.getPriceHourString());
        
    }
    
}
