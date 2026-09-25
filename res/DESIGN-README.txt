- STOCK PROGRAM DESIGN -

Our stock program follows the MVC design pattern with the respective three packages: model, view,
and controller.

- DESIGN/CODE CHANGES -
To support a GUI for the user, we created another controller and view with respective interfaces.
The new controller handles the logic of user inputs and interacts with the model to utilize the
previously made functionalities. The new view handles creating and displaying the GUI elements.

- Model -
The model package contains a model class with two other classes, Stock and Portfolio, which
represent individual Stocks and Portfolios in the StockProgram. These classes each implement their
respective interfaces: IModel, IStock, and IPortfolio to determine what methods every model, Stock,
and Portfolio should have. Under the model package is another class, called Query that acts as a
utility class that helps deal with File Reading, Creation, and Editing actions.

- View -
The view package contains a view class that implements an IView interface and serves to display the
menus, actions, result, and general input feedback depending on the user input to the controller.

- Controller -
The controller package contains a controller class that implements an IController interface and
serves to link inputs and outputs to the model and the view. In the controller package is another
class called StockProgram that hosts the main method to run the controller from.