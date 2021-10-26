package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sample.model.DataSource;

import java.util.Objects;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("startwindow.fxml")));
        primaryStage.setTitle("EcoHeat");
        primaryStage.setScene(new Scene(root, 750, 500));
        primaryStage.show();

//    When the application is starting it's trying to establish a connection with database, if there is any error
//        when trying to make a connection the app will stop and the user will have an alert with description
        if (!DataSource.getInstance().open()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "FATAL ERROR. Couldn't connect to database");
            System.out.println("FATAL ERROR. Couldn't connect to database");
            alert.showAndWait();
            Platform.exit();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    // When the app is closing it's also trying to close the connection with database
    @Override
    public void stop() throws Exception {
        super.stop();
        DataSource.getInstance().close();
    }
}
