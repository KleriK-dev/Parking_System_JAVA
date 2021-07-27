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
import parkingsystem.objects.EmergentPriceList;

public class EmergentPricelistController implements Initializable {

    @FXML
    private TableView<EmergentPriceList> table;
    @FXML
    private TableColumn<EmergentPriceList, String> col_id;
    @FXML
    private TableColumn<EmergentPriceList, String> col_descreption;
    @FXML
    private TableColumn<EmergentPriceList, String> col_entrance;
    @FXML
    private TableColumn<EmergentPriceList, String> col_priceH;
    @FXML
    private Button closePriceList;
    @FXML
    private TextField txt_id;
    
    ObservableList<EmergentPriceList> oblist = FXCollections.observableArrayList();
    SpecialAlert alert = new SpecialAlert();
    int index = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }    

    @FXML
    private void handleMouseClickedonTable(MouseEvent event) {
        
        index = table.getSelectionModel().getSelectedIndex();

        if (index <= -1) {
            return;
        } else {
            txt_id.setText(col_id.getCellData(index).toString());
        }
        
    }

    @FXML
    private void openAddPriceList(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("../resources/addEmergentPriceList.fxml"));
        Stage pricelistStage = new Stage();
        Scene pricelistScene = new Scene(root);
        pricelistStage.setScene(pricelistScene);
        pricelistStage.setTitle("Add Emergent PriceList");
        pricelistStage.setResizable(false);
        pricelistStage.show();
        
    }

    @FXML
    private void deletePriceList(ActionEvent event) {
        
        if ((txt_id.getText()).equals("")) {
            alert.show("Warning", "Select a pricelist!", Alert.AlertType.WARNING);
        } else {

            String id = txt_id.getText();
            EmergentPriceList pricelist = new EmergentPriceList(id, null, 0, 0);
            pricelist.deleteEmergentPriceList();
            loadData();
            txt_id.clear();

        }
        
    }

    @FXML
    private void editPriceList(ActionEvent event) throws IOException {
        
        try {
            //create scene for editing customer
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../resources/editEmergentPriceList.fxml"));
            Parent root = loader.load();

            //pass selected data from this controller to the editCustomerController
            EditEmergentPriceListController controller = loader.getController();
            controller.getData(table.getSelectionModel().getSelectedItem());

            Stage editStage = new Stage();
            Scene editScene = new Scene(root);
            editStage.setScene(editScene);
            editStage.setTitle("Edit Emergent PriceList");
            editStage.setResizable(false);
            editStage.show();

        } catch (Exception ex) { //catch the exception of the user dont select a customer
            alert.show("Warning", "Select a pricelist!", Alert.AlertType.WARNING);
        }
        
    }

    @FXML
    private void refresh(ActionEvent event) {
        loadData();
    }

    @FXML
    private void closePriceList(ActionEvent event) {
        Stage stage = (Stage) closePriceList.getScene().getWindow();
        stage.close();

    }
    
    public void loadData() { //select all the data from customers on database and display them

        oblist.clear();

        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT * FROM emergent_pricelist";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                oblist.add(new EmergentPriceList(rs.getString("emergentPricelist_id"), rs.getString("description"), rs.getDouble("price"), rs.getDouble("price_hour")));
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_descreption.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_entrance.setCellValueFactory(new PropertyValueFactory<>("entrancePrice"));
        col_priceH.setCellValueFactory(new PropertyValueFactory<>("priceHour"));

        table.setItems(oblist);
    }
    
    
}
