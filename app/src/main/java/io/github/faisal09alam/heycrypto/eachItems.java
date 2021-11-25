package io.github.faisal09alam.heycrypto;

public class eachItems {
    private double price;
    private String coinName;
    private String baseCoinName;
    private String coinVolume;
    private double percentageChange24H;

    public double getPercentageChange24H() {
        return percentageChange24H;
    }

    public void setPercentageChange24H(double percentageChange24H) {
        this.percentageChange24H = percentageChange24H;
    }

    public String getCoinVolume() {
        return coinVolume;
    }

    public void setCoinVolume(String coinVolume) {
        this.coinVolume = coinVolume;
    }

    public String getBaseCoinName() {
        return baseCoinName;
    }

    public void setBaseCoinName(String baseCoinName) {
        this.baseCoinName = baseCoinName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public eachItems( String name , double price , String baseCoinName , String coinVolume , double percentageChange24H) {
        this.coinName = name;
        this.price = price;
        this.baseCoinName = baseCoinName;
        this.coinVolume = coinVolume;
        this.percentageChange24H = percentageChange24H;
    }
}
