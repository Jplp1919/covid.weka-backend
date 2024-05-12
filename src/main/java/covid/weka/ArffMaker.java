package covid.weka;

import java.io.File;
import java.io.IOException;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class ArffMaker {
    // Método que converte arquivos de formato .csv para .arff
    public void csvToArff(String source, String output) {
        try {
            // Cria uma instância de CSVLoader para carregar os dados do arquivo CSV.
            CSVLoader loader = new CSVLoader();
            // Define o arquivo CSV de origem a ser carregado.
            loader.setSource(new File(source));
            // Carrega os dados do CSV e os armazena em um objeto Instances.
            Instances data = loader.getDataSet();

            // Remove o primeiro atributo dos dados, geralmente utilizado para remover identificadores.
            data.deleteAttributeAt(0);

            // Localiza o índice do atributo 'ValorQualitativo'.
            int attributeIndex = data.attribute("ValorQualitativo").index();

            // Cria um filtro NumericToNominal, que converte atributos numéricos para nominais.
            NumericToNominal numericToNominal = new NumericToNominal();
            // Configura o filtro para aplicar a conversão apenas no atributo 'ValorQualitativo'.
            numericToNominal.setAttributeIndices("" + (attributeIndex + 1)); // +1 porque Weka usa indexação baseada em 1.

            // Define o formato de entrada do filtro com base nos dados carregados.
            numericToNominal.setInputFormat(data);
            // Aplica o filtro e armazena os dados modificados em um novo objeto Instances.
            Instances newData = Filter.useFilter(data, numericToNominal);

            // Cria uma instância de ArffSaver para salvar os dados no formato ARFF.
            ArffSaver saver = new ArffSaver();
            // Define os dados a serem salvos.
            saver.setInstances(newData);

            // Cria um objeto File baseado no caminho de saída e verifica se ele já existe.
            File f = new File(output);
            if (f.exists()) {
                f.delete(); // Se existir, deleta o arquivo antigo.
            }

            // Configura o arquivo de saída para o saver.
            saver.setFile(new File(output));
            // Escreve os dados no arquivo no formato ARFF.
            saver.writeBatch();

        } catch (IOException e) {
            // Captura e imprime exceções relacionadas a problemas de I/O.
            System.out.println(e);
        } catch (Exception e) {
            // Captura e imprime outras exceções inesperadas.
            System.out.println("Erro inesperado");
            System.out.println(e.getMessage());
        }
    }   
}
