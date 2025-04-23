package mistake.trade.Entity;

import java.time.LocalDateTime;

public class Trade {
    private String ticker;
    private double entryPrice;
    private double exitPrice;
    private double rsi;
    private double macd;
    private double sma;
    private boolean isMistake;
    private LocalDateTime tradeTime; // New field

    public Trade(String ticker, double entryPrice, double exitPrice, double rsi, double macd, double sma, LocalDateTime tradeTime) {
        this.ticker = ticker;
        this.entryPrice = entryPrice;
        this.exitPrice = exitPrice;
        this.rsi = rsi;
        this.macd = macd;
        this.sma = sma;
        this.tradeTime = tradeTime;
    }

    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }
    public double getEntryPrice() { return entryPrice; }
    public void setEntryPrice(double entryPrice) { this.entryPrice = entryPrice; }
    public double getExitPrice() { return exitPrice; }
    public void setExitPrice(double exitPrice) { this.exitPrice = exitPrice; }
    public double getRsi() { return rsi; }
    public void setRsi(double rsi) { this.rsi = rsi; }
    public double getMacd() { return macd; }
    public void setMacd(double macd) { this.macd = macd; }
    public double getSma() { return sma; }
    public void setSma(double sma) { this.sma = sma; }
    public boolean isMistake() { return isMistake; }
    public void setMistake(boolean isMistake) { this.isMistake = isMistake; }
    public LocalDateTime getTradeTime() { return tradeTime; }
    public void setTradeTime(LocalDateTime tradeTime) { this.tradeTime = tradeTime; }
}
