package parkingsystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import parkingsystem.Common;
import parkingsystem.SpecialAlert;
import parkingsystem.database.mysqlConnection;
import parkingsystem.objects.Parking;

public class ParkingController implements Initializable {

    @FXML
    private Button closeParking;
    @FXML
    private TableView<Parking> table;
    @FXML
    private TableColumn<Parking, String> col_description;
    @FXML
    private TableColumn<Parking, String> col_floor;
    @FXML
    private TableColumn<Parking, String> col_space;
    @FXML
    private TableColumn<Parking, String> col_hidden_id;
    @FXML
    private TextField txt_id;
    @FXML
    private Label closed_label;
    @FXML
    private Label free_label;

    ObservableList<Parking> oblist = FXCollections.observableArrayList();
    SpecialAlert alert = new SpecialAlert();
    int index = -1;
    Common common = new Common();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    @FXML
    private void openAddParkingPlace(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../resources/addParking.fxml"));
        Stage addParkingStage = new Stage();
        Scene addParkingScene = new Scene(root);
        addParkingStage.setScene(addParkingScene);
        addParkingStage.setTitle("Add Parking Place");
        addParkingStage.setResizable(false);
        addParkingStage.show();

    }

    @FXML
    private void deleteParkingPlace(ActionEvent event) {

        if ((txt_id.getText()).equals("")) {
            alert.show("Warning", "Select a parking place!", Alert.AlertType.WARNING);
        } else {
            
            String parking_id = txt_id.getText();
            Parking parking = new Parking(parking_id, null, null, null);
            parking.deleteParking();
            loadData();
            txt_id.clear();

        }

    }

    @FXML
    private void closeParking(ActionEvent event) {

        Stage stage = (Stage) closeParking.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void refreshParkingTab() {
        loadData();
        txt_id.clear();
    }

    @FXML
    private void handleMouseClickedonTable(MouseEvent event) {

        index = table.getSelectionModel().getSelectedIndex();

        if (index <= -1) { //check if user clicked on nothing
            return; //return so the program doesn't crash
        } else { //else set the data to the textfields
            txt_id.setText(col_hidden_id.getCellData(index).toString());
            displayAvailability();
        }

    }

    public void loadData() { //select all the data from customers on database and display them
        //clear car table
        oblist.clear();

        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT * FROM parking_spaces";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                oblist.add(new Parking(rs.getString("parking_id"), rs.getString("description"), rs.getString("floor"), rs.getString("spaces")));
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }

        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_floor.setCellValueFactory(new PropertyValueFactory<>("floor"));
        col_space.setCellValueFactory(new PropertyValueFactory<>("space"));
        col_hidden_id.setCellValueFactory(new PropertyValueFactory<>("parking_id"));

        table.setItems(oblist);
    }
    
    public void displayAvailability(){
        
        //index = table.getSelectionModel().getSelectedIndex();
        
        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT (SELECT COUNT(*) FROM regular_parked WHERE parking_id = ?) + (SELECT COUNT(*) FROM emergent_parked WHERE parking_id = ?) AS closedParkings ";
        PreparedStatement prst;
        ResultSet rs;
        String parkingID = col_hidden_id.getCellData(index).toString();
        String parkingSpace = col_space.getCellData(index).toString();
        
        String closedParkings = null;
        
        try {
            prst = conn.prepareStatement(query);
            prst.setString(1, parkingID);
            prst.setString(2, parkingID);
            rs = prst.executeQuery();
            while (rs.next()) {
                closedParkings = rs.getString("closedParkings");
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }
        
        int closedNumber = Integer.parseInt(closedParkings);
        int SpaceNumber = Integer.parseInt(parkingSpace);
        
        Integer freeParkingsNum = SpaceNumber - closedNumber;
        String freeParking = freeParkingsNum.toString();

        closed_label.setText(closedParkings);
        free_label.setText(freeParking);
        
    }

}
