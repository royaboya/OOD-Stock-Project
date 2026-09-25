import org.junit.Test;

import java.awt.event.ActionEvent;
import java.io.Reader;
import java.io.StringReader;

import controller.Controller;

import controller.GUIController;
import controller.IGUIController;
import model.IModel;

import view.View;
import view.IView;

import static org.junit.Assert.assertEquals;

/**
 * Checks inputs for the Model class' methods from the controller.
 */
public class TestInputs {

  @Test
  public void testGUIController() {
    StringBuilder sb = new StringBuilder();
    IModel mock = new MockModel(sb);
    // Mock GUI View used so that for testing, we can call the log
    // instead of having to modify and break the original interface
    MockGUIView view = new MockGUIView();
    IGUIController control = new GUIController(view, mock);

    control.actionPerformed(new ActionEvent(view,0, "create" ));
    control.actionPerformed(new ActionEvent(view,0, "create" ));
    control.actionPerformed(new ActionEvent(view,0, "menu" ));

    String expected = "update screen\n"
            + "update screen\n"
            + "update screen\n"
            + "clear text\n"
            + "update screen\n";
    assertEquals(expected, view.log.toString());


  }

  @Test
  public void testOption1Inputs() {
    StringBuilder sb = new StringBuilder();
    MockModel mock = new MockModel(sb);
    IView view = new View(System.out);
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("1\nRTX\n2024-06-03\n2024-06-04\nq\n");
    Controller controller = new Controller(view, in, out);
    controller.start(mock);
    assertEquals("t = RTX sd = 2024-06-03 ed = 2024-06-04", mock.log.toString());
  }

  @Test
  public void testOption2Inputs() {
    StringBuilder sb = new StringBuilder();
    MockModel mock = new MockModel(sb);
    IView view = new View(System.out);
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("2\nRTX\n2024-06-03\n10\nq\n");
    Controller controller = new Controller(view, in, out);
    controller.start(mock);
    assertEquals("t = RTX sd = 2024-06-03 x = 10", mock.log.toString());
  }

  @Test
  public void testOption3Inputs() {
    StringBuilder sb = new StringBuilder();
    MockModel mock = new MockModel(sb);
    IView view = new View(System.out);
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("3\nRTX\n2024-06-03\n2024-06-04\n30\nq\n");
    Controller controller = new Controller(view, in, out);
    controller.start(mock);
    assertEquals("t = RTX sd = 2024-06-03 ed = 2024-06-04 x = 30", mock.log.toString());
  }

  @Test
  public void testOption4Inputs() {
    StringBuilder sb = new StringBuilder();
    MockModel mock = new MockModel(sb);
    IView view = new View(System.out);
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("4\n1\ntest\nBA\n100\n3\nm\nq\n");
    Controller controller = new Controller(view, in, out);
    controller.start(mock);
    assertEquals("n = testtest BA 100", mock.log.toString());
  }
}