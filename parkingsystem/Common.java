package parkingsystem;

public class Common {
    
    public static boolean onlyDigits(String str, int n){ //check if String input has only numbers from 0 to 9
        
        for(int i=0; i<n; i++){
            if(str.charAt(i) >= '0' && str.charAt(i) <= '9'){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    
}
