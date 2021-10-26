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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.model.HeatPump;
import sample.model.HeatPumpRepository;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class HeatPumpSelectionController {

    @FXML
    private Label requiredPowerLabel;

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
    private TableColumn<ObservableList<SimpleDoubleProperty>, SimpleDoubleProperty> match;

    @FXML
    private TableView<HeatPump> heatpumpsTable;

    @FXML
    private BorderPane heatPumpSelectionBorderPane;


    public void initialize() {
        requiredPowerLabel.setText(String.valueOf(RequiredPowerNeededHolder.getINSTANCE().getRequiredPower()));

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
        match.setCellValueFactory(new PropertyValueFactory<>("match"));

        customiseFactory(match);
    }

    @FXML
    public void handleOkButton(ActionEvent event) {
        if (heatpumpsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select heat pump");
            alert.setHeaderText("Error. No heat pump was selected");
            alert.showAndWait();
            return;
        }
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(heatPumpSelectionBorderPane.getScene().getWindow());
        dialog.setTitle("Add heat pump to a client");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addheatpumptoclientdialog.fxml"));

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
            if (heatpumpsTable.getSelectionModel().getSelectedItem() != null) {
                String manufacturer = heatpumpsTable.getSelectionModel().getSelectedItem().getManufacturer();
                String model = heatpumpsTable.getSelectionModel().getSelectedItem().getModel();
                double power = heatpumpsTable.getSelectionModel().getSelectedItem().getPower();
                int id = HeatPumpRepository.getInstance().queryIdFromHeatpumps(manufacturer, model, power);
                double requiredPower = RequiredPowerNeededHolder.getINSTANCE().getRequiredPower();
                AddHeatPumpToClientController controller = fxmlLoader.getController();
                if (controller.processResults(id, requiredPower)) {
                    try {
                        openProjectsWindow(event);
                    } catch (IOException e) {
                        System.out.println("Error when opening project window: " + e.getMessage());
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in ALL fields with PROPER VALUES");
                    alert.setHeaderText("ERROR: EMPTY FIELDS OR INVALID VALUES");
                    alert.showAndWait();
                    handleOkButton(event);
                }
            }
        }
    }

    @FXML
    private void openProjectsWindow(ActionEvent event) throws IOException {
        Parent projectsParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("projectswindow.fxml")));
        Scene projectsScene = new Scene(projectsParent, 1000, 700);
        Stage projectsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        projectsStage.hide();
        projectsStage.setScene(projectsScene);
        projectsStage.show();
    }

    @FXML
    public void handleGoBackButton(ActionEvent event) throws IOException {
        Parent areaParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("startwindow.fxml")));
        Scene areaScene = new Scene(areaParent, 750, 500);
        Stage areaStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        areaStage.hide();
        areaStage.setScene(areaScene);
        areaStage.show();
    }

    //    This method sets the cell's colour in the table based on the match value. If the match is lower than 70 or greater than 110 it sets colour to ligthcoral, otherwise
//    ligthgreen.
    private void customiseFactory(TableColumn calltypel) {
        calltypel.setCellFactory(column -> {
            return new TableCell<ObservableList<HeatPump>, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);

                    setText(empty ? "" : getItem().toString());
                    setGraphic(null);

                    TableRow currentRow = getTableRow();

                    if (!isEmpty()) {

                        if (item < 70 || item > 110)
                            currentRow.setStyle("-fx-background-color:lightcoral");
                        else
                            currentRow.setStyle("-fx-background-color:lightgreen");
                    }
                }
            };
        });
    }

}

