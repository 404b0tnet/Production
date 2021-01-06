import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

/**
 * @author Justin Smith
 */
public class Controller {

  private ObservableList<Product> productLine;

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

  @FXML
  private ListView<Product> produceListView;

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
  //    call loadProductionLog
  public void initialize() {

    for (int count = 1; count <= 10; count++) {
      cmbQuantity.getItems().add(String.valueOf(count));
    }

    for (ItemType it : ItemType.values()) {
      itemTypeCBox.getItems().addAll(String.valueOf(it));
    }

    cmbQuantity.getSelectionModel().selectFirst();

    ObservableList<Product> productLine = setupProductLineTable();
    productLineTV.setItems(productLine);

    // Display produce list
    loadProductList(productLine);

    // Display Production Log
    //loadProductionLog();

    //issue5Prod2();
    //testMultimedia();
  }


  @FXML
    //Event handler for adding a product in Product Line tab
  void addProductBtn() {

    String manufacturer = manufacturerTF.getText();
    String productName = productNameTF.getText();
    String typeString = itemTypeCBox.getSelectionModel().getSelectedItem();

    ItemType type = itemTypeFromString(typeString);

    Product p = new Product(type, manufacturer, productName);

    // initial check for invalid input into text fields
    if (p.getProductName().length() < 1
        || p.getProductManufacturer().length() < 1
        || p.getItemType().length() < 1) {
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

        PreparedStatement add_prod = conn.prepareStatement(SQL_AddProduct);

        try {
          add_prod.setString(1, productName);
          add_prod.setString(2, typeString);
          add_prod.setString(3, manufacturer);
          add_prod.executeUpdate();

          updateExistingProducts();

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

        productNameTF.clear();
        manufacturerTF.clear();

        add_prod.close();
        conn.close();
        System.out.println("Database was closed.");

      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }

  }

  @FXML
    // Event handler for recording a product in the Produce tab
  void recordProductdBtn() {

    loadProductionLog();

    try {
      //loadProductionLog();
    } catch (Exception e){
      System.out.println(e.getMessage());
      System.out.println(e.getCause());
      System.out.println(e.getLocalizedMessage());
      System.out.println(e.toString());
    }

    alert.setAlertType(AlertType.INFORMATION);
    alert.setHeaderText("Product was recorded!");
    alert.setContentText("");
    alert.showAndWait();

  }

  /*
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

      System.out.println("\n\n");
      System.out.println(p);
      p.play();
      p.stop();
      p.next();
      p.previous();
    }

    System.out.println("\n");
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
*/


  public void connectToDatabase() throws SQLException {

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected to database.");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println(e.getCause());
    }
  }

  public void closeDatabaseConnection() throws SQLException {
    //STEP 4: Close connection
    if (conn != null && stmt != null) {
      stmt.close();
      conn.close();
      System.out.println("Disconnected from databse.");
    }
  }

  public void updateExistingProducts() {
    ObservableList<Product> productLine = setupProductLineTable();
    productLineTV.setItems(productLine);

    loadProductList(productLine);

  }

  public void loadProductList(ObservableList<Product> list) {

    ObservableList<Product> data = FXCollections.observableArrayList(list);
    for (Product pr : data) {
      produceListView.getItems().add(pr);
    }


  }

  public void loadProductionLog() {

    // gets selection from produce tab
    Product productObject = produceListView.getSelectionModel()
        .getSelectedItem();
    int counter = Integer.parseInt(cmbQuantity.getValue());

    // create production record objects and send to array list
    ArrayList<ProductionRecord> productionRun = new ArrayList<>();

    for (int i = 0; i <= counter; i++) {
      productionRun.add(new ProductionRecord(productObject, i));
    }

    addToProductionRecordDB(productionRun);

  }

  public void addToProductionRecordDB(ArrayList<ProductionRecord> array_pr) {

    try {
      connectToDatabase();

      final String SQL_RecordProduct =
          "INSERT INTO PRODUCTIONRECORD(PRODUCT_ID,SERIAL_NUM,DATE_PRODUCED)"
              + "VALUES(?, ?, ?)";

      PreparedStatement add_recordProduct = conn
          .prepareStatement(SQL_RecordProduct);

      try {
        for (ProductionRecord prObj : array_pr) {
          Timestamp timestamp = new
              Timestamp(prObj.getProdDate().toInstant().getEpochSecond());

          add_recordProduct.setInt(1, prObj.getProductID());
          add_recordProduct.setString(2, prObj.getSerialNum());
          add_recordProduct.setTimestamp(3, timestamp);
          System.out.println(prObj.toString());
        }     // end for loop
      } catch (SQLException e) {
        System.out.println("SQLException: " + e.getMessage());
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("VendorError: " + e.getErrorCode());
        e.printStackTrace();
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }


  }


  public ObservableList<Product> setupProductLineTable() {

    ObservableList<Product> data = FXCollections.observableArrayList();

    prodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    prodNameCol
        .setCellValueFactory(new PropertyValueFactory<>("productName"));
    prodManCol
        .setCellValueFactory(
            new PropertyValueFactory<>("productManufacturer"));
    prodTypeCol.setCellValueFactory(new PropertyValueFactory<>("itemType"));
    productLineTV.getColumns()
        .setAll(prodIDCol, prodNameCol, prodTypeCol, prodManCol);

    try {
      connectToDatabase();
      String SQL = "SELECT * FROM PRODUCT";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(SQL);

      while (rs.next()) {

        ItemType tempType;
        switch (rs.getString("TYPE")) {
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
        Product newProduct = new Product();
        newProduct.setItemType(tempType);
        newProduct.setProductName(rs.getString("NAME"));
        newProduct.setProductManufacturer(rs.getString("MANUFACTURER"));
        newProduct.setID(rs.getInt("ID"));
        data.add(newProduct);

      }

      closeDatabaseConnection();
    } catch (SQLException e) {
      System.out.println(e);
    }

    return data;
  }


  private ItemType itemTypeFromString(String typeInput) {
    ItemType type;

    switch (typeInput) {
      case "AUDIO":
        type = ItemType.AUDIO;
        break;
      case "VISUAL":
        type = ItemType.VISUAL;
        break;
      case "AUDIO_MOBILE":
        type = ItemType.AUDIO_MOBILE;
        break;
      default:
        type = ItemType.VISUAL_MOBILE;
    }

    return type;
  }

}


