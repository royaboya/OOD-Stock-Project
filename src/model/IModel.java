package model;

/**
 * Represents a model in the MVC design pattern. This defines the methods to analyze stocks and
 * create stock portfolios.
 */
public interface IModel {

  /**
   * Examine the gain or loss of a stock over a specified period.
   *
   * @param tickerSymbol stock's stock symbol
   * @param startDate    start date of the desired stock to track
   * @param endDate      end date of the desired stock to track
   * @return The change of a stock
   */
  String evaluateChange(String tickerSymbol, String startDate, String endDate);

  /**
   * Evaluates the x-day moving average of a stock for a specified date.
   *
   * @param tickerSymbol stock's stock symbol
   * @param date         date to calculate the moving average
   * @param x            number of days for the moving average
   * @return The x-day moving average of the stock
   */
  String evaluateMovingAverage(String tickerSymbol, String date, int x);

  /**
   * Determines x-day crossovers for a stock within a specified period.
   *
   * @param tickerSymbol stock's stock symbol
   * @param startDate    start date of the analysis period
   * @param endDate      end date of the analysis period
   * @param x            number of days for the moving average
   * @return A list of crossovers within the specified period
   */
  String determineCrossover(String tickerSymbol, String startDate, String endDate, int x);

  /**
   * Creates a stock portfolio with the given name.
   *
   * @param name name of the portfolio
   */
  void createPortfolio(String name);

  /**
   * Adds a quantity of stock to the portfolio.
   *
   * @param name          name of the portfolio to be added to
   * @param tickerSymbol  a String ticker symbol
   * @param quantity      quantity of stocks to buy
   * @param datePurchased date when the stock was purchased
   */
  void addStock(String name, String tickerSymbol, double quantity, String datePurchased);


  /**
   * Removes the specified stock from the portfolio given the date purchased.
   *
   * @param name          name of the portfolio to be removed from
   * @param tickerSymbol  a String ticker symbol
   * @param quantity      quantity of stocks to sell
   * @param datePurchased date when the stock was purchased
   */
  void removeStock(String name, String tickerSymbol, double quantity, String datePurchased);


  /**
   * Returns a message of the list of the Portfolios already made.
   *
   * @return a String message of the available portfolios with their contents or a no portfolios
   *         message if no portfolios were made
   */
  String listPortfoliosAndContents();

  /**
   * Returns the composition of a portfolio at a specific date that can change depending on when the
   * stock was bought.
   *
   * @return Composition of portfolio with the list of stocks and their quantities
   */
  String determineComposition(String portfolioName, String date);

  /**
   * Checks if a given ticker symbol is valid.
   *
   * @param tickerSymbol stock's stock symbol
   * @return True if the ticker symbol is valid, false otherwise
   */
  boolean validTS(String tickerSymbol);

  /**
   * Checks if a given date is valid.
   *
   * @param tickerSymbol stock's stock symbol
   * @param date         date to check
   * @return True if the date is valid for the given ticker symbol, false otherwise
   */
  boolean validDate(String tickerSymbol, String date);

  /**
   * Checks if a given date is valid for evaluating a portfolio but not accounting for stock market
   * days.
   *
   * @param date date to check
   * @return True if the date is valid, false otherwise
   */
  boolean validPortfolioDate(String date);

  /**
   * Checks if the given ratio equals 100 and follows the format of #,#,#,#.
   *
   * @param ratio      ratio to verify
   * @param stockCount number of unique stocks in the portfolio
   * @return True if ratio is valid, false otherwise
   */
  boolean validRatio(String ratio, int stockCount);

  /**
   * Checks if a portfolio with the given name already exists.
   *
   * @param name name of the portfolio
   * @return true if the portfolio exists, false otherwise
   */
  boolean portfolioExists(String name);

  /**
   * Saves the specified portfolio to a CSV file.
   *
   * @param name name of the portfolio
   */
  void savePortfolio(String name);

  /**
   * Imports a CSV file of a portfolio into our stock program.
   *
   * @param name name of the portfolio
   */
  void importPortfolio(String name);

  /**
   * Checks if a user portfolio exists.
   *
   * @param name name of the portfolio
   */
  boolean userPortfolioExists(String name);

  /**
   * Checks if the portfolio contains only one stock.
   *
   * @param name name of the portfolio to check
   * @return true if the portfolio contains only one stock, false otherwise.
   */
  boolean oneStock(String name);

  /**
   * Lists all the stocks in a portfolio.
   *
   * @param name name of the portfolio to check
   * @return the list of stocks the portfolio has seperated by commas
   */
  String listStocks(String name);

  /**
   * Distributes the given weights to the portfolio with the given name
   * with respect to a given date.
   *
   * @param name    name of the portfolio to check
   * @param date    date to apply the weights on
   * @param weights weights of all the stocks
   */
  void distributeWeights(String name, String date, String weights);

  /**
   * Generate a textual representation of a bar chart to visualize the performance of a portfolio
   * or stock within a specified time range.
   *
   * @param portfolioName name of the portfolio to check
   * @param stockName     ticker Symbol of the stock to check
   * @param startDate     start date of the analysis period
   * @param endDate       end date of the analysis period
   * @return string representation of the generated bar chart
   */
  String generateGraph(String portfolioName, String stockName, String startDate, String endDate);

  /**
   * Finds the number of stocks with the given name and date in this portfolio.
   *
   * @param portfolioName name of the portfolio to check
   * @param tickerSymbol  the tickerSymbol of the stock to look for
   * @param purchaseDate  the purchase date of the stock
   * @return the number of total shares in the portfolio matching the specifications
   */
  double getStockQuantity(String portfolioName, String tickerSymbol, String purchaseDate);

  /**
   * Retrieves the number of unique stocks present in a specified portfolio.
   *
   * @param portfolioName name of the portfolio to check
   * @return Number of unique stocks in the portfolio
   */
  int stockCountPortfolio(String portfolioName);
}