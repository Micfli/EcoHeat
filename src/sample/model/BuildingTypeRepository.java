package sample.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BuildingTypeRepository {

    //    BUILDINGTYPES
    public static final String TABLE_BUILDINGTYPES = "buildingtypes";
    public static final String COLUMN_BUILDINGTYPES_BUILDINGTYPE = "buildingtype";
    public static final String COLUMN_BULDINGTYPES_NEEDEDHEAT = "neededheat";

    //    SELECT * FROM buildingtypes
    public static final String QUERY_BUILDINGTYPES = "SELECT * FROM " + TABLE_BUILDINGTYPES;
    //    SELECT neededheat FROM buildingtypes WHERE buildingtype = ?
    public static final String QUERY_NEEDEDHEAT_FROM_BUILDINGTYPES = "SELECT " + COLUMN_BULDINGTYPES_NEEDEDHEAT + " FROM " + TABLE_BUILDINGTYPES + " WHERE " + COLUMN_BUILDINGTYPES_BUILDINGTYPE +
            " = ?";

    private PreparedStatement queryNeededheatFromBuildingTypes;

    private static BuildingTypeRepository instance = new BuildingTypeRepository();

    private BuildingTypeRepository() {

    }

    public static BuildingTypeRepository getInstance() {
        return instance;
    }

    public void openPreparedStatementsFromBuildingTypeRepository() throws SQLException {
        queryNeededheatFromBuildingTypes = DataSource.getInstance().getConnection().prepareStatement(QUERY_NEEDEDHEAT_FROM_BUILDINGTYPES);
    }

    public void closePreparedStatementsFromBuildingTypeRepository() throws SQLException {
        queryNeededheatFromBuildingTypes.close();
    }

    public List<BuildingType> queryBuildingsTypes() {

        try (Statement statement = DataSource.getInstance().getConnection().createStatement();
             ResultSet result = statement.executeQuery(QUERY_BUILDINGTYPES)) {

            List<BuildingType> buildingTypeList = new ArrayList<>();
            while (result.next()) {
                BuildingType buildingType = new BuildingType();
                buildingType.setId(result.getInt(1));
                buildingType.setBuildingType(result.getString(2));
                buildingType.setNeededHeat(result.getInt(3));
                buildingTypeList.add(buildingType);
            }

            return buildingTypeList;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public double queryNeededheatFromBuildingTypes(String typeOfBuilding) {
        try {
            queryNeededheatFromBuildingTypes.setString(1, typeOfBuilding);
            ResultSet resultSet = queryNeededheatFromBuildingTypes.executeQuery();
            double neededHeat = resultSet.getDouble(1);
            resultSet.close();
            return neededHeat;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

}
