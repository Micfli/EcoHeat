package sample.model;

import sample.RequiredPowerNeededHolder;

import java.util.ArrayList;
import java.util.List;

//This class is responsible for calculating required power for a heat pump and heat pump's power to required power ratio
public class Calculator {

    public final static Calculator INSTANCE = new Calculator();

    //    Those are factors for calculating the required power with a fuel usage method
    private final static int FACTOR_Z = 40;
    private final static int FACTOR_SB = 3900;
    private final static int FACTOR_X = 24;

    private Calculator() {
    }

    public static Calculator getINSTANCE() {
        return INSTANCE;
    }

    //  In this method required power is calculated with the area method
    public void calculateRequiredPower(double area, double requiredHeatPerMeter) {
//               1000 to convert Wats to kiloWats
        double neededPower = area * requiredHeatPerMeter / 1000;
        //        converting neededPower to only have two decimal points
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

        List<Double> powerList = HeatPumpRepository.getInstance().queryPowerFromHeatPumps();
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
        double neededPower = (heatNeeded * FACTOR_Z) / (FACTOR_X * FACTOR_SB);
        //        converting neededPower to only have two decimal points
        neededPower = Math.round(neededPower * 100.0) / 100.0;
        RequiredPowerNeededHolder.getINSTANCE().setRequiredPower(neededPower);
    }
}

