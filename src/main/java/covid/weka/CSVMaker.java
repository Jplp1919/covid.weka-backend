package covid.weka;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CSVMaker {
     public void sqlToCSV(String filename, Connection con) {
         //popula um arquivo .csv com os dados do banco de dados
        try {
            Statement statement = con.createStatement();
            FileWriter fw = new FileWriter(filename + ".csv"); //cria um FileWriter
            
            ResultSet rs = statement.executeQuery("SELECT * FROM pessoa"); // pega os dados do banco de dados em um result set
            int cols = rs.getMetaData().getColumnCount();

            for (int i = 1; i <= cols; i++) { //popula o FileWriter com as colunas do bando de dados
                fw.append(rs.getMetaData().getColumnLabel(i)); 
                if (i < cols) {
                    fw.append(',');
                } else {
                    fw.append('\n');
                }

            }

            while (rs.next()) {
                for (int i = 1; i <= cols; i++) { //popula o FileWriter com as linhas do bando de dados
                    if (rs.getString(i) == null) { //se um campo foi deixado em branco, ? é colocado em seu lugar
                        fw.append("?"); 
                    } else {
                        fw.append(rs.getString(i));
                    }

                    if (i < cols) {
                        fw.append(',');
                    }
                }
                fw.append('\n');
            }

            fw.flush(); //escreve o documento utilizando os dados no FileWriter
            fw.close();
           

        } catch (IOException | SQLException e) {
          
            System.out.println(e.getMessage());
        }

    }
}
