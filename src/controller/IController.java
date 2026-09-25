package controller;

import model.IModel;

/**
 * Represents a controller in the MVC design pattern. This determines the flow of the command line
 * interface program, handling of user inputs, and interacting with the model and view.
 */
public interface IController {

  /**
   * Starts the program by displaying the menu and handling user inputs by outputting available
   * options and delegating actions to the model and view.
   */
  void start(IModel stock);
}