package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import sample.model.HeatPump;
import sample.model.HeatPumpRepository;

class GetAllHeatPumpsTask extends Task<ObservableList<HeatPump>> {

    @Override
    public ObservableList<HeatPump> call() {
        return FXCollections.observableArrayList(
                HeatPumpRepository.getInstance().queryHeatpumps());

    }
}
