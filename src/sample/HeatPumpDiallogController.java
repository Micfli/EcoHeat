package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.model.DataSource;
import sample.model.HeatPump;

public class HeatPumpDiallogController {

    @FXML
    private TextField manufacturerField;

    @FXML
    private TextField modelField;

    @FXML
    private TextField powerField;

    @FXML
    private TextField teField;

    @FXML
    private TextField twmaxField;

    @FXML
    private TextField copField;

    @FXML
    private TextField priceField;


    public boolean processResults() {
        try {
            String manufacturer = manufacturerField.getText().trim();
            String model = modelField.getText().trim();
//            Math.abs method converts negative numbers from user's input into positive numbers
            double power = Math.abs(Double.parseDouble(powerField.getText().trim()));
            double te = Math.abs(Double.parseDouble(teField.getText().trim()));
            double twmax = Math.abs(Double.parseDouble(twmaxField.getText().trim()));
            double cop = Math.abs(Double.parseDouble(copField.getText().trim()));
            double price = Math.abs(Double.parseDouble(priceField.getText().trim()));
            return DataSource.getInstance().insertHeatpumps(manufacturer, model, power, te, twmax, cop, price);
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void editHeatPump(HeatPump heatPump) {
        manufacturerField.setText(heatPump.getManufacturer());
        modelField.setText(heatPump.getModel());
        powerField.setText(String.valueOf(heatPump.getPower()));
        teField.setText(String.valueOf(heatPump.getTe()));
        twmaxField.setText(String.valueOf(heatPump.getTwmax()));
        copField.setText(String.valueOf(heatPump.getCop()));
        priceField.setText(String.valueOf(heatPump.getPrice()));
    }

    public boolean updateHeatPump(HeatPump heatPump) {
        int id = heatPump.get_id();
        String manufacturer = manufacturerField.getText().trim();
        String model = modelField.getText().trim();
        double power = Double.parseDouble(powerField.getText().trim());
        double te = Double.parseDouble(teField.getText().trim());
        double twmax = Double.parseDouble(twmaxField.getText().trim());
        double cop = Double.parseDouble(copField.getText().trim());
        double price = Double.parseDouble(priceField.getText().trim());
        return DataSource.getInstance().updateHeatpump(id, manufacturer, model, power, te, twmax, cop, price);
    }
}
