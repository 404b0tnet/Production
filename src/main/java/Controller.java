import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javax.swing.Action;


public class Controller {

    @FXML
    private ChoiceBox<String> itemTypeBox;

    @FXML
    private Button addProdBtn;

    @FXML
    //Event handler for button click
    void addProduct(javafx.event.ActionEvent event) {
        connectToDatabase();
    }


    @FXML
    private ComboBox<String> cmbQuantity;

    @FXML
    private Button recordProdBtn;


    public void initialize(){
        for (int count = 1; count <= 10; count++){
            cmbQuantity.getItems().add(String.valueOf(count));
        }
    }


    public void connectToDatabase(){


        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:./res/HRDB";


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


            String sql = "SELECT * FROM JOBS";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }


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

