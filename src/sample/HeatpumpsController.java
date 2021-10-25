package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.model.HeatPump;
import sample.model.HeatPumpRepository;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class HeatpumpsController {

    @FXML
    private TableColumn<ObservableList<HeatPump>, SimpleStringProperty> manufacturer;

    @FXML
    private TableColumn<ObservableList<HeatPump>, SimpleStringProperty> model;

    @FXML
    private TableColumn<ObservableList<HeatPump>, SimpleDoubleProperty> power;

    @FXML
    private TableColumn<ObservableList<HeatPump>, SimpleDoubleProperty> te;

    @FXML
    private TableColumn<ObservableList<HeatPump>, SimpleDoubleProperty> twmax;

    @FXML
    private TableColumn<ObservableList<HeatPump>, SimpleDoubleProperty> cop;

    @FXML
    private TableColumn<ObservableList<HeatPump>, SimpleDoubleProperty> price;

    @FXML
    private TableView<HeatPump> heatpumpsTable;

    @FXML
    private BorderPane heatPumpBorderPane;

    @FXML
    private ImageView heatPumpImage;

    public void initialize() {
        File file = new File("@../../Energy-Free-Download-PNG.png");
        Image image = new Image(file.toURI().toString());
        heatPumpImage.setImage(image);

        Task<ObservableList<HeatPump>> task = new GetAllHeatPumpsTask();
        heatpumpsTable.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();

        manufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        power.setCellValueFactory(new PropertyValueFactory<>("power"));
        te.setCellValueFactory(new PropertyValueFactory<>("te"));
        twmax.setCellValueFactory(new PropertyValueFactory<>("twmax"));
        cop.setCellValueFactory(new PropertyValueFactory<>("cop"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    public void handleGoBackButton(ActionEvent event) throws IOException {
        Parent heatpumpParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("startWindow.fxml")));
        Scene heatpumpScene = new Scene(heatpumpParent, 800, 650);
        Stage heatpumpStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        heatpumpStage.hide();
        heatpumpStage.setScene(heatpumpScene);
        heatpumpStage.show();
    }

    @FXML
    public void addHeatPump() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(heatPumpBorderPane.getScene().getWindow());
        dialog.setTitle("Add new heat pump");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("heatpumpdialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            HeatPumpDiallogController controller = fxmlLoader.getController();
            if (controller.processResults()) {
                Task<ObservableList<HeatPump>> task = new GetAllHeatPumpsTask();
                new Thread(task).start();
                heatpumpsTable.itemsProperty().bind(task.valueProperty());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in ALL fields with PROPER VALUES");
                alert.setHeaderText("ERROR: EMPTY FIELDS OR INVALID VALUES");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void deleteHeatPump() {
        HeatPump heatPump = heatpumpsTable.getSelectionModel().getSelectedItem();
        if (heatPump == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No heat pump selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the heat pump you want to delete");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete heat pump");
        alert.setHeaderText("Delete heat pump: " + heatPump.getManufacturer() + ", " + heatPump.getModel() + ", power: " + heatPump.getPower() + " kW");
        alert.setContentText("Are you sure? Press OK to confirm, or Cancel to back out.");
        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            if (HeatPumpRepository.getInstance().deleteFromHeatpumps(heatPump.get_id())) {
                Task<ObservableList<HeatPump>> task = new GetAllHeatPumpsTask();
                new Thread(task).start();
                heatpumpsTable.itemsProperty().bind(task.valueProperty());
            } else {
                System.out.println("Failed to delete heat pump from database");
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error while trying to delete heat pump from database");
                errorAlert.showAndWait();
            }
        }
    }

    @FXML
    public void editHeatPump() {
        HeatPump heatPump = heatpumpsTable.getSelectionModel().getSelectedItem();
        if (heatPump == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No heat pump selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the heat pump you want to edit");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(heatPumpBorderPane.getScene().getWindow());
        dialog.setTitle("Edit heatpump");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("heatpumpdialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        HeatPumpDiallogController controller = fxmlLoader.getController();
        controller.editHeatPump(heatPump);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (controller.updateHeatPump(heatPump)) {
                Task<ObservableList<HeatPump>> task = new GetAllHeatPumpsTask();
                new Thread(task).start();
                heatpumpsTable.itemsProperty().bind(task.valueProperty());
            } else {
                System.out.println("Failed to update heat pump to database");
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error while trying to update heat pump to database");
                errorAlert.showAndWait();
            }
        }
    }

    // This method provides shortcuts from keyboard to adding, deleting and editing heat pumps data base
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.DELETE)) {
            deleteHeatPump();
        } else if (keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.N)) {
            addHeatPump();
        } else if (keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.E)) {
            editHeatPump();
        }
    }
}

