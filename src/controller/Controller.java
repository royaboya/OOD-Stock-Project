package controller;

import java.util.Scanner;

import model.IModel;
import view.IView;

/**
 * Controller class in the MVC design pattern to handle CLI user inputs, interact with the view to
 * display menus and prompts, and use the model to perform different stock analysis.
 */
public class Controller implements IController {
  private IView view;
  private boolean exit;
  final Readable in;
  final Appendable out;

  /**
   * Creates a controller with a View, readable, and appendable.
   *
   * @param view an IView
   * @param in   a Readable
   * @param out  an Appendable
   */
  public Controller(IView view, Readable in, Appendable out) {
    this.view = view;
    this.in = in;
    this.out = out;
  }

  @Override
  public void start(IModel m) {
    Scanner s = new Scanner(this.in);
    while (true) {
      view.menu();
      String answer = s.nextLine().trim();
      if (answer.equalsIgnoreCase("q")) {
        view.quit();
        break;
      }
      handleInput(answer, s, m);
    }
    s.close();
  }

  // Handles the user's input based on their selected option
  private void handleInput(String answer, Scanner s, IModel m) {
    view.answerResponse(answer);
    switch (answer) {
      case "1":
        handleEvaluateChange(s, m);
        break;
      case "2":
        handleEvaluateMovingAverage(s, m);
        break;
      case "3":
        handleDetermineCrossover(s, m);
        break;
      case "4":
        handleViewStockOverTime(s, m);
        break;
      case "5":
        handlePortfolio(s, m);
        break;
      default:
        break;
    }
  }

  // Handles the process to evaluate the change in stock price
  private void handleEvaluateChange(Scanner s, IModel m) {
    String tS = requestValidTickerSymbol(s, m);
    String day1 = requestValidDate(s, tS, "start", m);
    String day2 = requestValidDate(s, tS, "end", m);
    view.evaluateChangeOutput(m.evaluateChange(tS, day1, day2), tS, day1, day2);
  }

  // Handles the process to evaluate the x-day moving average
  private void handleEvaluateMovingAverage(Scanner s, IModel m) {
    String tS = requestValidTickerSymbol(s, m);
    String date = requestValidDate(s, tS, "", m);
    int days = requestValidDay(s);
    view.evaluateMovingAverageOutput(m.evaluateMovingAverage(tS, date, days), tS, date, days);
  }

  // Handles the process to evaluate the x-day crossover
  private void handleDetermineCrossover(Scanner s, IModel m) {
    String tS = requestValidTickerSymbol(s, m);
    String day1 = requestValidDate(s, tS, "start", m);
    String day2 = requestValidDate(s, tS, "end", m);
    int days = requestValidDay(s);
    view.evaluateCrossoverOutput(m.determineCrossover(tS, day1, day2, days), tS, day1, day2, days);
  }

  // Handles the process to viewing a stock over time
  private void handleViewStockOverTime(Scanner s, IModel m) {
    String tS = requestValidTickerSymbol(s, m);
    String day1 = requestValidDate(s, tS, "start", m);
    String day2 = requestValidDate(s, tS, "end", m);
    view.barGraph(m.generateGraph("", tS, day1, day2));
  }

  // Handles the process of managing a portfolio
  private void handlePortfolio(Scanner s, IModel model) {
    while (true) {
      view.portfolioMenu();
      String answer = s.nextLine().trim();
      if (answer.equalsIgnoreCase("m")) {
        view.backToMenu();
        break;
      } else if (answer.equalsIgnoreCase("q")) {
        view.quit();
        exit = true;
        break;
      } else {
        handlePortfolioOptions(answer, s, model);
      }
    }
  }

  // Handles the options after creating a portfolio
  private void handlePortfolioOptions(String answer, Scanner s, IModel m) {
    view.portfolioResponse(answer);
    switch (answer) {
      case "1":
        handleNewPortfolio(s, m);
        break;
      case "2":
        handleAddToPortfolio(s, m);
        break;
      case "3":
        handleRebalancePortfolio(s, m);
        break;
      case "4":
        handleListPortfolioAndContents(s, m);
        break;
      case "5":
        handlePortfolioComposition(s, m);
        break;
      case "6":
        handleViewPortfolioOverTime(s, m);
        break;
      case "7":
        handleSavePortfolio(s, m);
        break;
      case "8":
        handleImportPortfolio(s, m);
        break;
      default:
        break;
    }
  }

  // Handles the process to creating a new portfolio
  private void handleNewPortfolio(Scanner s, IModel m) {
    String name = requestPortfolioName(s, m, "");
    String tS = requestValidTickerSymbol(s, m);
    double stockQuantity = requestValidStockQuantity(s, m, "new", "", "", "");
    String datePurchased = requestValidDate(s, tS, "purchased", m);
    m.createPortfolio(name);
    m.addStock(name, tS, stockQuantity, datePurchased);
    view.newPortfolio(name, tS, stockQuantity, datePurchased);
  }

  // Handles the process to creating a new portfolio
  private void handleAddToPortfolio(Scanner s, IModel m) {
    String name = requestPortfolioName(s, m, "add");
    String tS = requestValidTickerSymbol(s, m);
    String datePurchased = requestValidDate(s, tS, "purchased", m);
    double stockQuantity = requestValidStockQuantity(s, m, "add", name, tS, datePurchased);
    m.addStock(name, tS, stockQuantity, datePurchased);
    view.addedToPortfolio(name, tS, stockQuantity, datePurchased);
  }

  // Handles the process to rebalancing a portfolio
  private void handleRebalancePortfolio(Scanner s, IModel m) {
    String name = requestPortfolioName(s, m, "rebalance");
    String date = requestValidPortfolioDate(s, m);
    String ratio = requestRatio(s, m, name);
    m.distributeWeights(name, date, ratio);
    view.portfolioRebalanced(name, date);
  }

  // Handles the process to creating a new portfolio
  private void handleListPortfolioAndContents(Scanner s, IModel m) {
    view.listPortfolios(m.listPortfoliosAndContents());
  }

  // Handles the process to get a portfolio's composition at a specific date
  private void handlePortfolioComposition(Scanner s, IModel m) {
    String name = requestPortfolioName(s, m, "eval");
    String date = requestValidPortfolioDate(s, m);
    view.listPortfolios(m.determineComposition(name, date));
  }

  // Handles the process to viewing a portfolio over time
  private void handleViewPortfolioOverTime(Scanner s, IModel m) {
    String name = requestPortfolioName(s, m, "eval");
    String day1 = requestValidPortfolioDate(s, m);
    String day2 = requestValidPortfolioDate(s, m);
    view.barGraph(m.generateGraph(name, "", day1, day2));
  }

  // Handles the process to save a portfolio to a file
  private void handleSavePortfolio(Scanner s, IModel m) {
    String name = requestPortfolioName(s, m, "eval");
    m.savePortfolio(name);
    view.portfolioSaved(name);
  }

  // Handles the process to import a portfolio from a file
  private void handleImportPortfolio(Scanner s, IModel m) {
    String name = requestPortfolioName(s, m, "import");
    m.importPortfolio(name);
    view.portfolioImported(name);
  }

  // Requests and validates the ticker symbol
  private String requestValidTickerSymbol(Scanner s, IModel m) {
    while (true) {
      view.requestTickerSymbol();
      String tS = s.nextLine().trim();
      if (m.validTS(tS)) {
        return tS;
      } else {
        view.invalidTickerSymbol();
      }
    }
  }

  // Requests and validates the date
  private String requestValidDate(Scanner s, String tS, String f, IModel m) {
    while (true) {
      if (f.equals("start")) {
        view.requestStartDate();
      } else if (f.equals("end")) {
        view.requestEndDate();
      } else if (f.equals("purchased")) {
        view.requestDatePurchased();
      } else {
        view.requestDate();
      }
      String d = s.nextLine().trim();
      if (m.validDate(tS, d)) {
        return d;
      } else {
        view.invalidDate();
      }
    }
  }

  // Requests and validates the days
  private int requestValidDay(Scanner s) {
    while (true) {
      view.requestDays();
      int day = s.nextInt();
      s.nextLine();
      if (day > 0) {
        return day;
      } else {
        view.invalidXDay();
      }
    }
  }

  // Requests and validates the quantity of stocks
  private double requestValidStockQuantity(Scanner s, IModel m, String f, String pN, String tS,
                                           String pD) {
    while (true) {
      try {
        if (f.equals("new")) {
          view.requestStockQuantity();
        } else if (f.equals("add")) {
          view.requestChangeStockQuantity();
        }
        String input = s.nextLine().trim();
        double quantity = Double.parseDouble(input);

        if (f.equals("new") && quantity > 0 && quantity % 1 == 0) {
          return quantity;
        } else if (f.equals("add")) {
          if (quantity < 0 && Math.abs(quantity) <= m.getStockQuantity(pN, tS, pD)) {
            return quantity;
          } else if (quantity % 1 == 0 && quantity > 0) {
            return quantity;
          }
        }
        view.invalidStockQuantity();
      } catch (NumberFormatException e) {
        view.invalidStockQuantity();
      }
    }
  }

  // Requests and validates the days
  private String requestPortfolioName(Scanner s, IModel m, String f) {
    while (true) {
      view.requestName();
      String name = s.nextLine().trim();
      if (name.equalsIgnoreCase("m")) {
        view.backToMenu();
        handlePortfolio(s, m);
      } else if (name.isEmpty()) {
        view.invalidName();
      } else if (f.equals("import")) {
        if (m.userPortfolioExists(name)) {
          return name;
        } else {
          view.invalidName();
        }
      } else if (m.portfolioExists(name)) {
        if (f.equals("eval") || f.equals("add")) {
          return name;
        } else if (f.equals("rebalance")) {
          if (m.oneStock(name)) {
            view.oneStockPortfolio();
          } else {
            return name;
          }
        } else {
          view.portfolioExists();
        }
      } else {
        if (f.equals("eval") || f.equals("add") || f.equals("rebalance")) {
          view.noPortfolio();
        } else {
          return name;
        }
      }
    }
  }

  // Requests and validates the date
  private String requestValidPortfolioDate(Scanner s, IModel m) {
    while (true) {
      view.requestPortfolioDate();
      String d = s.nextLine().trim();
      if (m.validPortfolioDate(d)) {
        return d;
      } else {
        view.invalidDate();
      }
    }
  }

  // Requests and validates a ratio to rebalance a ratio
  private String requestRatio(Scanner s, IModel m, String name) {
    while (true) {
      view.listStocks(name, m.listStocks(name));
      view.requestRatio();
      String d = s.nextLine().trim();
      if (m.validRatio(d, m.stockCountPortfolio(name))) {
        return d;
      } else {
        view.invalidRatio();
      }
    }
  }
}