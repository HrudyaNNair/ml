package mistake.trade.Controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mistake.trade.Entity.Trade;
import mistake.trade.Service.TradeAnalysisService;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    @Autowired
    private TradeAnalysisService tradeAnalysisService;

    // Endpoint to analyze a single trade
    @PostMapping("/analyze")
    public String analyzeTrade(@RequestBody Trade trade) {
        return tradeAnalysisService.analyzeTradeMistake(trade);
    }

    // Endpoint to estimate profit or loss for a trade
    @PostMapping("/estimate")
    public String estimateProfitLoss(@RequestBody Trade trade) {
        return tradeAnalysisService.estimateProfitLoss(trade);
    }

    // Endpoint to save a trade
    @PostMapping("/save")
    public void saveTrade(@RequestBody Trade trade) {
        tradeAnalysisService.saveTrade(trade);
    }

    // Endpoint to get all saved trades
    @GetMapping("/all")
    public List<Trade> getAllTrades() {
        return tradeAnalysisService.getAllTrades();
    }
}

