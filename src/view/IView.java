package view;

/**
 * Represents a view in the MVC design pattern. This interacts with the user by displaying a menu
 * and confirms the user's input.
 */
public interface IView {

  /**
   * Displays a menu with options for stock analysis for the user to select from.
   */
  void menu();

  /**
   * Displays a message that the program is being quit.
   */
  void quit();

  /**
   * Displays a message that the program is going back to the menu.
   */
  void backToMenu();

  /**
   * Displays a menu with options after creating a portfolio.
   */
  void portfolioMenu();

  /**
   * Displays a confirmation of the options after creating a portfolio selected by the user.
   *
   * @param answer user's input to the portfolioOption method's prompt
   */
  void portfolioResponse(String answer);

  /**
   * Displays a confirmation of the stock analysis option selected by the user.
   *
   * @param answer user's input to the menu method's prompt
   */
  void answerResponse(String answer);

  /**
   * Prompt the user to enter a stock's ticker symbol.
   */
  void requestTickerSymbol();

  /**
   * Prompts the user to enter the start date for the stock's analysis in the format of YYYY-MM-DD.
   */
  void requestStartDate();

  /**
   * Prompts the user to enter the end date for the stock's analysis in the format of YYYY-MM-DD.
   */
  void requestEndDate();

  /**
   * Prompts the user to enter a date for the stock's analysis in the format of YYYY-MM-DD.
   */
  void requestDate();

  /**
   * Prompts the user to enter a date that a stock was purchased in the format of YYYY-MM-DD.
   */
  void requestDatePurchased();

  /**
   * Prompts the user to enter a date when to evaluate the portfolio in the format of YYYY-MM-DD.
   */
  void requestPortfolioDate();

  /**
   * Prompts the user to enter a number of days greater than 0 for the stock's analysis.
   */
  void requestDays();

  /**
   * Prompts the user to enter a quantity of stocks greater than 0 when creating a portfolio.
   */
  void requestStockQuantity();

  /**
   * Prompts the user to enter a quantity of stocks greater that is not 0 when adding/removing from
   * a portfolio.
   */
  void requestChangeStockQuantity();

  /**
   * Prompts the user to enter a name for their portfolio that is not empty.
   */
  void requestName();

  /**
   * Prompts the user to enter a ratio for their portfolio rebalancing.
   */
  void requestRatio();

  /**
   * Prompts the user to re-enter a ticker symbol after the given one was invalid.
   */
  void invalidTickerSymbol();

  /**
   * Prompts the user to re-enter a date after the given one was invalid.
   */
  void invalidDate();

  /**
   * Prompts the user to re-enter a number of days after the given one was invalid.
   */
  void invalidXDay();

  /**
   * Prompts the user to re-enter a quantity of stocks after the given one was invalid.
   */
  void invalidStockQuantity();

  /**
   * Prompts the user to re-enter a name for their portfolio after the given one was invalid.
   */
  void invalidName();

  /**
   * Prompts the user to re-enter a ratio for rebalancing a portfolio after the given one was
   * invalid.
   */
  void invalidRatio();

  /**
   * Prompts the user to re-enter a name for their portfolio as it already exists.
   */
  void portfolioExists();

  /**
   * Prompts the user to re-enter a name for their portfolio as it does not exist.
   */
  void noPortfolio();

  /**
   * Prompts the user to re-enter a name for their portfolio as it only has one stock.
   */
  void oneStockPortfolio();

  /**
   * Displays the result of evaluating the change between two stocks.
   *
   * @param result       result of evaluating the change between two stocks
   * @param tickerSymbol stock's stock symbol
   * @param startDate    start date of the desired stock to track
   * @param endDate      end date of the desired stock to track
   */
  void evaluateChangeOutput(String result, String tickerSymbol, String startDate, String endDate);

  /**
   * Displays the result of evaluating the x-day moving average of a stock for a specified date.
   *
   * @param result       result of evaluating the x-day moving average
   * @param tickerSymbol stock's stock symbol
   * @param date         date of the desired stock to track
   * @param days         days before the date
   */
  void evaluateMovingAverageOutput(String result, String tickerSymbol, String date, int days);

  /**
   * Displays the result of evaluating the x-day moving average of a stock for a specified date.
   *
   * @param result       result of evaluating the x-day moving average
   * @param tickerSymbol stock's stock symbol
   * @param startDate    start date of the desired stock to track
   * @param endDate      end date of the desired stock to track
   * @param days         days before the date
   */
  void evaluateCrossoverOutput(String result, String tickerSymbol, String startDate, String endDate,
                               int days);

  /**
   * Displays a confirmation of creating a portfolio.
   *
   * @param name          name of the portfolio
   * @param tickerSymbol  stock's stock symbol
   * @param quantity      number of stocks in the portfolio for the given stock
   * @param datePurchased date when the stock was purchased
   */
  void newPortfolio(String name, String tickerSymbol, double quantity, String datePurchased);

  /**
   * Displays a confirmation of adding to a portfolio.
   *
   * @param name          name of the portfolio
   * @param tickerSymbol  stock's stock symbol
   * @param quantity      number of stocks in the portfolio for the given stock
   * @param datePurchased date when the stock was purchased
   */
  void addedToPortfolio(String name, String tickerSymbol, double quantity, String datePurchased);

  /**
   * Displays a confirmation of saving a portfolio to a file.
   *
   * @param name name of the portfolio
   */
  void portfolioSaved(String name);

  /**
   * Displays a confirmation of importing a portfolio from a file.
   *
   * @param name name of the portfolio
   */
  void portfolioImported(String name);

  /**
   * Displays a confirmation that a portfolio has been rebalanced.
   *
   * @param name name of the portfolio
   * @param date date of when the portfolio was rebalanced
   */
  void portfolioRebalanced(String name, String date);

  /**
   * Displays the portfolios and its contents.
   *
   * @param result output of the current existing portfolios
   */
  void listPortfolios(String result);

  /**
   * Displays the stock or portfolio's bar graph.
   *
   * @param result output of the stock or portfolio's bar graph
   */
  void barGraph(String result);

  /**
   * Displays a portfolio's stocks.
   *
   * @param name output of a portfolio's stocks
   */
  void listStocks(String name, String stocks);
}