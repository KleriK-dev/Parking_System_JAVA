package parkingsystem.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import parkingsystem.SpecialAlert;
import parkingsystem.database.mysqlConnection;

public class PriceList {
    
    private String pricelist_id;
    private String description;
    private double price;
    SpecialAlert alert = new SpecialAlert();
    
    public PriceList() {};

    public PriceList(String pricelist_id, String description, double price) {
        this.pricelist_id = pricelist_id;
        this.description = description;
        this.price = price;
    }
    
    public void addPriceList(){
        
        Connection conn = mysqlConnection.connectToDB();
        PreparedStatement prst;

        String query = "INSERT INTO price_list (description, price) VALUES (?, ?)";

        try {

            prst = conn.prepareStatement(query);
            prst.setString(1, description);
            prst.setDouble(2, price);
            prst.executeUpdate();
            
            alert.show("Success", "PriceList was created successfully!", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            alert.show("Error", "Unknown error occurred!", Alert.AlertType.ERROR);
        }
        
    }
    
    public void editPriceList(){
        
        Connection conn = mysqlConnection.connectToDB();
        PreparedStatement prst;
        String query = "UPDATE price_list "
                        + "SET description = ?,"
                        + "price = ?"
                        + "WHERE pricelist_id = ?";
        
        try{
            prst = conn.prepareStatement(query);
            prst.setString(1, description);
            prst.setDouble(2, price);
            prst.setString(3, pricelist_id);
            prst.executeUpdate();
            
            alert.show("Success", "Pricelist was updated successfully!", Alert.AlertType.INFORMATION);
            
        } catch(SQLException ex){
            alert.show("Error", "Unknown error occurred!", Alert.AlertType.ERROR);
        }
        
    }
    
    public void deletePriceList(){
        
        Connection conn = mysqlConnection.connectToDB();
        String query = "DELETE FROM price_list WHERE pricelist_id = ?";
        PreparedStatement prst;

        try {
            prst = conn.prepareStatement(query);
            prst.setString(1, pricelist_id);
            prst.execute();
            alert.show("Success", "PriceList deleted!", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            alert.show("Error", "This pricelist is set on customer vehicles, you can edit it or delete the vehicle first!", Alert.AlertType.ERROR);
        }
        
    }

    public String getPricelist_id() {
        return pricelist_id;
    }

    public void setPricelist_id(String pricelist_id) {
        this.pricelist_id = pricelist_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }
    
    public String getPriceString() { //we need a getter that gets the price and parse it to string
        return String.valueOf(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
    
}
