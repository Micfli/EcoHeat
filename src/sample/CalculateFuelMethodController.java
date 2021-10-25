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

public class CalculateFuelMethodController {

    @FXML
    private ImageView calculateFuelAmountPumpImage;

    @FXML
    private ChoiceBox<String> typeOfFuelChoiceBox;

    @FXML
    private TextField fuelAmount;


    @FXML
    public void initialize() throws ExecutionException, InterruptedException {
        File file = new File("@../../Energy-Free-Download-PNG.png");
        Image image = new Image(file.toURI().toString());
        calculateFuelAmountPumpImage.setImage(image);

        Task<List<Fuel>> task = new GetAllFuels();
        new Thread(task).start();

        List<Fuel> fuelsList = task.get();

        for (Fuel fuel : fuelsList) {
            typeOfFuelChoiceBox.getItems().addAll(fuel.getName());
        }
        typeOfFuelChoiceBox.setValue(task.get().get(0).getName());
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
        if (typeOfFuelChoiceBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose the fuel and enter it's usage");
            alert.setHeaderText("ERROR: EMPTY FIELDS OR INVALID VALUES");
            alert.showAndWait();
            return;
        }
        double fuelUsage;
        try {
            fuelUsage = Math.abs(Double.parseDouble(fuelAmount.getText().trim()));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter fuel usage in tons");
            alert.setHeaderText("ERROR: EMPTY FIELDS OR INVALID VALUES");
            alert.showAndWait();
            return;
        }
        String fuel = typeOfFuelChoiceBox.getSelectionModel().getSelectedItem();
        int energy = FuelRepository.getInstance().queryEnergyFromFuels(fuel);

        Calculator.getINSTANCE().calculateRequiredPower(fuelUsage, energy);

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

class GetAllFuels extends Task<List<Fuel>> {

    @Override
    public List<Fuel> call() {
        return FuelRepository.getInstance().queryFuels();
    }
}


