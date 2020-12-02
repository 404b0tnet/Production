import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


public class Controller {

  @FXML
  private ChoiceBox<String> itemTypeCBox;

  @FXML
  private ComboBox<String> cmbQuantity;

  @FXML
  private TextField productNameTF;

  @FXML
  private TextField manufacturerTF;


  public void initialize() {
    for (int count = 1; count <= 10; count++) {
      cmbQuantity.getItems().add(String.valueOf(count));
    }

    for (ItemType it : ItemType.values()) {
      itemTypeCBox.getItems().addAll(String.valueOf(it));
    }

    cmbQuantity.getSelectionModel().selectFirst();
    cmbQuantity.setEditable(true);

    issue5Prod2();
    //testMultimedia();
  }


  @FXML
    //Event handler for adding a product in Product Line tab
  void addProductBtn() {
    Alert alert = new Alert(AlertType.WARNING);

    String itemType = itemTypeCBox.getValue();
    String manufacturer = manufacturerTF.getText();
    String productName = productNameTF.getText();

    boolean input = false;
    if (productName.length() < 1 || manufacturer.length() < 1
        || itemType.length() < 1) {
      alert.setTitle("Error");
      alert.setHeaderText("All fields are required.");
      alert.setContentText("INVALID INPUT!");
      alert.showAndWait();
    } else {
      try {
        String sql = "INSERT INTO Product(name, type, manufacturer)"
            + "VALUES ('" + productName + "','" + itemType + "','"
            + manufacturer + "');";

        connectToDatabase(sql);
        alert.setAlertType(AlertType.INFORMATION);
        alert.setHeaderText("Product was added!");
        alert.setContentText("");
        alert.showAndWait();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
  }

  @FXML
    // Event handler for recording a product in the Produce tab
  void recordPrdBtn() {
    System.out.println("Product was recorded");
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


  public static void issue5Prod2(){

    Product productProduced = new Product(ItemType.AUDIO,"Apple","iPod");

    // test constructor used when creating production records from user interface
    int numProduced = 3;  // this will come from the combobox in the UI
    int itemCount = 0;

    for (int productionRunProduct = 0; productionRunProduct < numProduced; productionRunProduct++) {
      ProductionRecord pr = new ProductionRecord(productProduced, itemCount++);
      // using the iterator as the product id for testing
      System.out.println(pr.toString());
    }


  }



  public void connectToDatabase(String sqlStatement) throws SQLException {

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Products";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    Statement stmt = null;

    try {

      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected to database.");

      //STEP 3: Execute a query
      stmt = conn.createStatement();
      stmt.executeUpdate(sqlStatement);


    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      System.out.println("SQLException: " + e.getMessage());
      System.out.println("SQLState: " + e.getSQLState());
      System.out.println("VendorError: " + e.getErrorCode());
      e.printStackTrace();
    } finally {

      // STEP 4: Clean-up environment
      try {
        stmt.close();
      } catch (Exception e) {
      }
      try {
        conn.close();
      } catch (Exception e) {
      }
    }
  }

  public void productionLog() throws SQLException {

    ProductionRecord productionRecord = new ProductionRecord(5);
    return;

  }

}

