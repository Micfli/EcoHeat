package sample.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HeatPump {
    private SimpleIntegerProperty _id;
    private SimpleStringProperty manufacturer;
    private SimpleStringProperty model;
    private SimpleDoubleProperty power;
    private SimpleDoubleProperty te;
    private SimpleDoubleProperty twmax;
    private SimpleDoubleProperty cop;
    private SimpleDoubleProperty price;
    private SimpleDoubleProperty match;

    public HeatPump() {
        this._id = new SimpleIntegerProperty();
        this.manufacturer = new SimpleStringProperty();
        this.model = new SimpleStringProperty();
        this.power = new SimpleDoubleProperty();
        this.te = new SimpleDoubleProperty();
        this.twmax = new SimpleDoubleProperty();
        this.power = new SimpleDoubleProperty();
        this.cop = new SimpleDoubleProperty();
        this.price = new SimpleDoubleProperty();
        this.match = new SimpleDoubleProperty();
    }

    public int get_id() {
        return _id.get();
    }

    public SimpleIntegerProperty _idProperty() {
        return _id;
    }

    public void set_id(int _id) {
        this._id.set(_id);
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

    public double getTe() {
        return te.get();
    }

    public SimpleDoubleProperty teProperty() {
        return te;
    }

    public void setTe(double te) {
        this.te.set(te);
    }

    public double getTwmax() {
        return twmax.get();
    }

    public SimpleDoubleProperty twmaxProperty() {
        return twmax;
    }

    public void setTwmax(double twmax) {
        this.twmax.set(twmax);
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

    public double getCop() {
        return cop.get();
    }

    public SimpleDoubleProperty copProperty() {
        return cop;
    }

    public void setCop(double cop) {
        this.cop.set(cop);
    }

    public double getMatch() {
        return match.get();
    }

    public SimpleDoubleProperty matchProperty() {
        return match;
    }

    public void setMatch(double match) {
        this.match.set(match);
    }
}
