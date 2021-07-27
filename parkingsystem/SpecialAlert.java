package parkingsystem;

import javafx.scene.control.Alert;

public class SpecialAlert {
    
    Alert alert = new Alert(Alert.AlertType.NONE);
    
    public void show(String title, String message, Alert.AlertType alertType) {
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.setAlertType(alertType);
        alert.showAndWait();
    }
    
}
