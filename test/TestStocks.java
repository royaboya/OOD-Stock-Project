import org.junit.Before;
import org.junit.Test;

import model.IModel;
import model.Model;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Model's methods for the Stocks program.
 */
public class TestStocks {
  private IModel model;

  @Before
  public void setUp() throws Exception {
    this.model = new Model();
  }

  @Test
  public void testEvaluateChange() {
    // test on one day range
    String result = model.evaluateChange("AMZN", "2024-06-04", "2024-06-05");
    assertEquals("1.94", result);
    // test on multiple day range
    String result2 = model.evaluateChange("AMZN", "2024-05-14", "2024-06-03");
    assertEquals("-8.73", result2);
    String result3 = model.evaluateChange("AMZN", "1999-11-01", "2024-06-05");
    assertEquals("112.15", result3);
  }

  @Test
  public void testEvaluateMovingAverage() {
    String result = model.evaluateMovingAverage("NOC", "2024-05-28", 6);
    assertEquals("467.45", result);
    // Test on multiple day range
    String result2 = model.evaluateMovingAverage("NOC", "2024-05-28", 5);
    assertEquals("467.15", result2);
    // Test on one day range
    String result3 = model.evaluateMovingAverage("NOC", "2024-05-28", 1);
    assertEquals("455.5", result3);
  }

  @Test
  public void determineCrossOvers() {
    // determineCrossOvers() with x = 5
    String result = model.determineCrossover("BA", "2024-05-08", "2024-06-05", 5);
    assertEquals("2024-06-05, 2024-06-04, "
            + "2024-06-03, 2024-05-31, 2024-05-22, 2024-05-21, "
            + "2024-05-20, 2024-05-17, 2024-05-16, 2024-05-14, "
            + "2024-05-09, 2024-05-08", result);
    // determine crossovers with x  = 8
    String result2 = model.determineCrossover("BA", "2024-05-08", "2024-06-05", 8);
    assertEquals("2024-06-05, 2024-06-04, 2024-06-03, "
            + "2024-05-31, 2024-05-22, 2024-05-21, 2024-05-20, 2024-05-17, "
            + "2024-05-16, 2024-05-14, 2024-05-10, 2024-05-09, 2024-05-08", result2);
    String result3 = model.determineCrossover("BA", "2024-05-08", "2024-06-03", 30);
    assertEquals("2024-06-03, 2024-05-31, 2024-05-22, 2024-05-21, 2024-05-20,"
            + " 2024-05-17, 2024-05-16, 2024-05-15, 2024-05-14, 2024-05-13, 2024-05-10,"
            + " 2024-05-09, 2024-05-08", result3);
  }

  @Test
  public void testListPortfoliosAndContents() {
    // Create one portfolio
    model.createPortfolio("Port1");
    String expected1 = "=== PORTFOLIO CONTENTS ===\n"
            + "\n"
            + "Portfolio: Port1\n"
            + "Stock Symbol | Stock Count | Purchase Date | Stock Value\n"
            + "Total value:                                 0.00\n";
    // Check listPortfoliosAndContents for one portfolio
    assertEquals(expected1, model.listPortfoliosAndContents());

    model.addStock("Port1", "AMZN", 10, "2024-06-04");
    String expected2 = "=== PORTFOLIO CONTENTS ===\n"
            + "\n"
            + "Portfolio: Port1\n"
            + "Stock Symbol | Stock Count | Purchase Date | Stock Value\n"
            + "AMZN           10            2024-06-04      1793.40             \n"
            + "Total value:                                 1793.40";
    // Check listPortfoliosAndContents after adding a stock
    assertEquals(expected2, model.listPortfoliosAndContents());
    model.createPortfolio("Port2");
    String expected3 = "=== PORTFOLIO CONTENTS ===\n" + "\n"
            + "Portfolio: Port2\n"
            + "Stock Symbol | Stock Count | Purchase Date | Stock Value\n"
            + "Total value:                                 0.00\n"
            + "\n"
            + "Portfolio: Port1\n"
            + "Stock Symbol | Stock Count | Purchase Date | Stock Value\n"
            + "AMZN           10            2024-06-04      1793.40             \n"
            + "Total value:                                 1793.40";
    // Check listPortfoliosAndContents after adding a portfolio
    //assertEquals(expected3, model.listPortfoliosAndContents());
    model.addStock("Port2", "BA", 30, "2024-06-04");
    String expected4 = "=== PORTFOLIO CONTENTS ===\n"
            + "\n"
            + "Portfolio: Port2\n"
            + "Stock Symbol | Stock Count | Purchase Date | Stock Value\n"
            + "BA             30            2024-06-04      5658.60             \n"
            + "Total value:                                 5658.60\n"
            + "\n"
            + "Portfolio: Port1\n"
            + "Stock Symbol | Stock Count | Purchase Date | Stock Value\n"
            + "AMZN           10            2024-06-04      1793.40             \n"
            + "Total value:                                 1793.40";
    // Check listPortfoliosAndContents after adding a portfolio
    assertEquals(expected4, model.listPortfoliosAndContents());
    model.addStock("Port2", "RTX", 5, "2024-06-04");
    String expected5 = "=== PORTFOLIO CONTENTS ===\n" + "Portfolio: Port2" + System.lineSeparator()
            + "BA->30" + System.lineSeparator() + "RTX->5" + System.lineSeparator()
            + "Portfolio: Port1" + System.lineSeparator() + "AMZN->10"
            + System.lineSeparator();
  }


  @Test
  public void testValidTS() {
    //  validTS test on a invalid ticker symbol
    assertEquals(false, model.validTS("krogincorp"));
    //  validTS test on a real publicly listed company
    assertEquals(true, model.validTS("RTX"));
  }

  @Test
  public void testValidDates() {
    //  validDate test on the first ever stock day
    assertEquals(true, model.validDate("AMZN", "1999-11-01"));
    // validDate test on a recent day
    assertEquals(true, model.validDate("AMZN", "2024-06-06"));
    // validDate test on date that does not exist (weekend)
    assertEquals(false, model.validDate("AMZN", "2024-06-02"));
    // validDate test on holiday (independence day)
    assertEquals(false, model.validDate("AMZN", "2024-08-15"));
    // validDate test on a badly formatted date
    assertEquals(false, model.validDate("AMZN", "10000-12-31"));
    // validDate test on zeroes
    assertEquals(false, model.validDate("AMZN", "0000-00-00"));
  }

  @Test
  public void testValidPortfolioDate() {
    // validPortfolioDate when the date is not length 10
    assertEquals(false, model.validPortfolioDate("2024-06-06333"));
    // validPortfolioDate when the date has a letter
    assertEquals(false, model.validPortfolioDate("20d-06-06333"));
    // validPortfolioDate when the date has a special character
    assertEquals(false, model.validPortfolioDate("2024-06-%3"));
    // validPortfolioDate when the date has a special character
    assertEquals(false, model.validPortfolioDate("2024-06-%3"));
    // validPortfolioDate when the date is a Weekend
    assertEquals(false, model.validPortfolioDate("2024-06-02"));
    // validPortfolioDate when the date is a real calendar date
    assertEquals(true, model.validPortfolioDate("2021-06-02"));
  }

  @Test
  public void testPortfolioExists() {
    // portfolioExists when no portfolios with the name exist
    assertEquals(false, model.portfolioExists("name"));
    // portfolioExists when a portfolio with the name exist
    model.createPortfolio("krog");
    assertEquals(true, model.portfolioExists("krog"));
  }

  @Test
  public void testGetStockQuantity() {
    model.createPortfolio("testing");
    // check stock quantity before adding
    assertEquals(0.0, model.getStockQuantity("testing", "BA", "2024-06-11"), 0.001);
    model.addStock("testing", "BA", 100, "2024-06-11");
    // check stock quantity after adding
    assertEquals(100, model.getStockQuantity("testing", "BA", "2024-06-11"), 0.001);
  }

  @Test
  public void testStockCountPortfolio() {
    model.createPortfolio("testing");
    assertEquals(0, model.stockCountPortfolio("testing"));

    model.addStock("testing", "BA", 100, "2024-06-11");
    assertEquals(1, model.stockCountPortfolio("testing"));

    model.addStock("testing", "BA", 100, "2024-06-12");
    assertEquals(2, model.stockCountPortfolio("testing"));

    model.addStock("testing", "GD", 100, "2024-06-12");
    assertEquals(3, model.stockCountPortfolio("testing"));


  }

  @Test
  public void testGenerateGraph() {
    // test generate graph from one week to another (portfolio)
    model.importPortfolio("testingImport");
    String expected = "Performance of Portfolio: 'testingImport' from 2024-06-05 to 2024-06-11 \n"
            + "2024-06-05: **************************************************\n"
            + "2024-06-06: **************************************************\n"
            + "2024-06-07: **************************************************\n"
            + "2024-06-10: **************************************************\n"
            + "2024-06-11: ************************************************\n"
            + "Scale: *= $382.84";
    assertEquals(expected, model.generateGraph("testingImport",  "", "2024-06-05", "2024-06-11"));
    // test generate graph from one year to another (portfolio)
    model.importPortfolio("dog");
    String expected2 = "Performance of Portfolio: 'dog' from 2023-06-05 to 2024-06-11 \n" +
            "2023-06-05: **********************************\n"
            + "2023-06-15: ***********************************\n"
            + "2023-06-28: ***********************************\n"
            + "2023-07-11: ************************************\n"
            + "2023-07-21: ************************************\n"
            + "2023-08-02: *************************************\n"
            + "2023-08-14: **************************************\n"
            + "2023-08-24: *************************************\n"
            + "2023-09-06: ************************************\n"
            + "2023-09-18: **************************************\n"
            + "2023-09-28: ************************************\n"
            + "2023-10-10: **************************************\n"
            + "2023-10-20: *************************************\n"
            + "2023-11-01: ***************************************\n"
            + "2023-11-13: ****************************************\n"
            + "2023-11-24: *****************************************\n"
            + "2023-12-06: *****************************************\n"
            + "2023-12-18: ******************************************\n"
            + "2023-12-29: ******************************************\n"
            + "2024-01-11: ******************************************\n"
            + "2024-01-24: *******************************************\n"
            + "2024-02-05: *********************************************\n"
            + "2024-02-15: *********************************************\n"
            + "2024-02-28: **********************************************\n"
            + "2024-03-11: **********************************************\n"
            + "2024-03-21: ***********************************************\n"
            + "2024-04-03: *************************************************\n"
            + "2024-04-15: ************************************************\n"
            + "2024-04-25: ***********************************************\n"
            + "2024-06-11: ************************************************* \n"
            + "Scale: *= $194.19";
    assertEquals(expected2, model.generateGraph("dog", "", "2023-06-05", "2024-06-11"));

    // test on stocks from multiple months away
    String expected3 = "Performance of Stock: 'RTX' from 2023-03-05 to 2024-06-11 \n" +
            "1999-11-01: *******************\n"
            + "2000-08-24: ********************\n"
            + "2001-06-20: ************************\n"
            + "2002-04-22: **********************\n"
            + "2003-02-13: *******************\n"
            + "2003-12-08: ****************************\n"
            + "2004-10-04: ******************************\n"
            + "2005-07-28: ****************\n"
            + "2006-05-23: ********************\n"
            + "2007-03-20: *********************\n"
            + "2008-01-11: ***********************\n"
            + "2008-11-04: ******************\n"
            + "2009-08-31: *******************\n"
            + "2010-06-25: **********************\n"
            + "2011-04-19: **************************\n"
            + "2012-02-13: ***************************\n"
            + "2012-12-07: **************************\n"
            + "2013-10-03: *********************************\n"
            + "2014-07-30: **********************************\n"
            + "2015-05-26: *************************************\n"
            + "2016-03-18: ********************************\n"
            + "2017-01-11: ***********************************\n"
            + "2017-11-03: ***************************************\n"
            + "2018-08-30: ******************************************\n"
            + "2019-06-27: *****************************************\n"
            + "2020-04-22: ********************\n"
            + "2021-02-16: ***********************\n"
            + "2021-12-08: ****************************\n"
            + "2022-10-04: ***************************\n"
            + "2024-06-11: ********************************** \n"
            + "Scale: *= $3.14";
    assertEquals(expected3, model.generateGraph("", "RTX", "2023-03-05", "2024-06-11"));

  }

  @Test
  public void testDistributeWeights() {
    // test rebalancing by distributing weights
    model.createPortfolio("testing");
    model.addStock("testing", "BA", 10, "2024-06-04");
    model.addStock("testing", "AMZN", 5, "2024-06-04");

    // distribute it with respect to "2024-06-11", stocks are still
    // labelled with original purchase dates
    model.distributeWeights("testing", "2024-06-11", "50,50");


    double ba = model.getStockQuantity("testing", "BA", "2024-06-04");
    double amzn = model.getStockQuantity("testing", "AMZN", "2024-06-04");
    // expected shares for balancing on the specified date
    assertEquals(7.523, ba, 0.001);
    assertEquals(7.453, amzn, 0.001);

    // test with different weights
    model.distributeWeights("testing", "2024-06-11", "30,70");
    double ba2 = model.getStockQuantity("testing", "BA", "2024-06-04");
    double amzn2 = model.getStockQuantity("testing", "AMZN", "2024-06-04");
    assertEquals(4.513, ba2, 0.001);
    assertEquals(10.435, amzn2, 0.001);

  }

  @Test
  public void testOneStock() {
    model.createPortfolio("testing");
    assertEquals(false, model.oneStock("testing"));
    model.addStock("testing", "BA", 100, "2024-06-11");
    assertEquals(true, model.oneStock("testing"));
    model.addStock("testing", "AMZN", 100, "2024-06-11");
    assertEquals(false, model.oneStock("testing"));
  }

  @Test
  public void testUserPortfolioExists() {
    // test on port that does not exist in file system
    assertEquals(false, model.userPortfolioExists("test"));
    // test on port that does exist in system
    assertEquals(true, model.userPortfolioExists("dog"));
  }

  @Test
  public void testListStocks() {
    model.createPortfolio("testing");
    assertEquals("", model.listStocks("testing"));
    model.addStock("testing", "AMZN", 100, "2024-06-11");
    model.addStock("testing", "BA", 100, "2024-06-11");
    assertEquals("AMZN, BA, ", model.listStocks("testing"));
  }

  @Test
  public void testSaveAndImportPortfolio() {
    IModel m = new Model();
    // test on import portfolio that does not exist
    m.importPortfolio("portfolioDoesNotExist");
    assertEquals("", m.listStocks("portfolioDoesNotExist"));

    // import from directory
    m.createPortfolio("testingImport");
    m.addStock("testingImport", "BA", 100, "2024-06-11");
    m.savePortfolio("testingImport");
    // test import by importing into a new model
    IModel m2 = new Model();
    m2.importPortfolio("testingImport");
    assertEquals("BA, ", m2.listStocks("testingImport"));
  }

  @Test
  public void testValidRatio() {
    // Test On invalid ratio (adds to 30, 60)
    assertEquals(false, model.validRatio("10, 20", 2));
    assertEquals(false, model.validRatio("10, 20, 30", 2));
    // Test on invalid ratio (no commas, greater than 100)
    assertEquals(false, model.validRatio("10 20", 2));
    assertEquals(false, model.validRatio("100 300", 2));
    // Test on valid ratios (spaces & no spaces)
    assertEquals(true, model.validRatio("50, 50", 2));
    // Test on valid ratios (make sure numbers match stock count)
    assertEquals(true, model.validRatio("30, 50, 20", 3));
    assertEquals(false, model.validRatio("30, 50, 20", 2));
  }
}