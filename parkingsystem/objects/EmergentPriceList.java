package parkingsystem.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import parkingsystem.SpecialAlert;
import parkingsystem.database.mysqlConnection;

public class EmergentPriceList {
    
    private String id;
    private String description;
    private double entrancePrice;
    private double priceHour;
    SpecialAlert alert = new SpecialAlert();
    
    public EmergentPriceList() {}

    public EmergentPriceList(String id, String description, double entrancePrice, double priceHour) {
        this.id = id;
        this.description = description;
        this.entrancePrice = entrancePrice;
        this.priceHour = priceHour;
    }
    
    public void addEmergentPriceList(){
        
        Connection conn = mysqlConnection.connectToDB();
        PreparedStatement prst;

        String query = "INSERT INTO emergent_pricelist (description, price, price_hour) VALUES (?, ?, ?)";

        try {

            prst = conn.prepareStatement(query);
            prst.setString(1, description);
            prst.setDouble(2, entrancePrice);
            prst.setDouble(3, priceHour);
            prst.executeUpdate();
            
            alert.show("Success", "PriceList was created successfully!", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            alert.show("Error", "Unknown error occurred!", Alert.AlertType.ERROR);
        }
        
    }
    
    public void editEmergentPriceList(){
        
        Connection conn = mysqlConnection.connectToDB();
        PreparedStatement prst;
        String query = "UPDATE emergent_pricelist "
                        + "SET description = ?,"
                        + "price = ?,"
                        + "price_hour = ?"
                        + "WHERE emergentPricelist_id = ?";
        
        try{
            prst = conn.prepareStatement(query);
            prst.setString(1, description);
            prst.setDouble(2, entrancePrice);
            prst.setDouble(3, priceHour);
            prst.setString(4, id);
            prst.executeUpdate();
            
            alert.show("Success", "Pricelist was updated successfully!", Alert.AlertType.INFORMATION);
            
        } catch(SQLException ex){
            alert.show("Error", "Unknown error occurred!", Alert.AlertType.ERROR);
        }
        
    }
    
    public void deleteEmergentPriceList(){
        
        Connection conn = mysqlConnection.connectToDB();
        String query = "DELETE FROM emergent_pricelist WHERE emergentPricelist_id = ?";
        PreparedStatement prst;

        try {
            prst = conn.prepareStatement(query);
            prst.setString(1, id);
            prst.execute();
            alert.show("Success", "PriceList deleted!", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            alert.show("Error", "This emergent pricelist is being used right now, export the car or you can edit it!", Alert.AlertType.ERROR);
        }
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getEntrancePrice() {
        return entrancePrice;
    }

    public void setEntrancePrice(double entrancePrice) {
        this.entrancePrice = entrancePrice;
    }

    public double getPriceHour() {
        return priceHour;
    }

    public void setPriceHour(double priceHour) {
        this.priceHour = priceHour;
    }

    public SpecialAlert getAlert() {
        return alert;
    }

    public void setAlert(SpecialAlert alert) {
        this.alert = alert;
    }
    
    public String getEntrancePriceString() { //we need a getter that gets the price and parse it to string
        return String.valueOf(entrancePrice);
    }
    
    public String getPriceHourString() { //we need a getter that gets the price per hour and parse it to string
        return String.valueOf(priceHour);
    }

}
