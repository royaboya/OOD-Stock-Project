package model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a portfolio that can store multiple stocks with respective quantities.
 */
public class Portfolio implements IPortfolio {
  private Map<IStock, Double> stocks;

  /**
   * Creates a Portfolio object with a name.
   */
  protected Portfolio() {
    this.stocks = new LinkedHashMap<>();
  }

  @Override
  public Map<IStock, Double> getStocks() {
    return stocks;
  }

  @Override
  public double stockQuantity(String tS, String purchaseDate) {
    double quantity = 0;
    for (IStock stock : stocks.keySet()) {
      if (stock.getTickerSymbol().equals(tS) && stock.getDatePurchased().equals(purchaseDate)) {
        quantity = stocks.get(stock);
      }
    }
    return quantity;
  }
}