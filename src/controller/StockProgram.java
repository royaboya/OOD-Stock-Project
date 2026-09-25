package controller;

import java.io.InputStreamReader;

import model.Model;

import view.GUIView;
import view.View;

/**
 * Entry point for the stock program by setting up the MVC components and starting it.
 */
public class StockProgram {

  /**
   * Main method to run the stock program by calling the controller's go method.
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      new GUIController(new GUIView(), new Model()).start();
    } else if (args.length == 1 && args[0].equals("-text")) {
      new Controller(new View(System.out),
              new InputStreamReader(System.in), System.out).start(new Model());
    } else {
      System.out.println("Error: invalid command line arguments");
    }
  }
}