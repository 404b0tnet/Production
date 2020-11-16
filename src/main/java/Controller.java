import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;



public class Controller{

     @FXML
    private ChoiceBox<String> itemTypeCBox;

    @FXML
    private ComboBox<String> cmbQuantity;

    @FXML
    private TextField productNameTF;

    @FXML
    private TextField manufacturerTF;


    public void initialize(){
        for (int count = 1; count <= 10; count++){
            cmbQuantity.getItems().add(String.valueOf(count));
        }

        for (ItemType it : ItemType.values()){
            itemTypeCBox.getItems().addAll(String.valueOf(it));
        }

        cmbQuantity.getSelectionModel().selectFirst();
        cmbQuantity.setEditable(true);

        //testMultimedia();
    }


    @FXML
        //Event handler for adding a product in Product Line tab
    void addProductBtn() {

        try {
            connectToDatabase();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Product button was clicked");
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


    public void connectToDatabase() throws SQLException {


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

            String itemType = itemTypeCBox.getValue();
            String manufacturer = manufacturerTF.getText();
            String productName = productNameTF.getText();

            String sql = "INSERT INTO Product(type, manufacturer, name)"
                        + "VALUES ('"+itemType+"','"+manufacturer+"','"+productName+"');";

            stmt.executeUpdate(sql);

            System.out.println("Product name was " + productName);
            System.out.println("Manufacturer name was " + manufacturer);
            System.out.println("Item type was: " + itemType);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            e.printStackTrace();
        } finally {

            // STEP 4: Clean-up environment
            try{stmt.close(); } catch (Exception e){}
            try{conn.close();}catch (Exception e){}
        }
    }
}

