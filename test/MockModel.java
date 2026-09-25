import model.IModel;

/**
 * Test class for adding method inputs to a log.
 */
public class MockModel implements IModel {
  final StringBuilder log;

  @Override
  public void addStock(String s, String b, double i, String d) {
    String logged = String.format("%s %s %s", s, b, i);
    log.append(logged);
  }

  @Override
  public void removeStock(String s, String b, double i, String d) {
    String logged = String.format("%s %s %s", s, b, i);
    log.append(logged);
  }

  @Override
  public String listPortfoliosAndContents() {
    return "";
  }

  @Override
  public String determineComposition(String portfolioName, String date) {
    log.append(String.format("n = %s %s", portfolioName, date));
    return "";
  }

  @Override
  public boolean validPortfolioDate(String s) {
    log.append(String.format("n = %s", s));
    return false;
  }

  @Override
  public boolean validRatio(String ratio, int stockCount) {
    log.append(String.format("n = %s %s", ratio, stockCount));
    return false;
  }

  @Override
  public boolean portfolioExists(String name) {
    log.append(String.format("n = %s", name));
    return false;
  }

  @Override
  public void savePortfolio(String name) {
    log.append(String.format("n = %s", name));
  }

  @Override
  public void importPortfolio(String name) {
    log.append(String.format("n = %s", name));
  }

  @Override
  public boolean userPortfolioExists(String name) {
    log.append(String.format("n = %s", name));
    return false;
  }

  @Override
  public boolean oneStock(String name) {
    log.append(String.format("n = %s", name));
    return false;
  }

  @Override
  public String listStocks(String name) {
    log.append(String.format("n = %s", name));
    return "";
  }

  @Override
  public void distributeWeights(String name, String date, String weights) {
    log.append(String.format("n = %s %s %s", name, date, weights));
  }

  @Override
  public String generateGraph(String portfolioName, String tS, String startDate, String endDate) {
    log.append(String.format("n = %s %s %s %s", portfolioName, tS, startDate, endDate));
    return "";
  }

  @Override
  public double getStockQuantity(String portfolioName, String tickerSymbol, String purchaseDate) {
    log.append(String.format("n = %s %s %s", portfolioName, tickerSymbol, purchaseDate));
    return 0;
  }

  @Override
  public int stockCountPortfolio(String portfolioName) {
    log.append(String.format("n = %s", portfolioName));
    return 0;
  }

  /**
   * Mock constructor for Stock Model.
   */
  public MockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public String evaluateChange(String tickerSymbol, String startDate, String endDate) {
    log.append(String.format("t = %s sd = %s ed = %s", tickerSymbol, startDate, endDate));
    return "";
  }

  @Override
  public String evaluateMovingAverage(String tickerSymbol, String date, int x) {
    log.append(String.format("t = %s sd = %s x = %d", tickerSymbol, date, x));
    return "";
  }

  @Override
  public String determineCrossover(String tickerSymbol, String startDate, String endDate, int x) {
    log.append(String.format("t = %s sd = %s ed = %s x = %d", tickerSymbol, startDate, endDate, x));
    return "";
  }

  @Override
  public void createPortfolio(String name) {
    log.append(String.format("n = %s", name));
  }

  @Override
  public boolean validTS(String tickerSymbol) {
    log.append(tickerSymbol).append("\n");
    return false;
  }

  @Override
  public boolean validDate(String tickerSymbol, String date) {
    log.append(String.format("n = %s %s", tickerSymbol, date));
    return false;
  }
}