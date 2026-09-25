import view.IView;

/**
 * Mock implementation for View testing.
 */
public class MockView implements IView {
  StringBuilder log;

  /**
   * Creates a Mock View object to help test with the controller.
   *
   * @param log a StringBuilder log used to build a log message
   */
  public MockView(StringBuilder log) {
    this.log = log;
  }

  // method to help add a string to the log
  private void addToLog(String s) {
    log.append(s).append("\n");
  }

  @Override
  public void menu() {
    addToLog("MENU");
  }

  @Override
  public void quit() {
    addToLog("QUIT");
  }

  @Override
  public void backToMenu() {
    addToLog("BACK TO MENU");
  }

  @Override
  public void portfolioMenu() {
    addToLog("PORTFOLIO MENU");
  }

  @Override
  public void portfolioResponse(String answer) {
    addToLog("PORTFOLIO RESPONSE: " + answer);
  }

  @Override
  public void answerResponse(String answer) {
    addToLog("ANSWER RESPONSE: " + answer);
  }

  @Override
  public void requestTickerSymbol() {
    addToLog("REQUEST TICKER SYMBOL");
  }

  @Override
  public void requestStartDate() {
    addToLog("REQUEST START DATE");
  }

  @Override
  public void requestEndDate() {
    addToLog("REQUEST END DATE");
  }

  @Override
  public void requestDate() {
    addToLog("REQUEST DATA");
  }

  @Override
  public void requestDatePurchased() {
    addToLog("REQUEST DATE PURCHASED");
  }

  @Override
  public void requestPortfolioDate() {
    addToLog("REQUEST PORTFOLIO DATA");
  }

  @Override
  public void requestDays() {
    addToLog("REQUEST DAYS");
  }

  @Override
  public void requestStockQuantity() {
    addToLog("REQUEST STOCK QUANTITY");
  }

  @Override
  public void requestChangeStockQuantity() {
    addToLog("REQUEST CHANGE STOCK QUANTITY");
  }

  @Override
  public void requestName() {
    addToLog("REQUEST NAME");
  }

  @Override
  public void requestRatio() {
    addToLog("REQUEST RATIO");
  }

  @Override
  public void invalidTickerSymbol() {
    addToLog("INVALID TICKER SYMBOL");
  }

  @Override
  public void invalidDate() {
    addToLog("INVALID DATE");
  }

  @Override
  public void invalidXDay() {
    addToLog("INVALID XDAY");
  }

  @Override
  public void invalidStockQuantity() {
    addToLog("INVALID STOCK QUANTITY");
  }

  @Override
  public void invalidName() {
    addToLog("INVALID NAME");
  }

  @Override
  public void invalidRatio() {
    addToLog("INVALID RATIO");
  }

  @Override
  public void portfolioExists() {
    addToLog("PORTFOLIO EXISTS");
  }

  @Override
  public void noPortfolio() {
    addToLog("NO PORTFOLIO");
  }

  @Override
  public void oneStockPortfolio() {
    addToLog("ONE STOCK");
  }

  @Override
  public void evaluateChangeOutput(String result,
                                   String tickerSymbol, String startDate, String endDate) {
    addToLog("EVALUATE CHANGE OUTPUT: " + result);
  }

  @Override
  public void evaluateMovingAverageOutput(String result,
                                          String tickerSymbol, String date, int days) {
    addToLog("EVALUATE MOVING AVERAGE OUTPUT: " + result);
  }

  @Override
  public void evaluateCrossoverOutput(String result, String tickerSymbol,
                                      String startDate, String endDate, int days) {
    addToLog("EVALUATE CROSSOVER OUTPUT: " + result);
  }

  @Override
  public void newPortfolio(String name, String tickerSymbol, double quantity,
                           String datePurchased) {
    addToLog("NEW PORTFOLIO: " + name);
  }

  @Override
  public void addedToPortfolio(String name, String tickerSymbol, double quantity,
                               String datePurchased) {
    addToLog("ADDED TO PORTFOLIO: " + name);
  }

  @Override
  public void portfolioSaved(String name) {
    addToLog("LIST SAVED");
  }

  @Override
  public void portfolioImported(String name) {
    addToLog("LIST IMPORTED");
  }

  @Override
  public void portfolioRebalanced(String name, String date) {
    addToLog("LIST REBALANCED");
  }

  @Override
  public void listPortfolios(String result) {
    addToLog("LIST PORTFOLIOS: " + result);
  }

  @Override
  public void barGraph(String result) {
    addToLog("LIST GRAPH: " + result);
  }

  @Override
  public void listStocks(String name, String stocks) {
    addToLog("LIST STOCKS: " + stocks);
  }
}