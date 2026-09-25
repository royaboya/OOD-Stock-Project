import java.awt.event.ActionListener;

import view.IGUIView;

/**
 * Mock class to test the GUIView's action calls.
 */
public class MockGUIView implements IGUIView {
  final StringBuilder log;

  public MockGUIView() {
    this.log = new StringBuilder();
  }

  public void record(String s) {
    this.log.append(s).append("\n");
  }

  public void setVisible(boolean b) {
    this.log.append(b);

  }

  @Override
  public void setListener(ActionListener listener) {
    record("listener");
  }

  @Override
  public void updateScreen(String command) {
    record("update screen");
  }

  @Override
  public void mainMenu() {
    record("main menu");
  }

  @Override
  public String getField(String field, int option) {
    record("get field");
    return "";
  }

  @Override
  public void clearTextFields() {
    record("clear text");
  }

  @Override
  public void error(String message) {
    record("error");
  }

  @Override
  public void success(String message) {
    record("success");
  }

  @Override
  public void displayResults(String result, String name, String date) {
    record("display");
  }
}
