package mistake.trade.Service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IndicatorService {

    @Value("${alphavantage.api.key}")
    private String apiKey;

    @Value("${alphavantage.api.url}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    public double getRSI(String symbol) {
        try {
            String url = baseUrl + "?function=RSI&symbol=" + symbol + "&interval=daily&time_period=14&series_type=close&apikey=" + apiKey;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            System.out.println(response.getBody()); // Debug output
            JSONObject json = new JSONObject(response.getBody());

            JSONObject rsiData = json.getJSONObject("Technical Analysis: RSI");
            String latestDate = rsiData.keys().next();
            return rsiData.getJSONObject(latestDate).getDouble("RSI");
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public double getMACD(String symbol) {
        try {
            String url = baseUrl + "?function=MACD&symbol=" + symbol + "&interval=daily&series_type=close&apikey=" + apiKey;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            System.out.println(response.getBody()); // Debug output
            JSONObject json = new JSONObject(response.getBody());

            JSONObject macdData = json.getJSONObject("Technical Analysis: MACD");
            String latestDate = macdData.keys().next();
            return macdData.getJSONObject(latestDate).getDouble("MACD");
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public double getSMA(String symbol) {
        try {
            String url = baseUrl + "?function=SMA&symbol=" + symbol + "&interval=daily&time_period=20&series_type=close&apikey=" + apiKey;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            System.out.println(response.getBody()); // Debug output
            JSONObject json = new JSONObject(response.getBody());

            JSONObject smaData = json.getJSONObject("Technical Analysis: SMA");
            String latestDate = smaData.keys().next();
            return smaData.getJSONObject(latestDate).getDouble("SMA");
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
