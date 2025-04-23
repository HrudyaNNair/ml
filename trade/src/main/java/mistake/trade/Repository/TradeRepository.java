package mistake.trade.Repository;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mistake.trade.Entity.Trade;

@Repository
public class TradeRepository {
    private List<Trade> trades = new ArrayList<>();

    public void saveTrade(Trade trade) {
        trades.add(trade);
    }

    public List<Trade> getAllTrades() {
        return trades;
    }
} 