module EcoHeat {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires itextpdf;
    requires junit;

    opens sample;
    opens sample.model;
    opens sample.test;
}