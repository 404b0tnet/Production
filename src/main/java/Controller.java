import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.h2.security.auth.impl.StaticRolesMapper;


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
  private TableView<Product> productLineTV;

  @FXML
  private TableColumn<Product, Integer> prodIDCol;

  @FXML
  private TableColumn<Product, String> prodNameCol;

  @FXML
  private TableColumn<Product, String> prodManCol;

  @FXML
  private TableColumn<Product, ItemType> prodTypeCol;

  //Controller initialize method should do things that you want to happen
  // once when the program starts:
  //    define the ObservableList (it can be declared at class level)
  //    call setupProductLineTable
  //    associate the ObservableList with the Product Line ListView
  //    call loadProductList
  //    call loadProductionLog
  public void initialize() {
    for (int count = 1; count <= 10; count++) {
      cmbQuantity.getItems().add(String.valueOf(count));
    }

    for (ItemType it : ItemType.values()) {
      itemTypeCBox.getItems().addAll(String.valueOf(it));
    }

    cmbQuantity.getSelectionModel().selectFirst();

    ObservableList<Product> data = setupProductLineTable();
    prodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    prodNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
    prodManCol
        .setCellValueFactory(new PropertyValueFactory<>("productManufacturer"));
    prodTypeCol.setCellValueFactory(new PropertyValueFactory<>("itemType"));
    productLineTV.setItems(data);

    // Display Production Log
    //productionRecordLog();

    //issue5Prod2();
    //testMultimedia();
  }


  @FXML
    //Event handler for adding a product in Product Line tab
  void addProductBtn() {

    String manufacturer = manufacturerTF.getText();
    String productName = productNameTF.getText();
    String type = itemTypeCBox.getSelectionModel().getSelectedItem();

    // initial check for invalid input into text fields
    if (productName.length() < 1 || manufacturer.length() < 1
        || type.length() < 1) {
      alert.setAlertType(AlertType.WARNING);
      alert.setTitle("Error");
      alert.setHeaderText("All fields are required.");
      alert.setContentText("INVALID INPUT!");
      alert.showAndWait();
    } else {
      try {   // if input is valid, add to database
        connectToDatabase();

        final String SQL_AddProduct =
            "INSERT INTO PRODUCT(NAME,TYPE,MANUFACTURER) VALUES(?, ?, ?)";

        try {
          PreparedStatement stmt = conn.prepareStatement(SQL_AddProduct);
          stmt.setString(1, productNameTF.getText());
          stmt.setString(2, itemTypeCBox.getSelectionModel().getSelectedItem());
          stmt.setString(3, manufacturerTF.getText());
          stmt.executeUpdate();

          ItemType tempType;
          switch (itemTypeCBox.getSelectionModel().getSelectedItem()) {
            case "AUDIO":
              tempType = ItemType.AUDIO;
              break;
            case "VISUAL":
              tempType = ItemType.VISUAL;
              break;
            case "AUDIO_MOBILE":
              tempType = ItemType.AUDIO_MOBILE;
              break;
            default:
              tempType = ItemType.VISUAL_MOBILE;
          }

          Product newProduct = new Product(tempType, productName,
              manufacturer);

          ProductionRecord newProductionRecord = new ProductionRecord(
              newProduct, 0);


        } catch (SQLException e) {
          System.out.println("SQLException: " + e.getMessage());
          System.out.println("SQLState: " + e.getSQLState());
          System.out.println("VendorError: " + e.getErrorCode());
          e.printStackTrace();
        }
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
    try {
      connectToDatabase();
      final String SQL_RecordProduct =
          "INSERT INTO PRODUCTIONRECORD(PRODUCT_ID,SERIAL_NUM,DATE_PRODUCED)"
              + "VALUES(?, ?, ?)";

      closeDatabaseConnection();
    } catch (SQLException e) {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("SQLState: " + e.getSQLState());
      System.out.println("VendorError: " + e.getErrorCode());
      e.printStackTrace();
    }
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


  /*public void issue5Prod2() {

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
   */


  public void connectToDatabase() throws SQLException {

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected to database.");

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("SQLState: " + e.getSQLState());
      System.out.println("VendorError: " + e.getErrorCode());
      e.printStackTrace();
    }
  }

  public void sqlQuery(int DBquery) {
    //STEP 3: Execute a query

    final String SQL_ProductLine =
        "SELECT * FROM PRODUCT";

    switch (DBquery) {
      case 1:

        break;
      case 2:
        try {
          PreparedStatement prodLine = conn.prepareStatement(SQL_ProductLine);
          ResultSet rs = prodLine.executeQuery();

        } catch (SQLException e) {
          System.out.println("SQLException: " + e.getMessage());
          System.out.println("SQLState: " + e.getSQLState());
          System.out.println("VendorError: " + e.getErrorCode());
          e.printStackTrace();
        }

        break;
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

    ObservableList<Product> data = FXCollections.observableArrayList();

    try {
      connectToDatabase();
      String SQL = "SELECT * FROM PRODUCT";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(SQL);

      while (rs.next()) {

        ItemType tempType;
        switch (rs.getString(3)) {
          case "AUDIO":
            tempType = ItemType.AUDIO;
            break;
          case "VISUAL":
            tempType = ItemType.VISUAL;
            break;
          case "AUDIO_MOBILE":
            tempType = ItemType.AUDIO_MOBILE;
            break;
          default:
            tempType = ItemType.VISUAL_MOBILE;
        }

        // product (type, name, manufacturer)
        Product newProduct = new Product(tempType, rs.getString(2),
            rs.getString(4));
        data.add(newProduct);
      }

      closeDatabaseConnection();
    } catch (SQLException e) {
      System.out.println(e);
    }

    return data;
  }
}


