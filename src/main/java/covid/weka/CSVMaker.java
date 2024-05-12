package covid.weka;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CSVMaker {
    // Método que converte dados de uma tabela SQL para um arquivo CSV.
    public void sqlToCSV(String filename, Connection con) {
        // Tenta executar o processo de conversão de SQL para CSV.
        try {
            // Cria um objeto Statement para poder executar consultas SQL.
            Statement statement = con.createStatement();
            // Cria um FileWriter para escrever os dados em um arquivo CSV, utilizando o nome fornecido.
            FileWriter fw = new FileWriter(filename + ".csv");

            // Executa uma consulta SQL para obter todos os dados da tabela 'pessoa'.
            ResultSet rs = statement.executeQuery("SELECT * FROM pessoa");
            // Obtém o número de colunas do ResultSet.
            int cols = rs.getMetaData().getColumnCount();

            // Escreve o nome de cada coluna no arquivo CSV, separados por vírgula.
            for (int i = 1; i <= cols; i++) {
                fw.append(rs.getMetaData().getColumnLabel(i));
                if (i < cols) {
                    fw.append(','); // Adiciona uma vírgula se não for a última coluna.
                } else {
                    fw.append('\n'); // Adiciona uma quebra de linha após a última coluna.
                }
            }

            // Escreve cada linha de dados do ResultSet no arquivo CSV.
            while (rs.next()) {
                for (int i = 1; i <= cols; i++) {
                    if (rs.getString(i) == null) {
                        // Insere "?" se o campo for nulo.
                        fw.append("?");
                    } else {
                        // Escreve o dado da coluna.
                        fw.append(rs.getString(i));
                    }
                    if (i < cols) {
                        fw.append(','); // Adiciona uma vírgula se não for a última coluna.
                    }
                }
                fw.append('\n'); // Adiciona uma quebra de linha após cada linha de dados.
            }

            // Força a escrita de todos os dados no arquivo e fecha o FileWriter.
            fw.flush();
            fw.close();

        } catch (IOException | SQLException e) {
            // Em caso de erro, imprime a mensagem de erro no console.
            System.out.println(e.getMessage());
        }
    }
}
