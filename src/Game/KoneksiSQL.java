
package Game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KoneksiSQL {
    
    private Connection connect;
    
    private String driverName = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/pbo";
    private String username = "root";
    private String password = "";
    
    public Connection getKoneksiSQL(){
        if(connect == null) {
            try {
                Class.forName(driverName);
                System.out.println("Classs driver ditemukan");
                
                connect = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(KoneksiSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return connect;
    }
}
