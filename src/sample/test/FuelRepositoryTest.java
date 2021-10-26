package sample.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import sample.model.DataSource;
import sample.model.Fuel;
import sample.model.FuelRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FuelRepositoryTest {

    @BeforeClass
    public static void openDB() {
        DataSource.getInstance().open();
    }

    @Test
    public void queryFuels() {
        List<Fuel> expectedFuels = initializeFuelsList();
        List<Fuel> testedFuels = FuelRepository.getInstance().queryFuels();


        int id = 0;

        for (Fuel fuel : expectedFuels) {
            assertEquals(fuel.getId(), testedFuels.get(id).getId());
            assertEquals(fuel.getName(), testedFuels.get(id).getName());
            assertEquals(fuel.getEnergy(), testedFuels.get(id).getEnergy());
            id++;
        }
    }

    private List<Fuel> initializeFuelsList() {
        Fuel coal = new Fuel();
        coal.setId(1);
        coal.setName("coal");
        coal.setEnergy(8000);

        Fuel stoneBriquette = new Fuel();
        stoneBriquette.setId(2);
        stoneBriquette.setName("stone briquette");
        stoneBriquette.setEnergy(5600);

        Fuel naturalGas = new Fuel();
        naturalGas.setId(3);
        naturalGas.setName("natural gas");
        naturalGas.setEnergy(10400);

        Fuel firewood = new Fuel();
        firewood.setId(4);
        firewood.setName("firewood");
        firewood.setEnergy(4200);

        Fuel heatingOil = new Fuel();
        heatingOil.setId(5);
        heatingOil.setName("heating oil");
        heatingOil.setEnergy(11800);

        Fuel pellet = new Fuel();
        pellet.setId(6);
        pellet.setName("pellet");
        pellet.setEnergy(4900);

        Fuel lpg = new Fuel();
        lpg.setId(7);
        lpg.setName("LPG");
        lpg.setEnergy(12100);

        Fuel fuelCoke = new Fuel();
        fuelCoke.setId(8);
        fuelCoke.setName("fuel coke");
        fuelCoke.setEnergy(8200);

        List<Fuel> fuels = new ArrayList<>();
        fuels.add(coal);
        fuels.add(stoneBriquette);
        fuels.add(naturalGas);
        fuels.add(firewood);
        fuels.add(heatingOil);
        fuels.add(pellet);
        fuels.add(lpg);
        fuels.add(fuelCoke);

        return fuels;
    }

    @AfterClass
    public static void closeDB() {
        DataSource.getInstance().close();
    }
}