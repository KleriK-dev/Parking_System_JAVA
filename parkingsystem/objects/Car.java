package parkingsystem.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import parkingsystem.SpecialAlert;
import parkingsystem.database.mysqlConnection;

public class Car {
    
    private String id;
    private String plate;
    private String brand;
    /*
    owner_id will get customer's id when data will be loaded in carsTab table
    and it will get customer's name when a car is inserted
    */
    private String owner_id; 
    SpecialAlert alert = new SpecialAlert();
    
    public Car(){}
    
    public Car(String id, String plate, String brand, String owner_id){
        
        this.id = id;
        this.plate = plate;
        this.brand = brand;
        this.owner_id = owner_id;
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }
    
    public void addCar(){
        
        Connection conn = mysqlConnection.connectToDB();
        PreparedStatement prst;

        String query = "INSERT INTO `cars` (car_plate, car_brand, customer_id)"
                + "SELECT ?, ?, customer_id " //select user's input and customer_id
                + "FROM `customers` " //from customers
                + "WHERE customer_id = (SELECT customer_id FROM `customers` WHERE full_name = ?)"; //where id is equal with the id of the customer name that the user had input

        try {

            prst = conn.prepareStatement(query);
            prst.setString(1, plate);
            prst.setString(2, brand);
            prst.setString(3, owner_id);
            prst.executeUpdate();
            
            alert.show("Success", "Car was inserted successfully!", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            alert.show("Error", "Unknown error occurred!", Alert.AlertType.ERROR);
        }
        
    }
    
    public void deleteCar(){
        
        Connection conn = mysqlConnection.connectToDB();
        
        String query = "DELETE FROM cars WHERE car_id = ?";
        PreparedStatement prst;

        try {
            prst = conn.prepareStatement(query);
            prst.setString(1, id);
            prst.execute();
            alert.show("Success", "Car deleted!", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            alert.show("Error", "Car is parked, export it from regular parkings and then delete it!", Alert.AlertType.ERROR);
        }
        
    }
    
}
