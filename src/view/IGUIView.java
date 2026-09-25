package view;

import java.awt.event.ActionListener;

/**
 * Interface for the GUI view of the Stocks Program.
 */
public interface IGUIView {

  /**
   * Sets the action listener for the GUI.
   *
   * @param listener the ActionListener to be set
   */
  void setListener(ActionListener listener);

  /**
   * Updates the current screen based on the given command.
   *
   * @param command the command indicating which screen to display
   */
  void updateScreen(String command);

  /**
   * Displays the main menu screen.
   */
  void mainMenu();

  /**
   * Retrieves the text from a specified input field.
   *
   * @param field  the field identifier (e.g. "pN" for portfolio name)
   * @param option the option indicating which input field to retrieve
   * @return the text from the specified input field
   */
  String getField(String field, int option);

  /**
   * Clears all the text fields in the GUI.
   */
  void clearTextFields();

  /**
   * Displays an error message to the user.
   *
   * @param message the error message to be displayed
   */
  void error(String message);

  /**
   * Displays a success message to the user.
   *
   * @param message the success message to be displayed
   */
  void success(String message);

  /**
   * Displays the results of evaluating a portfolio.
   *
   * @param result the evaluation results
   * @param name   the name of the portfolio
   * @param date   the date of evaluation
   */
  void displayResults(String result, String name, String date);


  /**
   * Sets the view GUI to be visible.
   * @param b a boolean value
   */
  void setVisible(boolean b);
}