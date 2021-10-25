package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuelRepository {

    private static FuelRepository instance = new FuelRepository();

    // FUELS
    public static final String TABLE_FUELS = "fuels";

    public static final String COLUMN_FUELS_ID = "_id";
    public static final String COLUMN_FUELS_NAME = "name";
    public static final String COLUMN_FUELS_ENERGY = "energy";

    //    SELECT * FROM fuels
    public static final String QUERY_FUELS = "SELECT * FROM " + TABLE_FUELS;
    //    SELECT energy FROM fuels WHERE name = ?
    public static final String QUERY_ENERGY_FROM_FUELS = "SELECT " + COLUMN_FUELS_ENERGY + " FROM " + TABLE_FUELS + " WHERE " +
            COLUMN_FUELS_NAME + " = ?";

//    private Connection connection = dataSource.getConnection();
    private static PreparedStatement queryEnergyFromFuel;

    private FuelRepository() {

    }

    public static FuelRepository getInstance() {
        return instance;
    }

    public List<Fuel> queryFuels() {
        try (Statement statement = DataSource.getInstance().getConnection().createStatement();
             ResultSet result = statement.executeQuery(QUERY_FUELS)) {

            List<Fuel> fuelsList = new ArrayList<>();
            while (result.next()) {
                Fuel fuel = new Fuel();
                fuel.setId(result.getInt(1));
                fuel.setName(result.getString(2));
                fuel.setEnergy(result.getInt(3));
                fuelsList.add(fuel);
            }

            return fuelsList;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public int queryEnergyFromFuels(String name) {
        try {
            queryEnergyFromFuel.setString(1, name);
            ResultSet resultSet = queryEnergyFromFuel.executeQuery();
            int energy = resultSet.getInt(1);
            resultSet.close();
            return energy;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return -1;
        }
    }

    public void openPreparedStatementsFromFuelRepository() throws SQLException {
        queryEnergyFromFuel = DataSource.getInstance().getConnection().prepareStatement(QUERY_ENERGY_FROM_FUELS);
    }

    public void closePreparedStatementsFromFuelRepository() throws SQLException {
        if (queryEnergyFromFuel != null) {
            queryEnergyFromFuel.close();
        }
    }

}
