package parkingsystem.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import parkingsystem.Common;
import parkingsystem.objects.Customer;
import parkingsystem.SpecialAlert;


public class AddCustomerController implements Initializable {

    @FXML
    private Button cancelAddCustomer;  
    @FXML
    private TextField full_name;
    @FXML
    private TextField vat_number;
    @FXML
    private TextField adress;
    @FXML
    private TextField zip_code;
    
    SpecialAlert alert = new SpecialAlert();
    Common checkDigits = new Common();
    
    @FXML
    public void addCustomerButton(){
        
        String name = full_name.getText();
        String vat = vat_number.getText();
        String adressA = adress.getText();
        String zip = zip_code.getText();
        
        //need the length to get one by one all the characters to check if they all numbers
        int vatLen = vat.length(); 
        int zipLen = zip.length();
        
        if(name.equals("") || vat.equals("") || adressA.equals("") || zip.equals("")){
            alert.show("Error", "Empty fields! Please fill them all!", Alert.AlertType.ERROR);
        }else if(!checkDigits.onlyDigits(vat, vatLen)){
            alert.show("Error", "VAT number field accepts only numbers!", Alert.AlertType.ERROR);
        }else if(!checkDigits.onlyDigits(zip, zipLen)){
            alert.show("Error", "ZIP code field accepts only numbers!", Alert.AlertType.ERROR);
        }else{
            Customer customer = new Customer(null, name, vat, adressA, zip); //null on the data we dont want to insert
        
            customer.addCustomer();
        
            full_name.clear();
            vat_number.clear();
            adress.clear();
            zip_code.clear();
        }
        
    }
    
    @FXML
    public void cancelAddCustomer(){ //close stage
        
        Stage stage = (Stage) cancelAddCustomer.getScene().getWindow(); //get the stage
        stage.close(); //and close it
        
    }   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
