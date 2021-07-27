package parkingsystem.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import parkingsystem.SpecialAlert;
import parkingsystem.database.mysqlConnection;

public class Customer {

    private String id;
    private String name;
    private String VAT;
    private String adress;
    private String ZIP;
    SpecialAlert alert = new SpecialAlert();

    public Customer() {
    }

    public Customer(String id, String name, String VAT, String adress, String ZIP) {
        this.id = id;
        this.name = name;
        this.VAT = VAT;
        this.adress = adress;
        this.ZIP = ZIP;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVAT() {
        return VAT;
    }

    public void setVAT(String VAT) {
        this.VAT = VAT;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getZIP() {
        return ZIP;
    }

    public void setZIP(String ZIP) {
        this.ZIP = ZIP;
    }

    public void addCustomer() {

        Connection conn = mysqlConnection.connectToDB();
        PreparedStatement prst;

        String query = "INSERT INTO customers (full_name, VAT_number, adress, zip_code) VALUES (?, ?, ?, ?)";

        try {

            prst = conn.prepareStatement(query);
            prst.setString(1, name);
            prst.setString(2, VAT);
            prst.setString(3, adress);
            prst.setString(4, ZIP);
            prst.executeUpdate();
            
            alert.show("Success", "Customer was inserted successfully!", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            alert.show("Error", "Unknown error occurred!", Alert.AlertType.ERROR);
        }
    }
    
    public void editCustomer(){
        
        Connection conn = mysqlConnection.connectToDB();
        PreparedStatement prst;
        String query = "UPDATE customers "
                        + "SET full_name = ?,"
                        + "VAT_number = ?,"
                        + "adress = ?,"
                        + "zip_code = ?"
                        + "WHERE customer_id = ?";
        
        try{
            prst = conn.prepareStatement(query);
            prst.setString(1, name);
            prst.setString(2, VAT);
            prst.setString(3, adress);
            prst.setString(4, ZIP);
            prst.setString(5, id);
            prst.executeUpdate();
            
            alert.show("Success", "Customer was updated successfully!", Alert.AlertType.INFORMATION);
            
        } catch(SQLException ex){
            alert.show("Error", "Unknown error occurred!", Alert.AlertType.ERROR);
        }
    }

    public void deleteCustomer() {

        Connection conn = mysqlConnection.connectToDB();
        String query = "DELETE FROM customers WHERE customer_id = ?";
        PreparedStatement prst;

        try {
            prst = conn.prepareStatement(query);
            prst.setString(1, id);
            prst.execute();
            alert.show("Success", "Customer deleted!", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            alert.show("Error", "This customer has a vehicle in the system, delete his vehicle first and then the customer!", Alert.AlertType.ERROR);
        }

    }

}
