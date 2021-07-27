package parkingsystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import parkingsystem.objects.User;
import parkingsystem.SpecialAlert;

public class LoginController implements Initializable {

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button btnok;

    SpecialAlert alert = new SpecialAlert();

    @FXML
    public void signin(ActionEvent event) throws IOException { //after pressing signin button

        //get the inputs from the fields
        String uname = username.getText();
        String pass = password.getText();

        if (uname.equals("")) { //check if username is empty
                loginMessageLabel.setText("Username is Blank!"); //display username warning
            } else if(pass.equals("")){ //check if password is empty
                loginMessageLabel.setText("Password is Blank!"); //display password warning
            } else {

            User user = new User(null, uname, pass);

            if (user.checkUser()) { //if true change stage
                btnok.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../resources/Main.fxml"));
                    Parent root = loader.load();
                    //pass user's user name to a label in main stage
                    MainController controller = loader.getController();
                    controller.getUser(user); 
                    
                Stage mainStage = new Stage();
                Scene mainScene = new Scene(root);
                mainStage.setScene(mainScene);
                mainStage.setTitle("Parking System");
                mainStage.setMaximized(true);
                mainStage.show();
            } else {
                loginMessageLabel.setText("Username or Password is incorrect!"); //display warning
            }

        }
    }

    @FXML
    public void onEnter(KeyEvent event) throws IOException { //sign in after pressing enter

        if (event.getCode() == KeyCode.ENTER) { //check if the key that was pressed is ENTER and do the same things as before

            //get the inputs from the fields
            String uname = username.getText();
            String pass = password.getText();

            if (uname.equals("")) { //check if username is empty
                loginMessageLabel.setText("Username is Blank!"); //display username warning
            } else if(pass.equals("")){ //check if password is empty
                loginMessageLabel.setText("Password is Blank!"); //display password warning
            }else {

                User user = new User(null, uname, pass);

                if (user.checkUser()) { //if true change stage
                    btnok.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../resources/Main.fxml"));
                    Parent root = loader.load();
                    
                    MainController controller = loader.getController();
                    controller.getUser(user);
                    
                    Stage mainStage = new Stage();
                    Scene mainScene = new Scene(root);
                    mainStage.setScene(mainScene);
                    mainStage.setTitle("Parking System by Klerido Bici");
                    mainStage.setMaximized(true);
                    mainStage.show();
                } else {
                    loginMessageLabel.setText("Username or Password is incorrect!"); //display warning
                }

            }

        }

    }

    @FXML
    public void cancel(ActionEvent event) { //close programm when cancel button is pressed
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }
    

}
