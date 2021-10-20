package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import sample.model.DataSource;

// This class is used to transfer a needed power from one scene to another
public class RequiredPowerNeededHolder {

    private SimpleDoubleProperty requiredPower;
    private final static RequiredPowerNeededHolder INSTANCE = new RequiredPowerNeededHolder();

    private RequiredPowerNeededHolder() {
        requiredPower = new SimpleDoubleProperty();
    }

    public double getRequiredPower() {
        return requiredPower.get();
    }

    public SimpleDoubleProperty requiredPowerProperty() {
        return requiredPower;
    }

    public void setRequiredPower(double requiredPower) {
        this.requiredPower.set(requiredPower);
    }

    public static RequiredPowerNeededHolder getINSTANCE() {
        return INSTANCE;
    }

}
