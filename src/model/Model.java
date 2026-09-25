package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Model class in the MVC design pattern to implement the functionality of the stock program with
 * stock analysis and creating and editing portfolios.
 */
public class Model implements IModel {
  private Map<String, Portfolio> portfolios;
  private final Query q;
  private final IStock s;

  /**
   * Constructs a new Model with an empty portfolio map.
   */
  public Model() {
    this.portfolios = new HashMap<>();
    this.q = new Query();
    this.s = new Stock();
  }

  @Override
  public String evaluateChange(String tS, String startDate, String endDate)
          throws IllegalArgumentException {
    if (tS == null || startDate == null || endDate == null) {
      throw new IllegalArgumentException("Invalid input");
    } else if (!validDate(tS, startDate) || !validDate(tS, endDate)) {
      throw new IllegalArgumentException("Invalid date");
    } else if (!validTS(tS)) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
    q.updateCSV(tS);
    Stock stock1 = s.createStockFromString(q.findStock(tS, startDate), tS);
    Stock stock2 = s.createStockFromString(q.findStock(tS, endDate), tS);
    return Math.round((stock2.getClosingPrice() - stock1.getClosingPrice()) * 100.0) / 100.0 + "";
  }

  @Override
  public String evaluateMovingAverage(String tS, String date, int x)
          throws IllegalArgumentException {
    if (tS == null || date == null || x <= 0) {
      throw new IllegalArgumentException("Invalid input");
    } else if (!new Model().validDate(tS, date)) {
      throw new IllegalArgumentException("Invalid date");
    } else if (!validTS(tS)) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
    q.updateCSV(tS);
    double sum = 0;
    try {
      List<String> results = q.getXDates(tS, date, x);
      for (String s : results) {
        double value = Double.parseDouble(s);
        sum += value;
      }
    } catch (Exception e) {
      throw new IllegalArgumentException();
    }
    return Math.round((sum / x) * 100.0) / 100.0 + "";
  }

  @Override
  public String determineCrossover(String tS, String startDate, String endDate, int x)
          throws IllegalArgumentException {
    if (tS == null || startDate == null || endDate == null) {
      throw new IllegalArgumentException("Invalid input");
    } else if (!validDate(tS, startDate) || !validDate(tS, endDate)) {
      throw new IllegalArgumentException("Invalid date");
    } else if (!validTS(tS)) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
    q.updateCSV(tS);
    List<String> days = new ArrayList<>();
    List<String> stockData = q.getDatesInRange(tS, startDate, endDate);
    for (String stockDataLine : stockData) {
      String yearMonthDay = stockDataLine.split(",")[0];
      String movingAverage = evaluateMovingAverage(tS, yearMonthDay, x);
      double currClosing = s.createStockFromString(stockDataLine, tS).getClosingPrice();
      if (currClosing > Double.parseDouble(movingAverage)) {
        days.add(yearMonthDay);
      }
    }
    return days.toString().substring(1, days.toString().length() - 1);
  }

  @Override
  public void createPortfolio(String name) {
    portfolios.put(name, new Portfolio());
  }

  @Override
  public void addStock(String name, String tS, double newQ, String datePurchased)
          throws IllegalArgumentException {
    if (tS == null || name == null || datePurchased == null || newQ == 0) {
      throw new IllegalArgumentException("Invalid input");
    } else if (!validDate(tS, datePurchased)) {
      throw new IllegalArgumentException("Invalid date");
    } else if (!validTS(tS)) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
    q.updateCSV(tS);
    Portfolio portfolio = portfolios.get(name);
    if (portfolio != null) {
      Map<IStock, Double> stocks = portfolio.getStocks();
      for (Map.Entry<IStock, Double> entry : stocks.entrySet()) {
        IStock stockKey = entry.getKey();
        String tickerSymbol = stockKey.getTickerSymbol();
        String stockDayBought = stockKey.getDatePurchased();
        if (tickerSymbol.equals(tS) && stockDayBought.equals(datePurchased)) {
          if (entry.getValue() <= Math.abs(newQ) && newQ < 0) {
            stocks.remove(stockKey);
          }
          entry.setValue(entry.getValue() + newQ);
          return;
        }
      }
      IStock newStock = s.createStockFromString(q.findStock(tS, datePurchased), tS);
      stocks.put(newStock, newQ);
    }
  }

  @Override
  public String listPortfoliosAndContents() {
    if (portfolios.entrySet().isEmpty()) {
      return "=== PORTFOLIO CONTENTS ===\nNo portfolios exist";
    }
    StringBuilder output = new StringBuilder("=== PORTFOLIO CONTENTS ===\n");
    String f = "%-14s %-13s %-15s %-20.2f%n";
    DecimalFormat df = new DecimalFormat("#.##");
    for (Map.Entry<String, Portfolio> entry : portfolios.entrySet()) {
      IPortfolio portfolio = entry.getValue();
      output.append("\nPortfolio: ").append(entry.getKey());
      output.append("\nStock Symbol | Stock Count | Purchase Date | Stock Value\n");
      double totalValue = 0;
      for (Map.Entry<IStock, Double> stockContents : portfolio.getStocks().entrySet()) {
        IStock stockKey = stockContents.getKey();
        String stockName = stockKey.getTickerSymbol();
        double stockQuantity = stockContents.getValue();
        String stockQuantityStr = df.format(stockQuantity);
        String datePurchased = stockKey.getDatePurchased();
        String foundStock = q.findStock(stockName, datePurchased);
        double closingPrice = s.createStockFromString(foundStock, stockName).getClosingPrice();
        double distribution = roundToTwo(stockQuantity * closingPrice);
        output.append(String.format(f, stockName, stockQuantityStr, datePurchased, distribution));
        totalValue += distribution;
      }
      output.append(String.format("%-45s%.2f%n", "Total value:", totalValue));
    }
    return output.toString();
  }

  @Override
  public String determineComposition(String portfolioName, String date)
          throws IllegalArgumentException {
    if (portfolioName == null || date == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    } else if (!validPortfolioDate(date)) {
      throw new IllegalArgumentException("Invalid date");
    }
    IPortfolio portfolio = portfolios.get(portfolioName);
    if (portfolio == null) {
      return "Portfolio does not exist.";
    }
    StringBuilder output = new StringBuilder("Portfolio: " + portfolioName
            + " (Evaluated on " + date + ")");
    output.append("\nStock Symbol | Stock Count | Purchase Date | Stock Value\n");
    String f = "%-14s %-13s %-15s %-20.2f%n";
    DecimalFormat df = new DecimalFormat("#.##");
    double totalValue = 0;
    for (Map.Entry<IStock, Double> stockContents : portfolio.getStocks().entrySet()) {
      IStock stockKey = stockContents.getKey();
      String stockName = stockKey.getTickerSymbol();
      double stockQuantity = stockContents.getValue();
      String stockQuantityStr = df.format(stockQuantity);
      String datePurchased = stockKey.getDatePurchased();
      String foundStock = q.findStock(stockName, date);
      double closingPrice;
      if (foundStock.isEmpty()) {
        closingPrice = 0.0;
      } else {
        closingPrice = s.createStockFromString(foundStock, stockName).getClosingPrice();
      }
      double distribution = roundToTwo(stockQuantity * closingPrice);
      output.append(String.format(f, stockName, stockQuantityStr, datePurchased, distribution));
      totalValue += distribution;
    }
    output.append(String.format("%-45s%.2f%n", "Total value:", totalValue));
    return output.toString();
  }

  @Override
  public boolean validTS(String tS) {
    String s = Query.queryStock(tS);
    new Query().updateCSV(tS);
    return !s.contains("No data found") && !s.contains("https://www.alphavantage.co/documentation");
  }

  @Override
  public boolean validDate(String tS, String date) {
    if (date.length() != 10 || date.charAt(4) != '-' || date.charAt(7) != '-') {
      return false;
    }
    for (int i = 0; i < date.length(); i++) {
      if (i != 4 && i != 7) {
        if (!Character.isDigit(date.charAt(i))) {
          return false;
        }
      }
    }
    return !q.findStock(tS, date).isEmpty();
  }

  @Override
  public boolean validPortfolioDate(String date) {
    if (date.length() != 10 || date.charAt(4) != '-' || date.charAt(7) != '-') {
      return false;
    }
    for (int i = 0; i < date.length(); i++) {
      if (i != 4 && i != 7) {
        if (!Character.isDigit(date.charAt(i))) {
          return false;
        }
      }
    }
    return !q.findStock("BA", date).isEmpty();
  }

  @Override
  public boolean portfolioExists(String name) {
    return portfolios.containsKey(name);
  }

  @Override
  public void savePortfolio(String name) {
    if (!portfolios.containsKey(name)) {
      return;
    }
    try {
      FileWriter fileWriter = new FileWriter("UserPortfolios/" + name + ".csv");
      IPortfolio portfolioToSave = portfolios.get(name);
      String output = "";
      for (Map.Entry<IStock, Double> stocks : portfolioToSave.getStocks().entrySet()) {
        IStock stockKey = stocks.getKey();
        output += stockKey.getTickerSymbol() + ","
                + stocks.getValue() + ","
                + stockKey.getDatePurchased() + ","
                + roundToTwo(stockKey.getClosingPrice() * stocks.getValue())
                + "\n";
      }
      fileWriter.write(output);
      fileWriter.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void importPortfolio(String name) {
    File file = new File("UserPortfolios/" + name + ".csv");
    if (!file.exists()) {
      return;
    } else {
      this.createPortfolio(name);
      String line;
      try {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while ((line = reader.readLine()) != null) {
          String[] data = line.split(",");
          this.addStock(name, data[0], Double.parseDouble(data[1]), data[2]);
        }
      } catch (Exception e) {
        System.out.print("");
      }
    }
  }

  @Override
  public void distributeWeights(String name, String date, String weights)
          throws IllegalArgumentException {
    if (name == null || date == null || weights == null) {
      throw new IllegalArgumentException("Invalid input");
    }
    String[] weightsArr = weights.split(",");
    if (weightsArr.length > this.portfolios.get(name).getStocks().entrySet().size()) {
      throw new IllegalArgumentException("Invalid ratio");
    }
    IPortfolio portfolioToRebalance = portfolios.get(name);
    double totalPortfolioValue = getTotalPortfolioValue(name, date);
    int i = 0;
    LinkedHashMap<IStock, Double> test = new LinkedHashMap<>();
    for (IStock stock : portfolioToRebalance.getStocks().keySet()) {
      double currTargetValue = totalPortfolioValue * (Double.parseDouble(weightsArr[i]) / 100.0);
      i += 1;
      double stockCount = portfolioToRebalance.getStocks().get(stock);
      double closePriceOnDate = Double.parseDouble(
              new Query().findStock(stock.getTickerSymbol(), date).split(",")[4]);
      double currentValue = stockCount * closePriceOnDate;
      double t_difference = currTargetValue - currentValue;
      double shareDifference = t_difference / closePriceOnDate;
      test.put(stock, portfolioToRebalance.getStocks().get(stock) + shareDifference);
    }
    portfolioToRebalance.getStocks().clear();
    portfolioToRebalance.getStocks().putAll(test);
  }

  @Override
  public void removeStock(String name, String tS, double newQ, String datePurchased) {
    if (tS == null || name == null || datePurchased == null || newQ == 0) {
      throw new IllegalArgumentException("Invalid input");
    } else if (!validDate(tS, datePurchased)) {
      throw new IllegalArgumentException("Invalid date");
    } else if (!validTS(tS)) {
      throw new IllegalArgumentException("Invalid ticker symbol");
    } else if (newQ < 0) {
      throw new IllegalArgumentException("Cannot sell negative stocks");
    }

    IPortfolio portfolio = portfolios.get(name);

    q.updateCSV(tS);
    if (portfolio != null) {
      Map<IStock, Double> stocks = portfolio.getStocks();
      for (Map.Entry<IStock, Double> entry : stocks.entrySet()) {
        IStock stockKey = entry.getKey();
        String tickerSymbol = stockKey.getTickerSymbol();
        String stockDayBought = stockKey.getDatePurchased();
        if (tickerSymbol.equals(tS) && stockDayBought.equals(datePurchased)) {
          if (entry.getValue() <= Math.abs(newQ)) {
            stocks.remove(stockKey);
          }
          entry.setValue(entry.getValue() + newQ);
          return;
        }
      }
      IStock newStock = s.createStockFromString(q.findStock(tS, datePurchased), tS);
      stocks.put(newStock, newQ);
    }

  }

  @Override
  public String generateGraph(String portName, String stockName, String day1, String day2) {
    if (portName == null || stockName == null || day1 == null || day2 == null) {
      throw new IllegalArgumentException("Invalid Input");
    } else if (!validDate("BA", day1) || !validDate("BA", day2)) {
      throw new IllegalArgumentException("Invalid Input");
    }
    String titleOptionEvaluated = (portName.isEmpty()) ? String.format("Stock: '%s'", stockName)
            : String.format("Portfolio: '%s'", portName);
    String title = String.format("Performance of %s from %s to %s \n",
            titleOptionEvaluated, day1, day2);
    StringBuilder output = new StringBuilder(title);
    double max = (stockName.isEmpty()) ? findMaxPortfolioValue(portName, day1, day2)
            : findMaxStockValue(stockName, day1, day2);
    String tickerSymbol1 = stockName;
    if (!portName.isEmpty()) {
      IPortfolio port = portfolios.get(portName);
      Set<IStock> keyset = port.getStocks().keySet();
      tickerSymbol1 = keyset.iterator().next().getTickerSymbol();
    }
    List<String> allDays = new Query().getDatesInRange(tickerSymbol1, day1, day2);
    Collections.reverse(allDays);
    int incrementCounter = 0;
    int increment = 1;
    if (allDays.size() > 30) {
      increment = (int) (allDays.size() / 30.0);
    }
    for (int i = 0; i < allDays.size(); i += increment) {
      if (incrementCounter == 29) {
        String dateLast = allDays.get(allDays.size() - 1).split(",")[0];
        double stockValue = Double.parseDouble(q.findStock(tickerSymbol1, dateLast).split(",")[4]);
        double value = (stockName.isEmpty()) ?
                this.getTotalPortfolioValue(portName, dateLast) : stockValue;
        String outputAdd = String.format("%s: %s \n", dateLast, createAsteriskScale(value, max));
        output.append(outputAdd);
        break;
      }
      incrementCounter += 1;
      String date = allDays.get(i).split(",")[0];
      double stockValue2 = Double.parseDouble(q.findStock(tickerSymbol1, date).split(",")[4]);
      double value2 = (stockName.isEmpty()) ?
              this.getTotalPortfolioValue(portName, date) : stockValue2;
      output.append(date).append(": ").append(createAsteriskScale(value2, max)).append("\n");
    }
    String scaleMessage = "Scale: * = $" + roundToTwo(max / 50.0);
    output.append(scaleMessage);
    return output.toString();
  }

  @Override
  public boolean userPortfolioExists(String name) {
    File file = new File("UserPortfolios/" + name + ".csv");
    return file.exists();
  }

  @Override
  public boolean oneStock(String name) {
    Portfolio portfolio = portfolios.get(name);
    return portfolio.getStocks().size() == 1;
  }

  @Override
  public String listStocks(String name) {
    Portfolio portfolio = portfolios.get(name);
    if (portfolio == null) {
      return "";
    }
    StringBuilder stocksList = new StringBuilder();
    for (IStock stock : portfolio.getStocks().keySet()) {
      stocksList.append(stock.getTickerSymbol()).append(", ");
    }
    return stocksList.toString();
  }

  @Override
  public boolean validRatio(String ratio, int stockCount) {
    if (ratio.contains(",,")) {
      return false;
    }
    String[] s = ratio.split(",");
    if (s.length != stockCount) {
      return false;
    }
    double sum = 0;
    for (String num : s) {
      num = num.trim();
      try {
        double value = Double.parseDouble(num);
        sum += value;
      } catch (NumberFormatException e) {
        return false;
      }
    }
    return Math.abs(sum - 100.0) < 0.0001;
  }

  @Override
  public double getStockQuantity(String portfolioName, String tickerSymbol, String purchaseDate) {
    if (portfolios.containsKey(portfolioName)) {
      Portfolio portfolio = portfolios.get(portfolioName);
      return portfolio.stockQuantity(tickerSymbol, purchaseDate);
    }
    return 0.0;
  }

  @Override
  public int stockCountPortfolio(String portfolioName) {
    IPortfolio portfolio = portfolios.get(portfolioName);
    Map<IStock, Double> stocks = portfolio.getStocks();
    int count = 0;
    for (IStock stock : stocks.keySet()) {
      count += 1;
    }
    return count;
  }

  // creates the asterisks to be used in the bar graph
  private String createAsteriskScale(double value, double max) {
    int numAsterisks = (int) Math.round((value / max) * 50);
    StringBuilder asterisks = new StringBuilder();
    for (int i = 0; i < numAsterisks; i++) {
      asterisks.append("*");
    }
    return asterisks.toString();
  }

  // gets the max value of a stock
  private double findMaxStockValue(String tS, String start, String end) {
    List<String> daysBetween = q.getDatesInRange(tS, start, end);
    double max = Double.parseDouble(daysBetween.get(0).split(",")[4]);
    for (String s : daysBetween) {
      double check = Double.parseDouble(s.split(",")[4]);
      if (check > max) {
        max = check;
      }
    }
    return max;
  }

  // gets the max value of a portfolio
  private double findMaxPortfolioValue(String portName, String day1, String day2) {
    List<String> daysBetween = q.getDatesInRange("BA", day1, day2);
    double max = getTotalPortfolioValue(portName, day1);
    for (String s : daysBetween) {
      double curr = getTotalPortfolioValue(portName, s.split(",")[0]);
      if (curr > max) {
        max = curr;
      }
    }
    return max;
  }

  // Gets the total value of a portfolio on a given date
  private double getTotalPortfolioValue(String name, String date) {
    if (name == null || date == null) {
      throw new IllegalArgumentException("Null inputs not allowed");
    } else if (!validDate("BA", date)) {
      throw new IllegalArgumentException("Invalid Date");
    }

    IPortfolio portfolio = portfolios.get(name);
    double sum = 0;
    if (portfolio == null) {
      return sum;
    }
    for (Map.Entry<IStock, Double> entry : portfolio.getStocks().entrySet()) {
      IStock stockKey = entry.getKey();
      double closePriceOnDate = Double.parseDouble(
              new Query().findStock(stockKey.getTickerSymbol(), date).split(",")[4]);
      sum += entry.getValue() * closePriceOnDate;
    }
    return roundToTwo(sum);
  }

  // Rounds doubles to two decimals
  private double roundToTwo(double value) {
    return Math.round(value * 100.0) / 100.0;
  }
}