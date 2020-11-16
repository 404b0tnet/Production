import java.util.Scanner;
import javafx.event.EventType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class Controller{

    Scanner scanner = new Scanner(System.in);

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
    }


    @FXML
        //Event handler for adding a product in Product Line tab
    void addProductBtn(ActionEvent event) {
        connectToDatabase();
        System.out.println("Product button was clicked");
    }

    @FXML
        // Event handler for recording a product in the Produce tab
    void recordPrdBtn(ActionEvent event) {
        System.out.println("Product was recorded");
    }


    public void connectToDatabase(){


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


            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

