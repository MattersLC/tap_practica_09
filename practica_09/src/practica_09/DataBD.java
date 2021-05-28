package practica_09;

import java.util.prefs.Preferences;

/**
 * @author Orlando Lucero
 */
public class DataBD {
    
    Preferences preferences = Preferences.userNodeForPackage(DataBD.class);
    
    public void setCredentials(String username, String password) { 
        preferences.put("db_username", username); 
        preferences.put("db_password", password); 
    }
    
    public String getUsername() { 
        return preferences.get("db_username", null); 
    } 
    
    public String getPassword() { 
        return preferences.get("db_password", null); 
    }

}
