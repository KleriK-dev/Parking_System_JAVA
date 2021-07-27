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
import parkingsystem.objects.RegularParkings;

public class ExportRegularController implements Initializable {

    @FXML
    private TextField nameField;
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
    private TextField plateField;
    @FXML
    private Button export;
    @FXML
    private Button cancel;
    @FXML
    private TextField parked_id;

    private RegularParkings selectedRow;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void export(ActionEvent event) {
        String id = parked_id.getText();
        
        RegularParkings parked = new RegularParkings(id, null, null, null, null, null, null);
        
        parked.exportRegularParking();
        
        Stage stage = (Stage) export.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    
    public void getData(RegularParkings parked) throws ParseException{
        
        selectedRow = parked;

        parked_id.setText(selectedRow.getRegularParking_id());
        nameField.setText(selectedRow.getCustomerName());
        plateField.setText(selectedRow.getCarPlate());
        entranceField.setText(selectedRow.getEntranceDate());
        exportField.setText(selectedRow.getCurrentExportTime());
        parkedTimeField.setText(selectedRow.getDurationParking());
        parkingField.setText(selectedRow.getParkingDesc());
        pricelistField.setText(selectedRow.getPricelistDesc());
        costField.setText(selectedRow.getCost(selectedRow.getPricelistDesc()));

    }
    
}
