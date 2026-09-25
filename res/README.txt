- PROGRAM FEATURES -

This readme describes the features of this program.

- Command Line Interface -
This program interacts with the user through a text-based menu system that allows users to navigate
various options such as stock analysis and portfolio management. This UI sanitizes and validates
inputs for dates, ticker symbols, portfolio names, and stock quantities which prompts the
appropriate error messages. The user can also exit the program when inside the main menu and has the
option to exit the portfolio management menu to the main menu.

- Graphical User Interface -
This program interacts with the user through a graphical-based system that allows users to navigate
various options with portfolio management. Users click on buttons for options to manage a portfolio
which include creating a portfolio, adding and removing stocks, evaluating a portfolio, and saving
and importing portfolios. This UI sanitizes and validates inputs for dates, ticker symbols,
portfolio names, and stock quantities which prompts the appropriate error pop-up messages. The user
can also exit the program by clicking the exit button or clicking the red window exit button.

- Stock Analysis -
This supports the feature of evaluating the change in stock price between two dates by calculating
the difference between the closing prices on the start and end dates. Users can evaluate the x-day
moving average of a stock on a specific date by calculating the average closing price over the
specified number of days leading up to the date. Users can determine crossover points where the
stock's closing price exceeds the moving average over a given period by identifying all crossover
points within a specified date range. Users can view a stock's performance over time by generating
a visual representation of a stock's price with a bar chart over a specified date range.

- Portfolio Management -
This supports creating a new portfolio by providing a unique name where the system checks for
existing portfolio names to prevent duplicates. Users can add and remove stocks that have been
purchased on a specific date to a specified portfolio by entering the stock ticker symbol and
quantity where the program validates the ticker symbol and ensures a valid quantity is added or
removed. If the stock already exists in the portfolio, the quantity is added to it. Users can
rebalance a portfolio on a specific date to evaluate it at and enter a ratio that represents the
percentage that each stock will make up of the portfolio. Users can view all existing portfolios
with their stocks and quantities. Users can evaluate and determine the composition of a portfolio on
a specific date by calculating the total value of the portfolio by multiplying the quantity with the
closing prices on the specified date of each stock for the composition which is added up to find the
total value of the portfolio. If a user selects the option to add a stock or get the value of a
portfolio before one is created, they have the option to go back to the stock portfolio options.
Users can save a portfolio that has been created to a file. Users can import a file with portfolio
data into the program which can then be viewed and edited. Users can view a portfolio's performance
over time by generating a visual representation of a portfolio's total value over a specified date
range.