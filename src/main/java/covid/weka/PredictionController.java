package covid.weka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;

@RestController
@RequestMapping("/api")
public class PredictionController {

    @Autowired
    private PredictService predictService;

    @Autowired
    private SavePredictionService savePredictionService;

    @Autowired
    private ConnectionFactory connectionFactory;

    @PostMapping("/predict")
    public PredictionResponse predict(@RequestBody PredictionRequest predictionRequest) throws Exception {
        Connection con = connectionFactory.establishConnection();
        String[] results = predictService.predict(con);
        if (results != null) {
            savePredictionService.save(results[0], results[1], predictionRequest.getId(), con);
            return new PredictionResponse(results[0], results[1], predictionRequest.getId());
        } else {
            throw new RuntimeException("Prediction failed");
        }
    }
}

class PredictionRequest {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

class PredictionResponse {
    private String prediction;
    private String percentage;
    private int id;

    public PredictionResponse(String prediction, String percentage, int id) {
        this.prediction = prediction;
        this.percentage = percentage;
        this.id = id;
    }

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
