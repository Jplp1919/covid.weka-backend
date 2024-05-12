package covid.weka;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

// A classe é marcada como um componente do Spring, o que permite que seja gerenciada pelo container do Spring e injetada em outras classes.
@Component
public class ConnectionFactory {

    // URL de conexão com o banco de dados, incluindo o timezone do servidor para evitar problemas com datas e horários.
    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/newmodeldb?useTimezone=true&serverTimezone=UTC";
    // Nome de usuário para acesso ao banco de dados.
    private static final String USERNAME = "root";
    // Senha para acesso ao banco de dados.
    private static final String PASSWORD = "root";

    // Método que tenta estabelecer uma conexão com o banco de dados e retorna essa conexão.
    public Connection establishConnection() {
        // Tenta criar uma conexão com o banco de dados utilizando os parâmetros definidos.
        try {
            return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            // Em caso de falha na tentativa de conexão, registra o erro com detalhes no log.
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Retorna null se a conexão não puder ser estabelecida, indicando que houve um erro.
        return null;
    }
}
