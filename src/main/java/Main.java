import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

    Scene scene = new Scene(root, 300, 275);

    primaryStage.setTitle("Production");
    primaryStage.setScene(scene);

    // Application styling with CSS
    //scene.getStylesheets().add
    //   (Main.class.getResource("Login.css").toExternalForm());

    // Display Application
    primaryStage.show();
  }
}

    /*


    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

    Scene scene = new Scene(root, 300, 275);

    primaryStage.setTitle("Employee Details");
    primaryStage.setScene(scene);



     */


    /* Event button
    Button btn = new Button("Sign in");
    HBox hbBtn = new HBox(10);
    hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbBtn.getChildren().add(btn);
    grid.add(hbBtn, 1, 4);


    //Text control for displaying the message after button press
    final Text actiontarget = new Text();
    grid.add(actiontarget, 1, 6);


    // Code to handle an event
    btn.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent e) {
        // Rather hard-coding the event has an ID for reference
        actiontarget.setId("actiontarget");
        actiontarget.setText("Sign in button pressed");
      }
    });
    */


    /*
    Code for Scene classes are below


    // Creates a title called "Welcome"
    Text scenetitle = new Text("Welcome");
    scenetitle.setId("welcome-text");
    grid.add(scenetitle, 0, 0, 2, 1);


    // Creates a username input field
    Label userName = new Label("User Name:");
    grid.add(userName, 0, 1);

    TextField userTextField = new TextField();
    grid.add(userTextField, 1, 1);


    // Creates a password input field
    Label pw = new Label("Password:");
    grid.add(pw, 0, 2);

    PasswordField pwBox = new PasswordField();
    grid.add(pwBox, 1, 2);

     */



