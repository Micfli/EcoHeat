package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import sample.model.DataSource;
import sample.model.HeatPump;

class GetAllHeatPumpsTask extends Task<ObservableList<HeatPump>> {

    @Override
    public ObservableList<HeatPump> call() {
        return FXCollections.observableArrayList(
                DataSource.getInstance().queryHeatpumps());

    }
}
