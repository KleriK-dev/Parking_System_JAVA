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
import parkingsystem.objects.User;

public class UsersController implements Initializable {

    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> col_id;
    @FXML
    private TableColumn<User, String> col_user;
    @FXML
    private TableColumn<User, String> col_password;
    @FXML
    private Button close;
    @FXML
    private TextField txt_id;
    
    ObservableList<User> oblist = FXCollections.observableArrayList();
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
    private void openAddUser(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("../resources/addUser.fxml"));
        Stage pricelistStage = new Stage();
        Scene pricelistScene = new Scene(root);
        pricelistStage.setScene(pricelistScene);
        pricelistStage.setTitle("Add User");
        pricelistStage.setResizable(false);
        pricelistStage.show();
        
    }

    @FXML
    private void deleteUser(ActionEvent event) {
        
        if ((txt_id.getText()).equals("")) {
            alert.show("Warning", "Select a user!", Alert.AlertType.WARNING);
        } else {

            String user_id = txt_id.getText();
            User user = new User(user_id, null, null);
            user.deleteUser();
            loadData();
            txt_id.clear();

        }
        
    }

    @FXML
    private void refresh(ActionEvent event) {
        loadData();
    }

    @FXML
    private void closeUserTab(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
    
    public void loadData() { //select all the data from customers on database and display them

        oblist.clear();

        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT * FROM users";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                oblist.add(new User(rs.getString("user_id"), rs.getString("user_name"), rs.getString("user_password")));
            }
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("userID"));
        col_user.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("password"));

        table.setItems(oblist);
    }
    
}
