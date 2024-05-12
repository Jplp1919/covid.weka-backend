package covid.weka;

// Importações necessárias para usar Spring Boot, anotações e conexão SQL
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.Connection;

// Define a classe como um controlador REST, respondendo a chamadas HTTP na rota "/api"
@RestController
@RequestMapping("/api")
public class PredictionController {

    // Injeção de dependências para os serviços utilizados
    @Autowired
    private PredictService predictService;

    @Autowired
    private SavePredictionService savePredictionService;

    @Autowired
    private ConnectionFactory connectionFactory;

    // Método que lida com POST requests em "/api/predict", esperando um corpo de requisição do tipo PredictionRequest
    @PostMapping("/predict")
    public PredictionResponse predict(@RequestBody PredictionRequest predictionRequest) throws Exception {
        // Estabelece uma conexão com o banco de dados
        Connection con = connectionFactory.establishConnection();
        // Chama o serviço de predição passando a conexão e recebe os resultados
        String[] results = predictService.predict(con);
        if (results != null) {
            // Se houver resultados, salva a predição usando outro serviço e retorna uma resposta
            savePredictionService.save(results[0], results[1], predictionRequest.getId(), con);
            return new PredictionResponse(results[0], results[1], predictionRequest.getId());
        } else {
            // Se não houver resultados, lança uma exceção indicando falha na predição
            throw new RuntimeException("Prediction failed");
        }
    }
}

// Classe que define a estrutura de dados para uma requisição de predição. 
// Esta classe é utilizada para capturar e manipular os dados necessários para realizar uma predição.
class PredictionRequest {
    // Atributo privado que armazena o identificador da requisição. O uso de 'private' garante o encapsulamento dos dados.
    private int id;

    // Método público para obter o valor do identificador. 
    // Permite que outras classes acessem o valor de 'id' de maneira controlada.
    public int getId() {
        return id;
    }

    // Método público para definir o valor do identificador.
    // Permite a alteração do valor de 'id' por outras classes, garantindo que o encapsulamento seja mantido.
    public void setId(int id) {
        this.id = id;
    }
}

// Classe que define a estrutura de dados para a resposta de uma predição.
// Esta classe é usada para formatar e retornar os resultados da predição ao usuário ou sistema cliente.
class PredictionResponse {
    // Atributos privados que armazenam os resultados da predição e o identificador associado.
    private String prediction;  // Guarda o resultado da predição.
    private String percentage;  // Guarda a porcentagem ou probabilidade da predição.
    private int id;             // Guarda o identificador da requisição, associando a resposta à sua respectiva requisição.

    // Construtor que inicializa os valores dos atributos quando uma instância é criada.
    // Recebe os resultados da predição e o identificador, configurando o estado inicial do objeto.
    public PredictionResponse(String prediction, String percentage, int id) {
        this.prediction = prediction;
        this.percentage = percentage;
        this.id = id;
    }

    // Métodos públicos para acessar os dados da resposta.
    // Cada método retorna o valor de um dos atributos, permitindo que outras classes acessem esses dados.
    public String getPrediction() {
        return prediction;
    }

    public String getPercentage() {
        return percentage;
    }

    public int getId() {
        return id;
    }
}
