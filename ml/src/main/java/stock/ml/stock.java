package stock.ml;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "stock_data")
public class stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticker;
    private double price;
    private double peRatio; // Price-to-Earnings Ratio
    private double eps; // Earnings Per Share
    private double yearHigh;
    private double yearLow;

    // Default Constructor
    public stock() {
    }

    // Constructor
    public stock(String ticker, double price, double peRatio, double eps, double yearHigh, double yearLow) {
        this.ticker = ticker;
        this.price = price;
        this.peRatio = peRatio;
        this.eps = eps;
        this.yearHigh = yearHigh;
        this.yearLow = yearLow;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPeRatio() {
        return peRatio;
    }

    public void setPeRatio(double peRatio) {
        this.peRatio = peRatio;
    }

    public double getEps() {
        return eps;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public double getYearHigh() {
        return yearHigh;
    }

    public void setYearHigh(double yearHigh) {
        this.yearHigh = yearHigh;
    }

    public double getYearLow() {
        return yearLow;
    }

    public void setYearLow(double yearLow) {
        this.yearLow = yearLow;
    }
}




