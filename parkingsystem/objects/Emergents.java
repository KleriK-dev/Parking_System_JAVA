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

public class Emergents {

    private String parked_id;
    private String car_plate;
    private String owner;
    private String ownerAdress;
    private String entrance_time;
    private String exit_time;
    private String emergentPricelist_id;
    private String parkingPlace_id;
    SpecialAlert alert = new SpecialAlert();

    public Emergents() {
    }

    public Emergents(String parked_id, String car_plate, String owner, String ownerAdress, String entrance_time, String exit_time, String emergentPricelist_id, String parkingPlace_id) {
        this.parked_id = parked_id;
        this.car_plate = car_plate;
        this.owner = owner;
        this.ownerAdress = ownerAdress;
        this.entrance_time = entrance_time;
        this.exit_time = exit_time;
        this.emergentPricelist_id = emergentPricelist_id;
        this.parkingPlace_id = parkingPlace_id;
    }

    public void insertEmergent() {

        Connection conn = mysqlConnection.connectToDB();
        PreparedStatement prst;

        String query = "INSERT INTO `emergent_parked` (plate, owner_name, owner_adress, entrance_time, exit_time, emergentPricelist_id, parking_id) "
                + "SELECT ?, ?, ?, ?, ?, emergentPricelist_id, parking_id "
                + "FROM `emergent_pricelist`, `parking_spaces` "
                + "WHERE emergentPricelist_id = (SELECT emergentPricelist_id FROM `emergent_pricelist` WHERE description = ?) "
                + "AND parking_id = (SELECT parking_id FROM `parking_spaces` WHERE description = ?)";

        try {

            prst = conn.prepareStatement(query);
            prst.setString(1, car_plate);
            prst.setString(2, owner);
            prst.setString(3, ownerAdress);
            prst.setString(4, entrance_time);
            prst.setString(5, exit_time);
            prst.setString(6, emergentPricelist_id);
            prst.setString(7, parkingPlace_id);
            prst.executeUpdate();

            alert.show("Success", "Car was inserted successfully!", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            alert.show("Error", "Unknown error occurred!", Alert.AlertType.ERROR);
        }

    }

    public void exportEmergent() {

        Connection conn = mysqlConnection.connectToDB();

        String query = "DELETE FROM emergent_parked WHERE parked_id = ?";
        PreparedStatement prst;

        try {
            prst = conn.prepareStatement(query);
            prst.setString(1, parked_id);
            prst.execute();
            alert.show("Success", "Car was exported!", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }

    }

    public String getCurrentExportTime() {

        //get the date and time when the export button was pressed
        Date currentExportingDate = new Date();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy \tHH:mm:ss");
        String exportTime = dateTimeFormat.format(currentExportingDate);

        return exportTime;
    }

    public String getDurationParking() throws ParseException {

        //parse strings to dates
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date entrance = format.parse(entrance_time);
        Date export = format.parse(getCurrentExportTime());

        //get the difference in milliseconds, convert it to minutes and parse it to String 
        long diffInMillies = export.getTime() - entrance.getTime();
        int min = (int)((diffInMillies / 1000) / 60);
        Integer minutes = min;
        
        String duration = minutes.toString();

        return duration;
    }
    
    public String calculateCost(String pricelist) throws ParseException{
        
        //run a query to select price and price_hour from the pricelist that the customer has
        Connection conn = mysqlConnection.connectToDB();
        String query = "SELECT price, price_hour FROM emergent_pricelist WHERE description = ?"; 
        PreparedStatement prst;
        ResultSet rs;
        
        String durationString = getDurationParking();
        String priceString = null;
        String price_hourString = null;
        
        try{
            prst = conn.prepareStatement(query);
            prst.setString(1, pricelist);
            rs = prst.executeQuery();
            
            while(rs.next()){
                priceString = rs.getString("price");
                price_hourString = rs.getString("price_hour");
            }
            
        } catch(SQLException ex) {
            alert.show("Error", "Unknown error occured!", Alert.AlertType.ERROR);
        }
        
        //parse to double all the strings so we can do the calculations
        double duration = Double.parseDouble(durationString);
        double price = Double.parseDouble(priceString);
        double price_hour = Double.parseDouble(price_hourString);
        
        if(duration <= 60){ //if duration is smaller than 60 mins or equals 
            
            return priceString; //return the primary price on string so we cant store it to a textfield
            
        } else if(duration > 60){ //else if duration is bigger than 60 mins
            
            /*
            Store the equation in a double variable extra_cost,
            from duration substract 60 as the customer has paid for the first hour the primary price
            and devide by 60 to get the new duration after the substraction.
            Then multiply by the price per hour the customer has set plus the primary price to get the final cost
            */
            
            double extra_cost = ((duration - 60) / 60) * price_hour + price; 
            
            //round number + 0.3 (that means round number when it gets for example 1.3 to 2.0 
            double rounded = Math.round(extra_cost + 0.3); //That gives the customer 12 min time to get his car before he gets charged for the other hour
            
            String finalCost = String.valueOf(rounded); //parse it to string
            
            return finalCost;
            
        }
        return priceString;
    }

    public String getParked_id() {
        return parked_id;
    }

    public void setParked_id(String parked_id) {
        this.parked_id = parked_id;
    }

    public String getCar_plate() {
        return car_plate;
    }

    public void setCar_plate(String car_plate) {
        this.car_plate = car_plate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerAdress() {
        return ownerAdress;
    }

    public void setOwnerAdress(String ownerAdress) {
        this.ownerAdress = ownerAdress;
    }

    public String getEntrance_time() {
        return entrance_time;
    }

    public void setEntrance_time(String entrance_time) {
        this.entrance_time = entrance_time;
    }

    public String getExit_time() {
        return exit_time;
    }

    public void setExit_time(String exit_time) {
        this.exit_time = exit_time;
    }

    public String getEmergentPricelist_id() {
        return emergentPricelist_id;
    }

    public void setEmergentPricelist_id(String emergentPricelist_id) {
        this.emergentPricelist_id = emergentPricelist_id;
    }

    public String getParkingPlace_id() {
        return parkingPlace_id;
    }

    public void setParkingPlace_id(String parkingPlace_id) {
        this.parkingPlace_id = parkingPlace_id;
    }

    public SpecialAlert getAlert() {
        return alert;
    }

    public void setAlert(SpecialAlert alert) {
        this.alert = alert;
    }

}
