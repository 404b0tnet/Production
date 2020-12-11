import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class Controller {

  // Database variables
  final String JDBC_DRIVER = "org.h2.Driver";
  final String DB_URL = "jdbc:h2:./res/Products";

  //  Database credentials
  final String USER = "";
  final String PASS = "";
  Connection conn = null;
  Statement stmt = null;

  // JavaFX Alert variable
  Alert alert = new Alert(AlertType.NONE);


  @FXML
  private ChoiceBox<String> itemTypeCBox;

  @FXML
  private ComboBox<String> cmbQuantity;

  @FXML
  private TextField productNameTF;

  @FXML
  private TextField manufacturerTF;

  //@FXML
  //private ListView<Product> produceListView;

  @FXML
  private TextArea productionLogTextArea;

  @FXML
  private TableView<Product> productTV;


  public void initialize() {
    for (int count = 1; count <= 10; count++) {
      cmbQuantity.getItems().add(String.valueOf(count));
    }

    for (ItemType it : ItemType.values()) {
      itemTypeCBox.getItems().addAll(String.valueOf(it));
    }

    cmbQuantity.getSelectionModel().selectFirst();

    ObservableList<Product> productLine = setupProductLineTable();
    productTV.setItems(productLine);

    // Display Production Log
    //productionRecordLog();

    //issue5Prod2();
    //testMultimedia();
  }


  @FXML
    //Event handler for adding a product in Product Line tab
  void addProductBtn() {

    String itemType = itemTypeCBox.getValue();
    String manufacturer = manufacturerTF.getText();
    String productName = productNameTF.getText();

    if (productName.length() < 1 || manufacturer.length() < 1
        || itemType.length() < 1) {
      alert.setAlertType(AlertType.WARNING);
      alert.setTitle("Error");
      alert.setHeaderText("All fields are required.");
      alert.setContentText("INVALID INPUT!");
      alert.showAndWait();
    } else {
      try {
        connectToDatabase(1);
        alert.setAlertType(AlertType.INFORMATION);
        alert.setHeaderText("Product was added!");
        alert.setContentText("");
        alert.showAndWait();
        closeDatabaseConnection();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
  }

  @FXML
    // Event handler for recording a product in the Produce tab
  void recordProductdBtn() {
    //PreparedStatement query = "INSERT INTO PRODUCTIONRECORD(NAME,"
    //    + "SERIAL_NUM, DATE_PRODUCED)"
    //    + "VALUES ('" + productName + "','" + itemType + "','"
    //    + manufacturer + "');";

  }

  public static void testMultimedia() {

    AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A",
        "Onkyo",

        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC",
        "M3U/PLS/WPL");

    Screen newScreen = new Screen("720x480", 40, 22);

    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101",
        "OracleProduction", newScreen,
        MonitorType.LCD);
    ArrayList<MultimediaControl> productList = new ArrayList<MultimediaControl>();
    productList.add(newAudioProduct);
    productList.add(newMovieProduct);

    for (MultimediaControl p : productList) {

      System.out.println("\n/******************/\n");
      System.out.println(p);
      p.play();
      p.stop();
      p.next();
      p.previous();
    }

    System.out.println("\n/******************/");
  }


  public void issue5Prod2() {

    Product productProduced = new Product(ItemType.AUDIO, "Apple", "iPod");

    // test constructor used when creating production records from user interface
    int numProduced = 3;  // this will come from the combobox in the UI
    int itemCount = 0;

    for (int productionRunProduct = 0; productionRunProduct < numProduced;
        productionRunProduct++) {
      ProductionRecord pr = new ProductionRecord(productProduced, itemCount++);
      // using the iterator as the product id for testing
      System.out.println(pr);

      //productionLog();
    }


  }


  public void connectToDatabase(int DBquery) throws SQLException {

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected to database.");

      //STEP 3: Execute a query
      final String SQL_AddProduct =
          "INSERT INTO PRODUCT(NAME,TYPE,MANUFACTURER) VALUES(?, ?, ?)";

      final String SQL_ProductLine =
          "SELECT * FROM PRODUCT";

      final String SQL_RecordProduct =
          "INSERT INTO PRODUCTIONRECORD(PRODUCT_ID,SERIAL_NUM,DATE_PRODUCED)"
              + "VALUES(?, ?, ?)";

      switch (DBquery) {
        case 1:
          PreparedStatement stmt = conn.prepareStatement(SQL_AddProduct);
          stmt.setString(1, productNameTF.getText());
          stmt.setString(2, itemTypeCBox.getValue());
          stmt.setString(3, manufacturerTF.getText());
          stmt.executeUpdate();

          break;
        case 2:
          PreparedStatement statement = conn.prepareStatement(SQL_ProductLine);
          ResultSet rs = statement.executeQuery();
          break;
      }

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("SQLState: " + e.getSQLState());
      System.out.println("VendorError: " + e.getErrorCode());
      e.printStackTrace();
    }
  }


  public void closeDatabaseConnection() {
    //STEP 4: Close connection
    try {
      stmt.close();
      conn.close();
    } catch (Exception e) {
    }
  }


  public ObservableList<Product> setupProductLineTable() {

    return productLine;

  }
}

