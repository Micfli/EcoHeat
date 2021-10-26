package sample.test;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import sample.model.BuildingType;
import sample.model.BuildingTypeRepository;
import sample.model.DataSource;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BuildingTypeRepositoryTest {

    @BeforeClass
    public static void openDB() {
        DataSource.getInstance().open();
    }

    @Test
    public void queryNeededheatFromBuildingTypesWithNull() {
        Assert.assertEquals(-1D, BuildingTypeRepository.getInstance().queryNeededheatFromBuildingTypes(null), 0);
    }

    @Test
    public void queryNeededheatFromBuildingTypes() {
        List<BuildingType> buildingTypes = BuildingTypeRepository.getInstance().queryBuildingsTypes();
        for (BuildingType buildingType : buildingTypes) {
            var name = buildingType.getBuildingType();
            var expectedNeededHeat = buildingType.getNeededHeat();
            var actualNeededHeat = BuildingTypeRepository.getInstance().queryNeededheatFromBuildingTypes(name);
            assertEquals(expectedNeededHeat, actualNeededHeat, 0);
        }
    }

    @AfterClass
    public static void closeDB() {
        DataSource.getInstance().close();
    }
}