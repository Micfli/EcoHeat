package sample;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.model.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class CalculateAreaMethodController {

    @FXML
    private ImageView calculateAreaHeatPumpImage;

    @FXML
    private ChoiceBox<String> typeOfBuildingChoiceBox;

    @FXML
    private TextField area;


    @FXML
    public void initialize() throws ExecutionException, InterruptedException {
        Task<List<BuildingType>> task = new GetAllBuildingTypes();
        new Thread(task).start();
        List<BuildingType> buildingTypeList = task.get();

        for (BuildingType buildingType : buildingTypeList) {
            typeOfBuildingChoiceBox.getItems().addAll(buildingType.getBuildingType());
        }

        typeOfBuildingChoiceBox.setValue(task.get().get(2).getBuildingType());

        File file = new File("@../../Energy-Free-Download-PNG.png");
        Image image = new Image(file.toURI().toString());
        calculateAreaHeatPumpImage.setImage(image);

    }

    @FXML
    public void handleGoBackButton(ActionEvent event) throws IOException {
        Parent startParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("startWindow.fxml")));
        Scene startScene = new Scene(startParent, 750, 500);
        Stage startStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        startStage.hide();
        startStage.setScene(startScene);
        startStage.show();
    }

    @FXML
    public void handleOkButton(ActionEvent event) throws IOException {
        if (typeOfBuildingChoiceBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose the type of the building and it's area in square meters");
            alert.setHeaderText("ERROR: EMPTY FIELDS OR INVALID VALUES");
            alert.showAndWait();
            return;
        }

        try {
//            Getting a value from a text field as a double
            Double.parseDouble(area.getText().trim());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter area in square meters");
            alert.setHeaderText("ERROR: EMPTY FIELDS OR INVALID VALUES");
            alert.showAndWait();
            return;
        }
        String typeofBuilding = typeOfBuildingChoiceBox.getSelectionModel().getSelectedItem();
        double requierdHeatPerMeter = BuildingTypeRepository.getInstance().queryNeededheatFromBuildingTypes(typeofBuilding);
        double areaValue = Math.abs(Double.parseDouble(area.getText().trim()));

        Calculator.getINSTANCE().calculateRequiredPower(areaValue, requierdHeatPerMeter);

        List<Double> matches = Calculator.getINSTANCE().calculatePercentageMatch();
        int id = 1;

        for (Double match : matches) {
            HeatPumpRepository.getInstance().updateHeatpumpsSetMatch(match, id);
            id++;
        }

        Parent selectHeatPumpParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("heatpumpselection.fxml")));
        Scene selectHeatPumpScene = new Scene(selectHeatPumpParent, 800, 650);
        Stage selectHeatPumpStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        selectHeatPumpStage.hide();
        selectHeatPumpStage.setScene(selectHeatPumpScene);
        selectHeatPumpStage.show();
    }
}

class GetAllBuildingTypes extends Task<List<BuildingType>> {

    @Override
    public List<BuildingType> call() {
        return BuildingTypeRepository.getInstance().queryBuildingsTypes();

    }
}
