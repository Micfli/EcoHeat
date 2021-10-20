package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

// Controller for starting window, the first window in this application
public class StartWindowController {

    @FXML
    private ImageView heatPumpImage;

    public void initialize() {
        File file = new File("@../../Energy-Free-Download-PNG.png");
        Image image = new Image(file.toURI().toString());
        heatPumpImage.setImage(image);
    }

    @FXML
    public void handleHeatPumpsButton(ActionEvent event) throws IOException {
        Parent heatpumpParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("heatpumps.fxml")));
        Scene heatpumpScene = new Scene(heatpumpParent, 800, 650);
        Stage heatpumpStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        heatpumpStage.hide();
        heatpumpStage.setScene(heatpumpScene);
        heatpumpStage.show();

    }

    @FXML
    public void handleNewProjectButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New Project");
        alert.setHeaderText("Calculating a required power of a heat pump");
        alert.setContentText("Pick a method to calculate required power of a heat pump");

        ButtonType buttonTypeArea = new ButtonType("Area method");
        ButtonType buttonTypeFuel = new ButtonType("Fuel usage method");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeArea, buttonTypeFuel, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeArea){
            Parent areaParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("calculateareamethodwindow.fxml")));
            Scene areaScene = new Scene(areaParent, 600, 400);
            Stage areaStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            areaStage.hide();
            areaStage.setScene(areaScene);
            areaStage.show();
        } else if (result.get() == buttonTypeFuel) {
            Parent fuelParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("calculatefuelmethodwindow.fxml")));
            Scene fuelScene = new Scene(fuelParent, 600, 400);
            Stage fuelStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            fuelStage.hide();
            fuelStage.setScene(fuelScene);
            fuelStage.show();
        }
    }

    @FXML
    public void handleLoadProjectButton(ActionEvent event) throws IOException {
        Parent projectsParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("projectswindow.fxml")));
        Scene projectsScene = new Scene(projectsParent, 1000, 700);
        Stage projectsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        projectsStage.hide();
        projectsStage.setScene(projectsScene);
        projectsStage.show();
    }
}
