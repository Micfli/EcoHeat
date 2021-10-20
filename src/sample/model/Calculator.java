package sample.model;

import sample.RequiredPowerNeededHolder;

import java.util.ArrayList;
import java.util.List;

//This class is responsible for calculating required power for a heat pump and heat pump's power to required power ratio
public class Calculator {

    public final static Calculator INSTANCE = new Calculator();

    private Calculator() {
    }

    public static Calculator getINSTANCE() {
        return INSTANCE;
    }

    //  In this method required power is calculated with the area method
    public void calculateRequiredPower(double area, double requiredHeatPerMeter) {
        double neededPower = area * requiredHeatPerMeter / 1000;
        neededPower = Math.round(neededPower * 100.0) / 100.0;
        RequiredPowerNeededHolder.getINSTANCE().setRequiredPower(neededPower);
    }

    //   This method calculates heat pump's power to required power ratio
    public List<Double> calculatePercentageMatch() {
        double requiredPower = RequiredPowerNeededHolder.getINSTANCE().getRequiredPower();
        if (requiredPower <= 0) {
            System.out.println("Please set the proper power of heat pump");
            return null;
        }

        List<Double> powerList = DataSource.getInstance().queryPowerFromHeatPumps();
        List<Double> matchList = new ArrayList<>();

        for (Double power : powerList) {
            double match = power / requiredPower * 100;
            matchList.add(match);
        }
        return matchList;
    }

    //  In this method required power is calculated with the fuel method
    public void calculateRequiredPower(double fuelUsage, int fuelEnergy) {
        double heatNeeded = (double) fuelEnergy * fuelUsage;
        double neededPower = (heatNeeded * 40) / (24 * 3900);
        neededPower = Math.round(neededPower * 100.0) / 100.0;
        RequiredPowerNeededHolder.getINSTANCE().setRequiredPower(neededPower);
    }
}

