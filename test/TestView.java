import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import controller.Controller;
import model.IModel;

import model.Model;

import static org.junit.Assert.assertEquals;

/**
  * Checks inputs from the Controller into the Model class.
 */
public class TestView {

  @Test
  public void testViewOption1() {
    StringBuilder sb = new StringBuilder();
    IModel model = new Model();
    MockView view = new MockView(sb);
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("1\nRTX\n2024-06-03\n2024-06-04\nq\n");
    Controller controller = new Controller(view, in, out);
    controller.start(model);
    assertEquals("MENU\nANSWER RESPONSE: 1\nREQUEST TICKER SYMBOL\nREQUEST START DATE\n"
            + "REQUEST END DATE\nEVALUATE CHANGE OUTPUT: 0.53\nMENU\nQUIT\n", view.log.toString());
  }

  @Test
  public void testViewOption2() {
    StringBuilder sb = new StringBuilder();
    IModel model = new Model();
    MockView view = new MockView(sb);
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("2\nRTX\n2024-06-03\n10\nq\n");
    Controller controller = new Controller(view, in, out);
    controller.start(model);
    assertEquals("MENU\nANSWER RESPONSE: 2\nREQUEST TICKER SYMBOL\nREQUEST DATA\nREQUEST DAYS\n"
            + "EVALUATE MOVING AVERAGE OUTPUT: 105.94\nMENU\nQUIT\n", view.log.toString());
  }

  @Test
  public void testViewOption3() {
    StringBuilder sb = new StringBuilder();
    IModel model = new Model();
    MockView view = new MockView(sb);
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("3\nRTX\n2024-06-03\n2024-06-04\n30\nq\n");
    Controller controller = new Controller(view, in, out);
    controller.start(model);
    assertEquals("MENU\nANSWER RESPONSE: 3\nREQUEST TICKER SYMBOL\nREQUEST START DATE\nREQUEST END "
                    + "DATE\nREQUEST DAYS\nEVALUATE CROSSOVER OUTPUT: 2024-06-04, 2024-06-03\nMENU"
                    + "\nQUIT\n",
            view.log.toString());
  }

  @Test
  public void testViewOption4() {
    StringBuilder sb = new StringBuilder();
    IModel model = new Model();
    MockView view = new MockView(sb);
    StringBuilder out = new StringBuilder();
    Reader in = new StringReader("4\n1\ntest\nBA\n100\n3\nm\nq\n");
    Controller controller = new Controller(view, in, out);
    controller.start(model);
    assertEquals("MENU\nANSWER RESPONSE: 4\nPORTFOLIO MENU\nPORTFOLIO RESPONSE: 1\nREQUEST NAME\n"
                    + "REQUEST TICKER SYMBOL\nREQUEST STOCK QUANTITY\nNEW PORTFOLIO: test"
                    + "\nPORTFOLIO MENU\nPORTFOLIO RESPONSE: 3\nLIST PORTFOLIOS: === PORTFOLIO "
                    + "CONTENTS ===\nPortfolio: test\nBA->100\n\nPORTFOLIO MENU\nBACK TO MENU\n"
                    + "MENU\nQUIT\n",
            view.log.toString());
  }
}