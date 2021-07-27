package parkingsystem.controllers;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import parkingsystem.objects.Emergents;

public class ExportController implements Initializable {

    @FXML
    private TextField parked_id;
    @FXML
    private TextField nameField;
    @FXML
    private TextField adressField;
    @FXML
    private TextField plateField;
    @FXML
    private TextField entranceField;
    @FXML
    private TextField exportField;
    @FXML
    private TextField parkedTimeField;
    @FXML
    private TextField parkingField;
    @FXML
    private TextField pricelistField;
    @FXML
    private TextField costField;
    @FXML
    private Button export;
    @FXML
    private Button cancel;

    private Emergents selectedRow;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void export(ActionEvent event) throws ParseException {
        
        String id = parked_id.getText();
        
        Emergents emergentParked = new Emergents(id, null, null, null, null, null, null, null);
        
        emergentParked.exportEmergent();
        
        Stage stage = (Stage) export.getScene().getWindow();
        stage.close();
        
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void getData(Emergents customer) throws ParseException{
        
        selectedRow = customer;

        parked_id.setText(selectedRow.getParked_id());
        nameField.setText(selectedRow.getOwner());
        adressField.setText(selectedRow.getOwnerAdress());
        plateField.setText(selectedRow.getCar_plate());
        entranceField.setText(selectedRow.getEntrance_time());
        exportField.setText(selectedRow.getCurrentExportTime());
        parkedTimeField.setText(selectedRow.getDurationParking());
        parkingField.setText(selectedRow.getParkingPlace_id());
        pricelistField.setText(selectedRow.getEmergentPricelist_id());
        costField.setText(selectedRow.calculateCost(selectedRow.getEmergentPricelist_id()));

    }

}
