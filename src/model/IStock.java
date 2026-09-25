package model;

/**
 * Represents a stock for our stock program. This defines the methods to get the closing price of a
 * stock and creating a stock object from CVS file stock data.
 */
public interface IStock {

  /**
   * Creates a stock object from a stock's ticker symbol and data.
   *
   * @param stockData String containing stock information
   * @return Stock object created from the provided data
   */
  Stock createStockFromString(String stockData, String tickerSymbol);

  /**
   * Gets the closing price of a stock.
   *
   * @return closing price of a stock
   */
  double getClosingPrice();

  /**
   * Gets the ticker symbol of a stock.
   *
   * @return ticker symbol of a stock
   */
  String getTickerSymbol();

  /**
   * Gets the date purchased of a stock.
   *
   * @return date purchased of a stock
   */
  String getDatePurchased();

  /**
   * Gets the opening price of a stock.
   *
   * @return opening price of a stock
   */
  double getOpeningPrice();

  /**
   * Gets the highest price of a stock.
   *
   * @return highest price of a stock
   */
  double getHigh();

  /**
   * Gets the lowest price of a stock.
   *
   * @return lowest price of a stock
   */
  double getLow();

  /**
   * Gets the volume of a stock.
   *
   * @return volume of a stock
   */
  int getVolume();
}