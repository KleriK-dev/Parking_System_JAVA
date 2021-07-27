package parkingsystem.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Alert;
import parkingsystem.SpecialAlert;
import parkingsystem.database.mysqlConnection;

public class RegularParkings {
    
    private String regularParking_id;
    private String customerName;
    private String carPlate;
    private String pricelistDesc;
    private String parkingDesc;
    private String entranceDate;
    private String estimatedExitDate;
    SpecialAlert alert = new SpecialAlert();
    
    public RegularParkings() {}

    public RegularParkings(String regularParking_id, String customerName, String carPlate, String pricelistDesc, String parkingDesc, String entranceDate, String estimatedExitDate) {
        this.regularParking_id = regularParking_id;
        this.customerName = customerName;
        this.carPlate = carPlate;
        this.pricelistDesc = pricelistDesc;
        this.parkingDesc = parkingDesc;
        this.entranceDate = entranceDate;
        this.estimatedExitDate = estimatedExitDate;
    }
    
    public void insertRegularParking(){
        
        Connection conn = mysqlConnection.connectToDB();
        PreparedStatement prst;

        String query = "INSERT INTO `regular_parked` (customer_id, car_id, pricelist_id, parking_id, entrance_date, estimatedExit_date) "
                + "SELECT customers.customer_id, cars.car_id, price_list.pricelist_id, parking_spaces.parking_id, ?, ? "
                + "FROM `customers`, `cars`, `price_list`, `parking_spaces`"
                + "WHERE customers.customer_id = (SELECT customer_id FROM `customers` WHERE full_name = ?) "
                + "AND cars.car_id = (SELECT car_id FROM `cars` WHERE car_plate = ?) "
                + "AND price_list.pricelist_id = (SELECT pricelist_id FROM `price_list` WHERE description = ?) "
                + "AND parking_spaces.parking_id = (SELECT parking_id FROM `parking_spaces` WHERE description = ?) ";

        try {

            prst = conn.prepareStatement(query);
            prst.setString(1, entranceDate);
            prst.setString(2, estimatedExitDate);
            prst.setString(3, customerName);
            prst.setString(4, carPlate);
            prst.setString(5, pricelistDesc);
            prst.setString(6, parkingDesc);
            prst.executeUpdate();

            alert.show("Success", "Car was inserted successfully!", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            alert.show("Error", "Unknown error occurred!", Alert.AlertType.ERROR);
        }
        
    }

    public void exportRegularParking(){
        
        Connection conn = mysqlConnection.connectToDB();

        String query = "DELETE FROM regular_parked WHERE parkedCar_id = ?";
        PreparedStatement prst;

        try {
            prst = conn.prepareStatement(query);
            prst.setString(1, regularParking_id);
            prst.execute();
            alert.show("Success", "Customer's car was exported!", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }
        
    }
    
    public String getCurrentExportTime() {

        //get date when export button was pressed
        Date currentExportingDate = new Date();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
        String exportTime = dateTimeFormat.format(currentExportingDate);

        return exportTime;
    }
    
    public String getDurationParking() throws ParseException {

        //parse strings to dates
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date entrance = format.parse(entranceDate);
        Date export = format.parse(getCurrentExportTime());

        //get the difference in milliseconds, convert it to days and parse it to String 
        long diffInMillies = export.getTime() - entrance.getTime();
        int DAY = (int)(diffInMillies / (1000*60*60*24));
        Integer days = DAY;
        
        String duration = days.toString();

        return duration;
    }
    
    public String getCost(String pricelist){
        
        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT price FROM price_list WHERE description = ?"; 
        PreparedStatement prst;
        ResultSet rs;
       
        String price = null;
        
        try{
            prst = conn.prepareStatement(query);
            prst.setString(1, pricelist);
            rs = prst.executeQuery();
            
            while(rs.next()){
                price = rs.getString("price");
            }
            
        } catch(SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }
        
        return price;
    }

    public String getRegularParking_id() {
        return regularParking_id;
    }

    public void setRegularParking_id(String regularParking_id) {
        this.regularParking_id = regularParking_id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getParkingDesc() {
        return parkingDesc;
    }

    public void setParkingDesc(String parkingDesc) {
        this.parkingDesc = parkingDesc;
    }

    public String getEntranceDate() {
        return entranceDate;
    }

    public void setEntranceDate(String entranceDate) {
        this.entranceDate = entranceDate;
    }

    public String getEstimatedExitDate() {
        return estimatedExitDate;
    }

    public void setEstimatedExitDate(String estimatedExitDate) {
        this.estimatedExitDate = estimatedExitDate;
    }
    
    public String getPricelistDesc() {
        return pricelistDesc;
    }

    public void setPricelistDesc(String pricelistDesc) {
        this.pricelistDesc = pricelistDesc;
    }
    
    
    
}
