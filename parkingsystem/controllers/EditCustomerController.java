package parkingsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import parkingsystem.objects.Customer;
import parkingsystem.SpecialAlert;

public class EditCustomerController {
    
    @FXML
    private Button updateEditButton; 
    @FXML
    private Button cancelEditCustomer; 
    //editCustomerID label will be invisible as we just need it for the query and so it doesn't get changed
    @FXML 
    private Label editCustomerID;  
    @FXML
    private TextField full_name;
    @FXML
    private TextField vat_number;
    @FXML
    private TextField adress;
    @FXML
    private TextField zip_code;
    
    private Customer selectedCustomer;
    SpecialAlert alert = new SpecialAlert();
    
    @FXML
    public void updateCustomerButton(){
        
        String id = editCustomerID.getText();
        String name = full_name.getText();
        String vat = vat_number.getText();
        String adressE = adress.getText();
        String zip = zip_code.getText();
        
        Customer customer = new Customer(id, name, vat, adressE, zip);
        
        customer.editCustomer();
        
        Stage stage = (Stage) updateEditButton.getScene().getWindow(); 
        stage.close();
    }
    
    @FXML
    public void cancelEditCustomer(){ //close stage
        
        Stage stage = (Stage) cancelEditCustomer.getScene().getWindow(); //get the stage
        stage.close(); //and close it
        
    }
    
        public void getData(Customer customer) {
        
            selectedCustomer = customer;
        
            editCustomerID.setText(selectedCustomer.getId()); 
            full_name.setText(selectedCustomer.getName());
            vat_number.setText(selectedCustomer.getVAT());
            adress.setText(selectedCustomer.getAdress());
            zip_code.setText(selectedCustomer.getZIP());
        
    }
    
}
