package parkingsystem.objects;

import parkingsystem.database.mysqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import parkingsystem.SpecialAlert;

public class User {
    
    private String userID;
    private String username;
    private String password;
    SpecialAlert alert = new SpecialAlert();
    
    public User(){} //empty constructor
    
    public User(String userID, String username, String password){ //constructor that gets two data
        
        this.userID = userID;
        this.username = username;
        this.password = password;
        
    }
    
    //Setters and Getters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void addUser(){
        
        Connection conn = mysqlConnection.connectToDB();
        PreparedStatement prst;

        String query = "INSERT INTO users (user_name, user_password) VALUES (?, ?)";

        try {

            prst = conn.prepareStatement(query);
            prst.setString(1, username);
            prst.setString(2, password);
            prst.executeUpdate();
            
            alert.show("Success", "User was created successfully!", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            alert.show("Error", "Unknown error occurred!", Alert.AlertType.ERROR);
        }
        
    }
    
    public void deleteUser(){
        
        Connection conn = mysqlConnection.connectToDB();
        String query = "DELETE FROM users WHERE user_id = ?";
        PreparedStatement prst;

        try {
            prst = conn.prepareStatement(query);
            prst.setString(1, userID);
            prst.execute();
            alert.show("Success", "User deleted!", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            alert.show("Error", "Unkown error occured!", Alert.AlertType.ERROR);
        }
        
    }
    
    //method that checks if users input are the same with the data from database
    public boolean checkUser(){
        
        Connection conn = mysqlConnection.connectToDB();
        ResultSet rs = null;
        PreparedStatement prst = null;
        
        String query = "SELECT * FROM users WHERE user_name = ? AND user_password = ? "; //store select query to a string 
        
        try{
            prst = conn.prepareStatement(query); //prepare the query
            prst.setString(1, username); //set to the first ? the username
            prst.setString(2, password); //set to the second ? the password
            rs = prst.executeQuery(); //execute the query
            if(rs.next()){ //if the selection gives data that means the inputs are correct
                return true; //so return true
            } else {
                return false; //else return false
            }
        } catch(Exception e) {
            alert.show("Error", "Could not login, Unknown error!", Alert.AlertType.ERROR);
            return false;
        }
        
    }
    
}
