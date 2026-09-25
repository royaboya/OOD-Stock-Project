package view;

import java.io.PrintStream;
import java.text.DecimalFormat;

/**
 * View class in the MVC design pattern to display to the user by displaying menus and prompting
 * inputs for types of stock analysis, ticker symbols, and start/end dates.
 */
public class View implements IView {
  private final PrintStream out;

  /**
   * Creates view with a PrintStream.
   *
   * @param out a PrintStream
   */
  public View(PrintStream out) {
    this.out = out;
  }

  @Override
  public void menu() {
    this.out.println("Enter a number 1-5 (Q to quit) for the options listed below:\n"
            + "1. Examine gain or loss\n"
            + "2. Examine the x-day moving average\n"
            + "3. Find what days are x-day crossovers\n"
            + "4. View a stock's performance over time\n"
            + "5. Portfolio options\n"
            + "Q. Exit the program");
  }

  @Override
  public void answerResponse(String answer) {
    switch (answer) {
      case "1":
        this.out.println("Option 1 selected: Examine gain or loss");
        break;
      case "2":
        this.out.println("Option 2 selected: Examine the x-day moving average");
        break;
      case "3":
        this.out.println("Option 3 selected: Find what days are x-day crossovers");
        break;
      case "4":
        this.out.println("Option 4 selected: View a stock's performance over time");
        break;
      case "5":
        this.out.println("Option 5 selected: Portfolio options");
        break;
      default:
        this.out.println("Invalid option selected, select a number 1-5");
        break;
    }
  }

  @Override
  public void portfolioMenu() {
    this.out.println("Enter a number 1-8 (M to menu) for the options listed below:\n"
            + "1. Create a portfolio\n"
            + "2. Add/remove stocks from a portfolio\n"
            + "3. Rebalance a portfolio\n"
            + "4. List existing portfolios and their contents\n"
            + "5. Evaluate a portfolio on a date\n"
            + "6. View a portfolio's performance over time\n"
            + "7. Save a portfolio to a file\n"
            + "8. Import a portfolio into the program\n"
            + "M. Exit to menu");
  }

  @Override
  public void portfolioResponse(String answer) {
    switch (answer) {
      case "1":
        this.out.println("Option 1 selected: Create a portfolio");
        break;
      case "2":
        this.out.println("Option 2 selected: Add/remove stocks from a portfolio");
        break;
      case "3":
        this.out.println("Option 3 selected: Rebalance a portfolio");
        break;
      case "4":
        this.out.println("Option 4 selected: List existing portfolios and their contents");
        break;
      case "5":
        this.out.println("Option 5 selected: Evaluate a portfolio on a date");
        break;
      case "6":
        this.out.println("Option 6 selected: View a portfolio's performance over time");
        break;
      case "7":
        this.out.println("Option 7 selected: Save a portfolio to a file");
        break;
      case "8":
        this.out.println("Option 8 selected: Import a portfolio from file");
        break;
      default:
        this.out.println("Invalid option selected, select a number 1-8");
        break;
    }
  }

  @Override
  public void quit() {
    this.out.println("Quitting program...");
  }

  @Override
  public void backToMenu() {
    this.out.println("Exiting to menu...");
  }

  @Override
  public void requestTickerSymbol() {
    this.out.println("Enter the ticker symbol of a stock:");
  }

  @Override
  public void requestStartDate() {
    this.out.println("Enter the start date for the stock's analysis (YYYY-MM-DD):");
  }

  @Override
  public void requestEndDate() {
    this.out.println("Enter the end date for the stock's analysis (YYYY-MM-DD):");
  }

  @Override
  public void requestDate() {
    this.out.println("Enter a date for the stock's analysis (YYYY-MM-DD):");
  }

  @Override
  public void requestDatePurchased() {
    this.out.println("Enter a date when the stock was purchased (YYYY-MM-DD):");
  }

  @Override
  public void requestPortfolioDate() {
    this.out.println("Enter a date for when the portfolio should be evaluated (YYYY-MM-DD):");
  }

  @Override
  public void requestDays() {
    this.out.println("Enter a number of days greater than 0 for the stock's analysis:");
  }

  @Override
  public void requestStockQuantity() {
    this.out.println("Enter a positive quantity of stocks to add to the portfolio:");
  }

  @Override
  public void requestChangeStockQuantity() {
    this.out.println("Enter a positive quantity of stocks to buy or a negative quantity to sell "
            + "from the portfolio:");
  }

  @Override
  public void requestName() {
    this.out.println("Enter a name (or M to go back to options) for the portfolio:");
  }

  @Override
  public void requestRatio() {
    this.out.println("Enter a ratio to rebalance the portfolio:"
            + "\n(Ratio must add up to 100 and correspond to the respective stock seperated by "
            + "commas (#,#,#,#)");
  }

  @Override
  public void invalidTickerSymbol() {
    this.out.println("Invalid ticker symbol was given, please try again");
  }

  @Override
  public void invalidDate() {
    this.out.println("Invalid date was given, please try again");
  }

  @Override
  public void invalidXDay() {
    this.out.println("Invalid number of days was given, please try again");
  }

  @Override
  public void invalidStockQuantity() {
    this.out.println("Invalid quantity of stocks was given, please try again");
  }

  @Override
  public void invalidName() {
    this.out.println("Invalid name for portfolio was given, please try again");
  }

  @Override
  public void invalidRatio() {
    this.out.println("Invalid ratio for portfolio rebalancing was given, please try again");
  }

  @Override
  public void portfolioExists() {
    this.out.println("Given name for portfolio already exists, please try again");
  }

  @Override
  public void noPortfolio() {
    this.out.println("Given name for portfolio does not exist, please try again");
  }

  @Override
  public void oneStockPortfolio() {
    this.out.println("This portfolio only has one stock, please try again");
  }

  @Override
  public void evaluateChangeOutput(String result, String tickerSymbol, String startDate,
                                   String endDate) {
    this.out.println("The difference between " + tickerSymbol + "'s " + "stock at " + startDate
            + " and " + endDate + " is " + "$" + result);
  }

  @Override
  public void evaluateMovingAverageOutput(String result, String tickerSymbol, String date,
                                          int days) {
    this.out.println("The " + days + "-day moving average of " + tickerSymbol + "'s "
            + "stock at " + date + " is " + "$" + result);
  }

  @Override
  public void evaluateCrossoverOutput(String result, String tickerSymbol, String startDate,
                                      String endDate, int days) {
    this.out.println("The " + days + "-day crossover(s) of " + tickerSymbol + "'s stock between "
            + startDate + " and " + endDate + " are as follows: " + result);
  }

  @Override
  public void newPortfolio(String name, String tickerSymbol, double quantity,
                           String datePurchased) {
    this.out.println("The portfolio '" + name + "' was created with "
            + String.format("%.0f", quantity) + " shares of " + tickerSymbol + " on "
            + datePurchased);
  }

  @Override
  public void addedToPortfolio(String name, String tS, double quantity, String datePurchased) {
    this.out.println("Added/removed " + new DecimalFormat("#.##").format(quantity) + " shares of "
            + tS + " on " + datePurchased + " to portfolio " + "'" + name + "'");
  }

  @Override
  public void portfolioSaved(String name) {
    this.out.println("Portfolio '" + name + "' has been saved");
  }

  @Override
  public void portfolioImported(String name) {
    this.out.println("Portfolio '" + name + "' has been imported");
  }

  @Override
  public void portfolioRebalanced(String name, String date) {
    this.out.println("Portfolio '" + name + "' has been rebalanced for " + date);
  }

  @Override
  public void listPortfolios(String result) {
    this.out.println(result);
  }

  @Override
  public void barGraph(String result) {
    this.out.println(result);
  }

  @Override
  public void listStocks(String name, String stocks) {
    this.out.println("Portfolio '" + name + "' has the following stocks: " + stocks);
  }
}