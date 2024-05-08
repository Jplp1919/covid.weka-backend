
package covid.weka;

import java.io.File;
import java.io.IOException;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;


public class ArffMaker {
 public void csvToArff(String source, String output) {
        //Converte .csv para .arff
        try {

            CSVLoader loader = new CSVLoader(); //cria um Csv loader
            loader.setSource(new File(source)); //coloca a fonte dele como o caminho para o csv
            Instances data = loader.getDataSet(); //cria um objeto instances com o csv carregado pelo loader

            data.deleteAttributeAt(0);

            int attributeIndex = data.attribute("ValorQualitativo").index(); //index do atributo "ValorQualitativo"

            NumericToNominal numericToNominal = new NumericToNominal();//cria um filtro que muda numericos para numerais
            numericToNominal.setAttributeIndices("" + (attributeIndex + 1)); // +1 because Weka uses 1-based indexing

            numericToNominal.setInputFormat(data); // escolhe o dataset a ser filtrado
            Instances newData = Filter.useFilter(data, numericToNominal); // Aplica o filtro a o dataset

            ArffSaver saver = new ArffSaver();
            saver.setInstances(newData); // escolhe o dataset para ser convertido

            File f = new File(output); // checa se o objeto File output existe, se sim, o deleta
            if (f.exists()) {
                f.delete();
            }

            saver.setFile(new File(output)); //esccolhe o output
            saver.writeBatch(); //escreve o arquivo

        } catch (IOException e) {

            System.out.println(e);
        } catch (Exception e) {
            System.out.println("Erro inesperado");
            System.out.println(e.getMessage());

        }
    }   
}
