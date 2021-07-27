package parkingsystem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import parkingsystem.SpecialAlert;

public class mysqlConnection implements DBInfo{

    static Connection conn = null;
    static SpecialAlert alert = new SpecialAlert();
    
    public static Connection connectToDB(){
              
        try{  
            conn = DriverManager.getConnection(DB_NAME_WITH_ENCODING, USER, PASSWORD);
            return conn;   
        } catch(SQLException ex){ 
            alert.show("Error", "Could not connect to database, Unknown error!", Alert.AlertType.ERROR);
            return null;
        }
        
    }
    
}
