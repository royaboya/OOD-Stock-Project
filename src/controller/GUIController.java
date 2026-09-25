package controller;

import java.awt.event.ActionEvent;
import java.text.DecimalFormat;

import model.IModel;
import view.IGUIView;

/**
 * Controller class in the MVC design pattern to handle GUI user inputs, interacts with the view to
 * display menus and prompts, and use the model to perform portfolio management.
 */
public class GUIController implements IGUIController {
  private final IGUIView view;
  private final IModel model;

  /**
   * GUIController class to handle inputs for model and view behavior
   * depending on user input.
   *
   * @param view  a GUIView instance
   * @param model an IModel instance
   */
  public GUIController(IGUIView view, IModel model) {
    this.view = view;
    this.model = model;
  }

  @Override
  public void start() {
    view.setListener(this);
    view.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) throws IllegalArgumentException {
    view.updateScreen(e.getActionCommand());
    if (e.getActionCommand().contains("menu")) {
      view.clearTextFields();
      view.updateScreen("menu");
      return;
    }
    switch (e.getActionCommand()) {
      case "submitCreatePortfolio":
        handleCreatePortfolio();
        break;
      case "submitAddRemove":
        handleAddRemoveStock();
        break;
      case "submitEvaluatePortfolio":
        handleEvaluatePortfolio();
        view.updateScreen("result");
        break;
      case "submitSavePortfolio":
        handleSavePortfolio();
        break;
      case "submitImportPortfolio":
        handleImportPortfolio();
        break;
      case "exit":
        System.exit(0);
        break;
      default:
        break;
    }
  }

  private void handleCreatePortfolio() {
    String name = validatePortfolioName("", 1);
    if (name.isEmpty()) {
      return;
    }
    String tS = validateTickerSymbol(1);
    if (tS.isEmpty()) {
      return;
    }
    double quantity = validateStockQuantity("new", "", "", "", 1);
    if (quantity <= 0) {
      return;
    }
    String date = validateDatePurchased(tS, 1);
    if (date.isEmpty()) {
      return;
    }
    model.createPortfolio(name);
    model.addStock(name, tS, quantity, date);
    view.success("The portfolio '" + name + "' was created with " + String.format("%.0f", quantity)
            + " shares of " + tS + " on " + date);
    view.clearTextFields();
    view.mainMenu();
  }

  private void handleAddRemoveStock() {
    String name = validatePortfolioName("made", 2);
    if (name.isEmpty()) {
      return;
    }
    String tS = validateTickerSymbol(2);
    if (tS.isEmpty()) {
      return;
    }
    String date = validateDatePurchased(tS, 2);
    if (date.isEmpty()) {
      return;
    }
    double quantity = validateStockQuantity("add", name, tS, date, 2);
    if (quantity == 0) {
      return;
    }
    model.addStock(name, tS, quantity, date);
    view.success("Added/removed " + new DecimalFormat("#.##").format(quantity) + " shares of " + tS
            + " on " + date + " to portfolio " + "'" + name + "'");
    view.clearTextFields();
    view.mainMenu();
  }

  private void handleEvaluatePortfolio() {
    String name = validatePortfolioName("made", 3);
    if (name.isEmpty()) {
      return;
    }
    String date = validatePortfolioDate(3);
    if (date.isEmpty()) {
      return;
    }
    String result = model.determineComposition(name, date);
    view.displayResults(result, name, date);
    view.clearTextFields();
    view.mainMenu();
  }

  private void handleSavePortfolio() {
    String name = validatePortfolioName("made", 4);
    if (name.isEmpty()) {
      return;
    }
    try {
      model.savePortfolio(name);
      view.success("Portfolio '" + name + "' has been successfully saved.");
    } catch (Exception e) {
      view.error("An error occurred while saving the portfolio: " + e.getMessage());
    }
    view.clearTextFields();
    view.mainMenu();
  }

  private void handleImportPortfolio() {
    String name = validatePortfolioName("import", 5);
    if (name.isEmpty()) {
      return;
    }
    try {
      model.importPortfolio(name);
      view.success("Portfolio '" + name + "' has been successfully imported.");
    } catch (Exception e) {
      view.error("An error occurred while importing the portfolio: " + e.getMessage());
    }
    view.clearTextFields();
    view.mainMenu();
  }

  private String validatePortfolioName(String f, int option) {
    String name = view.getField("pN", option);
    if (name.isEmpty()) {
      view.error("Portfolio name cannot be empty.");
      return "";
    } else if (f.equals("import")) {
      if (model.userPortfolioExists(name)) {
        return name;
      } else {
        view.error("Portfolio does not exist.");
        return "";
      }
    } else if (f.equals("made")) {
      if (model.portfolioExists(name)) {
        return name;
      } else {
        view.error("Portfolio does not exist");
        return "";
      }
    } else if (model.portfolioExists(name)) {
      view.error("Portfolio already exists.");
      return "";
    }
    return name;
  }

  private String validateTickerSymbol(int option) {
    String tS = view.getField("tS", option);
    if (model.validTS(tS)) {
      return tS;
    } else {
      view.error("Invalid ticker symbol.");
      return "";
    }
  }

  private double validateStockQuantity(String f, String pN, String tS, String pD, int option) {
    try {
      double quantity = Double.parseDouble(view.getField("sQ", option));
      if (f.equals("new") && quantity > 0 && quantity % 1 == 0) {
        return quantity;
      } else if (f.equals("add")) {
        if (quantity < 0 && Math.abs(quantity) <= model.getStockQuantity(pN, tS, pD)) {
          return quantity;
        } else if (quantity % 1 == 0 && quantity > 0) {
          return quantity;
        }
      }
      view.error("Invalid stock quantity.");
    } catch (NumberFormatException e) {
      view.error("Invalid stock quantity.");
    }
    return 0;
  }

  private String validateDatePurchased(String tS, int option) {
    String date = view.getField("dP", option);
    if (model.validDate(tS, date)) {
      return date;
    } else {
      view.error("Invalid date.");
      return "";
    }
  }

  private String validatePortfolioDate(int option) {
    String date = view.getField("dP", option);
    if (model.validPortfolioDate(date)) {
      return date;
    } else {
      view.error("Invalid date.");
      return "";
    }
  }
}