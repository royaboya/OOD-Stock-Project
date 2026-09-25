package model;

/**
 * Represents a stock in the model of the MVC design pattern and methods to analyze stocks and
 * create stock portfolios.
 */
public class Stock implements IStock {

  private String tickerSymbol;
  private String datePurchased;
  private double openingPrice;
  private double high;
  private double low;
  private double closingPrice;
  private int volume;

  /**
   * Creates a Stock object using a ticker symbol and the date purchased.
   *
   * @param tickerSymbol  ticker symbol of the stock
   * @param datePurchased date the stock was purchased
   */
  protected Stock(String tickerSymbol, String datePurchased) {
    this.tickerSymbol = tickerSymbol;
    this.datePurchased = datePurchased;
  }

  private Stock(String tS, String datePurchased, double openingPrice,
                double high, double low, double closingPrice, int volume) {
    this.tickerSymbol = tS;
    this.datePurchased = datePurchased;
    this.openingPrice = openingPrice;
    this.high = high;
    this.low = low;
    this.closingPrice = closingPrice;
    this.volume = volume;
  }

  /**
   * Default constructor for the Stock class.
   */
  public Stock() {
    // Default empty constructor
  }

  @Override
  public Stock createStockFromString(String stockData, String tickerSymbol) {
    String[] stockInfo = stockData.split(",");
    String datePurchased = stockInfo[0];
    double openingPrice = Double.parseDouble(stockInfo[1]);
    double high = Double.parseDouble(stockInfo[2]);
    double low = Double.parseDouble(stockInfo[3]);
    double closingPrice = Double.parseDouble(stockInfo[4]);
    int volume = Integer.parseInt(stockInfo[5]);
    return new Stock(tickerSymbol, datePurchased, openingPrice, high, low, closingPrice, volume);
  }

  @Override
  public double getClosingPrice() {
    return closingPrice;
  }

  @Override
  public String getTickerSymbol() {
    return tickerSymbol;
  }

  @Override
  public String getDatePurchased() {
    return datePurchased;
  }

  @Override
  public double getHigh() {
    return high;
  }

  @Override
  public double getLow() {
    return low;
  }

  @Override
  public double getOpeningPrice() {
    return openingPrice;
  }

  @Override
  public int getVolume() {
    return volume;
  }
}