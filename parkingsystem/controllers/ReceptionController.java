package parkingsystem.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import parkingsystem.SpecialAlert;
import parkingsystem.database.mysqlConnection;
import parkingsystem.objects.Emergents;
import parkingsystem.objects.RegularParkings;

public class ReceptionController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button closeReceptionTab;
    @FXML
    private TextField userName;
    @FXML
    private TableView<Emergents> table;
    @FXML
    private TableColumn<Emergents, String> col_code;
    @FXML
    private TableColumn<Emergents, String> col_plate;
    @FXML
    private TableColumn<Emergents, String> col_name;
    @FXML
    private TableColumn<Emergents, String> col_adress;
    @FXML
    private TableColumn<Emergents, String> col_entrance;
    @FXML
    private TableColumn<Emergents, String> col_exit;
    @FXML
    private TableColumn<Emergents, String> col_parked;
    @FXML
    private TableColumn<Emergents, String> col_pricelist;
    @FXML
    private TextField txt_id;
    
    ObservableList<Emergents> oblist = FXCollections.observableArrayList();
    SpecialAlert alert = new SpecialAlert();
    int index = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    public void handleMouseClickedonTable(MouseEvent event) { //set data to a textfield when its selected by click

        index = table.getSelectionModel().getSelectedIndex();

        if (index <= -1) { //check if user clicked on nothing
            return; //return so the program doesn't crash
        } else { //else set the data to the textfields
            txt_id.setText(col_code.getCellData(index).toString());
        }

    }
    
    public void getUserFromMain(String name){
        userName.setText(name);
    }

    @FXML
    private void openCustomersTab(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/Customer.fxml"));
        Stage customerStage = new Stage();
        Scene customerScene = new Scene(root);
        customerStage.setScene(customerScene);
        customerStage.setTitle("Customers");
        customerStage.setResizable(false);
        customerStage.show();
    }

    @FXML
    private void openCarsTab(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/Cars.fxml"));
        Stage customerStage = new Stage();
        Scene customerScene = new Scene(root);
        customerStage.setScene(customerScene);
        customerStage.setTitle("Cars");
        customerStage.setResizable(false);
        customerStage.show();
    }

    @FXML
    private void changeToRegularReception(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../resources/ReceptionRegularCustomer.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void changeToMainReception(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("../resources/Reception.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void closeReceptionTab(ActionEvent event) {
        Stage stage = (Stage) closeReceptionTab.getScene().getWindow(); 
        stage.close();
    }

    @FXML
    private void openRegularPriceLists(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/PriceList.fxml"));
        Stage pricelistStage = new Stage();
        Scene pricelistScene = new Scene(root);
        pricelistStage.setScene(pricelistScene);
        pricelistStage.setTitle("Regular Pricelists");
        pricelistStage.setResizable(false);
        pricelistStage.show();
    }

    @FXML
    private void openParkingTab(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/Parking.fxml"));
        Stage parkingStage = new Stage();
        Scene parkingScene = new Scene(root);
        parkingStage.setScene(parkingScene);
        parkingStage.setTitle("Parking places");
        parkingStage.setResizable(false);
        parkingStage.show();
    }

    @FXML
    private void openInsertionTab(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/InsertEmergentCar.fxml"));
        Stage customerStage = new Stage();
        Scene customerScene = new Scene(root);
        customerStage.setScene(customerScene);
        customerStage.setTitle("Insertion");
        customerStage.setResizable(false);
        customerStage.show();
    }

    @FXML
    private void exportCar(ActionEvent event) throws IOException {
        
         try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../resources/Export.fxml"));
            Parent root = loader.load();

            ExportController controller = loader.getController();
            controller.getData(table.getSelectionModel().getSelectedItem());

            Stage editStage = new Stage();
            Scene editScene = new Scene(root);
            editStage.setScene(editScene);
            editStage.setTitle("Exporting");
            editStage.setResizable(false);
            editStage.show();

        } catch (Exception ex) { //catch the exception of the user dont select a customer
            alert.show("Warning", "Select a car!", Alert.AlertType.WARNING);
        }
        
    }

    @FXML
    private void refresh(ActionEvent event) {
        loadData();
    }

    @FXML
    private void openPriceListTab(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("../resources/EmergentPriceList.fxml"));
        Stage pricelistStage = new Stage();
        Scene pricelistScene = new Scene(root);
        pricelistStage.setScene(pricelistScene);
        pricelistStage.setTitle("Add Emergent PriceList");
        pricelistStage.setResizable(false);
        pricelistStage.show();
        
    }
    
    public void loadData() { //select all the data from customers on database and display them
        //clear car table
        oblist.clear();

        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT parked_id,"
                        + "plate, owner_name, owner_adress, entrance_time, exit_time,"
                        + "parking_spaces.description, emergent_pricelist.description "
                + "FROM emergent_parked "
                + "INNER JOIN parking_spaces ON emergent_parked.parking_id = parking_spaces.parking_id "
                + "INNER JOIN emergent_pricelist ON emergent_pricelist.emergentPricelist_id = emergent_parked.emergentPricelist_id";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                oblist.add(new Emergents(rs.getString("parked_id"), rs.getString("plate"), rs.getString("owner_name"),
                        rs.getString("owner_adress"), rs.getString("entrance_time"), rs.getString("exit_time"),
                        rs.getString("emergent_pricelist.description"), rs.getString("parking_spaces.description")));
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }

        col_code.setCellValueFactory(new PropertyValueFactory<>("parked_id"));
        col_plate.setCellValueFactory(new PropertyValueFactory<>("car_plate"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("owner"));
        col_adress.setCellValueFactory(new PropertyValueFactory<>("ownerAdress"));
        col_entrance.setCellValueFactory(new PropertyValueFactory<>("entrance_time"));
        col_exit.setCellValueFactory(new PropertyValueFactory<>("exit_time"));
        col_parked.setCellValueFactory(new PropertyValueFactory<>("parkingPlace_id"));
        col_pricelist.setCellValueFactory(new PropertyValueFactory<>("emergentPricelist_id"));

        table.setItems(oblist);
    }
    
    /* HERE IS EVERYTHING FOR RECEPTION REGULAR PARKED CAR FXML */
    
    @FXML
    private TableView<RegularParkings> tableReg;
    @FXML
    private TableColumn<RegularParkings, String> col_codeReg;
    @FXML
    private TableColumn<RegularParkings, String> col_customer;
    @FXML
    private TableColumn<RegularParkings, String> col_carPlate;
    @FXML
    private TableColumn<RegularParkings, String> col_RegEntrance;
    @FXML
    private TableColumn<RegularParkings, String> col_RegExit;
    @FXML
    private TableColumn<RegularParkings, String> col_RegParked;
    @FXML
    private TableColumn<RegularParkings, String> col_RegPricelist;
    @FXML
    private TextField txt_Regid;
    
    ObservableList<RegularParkings> list = FXCollections.observableArrayList();
    
    public void InsertRegularCustomer(ActionEvent event) throws IOException{
        
        Parent root = FXMLLoader.load(getClass().getResource("../resources/InsertRegularParkedCar.fxml"));
        Stage pricelistStage = new Stage();
        Scene pricelistScene = new Scene(root);
        pricelistStage.setScene(pricelistScene);
        pricelistStage.setTitle("Insert Regular Parked Car");
        pricelistStage.setResizable(false);
        pricelistStage.show();
        
    }
    
    @FXML
    private void exportRegCar(ActionEvent event) throws IOException {
        
         try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../resources/ExportRegular.fxml"));
            Parent root = loader.load();

            ExportRegularController controller = loader.getController();
            controller.getData(tableReg.getSelectionModel().getSelectedItem());

            Stage editStage = new Stage();
            Scene editScene = new Scene(root);
            editStage.setScene(editScene);
            editStage.setTitle("Exporting Regular Car");
            editStage.setResizable(false);
            editStage.show();

        } catch (Exception ex) { //catch the exception of the user dont select a customer
            alert.show("Warning", "Select a car!", Alert.AlertType.WARNING);
        }
        
    }
    
    @FXML
    private void refreshReg(ActionEvent event) {
        loadRegularInertData();
    }
    
    public void loadRegularInertData(){
        
        list.clear();

        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT regular_parked.parkedCar_id, customers.full_name, cars.car_plate, price_list.description AS listname, "
                    + "parking_spaces.description AS parkingName, regular_parked.entrance_date, regular_parked.estimatedExit_date "
                + "FROM `regular_parked` "
                + "INNER JOIN `customers` ON customers.customer_id = regular_parked.customer_id "
                + "INNER JOIN `cars` ON cars.car_id = regular_parked.car_id "
                + "INNER JOIN `price_list` ON price_list.pricelist_id = regular_parked.pricelist_id "
                + "INNER JOIN `parking_spaces` ON parking_spaces.parking_id = regular_parked.parking_id ";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                list.add(new RegularParkings(rs.getString("parkedCar_id"), rs.getString("full_name"), rs.getString("car_plate"), rs.getString("listname"), 
                        rs.getString("parkingName"), rs.getString("entrance_date"), rs.getString("estimatedExit_date")));
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }
        
        RegularParkings parked = new RegularParkings();
        
        col_codeReg.setCellValueFactory(new PropertyValueFactory<>("regularParking_id"));
        col_customer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        col_carPlate.setCellValueFactory(new PropertyValueFactory<>("carPlate"));
        col_RegPricelist.setCellValueFactory(new PropertyValueFactory<>("pricelistDesc"));
        col_RegParked.setCellValueFactory(new PropertyValueFactory<>("parkingDesc"));
        col_RegEntrance.setCellValueFactory(new PropertyValueFactory<>("entranceDate"));
        col_RegExit.setCellValueFactory(new PropertyValueFactory<>("estimatedExitDate"));

        tableReg.setItems(list);
        
    }
    
    @FXML
    public void handleMouseClickedonRegTable(MouseEvent event) { //set data to a textfield when its selected by click

        index = tableReg.getSelectionModel().getSelectedIndex();

        if (index <= -1) { //check if user clicked on nothing
            return; //return so the program doesn't crash
        } else { //else set the data to the textfields
            txt_Regid.setText(col_codeReg.getCellData(index).toString());
        }

    }
    
}
