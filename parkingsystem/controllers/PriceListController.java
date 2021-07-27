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
import parkingsystem.objects.PriceList;

public class PriceListController implements Initializable {

    @FXML
    private Button closePriceList;
    @FXML
    private TableView<PriceList> table;
    @FXML
    private TableColumn<PriceList, String> col_id;
    @FXML
    private TableColumn<PriceList, String> col_descreption;
    @FXML
    private TableColumn<PriceList, String> col_price;
    @FXML
    private TextField txt_id;

    ObservableList<PriceList> oblist = FXCollections.observableArrayList();
    SpecialAlert alert = new SpecialAlert();
    int index = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    @FXML
    private void openAddPriceList(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../resources/addPriceList.fxml"));
        Stage pricelistStage = new Stage();
        Scene pricelistScene = new Scene(root);
        pricelistStage.setScene(pricelistScene);
        pricelistStage.setTitle("Add PriceList");
        pricelistStage.setResizable(false);
        pricelistStage.show();

    }

    @FXML
    private void deletePriceList(ActionEvent event) {

        if ((txt_id.getText()).equals("")) {
            alert.show("Warning", "Select a pricelist!", Alert.AlertType.WARNING);
        } else {

            String pricelist_id = txt_id.getText();
            PriceList pricelist = new PriceList(pricelist_id, null, 0);
            pricelist.deletePriceList();
            loadData();
            txt_id.clear();

        }

    }

    @FXML
    private void editPriceList(ActionEvent event) throws IOException {

        try {
            //create scene for editing customer
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../resources/editPriceList.fxml"));
            Parent root = loader.load();

            //pass selected data from this controller to the editCustomerController
            EditPriceListController controller = loader.getController();
            controller.getData(table.getSelectionModel().getSelectedItem());

            Stage editStage = new Stage();
            Scene editScene = new Scene(root);
            editStage.setScene(editScene);
            editStage.setTitle("Edit PriceList");
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

    @FXML
    public void handleMouseClickedonTable(MouseEvent event) {

        index = table.getSelectionModel().getSelectedIndex();

        if (index <= -1) {
            return;
        } else {
            txt_id.setText(col_id.getCellData(index).toString());
        }

    }

    public void loadData() { //select all the data from customers on database and display them

        oblist.clear();

        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT * FROM price_list";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                oblist.add(new PriceList(rs.getString("pricelist_id"), rs.getString("description"), rs.getDouble("price")));
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("pricelist_id"));
        col_descreption.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.setItems(oblist);
    }

}
