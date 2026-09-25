package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a controller in the MVC design pattern. This determines the flow of the graphical user
 * interface program, handling of user inputs, and interacting with the model and view.
 */
public interface IGUIController extends ActionListener {

  /**
   * Handles the actions performed by the user in the GUI.
   *
   * @param event the event to be processed which is triggered by the user via GUI
   */
  void actionPerformed(ActionEvent event);

  /**
   * Starts the GUI application by setting up listeners and making the view visible.
   */
  void start();
}