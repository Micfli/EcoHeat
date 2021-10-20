package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// BuildingTypes are used to create different buildings objects with different insulation and due to insulation - different
// heat needs per square meter.
public class BuildingType {

    private SimpleIntegerProperty id;
    private SimpleStringProperty buildingType;
    private SimpleIntegerProperty neededHeat;

    public BuildingType() {
        this.id = new SimpleIntegerProperty();
        this.buildingType = new SimpleStringProperty();
        this.neededHeat = new SimpleIntegerProperty();
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getBuildingType() {
        return buildingType.get();
    }

    public SimpleStringProperty buildingTypeProperty() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType.set(buildingType);
    }

    public int getNeededHeat() {
        return neededHeat.get();
    }

    public SimpleIntegerProperty neededHeatProperty() {
        return neededHeat;
    }

    public void setNeededHeat(int neededHeat) {
        this.neededHeat.set(neededHeat);
    }
}
