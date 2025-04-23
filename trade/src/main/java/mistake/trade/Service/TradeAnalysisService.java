package mistake.trade.Service;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import mistake.trade.Entity.Trade;
// import mistake.trade.Repository.TradeRepository;

// @Service
// public class TradeAnalysisService {

//     @Autowired
//     private TradeRepository tradeRepository;

//     public String analyzeTradeMistake(Trade trade) {
//         // Here is where you would call Alpha Vantage API to get historical prices
//         // For now, we simulate with dummy logic

//         double entry = trade.getEntryPrice();
//         double exit = trade.getExitPrice();
//         double rsi = trade.getRsi();
//         double macd = trade.getMacd();
//         double sma = trade.getSma();

//         // Simulated mistake check logic
//         if (rsi > 70 && exit < entry) {
//             trade.setMistake(true);
//             return "Mistake: Overbought stock at high price.";
//         } else if (macd < 0 && exit < entry) {
//             trade.setMistake(true);
//             return "Mistake: Bearish MACD trend.";
//         } else if (sma > entry && exit < entry) {
//             trade.setMistake(true);
//             return "Mistake: SMA was bullish, but you sold too soon.";
//         }

//         trade.setMistake(false);
//         return "Optimal trade based on indicators.";
//     }

//     public String estimateProfitLoss(Trade trade) {
//         double entry = trade.getEntryPrice();
//         double exit = trade.getExitPrice();
//         double profit = exit - entry;

//         double probability = Math.random();

//         if (profit > 0 && probability > 0.6) return "High Probability of Profit";
//         if (profit < 0 && probability > 0.6) return "High Probability of Loss";
//         return "Uncertain Probability";
//     }

//     public void saveTrade(Trade trade) {
//         tradeRepository.saveTrade(trade);
//     }

//     public List<Trade> getAllTrades() {
//         return tradeRepository.getAllTrades();
//     }
// }


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mistake.trade.Entity.Trade;
import mistake.trade.Repository.TradeRepository;

@Service
public class TradeAnalysisService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private IndicatorService indicatorService;

    public String analyzeTradeMistake(Trade trade) {
        double entryPrice = trade.getEntryPrice();
        double exitPrice = trade.getExitPrice();
        String ticker = trade.getTicker();

        double rsi = indicatorService.getRSI(ticker);
        double macd = indicatorService.getMACD(ticker);
        double sma = indicatorService.getSMA(ticker);

        trade.setRsi(rsi);
        trade.setMacd(macd);
        trade.setSma(sma);

        if (rsi > 70 && exitPrice < entryPrice) {
            trade.setMistake(true);
            return "Mistake: Overbought stock at high price.";
        } else if (macd < 0 && exitPrice < entryPrice) {
            trade.setMistake(true);
            return "Mistake: Bearish market trend when exiting.";
        } else if (sma > entryPrice && exitPrice < entryPrice) {
            trade.setMistake(true);
            return "Mistake: Exiting at a lower price when trend was positive.";
        } else {
            trade.setMistake(false);
            return "Optimal Trade.";
        }
    }

    public String estimateProfitLoss(Trade trade) {
        double entryPrice = trade.getEntryPrice();
        double predictedPrice = trade.getExitPrice();

        double predictedChange = predictedPrice - entryPrice;
        double probability = Math.random();

        if (predictedChange > 0 && probability > 0.6) {
            return "High Probability of Profit";
        } else if (predictedChange < 0 && probability > 0.6) {
            return "High Probability of Loss";
        } else {
            return "Uncertain Probability";
        }
    }

    public void saveTrade(Trade trade) {
        tradeRepository.saveTrade(trade);
    }

    public List<Trade> getAllTrades() {
        return tradeRepository.getAllTrades();
    }
}
