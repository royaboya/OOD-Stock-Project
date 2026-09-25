package model;

import java.util.Map;

/**
 * Represents a portfolio for our stock program. This defines the methods to add stocks and get the
 * value of a specified date for a portfolio.
 */
public interface IPortfolio {

  /**
   * Gets the map of stocks where the ticker symbol and quantity of the stock are.
   *
   * @return map of the stocks contained in the portfolio
   */
  Map<IStock, Double> getStocks();


  /**
   * inds the number of stocks with the given ticker Symbol and date in this portfolio.
   * @param tickerSymbol the ticker Symbol of the stock
   * @param purchaseDate the date the stock was purchased
   * @return the count of stock shares of the specified stock
   */
  double stockQuantity(String tickerSymbol, String purchaseDate);
}