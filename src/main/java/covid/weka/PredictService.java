package covid.weka;

// Importações necessárias para manipulação de arquivos, conexão SQL, logging e o uso de modelos Weka.
import java.io.File;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

// Anotação que define esta classe como um serviço no contexto do Spring, permitindo sua injeção automática.
@Service
public class PredictService {
    // Método para realizar predição usando modelo de RNA (Rede Neural Artificial)
    public String[] predict(Connection con) throws Exception {
        // Cria uma instância de CSVMaker para converter dados SQL em CSV.
        CSVMaker csvMaker = new CSVMaker();
        // Define o nome do arquivo onde os dados CSV serão salvos.
        String filename = System.getProperty("user.dir") + "/person";
        // Executa a conversão de SQL para CSV.
        csvMaker.sqlToCSV(filename, con);

        // Cria uma instância de ArffMaker para converter CSV em ARFF (formato aceito pelo Weka).
        ArffMaker arffMaker = new ArffMaker();
        // Define o caminho dos arquivos de entrada e saída para a conversão.
        String input = System.getProperty("user.dir") + "/person.csv";
        String output = System.getProperty("user.dir") + "/person.arff";
        // Realiza a conversão de CSV para ARFF.
        arffMaker.csvToArff(input, output);

        // Define o caminho do modelo de RNA treinado.
        String modelPath = System.getProperty("user.dir") + "/RNA.model";

        try {
            // Carrega os dados ARFF para serem usados no modelo.
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(output));
            Instances dataset = loader.getDataSet();
            // Define qual atributo dos dados é a classe (target da predição).
            dataset.setClassIndex(dataset.numAttributes() - 1);

            // Carrega o modelo de RNA.
            MultilayerPerceptron rnaModel = (MultilayerPerceptron) weka.core.SerializationHelper.read(modelPath);
            // Pega a última instância do conjunto de dados, que será usada para fazer a predição.
            Instance lastInstance = dataset.lastInstance();
            // Obtém a distribuição de probabilidades da predição.
            double[] predictionDistribution = rnaModel.distributionForInstance(lastInstance);
            // Realiza a predição.
            double prediction = rnaModel.classifyInstance(lastInstance);
            // Converte o resultado e a porcentagem da classe prevista para String.
            String result = Double.toString(prediction);
            String percent = Double.toString(predictionDistribution[(int) prediction] * 100) + "%";
            // Retorna o resultado e a percentagem da predição.
            return new String[]{result, percent};
        } catch (Exception ex) {
            // Em caso de erro, registra no log.
            Logger.getLogger(PredictService.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Retorna null se houver falha durante o processo.
        return null;
    }
}
