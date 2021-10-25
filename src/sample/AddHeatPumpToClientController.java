package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.model.ClientRepository;

public class AddHeatPumpToClientController {

    @FXML
    private TextField clientsNameField;

    @FXML
    private TextField clientsAdressField;

    public boolean processResults(int id, double requiredPower) {
        String name = clientsNameField.getText().trim();
        String adress = clientsAdressField.getText().trim();
        if ((name.isEmpty() || adress.isEmpty())) {
            return false;
        }
        return ClientRepository.getInstance().insertIntoClients(name, adress, id, requiredPower);
    }
}
