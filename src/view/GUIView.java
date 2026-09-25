package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

/**
 * Represents the view for the Stocks Program with a graphical user interface that utilizes the
 * JFrame Swing framework, allowing user input and feedback.
 */
public class GUIView extends JFrame implements IGUIView {
  private final JPanel base;
  private final JButton exit;
  private final JButton option1;
  private final JTextField portfolioName1;
  private final JTextField tickerSymbol1;
  private final JTextField stockQuantity1;
  private final JTextField datePurchased1;
  private final JButton submitCreatePortfolio;
  private final JButton back1;
  private final JButton option2;
  private final JTextField portfolioName2;
  private final JTextField tickerSymbol2;
  private final JTextField stockQuantity2;
  private final JTextField datePurchased2;
  private final JButton submitAddRemove;
  private final JButton back2;
  private final JButton option3;
  private final JTextField portfolioName3;
  private final JTextField datePurchased3;
  private final JButton submitEvaluatePortfolio;
  private final JButton back3;
  private final JButton option4;
  private final JTextField portfolioName4;
  private final JButton submitSavePortfolio;
  private final JButton back4;
  private final JButton option5;
  private final JTextField portfolioName5;
  private final JButton submitImportPortfolio;
  private final JButton back5;
  private final JPanel resultScreen;
  private final JButton back6;

  /**
   * Constructs a GUIView for the Portfolio Manager application, initializing the main window with
   * panels for different actions: creating a portfolio, adding/removing stocks, evaluating a
   * portfolio, saving a portfolio, and importing a portfolio.
   */
  public GUIView() {
    this.setTitle("Portfolio Manager");
    setSize(800, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    base = new JPanel(new CardLayout());
    JLabel selectOptions = createJLabel("Select one of the options listed below:");
    selectOptions.setFont(new Font("Arial", Font.BOLD, 14));
    JPanel menuScreen = createPanel();
    exit = createButton("Exit", "exit");

    option1 = createButton("Create a portfolio", "create");
    JPanel createPortfolioScreen = createPanel();
    portfolioName1 = createTextField();
    tickerSymbol1 = createTextField();
    stockQuantity1 = createTextField();
    datePurchased1 = createTextField();
    submitCreatePortfolio = createButton("Submit", "submitCreatePortfolio");
    back1 = createButton("Back", "menu1");
    startPanel(createPortfolioScreen, "Create a portfolio");
    addToPanel(createPortfolioScreen, "pN", portfolioName1);
    addToPanel(createPortfolioScreen, "tS", tickerSymbol1);
    addToPanel(createPortfolioScreen, "sQ", stockQuantity1);
    addToPanel(createPortfolioScreen, "dP", datePurchased1);
    finishPanel(createPortfolioScreen, submitCreatePortfolio, back1);

    option2 = createButton("Add/remove stocks from a portfolio", "addRemove");
    JPanel addRemoveScreen = createPanel();
    portfolioName2 = createTextField();
    tickerSymbol2 = createTextField();
    stockQuantity2 = createTextField();
    datePurchased2 = createTextField();
    submitAddRemove = createButton("Submit", "submitAddRemove");
    back2 = createButton("Back", "menu2");
    startPanel(addRemoveScreen, "Add/remove stocks from a portfolio");
    addToPanel(addRemoveScreen, "pN", portfolioName2);
    addToPanel(addRemoveScreen, "tS", tickerSymbol2);
    addToPanel(addRemoveScreen, "Enter positive quantity of stocks to buy or a negative quantity "
            + "to sell:", stockQuantity2);
    addToPanel(addRemoveScreen, "dP", datePurchased2);
    finishPanel(addRemoveScreen, submitAddRemove, back2);

    option3 = createButton("Evaluate a portfolio on a date", "evaluate");
    JPanel evaluateScreen = createPanel();
    portfolioName3 = createTextField();
    datePurchased3 = createTextField();
    submitEvaluatePortfolio = createButton("Submit", "submitEvaluatePortfolio");
    back3 = createButton("Back", "menu3");
    startPanel(evaluateScreen, "Evaluate a portfolio on a date");
    addToPanel(evaluateScreen, "pN", portfolioName3);
    addToPanel(evaluateScreen, "dP", datePurchased3);
    finishPanel(evaluateScreen, submitEvaluatePortfolio, back3);

    option4 = createButton("Save a portfolio to a file", "save");
    JPanel saveScreen = createPanel();
    portfolioName4 = createTextField();
    submitSavePortfolio = createButton("Submit", "submitSavePortfolio");
    back4 = createButton("Back", "menu4");
    startPanel(saveScreen, "Save a portfolio to a file");
    addToPanel(saveScreen, "pN", portfolioName4);
    finishPanel(saveScreen, submitSavePortfolio, back4);

    option5 = createButton("Import a portfolio into the program", "import");
    JPanel importScreen = createPanel();
    portfolioName5 = createTextField();
    submitImportPortfolio = createButton("Submit", "submitImportPortfolio");
    back5 = createButton("Back", "menu5");
    startPanel(importScreen, "Import a portfolio into the program");
    addToPanel(importScreen, "pN", portfolioName5);
    finishPanel(importScreen, submitImportPortfolio, back5);

    resultScreen = createPanel();
    back6 = createButton("Back", "menu6");

    startPanel(menuScreen, "Welcome to our portfolio management program!");
    menuScreen.add(createSpacer());
    menuScreen.add(selectOptions);
    menuScreen.add(option1);
    menuScreen.add(option2);
    menuScreen.add(option3);
    menuScreen.add(option4);
    menuScreen.add(option5);
    menuScreen.add(exit);

    base.add(menuScreen, "menu");
    base.add(createPortfolioScreen, "create");
    base.add(addRemoveScreen, "addRemove");
    base.add(evaluateScreen, "evaluate");
    base.add(saveScreen, "save");
    base.add(importScreen, "import");
    base.add(resultScreen, "result");
    this.add(base);
    pack();
  }

  @Override
  public void setListener(ActionListener listener) {
    option1.addActionListener(listener);
    option2.addActionListener(listener);
    option3.addActionListener(listener);
    option4.addActionListener(listener);
    option5.addActionListener(listener);
    exit.addActionListener(listener);
    submitCreatePortfolio.addActionListener(listener);
    submitAddRemove.addActionListener(listener);
    submitEvaluatePortfolio.addActionListener(listener);
    submitSavePortfolio.addActionListener(listener);
    submitImportPortfolio.addActionListener(listener);
    back1.addActionListener(listener);
    back2.addActionListener(listener);
    back3.addActionListener(listener);
    back4.addActionListener(listener);
    back5.addActionListener(listener);
    back6.addActionListener(listener);
  }

  @Override
  public void updateScreen(String command) {
    CardLayout cl = (CardLayout) base.getLayout();
    cl.show(base, command);
  }

  @Override
  public void mainMenu() {
    updateScreen("menu");
  }

  @Override
  public String getField(String field, int option) {
    if ("pN".equals(field)) {
      if (option == 1) {
        return portfolioName1.getText().trim();
      } else if (option == 2) {
        return portfolioName2.getText().trim();
      } else if (option == 3) {
        return portfolioName3.getText().trim();
      } else if (option == 4) {
        return portfolioName4.getText().trim();
      } else if (option == 5) {
        return portfolioName5.getText().trim();
      }
    } else if ("tS".equals(field)) {
      if (option == 1) {
        return tickerSymbol1.getText().trim();
      } else if (option == 2) {
        return tickerSymbol2.getText().trim();
      }
    } else if ("sQ".equals(field)) {
      if (option == 1) {
        return stockQuantity1.getText().trim();
      } else if (option == 2) {
        return stockQuantity2.getText().trim();
      }
    } else if ("dP".equals(field)) {
      if (option == 1) {
        return datePurchased1.getText().trim();
      } else if (option == 2) {
        return datePurchased2.getText().trim();
      } else if (option == 3) {
        return datePurchased3.getText().trim();
      }
    }
    return "";
  }

  @Override
  public void clearTextFields() {
    portfolioName1.setText("");
    tickerSymbol1.setText("");
    stockQuantity1.setText("");
    datePurchased1.setText("");
    portfolioName2.setText("");
    tickerSymbol2.setText("");
    stockQuantity2.setText("");
    datePurchased2.setText("");
    datePurchased3.setText("");
    portfolioName1.setText("");
    portfolioName2.setText("");
    portfolioName3.setText("");
    portfolioName4.setText("");
    portfolioName5.setText("");
  }

  @Override
  public void error(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void success(String message) {
    JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void displayResults(String result, String name, String date) {
    resultScreen.removeAll();
    String[] columns = {"Stock Symbol", "Stock Count", "Purchase Date", "Stock Value"};
    String[] lines = result.split("\n");
    List<String[]> dataList = new ArrayList<>();
    for (int i = 2; i < lines.length - 1; i++) {
      String line = lines[i].replaceAll("\\|", "");
      String[] parts = line.trim().split("\\s+");
      dataList.add(parts);
    }
    String[][] data = new String[dataList.size()][4];
    dataList.toArray(data);
    JTable table = new JTable(data, columns);
    JScrollPane scrollPane = new JScrollPane(table);
    table.setFillsViewportHeight(true);
    JPanel panel = new JPanel(new BorderLayout());

    JLabel header = new JLabel("Portfolio: " + name + " (Evaluated on " + date + ")",
            JLabel.CENTER);
    panel.add(header, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);
    double totalValue = Double.parseDouble(lines[lines.length - 1].split(":")[1].trim());
    JLabel footer = new JLabel(String.format("Total value: %.2f", totalValue), JLabel.CENTER);
    panel.add(footer, BorderLayout.SOUTH);
    resultScreen.add(panel);
    resultScreen.add(back6);
    clearTextFields();
  }

  private JPanel createPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setPreferredSize(new Dimension(500, 500));
    return panel;
  }

  private JButton createButton(String text, String actionCommand) {
    JButton button = new JButton(text);
    button.setActionCommand(actionCommand);
    button.setAlignmentX(Component.CENTER_ALIGNMENT);
    button.setMaximumSize(new Dimension(250, 50));
    return button;
  }

  private JPanel createSpacer() {
    JPanel spacer = new JPanel();
    spacer.setMaximumSize(new Dimension(0, 20));
    return spacer;
  }

  private JLabel createJLabel(String flag) {
    JLabel jLabel;
    switch (flag) {
      case "pN":
        jLabel = new JLabel("Enter portfolio name:");
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return jLabel;
      case "tS":
        jLabel = new JLabel("Enter desired stock's ticker symbol:");
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return jLabel;
      case "sQ":
        jLabel = new JLabel("Enter positive quantity of stock:");
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return jLabel;
      case "dP":
        jLabel = new JLabel("Enter purchase date of the stock (YYYY-MM-DD):");
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return jLabel;
      default:
        jLabel = new JLabel(flag);
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return jLabel;
    }
  }

  private JTextField createTextField() {
    JTextField field = new JTextField("");
    field.setMaximumSize(new Dimension(200, 30));
    field.setAlignmentX(Component.CENTER_ALIGNMENT);
    return field;
  }

  private void startPanel(JPanel panel, String text) {
    panel.add(createSpacer());
    JLabel temp = createJLabel(text);
    temp.setFont(new Font("Arial", Font.BOLD, 16));
    panel.add(temp);
  }

  private void addToPanel(JPanel panel, String text, JTextField field) {
    panel.add(createSpacer());
    panel.add(createJLabel(text));
    panel.add(field);
  }

  private void finishPanel(JPanel panel, JButton submit, JButton back) {
    panel.add(createSpacer());
    panel.add(submit);
    panel.add(back);
  }
}