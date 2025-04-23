package stock.ml;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class stockController {
    @Autowired
    private final stockService stockRiskService;

    public stockController(stockService stockRiskService) {
        this.stockRiskService = stockRiskService;
    }

    @GetMapping("/predict")
    public String predictRisk(@RequestParam String ticker) {
        try {
            return stockRiskService.predictStockRisk(ticker);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}