package sample.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// This class is responsible for contact with database
public class DataSource {

    public static final String DB_NAME = "ecoheatDB.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;

    private Connection connection;

    private final FuelRepository fuelRepository = FuelRepository.getInstance();

    private final HeatPumpRepository heatPumpRepository = HeatPumpRepository.getInstance();

    private final ClientRepository clientRepository = ClientRepository.getInstance();

    private final BuildingTypeRepository buildingTypeRepository = BuildingTypeRepository.getInstance();


    private static final DataSource instance = new DataSource();

    //    Private constructor to make this class a Singleton
    private DataSource() {

    }

    public static DataSource getInstance() {
        return instance;
    }

    //    This method is supposed to establish a connection with a data base
    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);

            heatPumpRepository.openPreparedStatementsFromHeatPumpRepository();
            clientRepository.openPreparedStatementsFromClientRepository();
            buildingTypeRepository.openPreparedStatementsFromBuildingTypeRepository();
            fuelRepository.openPreparedStatementsFromFuelRepository();
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //    This method is supposed to close a connection with a data base
    public boolean close() {
        try {
            heatPumpRepository.closePreparedStatementsFromHeatPumpRepository();
            clientRepository.closePreparedStatementsFromClientRepository();
            buildingTypeRepository.closePreparedStatementsFromBuildingTypeRepository();
            fuelRepository.closePreparedStatementsFromFuelRepository();

            if (connection != null) {
                connection.close();
                return true;
            } else {
                System.out.println("Connection is null");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close the database: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
