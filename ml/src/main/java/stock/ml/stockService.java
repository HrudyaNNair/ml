// package stock.ml;

// import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
// import org.deeplearning4j.nn.conf.layers.DenseLayer;
// import org.deeplearning4j.nn.conf.layers.OutputLayer;
// import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
// import org.deeplearning4j.nn.weights.WeightInit;
// import org.nd4j.linalg.activations.Activation;
// import org.nd4j.linalg.api.ndarray.INDArray;
// import org.nd4j.linalg.factory.Nd4j;
// import org.nd4j.linalg.lossfunctions.LossFunctions;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// @Service
// public class stockService {

//     private final MultiLayerNetwork model;
//     private final RestTemplate restTemplate = new RestTemplate();
//     private final ObjectMapper objectMapper = new ObjectMapper();

//     @Value("${alphavantage.api.key}")
//     private String apiKey;

//     @Value("${alphavantage.api.url}")
//     private String apiUrl;

//     public stockService() {
//         model = new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
//                 .seed(123)
//                 .weightInit(WeightInit.XAVIER)
//                 .list()
//                 .layer(0, new DenseLayer.Builder().nIn(5).nOut(10).activation(Activation.RELU).build())
//                 .layer(1, new DenseLayer.Builder().nIn(10).nOut(10).activation(Activation.RELU).build())
//                 .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.MCXENT)
//                         .nIn(10).nOut(3).activation(Activation.SOFTMAX).build())
//                 .build());
//         model.init();
//     }

//     public String predictStockRisk(String ticker) {
//         try {
//             String function = "OVERVIEW";
//             String url = apiUrl + "?function=" + function + "&symbol=" + ticker + "&apikey=" + apiKey;

//             String response = restTemplate.getForObject(url, String.class);
//             JsonNode root = objectMapper.readTree(response);

//             double price = Double.parseDouble(root.get("PreviousClose").asText("0"));
//             double peRatio = Double.parseDouble(root.get("PERatio").asText("0"));
//             double eps = Double.parseDouble(root.get("EPS").asText("0"));
//             double yearHigh = Double.parseDouble(root.get("52WeekHigh").asText("0"));
//             double yearLow = Double.parseDouble(root.get("52WeekLow").asText("0"));

//             INDArray input = Nd4j.create(new double[]{price, peRatio, eps, yearHigh, yearLow}, 1, 5);
//             INDArray output = model.output(input);
//             int predictedClass = Nd4j.argMax(output, 1).getInt(0);
//             String[] riskLevels = {"Low", "Medium", "High"};
//             return "Predicted Risk Level: " + riskLevels[predictedClass];

//         } catch (Exception e) {
//             return "Error fetching data from Alpha Vantage: " + e.getMessage();
//         }
//     }
// }
package stock.ml;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class stockService {

    private final MultiLayerNetwork model;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${alphavantage.api.key}")
    private String apiKey;

    @Value("${alphavantage.api.url}")
    private String apiUrl;

    public stockService() {
        model = new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
                .seed(123)
                .weightInit(WeightInit.XAVIER)
                .list()
                .layer(0, new DenseLayer.Builder().nIn(5).nOut(10).activation(Activation.RELU).build())
                .layer(1, new DenseLayer.Builder().nIn(10).nOut(10).activation(Activation.RELU).build())
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.MCXENT)
                        .nIn(10).nOut(3).activation(Activation.SOFTMAX).build())
                .build());
        model.init();
    }

    public String predictStockRisk(String ticker) {
        try {
            String function = "OVERVIEW";
            String url = apiUrl + "?function=" + function + "&symbol=" + ticker + "&apikey=" + apiKey;

            String response = restTemplate.getForObject(url, String.class);
            System.out.println("API Response: " + response); // Debug line

            JsonNode root = objectMapper.readTree(response);

            double price = parseDoubleOrDefault(root, "PreviousClose", 0.0);
            double peRatio = parseDoubleOrDefault(root, "PERatio", 0.0);
            double eps = parseDoubleOrDefault(root, "EPS", 0.0);
            double yearHigh = parseDoubleOrDefault(root, "52WeekHigh", 0.0);
            double yearLow = parseDoubleOrDefault(root, "52WeekLow", 0.0);

            INDArray input = Nd4j.create(new double[]{price, peRatio, eps, yearHigh, yearLow}, 1, 5);
            INDArray output = model.output(input);
            int predictedClass = Nd4j.argMax(output, 1).getInt(0);

            String[] riskLevels = {"Low", "Medium", "High"};
            return "Predicted Risk Level: " + riskLevels[predictedClass];

        } catch (Exception e) {
            return "Error fetching data from Alpha Vantage: " + e.getMessage();
        }
    }

    private double parseDoubleOrDefault(JsonNode root, String key, double defaultValue) {
        JsonNode node = root.get(key);
        if (node == null || node.asText().isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(node.asText());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}

