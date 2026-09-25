package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileWriter;

import java.text.SimpleDateFormat;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Queries and updates stock data with methods to find a specific stock and updating a CSV file by
 * querying stock data.
 */
public class Query {

  /**
   * Reads through the CSV and looks for day with the Calendar date and returns it.
   *
   * @param tickerSymbol stock's stock symbol
   * @param date         date of the desired stock
   * @return String line of stock data if found, empty string otherwise.
   */
  protected String findStock(String tickerSymbol, String date) {
    try {
      FileReader fileReader = new FileReader("CSVFiles/" + tickerSymbol + ".csv");
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        if (line.contains(date)) {
          return line;
        }
      }
    } catch (Exception e) {
      return "";
    }
    return "";
  }

  /**
   * Updates the CSV for a stock if it is out of date.
   *
   * @param tickerSymbol the stock's stock symbol
   * @throws IllegalStateException If CSV file is already updated
   */
  protected void updateCSV(String tickerSymbol) throws IllegalStateException {
    File csvFile = new File(String.format("CSVFiles/%s.csv", tickerSymbol));
    if (csvFile.exists() && isUpdated(tickerSymbol)) {
      return;
    }
    String output = queryStock(tickerSymbol);
    if (output.contains("https://www.alphavantage.co/premium/")) {
      return;
    }
    try (FileWriter fileWriter = new FileWriter(csvFile)) {
      fileWriter.write(output);
    } catch (IOException e) {
      System.out.println("Cannot update stock: " + e.getMessage());
    }
  }

  protected List<String> getXDates(String tickerSymbol, String date, int x) {
    List<String> stockData = new ArrayList<>();
    try {
      FileReader fileReader = new FileReader("CSVFiles/" + tickerSymbol + ".csv");
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        if (line.contains(date)) {
          stockData.add(line.split(",")[4]);
          for (int i = 0; i < x - 1; i++) {
            line = bufferedReader.readLine();
            stockData.add(line.split(",")[4]);
          }
        }
      }
    } catch (Exception e) {
      System.out.println("The file was not found and: " + e.getMessage());
    }
    return stockData;
  }

  /**
   * Gets all dates within the ranges inputted, assumes the dates are valid.
   * @param tickerSymbol the stock Symbol
   * @param startDate the from Date
   * @param endDate the to Date
   * @return a List of all the dates in the range
   */
  protected List<String> getDatesInRange(String tickerSymbol, String startDate,
                                              String endDate) {
    List<String> stockData = new ArrayList<>();
    try {
      FileReader fileReader = new FileReader("CSVFiles/" + tickerSymbol + ".csv");
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        if (line.contains(endDate)) {
          stockData.add(line);
          break;
        }
      }
      while ((line = bufferedReader.readLine()) != null) {
        if (line.contains(startDate)) {
          stockData.add(line);
          break;
        }
        stockData.add(line);
      }
    } catch (Exception e) {
      System.out.println("File not found: " + e.getMessage());
    }
    return stockData;
  }

  // Checks if the CSV is updated with the latest date available
  private static boolean isUpdated(String tickerSymbol) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String currentDate = dateFormat.format(Calendar.getInstance().getTime());
    int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("CSVFiles/" +
            tickerSymbol + ".csv"))) {
      bufferedReader.readLine();
      String line = bufferedReader.readLine();
      String[] data = line.split(",");
      Calendar csvCalendar = Calendar.getInstance();
      csvCalendar.setTime(dateFormat.parse(data[0]));
      int csvDay = csvCalendar.get(Calendar.DAY_OF_WEEK);
      if ((day == Calendar.SATURDAY || day == Calendar.SUNDAY) && csvDay == Calendar.FRIDAY) {
        return true;
      } else if (line.contains(currentDate)) {
        return true;
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
    return false;
  }

  // Queries the AlphaVantage API and returns the string with csv data of the stock
  protected static String queryStock(String tickerSymbol) {
    String apiKey = "7ATBPTTFQSAV4IUI";
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + tickerSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("The alphavantage API has either changed or no longer works");
    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try {
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      return "No data found";
    }
    return output.toString();
  }
}