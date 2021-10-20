package sample.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Project {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty adress;
    private SimpleStringProperty manufacturer;
    private SimpleStringProperty model;
    private SimpleDoubleProperty power;
    private SimpleDoubleProperty price;
    private SimpleDoubleProperty requiredPower;

    public Project() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.adress = new SimpleStringProperty();
        this.manufacturer = new SimpleStringProperty();
        this.model = new SimpleStringProperty();
        this.power = new SimpleDoubleProperty();
        this.price = new SimpleDoubleProperty();
        this.requiredPower = new SimpleDoubleProperty();
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAdress() {
        return adress.get();
    }

    public SimpleStringProperty adressProperty() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress.set(adress);
    }

    public String getManufacturer() {
        return manufacturer.get();
    }

    public SimpleStringProperty manufacturerProperty() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer.set(manufacturer);
    }

    public String getModel() {
        return model.get();
    }

    public SimpleStringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public double getPower() {
        return power.get();
    }

    public SimpleDoubleProperty powerProperty() {
        return power;
    }

    public void setPower(double power) {
        this.power.set(power);
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
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
}
