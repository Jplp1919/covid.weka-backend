package covid.weka;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Checker {

    int rowCount = 0;

    public int checkCount(Connection con) {
        //checa o numero de linhas na tabela pessoa
        try {
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM pessoa");
            // pega o numeto de linhas no result set
            rs.next();
            rowCount = rs.getInt(1);
            //System.out.println(rowCount);
        } catch (SQLException ex) {

            Logger.getLogger(Checker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowCount;
    }
}
