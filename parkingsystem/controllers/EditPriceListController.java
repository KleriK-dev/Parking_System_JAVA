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
import parkingsystem.objects.PriceList;

public class EditPriceListController implements Initializable {

    @FXML
    private TextField descriptionField;
    @FXML
    private TextField priceField;
    @FXML
    private Button cancelEditPriceLlist;
    @FXML
    private Button updateEditButton;
    @FXML
    private Label editPriceListID;

    SpecialAlert alert = new SpecialAlert();
    private PriceList selectedPriceList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void updatePriceListButton(ActionEvent event) {

        String id = editPriceListID.getText();
        String description = descriptionField.getText();
        String price = priceField.getText();
        double price_double = 0;

        try {
            price_double = Double.parseDouble(price);
            PriceList pricelist = new PriceList(id, description, price_double);

            pricelist.editPriceList();

            Stage stage = (Stage) updateEditButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException ex) {
            alert.show("Error", "Price field accepts only decimal numbers! ", Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void cancelEditPriceLlist(ActionEvent event) {
        
        Stage stage = (Stage) cancelEditPriceLlist.getScene().getWindow(); 
        stage.close();
        
    }
    
    public void getData(PriceList pricelist) {
        
            selectedPriceList = pricelist;
        
            editPriceListID.setText(selectedPriceList.getPricelist_id()); 
            descriptionField.setText(selectedPriceList.getDescription());
            priceField.setText(selectedPriceList.getPriceString());
        
    }

}
