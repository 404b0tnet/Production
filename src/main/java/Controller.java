import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;



public class Controller {

    @FXML
    private ChoiceBox<String> itemTypeBox;

    @FXML
    //Event handler for adding a product in Product Line tab
    void addProductBtn(ActionEvent event) {
        System.out.println("Produce was added");
        connectToDatabase();
    }


    @FXML
    // Event handler for recording a product in the Produce tab
    void recordPrdBtn(ActionEvent event) {
        System.out.println("Product was recorded");
    }


    @FXML
    private ComboBox<String> cmbQuantity;


    public void initialize(){
        for (int count = 1; count <= 10; count++){
            cmbQuantity.getItems().add(String.valueOf(count));
        }
        cmbQuantity.getSelectionModel().selectFirst();
        cmbQuantity.setEditable(true);
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


            String sql = "INSERT INTO Product(type, manufacturer, name)"
                        + "VALUES ( 'AUDIO', 'Apple', 'iPod' );";

            ResultSet rs = stmt.executeQuery(sql);



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

