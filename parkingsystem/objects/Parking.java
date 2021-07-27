package parkingsystem.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import parkingsystem.SpecialAlert;
import parkingsystem.database.mysqlConnection;

public class Parking {
    
    private String parking_id;
    private String description;
    private String floor;
    private String space;
    
    SpecialAlert alert = new SpecialAlert();
    
    public Parking(){}

    public Parking(String parking_id, String description, String floor, String space) {
        this.parking_id = parking_id;
        this.description = description;
        this.floor = floor;
        this.space = space;
    }
    
    
    public void createParking(){
        
        Connection conn = mysqlConnection.connectToDB();
        PreparedStatement prst;

        String query = "INSERT INTO parking_spaces (description, floor, spaces) VALUES (?, ?, ?)";

        try {

            prst = conn.prepareStatement(query);
            prst.setString(1, description);
            prst.setString(2, floor);
            prst.setString(3, space);
            prst.executeUpdate();
            
            alert.show("Success", "Paring place was created!", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            alert.show("Error", "Unknown error occurred!", Alert.AlertType.ERROR);
        }
        
    }
    
    public void deleteParking(){
        
        Connection conn = mysqlConnection.connectToDB();
        
        String query = "DELETE FROM parking_spaces WHERE parking_id = ?";
        PreparedStatement prst;

        try {
            prst = conn.prepareStatement(query);
            prst.setString(1, parking_id);
            prst.execute();
            alert.show("Success", "Parking place deleted!", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            alert.show("Error", "Cars are parked in this place, Please export all the cars first!", Alert.AlertType.ERROR);
        }
        
    }
    
//    public void countFreeSpaces(){
//
//        Connection conn = mysqlConnection.connectToDB();
//        PreparedStatement prst;
//        ResultSet rs;
//
//        String query = "SELECT COUNT(*) AS parked_cars FROM `emergent_parked` "
//                + "WHERE parking_id = (SELECT parking_id FROM `parking_spaces` WHERE description = ?)";
//
//        try {
//
//            prst = conn.prepareStatement(query);
//            prst.setString(1, description);
//            rs = prst.executeQuery();
//            
//            while (rs.next()) {
//                
//            }
//
//        } catch (SQLException e) {
//            alert.show("Error", "Den doulevei kala to common!", Alert.AlertType.ERROR);
//            System.out.println(e.getMessage());
//        }
//        
//    }

    public String getParking_id() {
        return parking_id;
    }

    public void setParking_id(String parking_id) {
        this.parking_id = parking_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }
    
}
