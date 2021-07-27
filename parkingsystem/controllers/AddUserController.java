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
import parkingsystem.objects.User;

public class AddUserController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button cancel;
    
    SpecialAlert alert = new SpecialAlert();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AddUserButton(ActionEvent event) {
        
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if(username.equals("") || password.equals("")){
            alert.show("Error", "Empty fields! Please fill them all!", Alert.AlertType.ERROR);
        }else{
            
            User user = new User(null, username, password);
            
            user.addUser();
            
            usernameField.clear();
            passwordField.clear();
            
        }
        
    }

    @FXML
    private void cancelAddUser(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow(); 
        stage.close(); 
    }
    
}
