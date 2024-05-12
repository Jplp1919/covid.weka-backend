package covid.weka;

import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

// A classe é marcada como um serviço no Spring, permitindo sua injeção automática onde for necessário.
@Service
public class SavePredictionService {

    // Método que salva os resultados de uma predição no banco de dados.
    public void save(String prediction, String percentage, int id, Connection con) {
        // Comando SQL para inserir os resultados da predição na tabela 'prediction'.
        String sql = "INSERT INTO prediction (TemCovid, Porcentagem, idPessoa) VALUES (?, ?, ?);";
        try {
            // Prepara o comando SQL para execução, garantindo que sejam tratados de forma segura os valores a serem inseridos.
            PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Insere o resultado da predição no primeiro parâmetro do comando SQL.
            pstm.setString(1, prediction);
            // Insere a porcentagem da predição no segundo parâmetro do comando SQL.
            pstm.setString(2, percentage);
            // Insere o ID da pessoa associado à predição no terceiro parâmetro do comando SQL.
            pstm.setInt(3, id);
            // Executa o comando SQL.
            pstm.execute();
        } catch (SQLException e) {
            // Em caso de erro ao executar o comando SQL, imprime o erro no console.
            System.out.println(e);
        }
    }
}
