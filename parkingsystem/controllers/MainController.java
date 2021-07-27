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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import parkingsystem.objects.User;

public class MainController implements Initializable {

    @FXML
    private Label programUser;

    private User user;

    @FXML //create a new scene for the customers when its pressed one of the three customers button
    public void openCustomerTab(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../resources/Customer.fxml"));
        Stage customerStage = new Stage();
        Scene customerScene = new Scene(root);
        customerStage.setScene(customerScene);
        customerStage.setTitle("Customers");
        customerStage.setResizable(false);
        customerStage.show();

    }

    @FXML
    public void openCarTab(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../resources/Cars.fxml"));
        Stage customerStage = new Stage();
        Scene customerScene = new Scene(root);
        customerStage.setScene(customerScene);
        customerStage.setTitle("Cars");
        customerStage.setResizable(false);
        customerStage.show();

    }

    @FXML
    public void openParkingTab(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../resources/Parking.fxml"));
        Stage parkingStage = new Stage();
        Scene parkingScene = new Scene(root);
        parkingStage.setScene(parkingScene);
        parkingStage.setTitle("Parking places");
        parkingStage.setResizable(false);
        parkingStage.show();

    }

    @FXML
    public void openPriceListTab(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../resources/PriceList.fxml"));
        Stage pricelistStage = new Stage();
        Scene pricelistScene = new Scene(root);
        pricelistStage.setScene(pricelistScene);
        pricelistStage.setTitle("Pricelists");
        pricelistStage.setResizable(false);
        pricelistStage.show();

    }

    @FXML
    public void openAddCustomerTab(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../resources/addCustomer.fxml"));
        Stage customerStage = new Stage();
        Scene customerScene = new Scene(root);
        customerStage.setScene(customerScene);
        customerStage.setTitle("Add Customer");
        customerStage.setResizable(false);
        customerStage.show();

    }

    @FXML
    public void openAddCarTab(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../resources/addCar.fxml"));
        Stage addCarStage = new Stage();
        Scene addCarScene = new Scene(root);
        addCarStage.setScene(addCarScene);
        addCarStage.setTitle("Add Car");
        addCarStage.setResizable(false);
        addCarStage.show();

    }

    @FXML
    public void openAddParkingTab(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../resources/addParking.fxml"));
        Stage addParkingStage = new Stage();
        Scene addParkingScene = new Scene(root);
        addParkingStage.setScene(addParkingScene);
        addParkingStage.setTitle("Add Parking Place");
        addParkingStage.setResizable(false);
        addParkingStage.show();

    }

    @FXML
    public void openAddPriceListTab() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../resources/addPriceList.fxml"));
        Stage pricelistStage = new Stage();
        Scene pricelistScene = new Scene(root);
        pricelistStage.setScene(pricelistScene);
        pricelistStage.setTitle("Add PriceList");
        pricelistStage.setResizable(false);
        pricelistStage.show();

    }

    @FXML
    public void openReceptionTab() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../resources/Reception.fxml"));
        Parent root = loader.load();

        ReceptionController controller = loader.getController();
        controller.getUserFromMain(programUser.getText());

        Stage pricelistStage = new Stage();
        Scene pricelistScene = new Scene(root);
        pricelistStage.setScene(pricelistScene);
        pricelistStage.setTitle("Reception Parking");
        pricelistStage.setResizable(true);
        pricelistStage.show();

    }

    @FXML
    public void openEmergentPriceListTab() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../resources/EmergentPriceList.fxml"));
        Stage pricelistStage = new Stage();
        Scene pricelistScene = new Scene(root);
        pricelistStage.setScene(pricelistScene);
        pricelistStage.setTitle("Add Emergent PriceList");
        pricelistStage.setResizable(false);
        pricelistStage.show();

    }

    @FXML
    public void addEmergentPriceList() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../resources/addEmergentPriceList.fxml"));
        Stage pricelistStage = new Stage();
        Scene pricelistScene = new Scene(root);
        pricelistStage.setScene(pricelistScene);
        pricelistStage.setTitle("Add Emergent PriceList");
        pricelistStage.setResizable(false);
        pricelistStage.show();

    }

    @FXML
    public void openUsersTab() throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("../resources/Users.fxml"));
        Stage pricelistStage = new Stage();
        Scene pricelistScene = new Scene(root);
        pricelistStage.setScene(pricelistScene);
        pricelistStage.setTitle("Users");
        pricelistStage.setResizable(false);
        pricelistStage.show();

    }

    public void getUser(User userName) {

        user = userName;

        programUser.setText(user.getUsername());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
