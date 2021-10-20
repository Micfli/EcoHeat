package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// This class is responsible for contact with database
public class DataSource {

    public static final String DB_NAME = "ecoheatDB.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;

    //    HEATPUMPS
    public static final String TABLE_HEATPUMPS = "heatpumps";
    public static final String COLUMN_HEATPUMPS_ID = "_id";
    public static final String COLUMN_HEATPUMPS_MANUFACTURER = "manufacturer";
    public static final String COLUMN_HEATPUMPS_MODEL = "model";
    public static final String COLUMN_HEATPUMPS_POWER = "power";
    public static final String COLUMN_HEATPUMPS_TE = "te";
    public static final String COLUMN_HEATPUMPS_TWMAX = "twmax";
    public static final String COLUMN_HEATPUMPS_COP = "cop";
    public static final String COLUMN_HEATPUMPS_PRICE = "price";
    public static final String COLUMN_HEATPUMPS_MATCH = "match";

    public static final int INDEX_HEATPUMPS_ID = 1;
    public static final int INDEX_HEATPUMPS_MANUFACTURER = 2;
    public static final int INDEX_HEATPUMPS_MODEL = 3;
    public static final int INDEX_HEATPUMPS_POWER = 4;
    public static final int INDEX_HEATPUMPS_TE = 5;
    public static final int INDEX_HEATPUMPS_TWMAX = 6;
    public static final int INDEX_HEATPUMPS_COP = 7;
    public static final int INDEX_HEATPUMPS_PRICE = 8;
    public static final int INDEX_HEATPUMPS_MATCH = 9;

    //  SELECT * FROM heatpumps
    public static final String QUERY_HEATPUMPS = "SELECT * FROM " + TABLE_HEATPUMPS;

    //   INSERT INTO heatpumps (manufacturer, model, power, te, twmax, cop, price) VALUES (?, ?, ?, ?, ?, ?, ?)
    public static final String INSERT_INTO_HEATPUMPS = "INSERT INTO " + TABLE_HEATPUMPS + " (" + COLUMN_HEATPUMPS_MANUFACTURER + ", " + COLUMN_HEATPUMPS_MODEL + ", " + COLUMN_HEATPUMPS_POWER +
            ", " + COLUMN_HEATPUMPS_TE + ", " + COLUMN_HEATPUMPS_TWMAX + ", " + COLUMN_HEATPUMPS_COP + ", " + COLUMN_HEATPUMPS_PRICE + ") VALUES (?, ?, ?, ?, ?, ?, ?)";

    //    DELETE FROM heatpumps WHERE heatpumps._id = ?
    public static final String DELETE_FROM_HEATPUMPS = "DELETE FROM " + TABLE_HEATPUMPS + " WHERE " + COLUMN_HEATPUMPS_ID + " =  ?";

    //    UPDATE heatpumps SET manufacturer = ?, model = ?, power = ?, te = ?, twmax = ?, cop = ?, price = ? WHERE heatpumps._id = ?
    public static final String UPDATE_HEATPUMPS = "UPDATE " + TABLE_HEATPUMPS + " SET " + COLUMN_HEATPUMPS_MANUFACTURER + " = ?, " + COLUMN_HEATPUMPS_MODEL + " = ?, " +
            COLUMN_HEATPUMPS_POWER + " = ?, " + COLUMN_HEATPUMPS_TE + " = ?, " + COLUMN_HEATPUMPS_TWMAX + " = ?, " + COLUMN_HEATPUMPS_COP + " = ?, " + COLUMN_HEATPUMPS_PRICE +
            " = ? WHERE " + TABLE_HEATPUMPS + "." + COLUMN_HEATPUMPS_ID + " = ?";
    //    SELECT power FROM heatpumps
    public static final String QUERY_POWER_FROM_HEATPUMPS = "SELECT " + COLUMN_HEATPUMPS_POWER + " FROM " + TABLE_HEATPUMPS;
    //    SELECT match FROM heatpumps
    public static final String QUERY_MATCH_FROM_HEATPUMPS = "SELECT " + COLUMN_HEATPUMPS_MATCH + " FROM " + TABLE_HEATPUMPS;
    //    UPDATE heatpumps SET match = ? WHERE _id = ?
    public static final String UPDATE_HEATPUMPS_SET_MATCH = "UPDATE " + TABLE_HEATPUMPS + " SET " + COLUMN_HEATPUMPS_MATCH + " = ? WHERE " + COLUMN_HEATPUMPS_ID + " = ?";
    // ORDER BY match
    public static final String HEATPUMPS_ORDER_BY_MATCH = "ORDER BY " + COLUMN_HEATPUMPS_MATCH;
    //    SELECT _id FROM heatpumps WHERE heatpumps.manufacturer = ? AND heatpumps.model = ? AND heatpumps.power = ?
    public static final String QUERY_ID_FROM_HEATPUMPS = "SELECT " + COLUMN_HEATPUMPS_ID + " FROM " + TABLE_HEATPUMPS + " WHERE " + COLUMN_HEATPUMPS_MANUFACTURER + " = ? AND " +
            COLUMN_HEATPUMPS_MODEL + " = ? AND " + COLUMN_HEATPUMPS_POWER + " = ?";

    //    BUILDINGTYPES
    public static final String TABLE_BUILDINGTYPES = "buildingtypes";
    public static final String COLUMN_BUILDINGTYPES_BUILDINGTYPE = "buildingtype";
    public static final String COLUMN_BULDINGTYPES_NEEDEDHEAT = "neededheat";

    //    SELECT * FROM buildingtypes
    public static final String QUERY_BUILDINGTYPES = "SELECT * FROM " + TABLE_BUILDINGTYPES;
    //    SELECT neededheat FROM buildingtypes WHERE buildingtype = ?
    public static final String QUERY_NEEDEDHEAT_FROM_BUILDINGTYPES = "SELECT " + COLUMN_BULDINGTYPES_NEEDEDHEAT + " FROM " + TABLE_BUILDINGTYPES + " WHERE " + COLUMN_BUILDINGTYPES_BUILDINGTYPE +
            " = ?";

    //    CLIENTS
    public static final String TABLE_CLIENTS = "clients";

    public static final String COLUMN_CLIENTS_ID = "_id";
    public static final String COLUMN_CLIENTS_NAME = "name";
    public static final String COLUMN_CLIENTS_ADRESS = "adress";
    public static final String COLUMN_CLIENTS_HEATPUMP = "heatpump";
    public static final String COLUMN_CLIENTS_REQUIREDPOWER = "requiredpower";

    public static final int INDEX_CLIENTS_ID = 1;
    public static final int INDEX_CLIENTS_NAME = 2;
    public static final int INDEX_CLIENTS_ADRESS = 3;

    //    INSERT INTO clients (name, adress, heatpump, requiredpower) VALUES (?, ?, ?, ?)
    public static final String INSERT_INTO_CLIENTS = "INSERT INTO " + TABLE_CLIENTS + " (" + COLUMN_CLIENTS_NAME + ", " + COLUMN_CLIENTS_ADRESS + ", " + COLUMN_CLIENTS_HEATPUMP + ", " +
            COLUMN_CLIENTS_REQUIREDPOWER + ") VALUES (?, ?, ?, ?)";
    //    SELECT clients.name, clients.adress, heatpumps.manufacturer, heatpumps.model, heatpumps.power, heatpumps.price, clients.requiredpower
//    FROM clients INNER JOIN heatpumps ON heatpumps._id = clients.heatpump
    public static final String QUERY_CLIENTS = "SELECT " + TABLE_CLIENTS + "." + COLUMN_CLIENTS_NAME + ", " + TABLE_CLIENTS + "." + COLUMN_CLIENTS_ADRESS + ", " +
            TABLE_HEATPUMPS + "." + COLUMN_HEATPUMPS_MANUFACTURER + ", " + TABLE_HEATPUMPS + "." + COLUMN_HEATPUMPS_MODEL + ", " +
            TABLE_HEATPUMPS + "." + COLUMN_HEATPUMPS_POWER + ", " + TABLE_HEATPUMPS + "." + COLUMN_HEATPUMPS_PRICE + ", " + TABLE_CLIENTS + "." +
            COLUMN_CLIENTS_REQUIREDPOWER + " FROM " + TABLE_CLIENTS + " INNER JOIN " + TABLE_HEATPUMPS + " ON " +
            TABLE_HEATPUMPS + "." + COLUMN_HEATPUMPS_ID + " = " + TABLE_CLIENTS + "." + COLUMN_CLIENTS_HEATPUMP;
    //    DELETE FROM clients WHERE clients._id = ?
    public static final String DELETE_FROM_CLIENTS = "DELETE FROM " + TABLE_CLIENTS + " WHERE " + TABLE_CLIENTS + "." + COLUMN_CLIENTS_ID + " = ?";
    //    SELECT _id FROM clients WHERE clients.name = ? AND clients.adress = ? AND clients.heatpump = ?
    public static final String QUERY_ID_FROM_CLIENTS = "SELECT " + TABLE_CLIENTS + "." + COLUMN_CLIENTS_ID + " FROM " + TABLE_CLIENTS + " WHERE " + TABLE_CLIENTS +
            "." + COLUMN_CLIENTS_NAME + " = ? AND " + TABLE_CLIENTS + "." + COLUMN_CLIENTS_ADRESS + " = ? AND " + TABLE_CLIENTS + "." + COLUMN_CLIENTS_HEATPUMP
            + " = ?";


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

    private Connection connection;

    //    Declaring PreparedStatements to prevent SQL Injection attack
    private PreparedStatement insertIntoHeatpumps;

    private PreparedStatement deleteFromHeatpumps;

    private PreparedStatement updateHeatpumps;

    private PreparedStatement updateHeatpumpsSetMatch;

    private PreparedStatement queryNeededheatFromBuildingTypes;

    private PreparedStatement insertIntoClients;

    private PreparedStatement queryIdFromHeatpumps;

    private PreparedStatement deleteFromClients;

    private PreparedStatement queryIdFromClients;

    private PreparedStatement queryEnergyFromFuel;

    private static DataSource instance = new DataSource();

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

            insertIntoHeatpumps = connection.prepareStatement(INSERT_INTO_HEATPUMPS);
            deleteFromHeatpumps = connection.prepareStatement(DELETE_FROM_HEATPUMPS);
            updateHeatpumps = connection.prepareStatement(UPDATE_HEATPUMPS);
            updateHeatpumpsSetMatch = connection.prepareStatement(UPDATE_HEATPUMPS_SET_MATCH);
            queryNeededheatFromBuildingTypes = connection.prepareStatement(QUERY_NEEDEDHEAT_FROM_BUILDINGTYPES);
            insertIntoClients = connection.prepareStatement(INSERT_INTO_CLIENTS);
            queryIdFromHeatpumps = connection.prepareStatement(QUERY_ID_FROM_HEATPUMPS);
            deleteFromClients = connection.prepareStatement(DELETE_FROM_CLIENTS);
            queryIdFromClients = connection.prepareStatement(QUERY_ID_FROM_CLIENTS);
            queryEnergyFromFuel = connection.prepareStatement(QUERY_ENERGY_FROM_FUELS);
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
            if (insertIntoHeatpumps != null) {
                insertIntoHeatpumps.close();
            }

            if (deleteFromHeatpumps != null) {
                deleteFromHeatpumps.close();
            }

            if (updateHeatpumps != null) {
                updateHeatpumps.close();
            }

            if (queryNeededheatFromBuildingTypes != null) {
                queryNeededheatFromBuildingTypes.close();
            }

            if (updateHeatpumpsSetMatch != null) {
                updateHeatpumpsSetMatch.close();
            }

            if (insertIntoClients != null) {
                insertIntoClients.close();
            }

            if (queryIdFromHeatpumps != null) {
                queryIdFromHeatpumps.close();
            }

            if (deleteFromClients != null) {
                deleteFromClients.close();
            }

            if (queryIdFromClients != null) {
                queryIdFromClients.close();
            }

            if (queryEnergyFromFuel != null) {
                queryEnergyFromFuel.close();
            }

            if (connection != null) {
                connection.close();
                return true;
            } else {
                System.out.println("Connection is null");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<HeatPump> queryHeatpumps() {

        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(QUERY_HEATPUMPS)) {

            List<HeatPump> heatPumpsList = new ArrayList<>();
            while (result.next()) {
                HeatPump heatPump = new HeatPump();
                heatPump.set_id(result.getInt(INDEX_HEATPUMPS_ID));
                heatPump.setManufacturer(result.getString(INDEX_HEATPUMPS_MANUFACTURER));
                heatPump.setModel(result.getString(INDEX_HEATPUMPS_MODEL));
                heatPump.setPower(result.getDouble(INDEX_HEATPUMPS_POWER));
                heatPump.setTe(result.getDouble(INDEX_HEATPUMPS_TE));
                heatPump.setTwmax(result.getDouble(INDEX_HEATPUMPS_TWMAX));
                heatPump.setCop(result.getDouble(INDEX_HEATPUMPS_COP));
                heatPump.setPrice(result.getDouble(INDEX_HEATPUMPS_PRICE));
                heatPump.setMatch(result.getDouble(INDEX_HEATPUMPS_MATCH));
                heatPumpsList.add(heatPump);
            }

            return heatPumpsList;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Double> queryPowerFromHeatPumps() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_POWER_FROM_HEATPUMPS)) {

            List<Double> powerOfHeatPumpsList = new ArrayList<>();
            while (resultSet.next()) {
                double power = resultSet.getDouble(1);
                powerOfHeatPumpsList.add(power);
            }
            return powerOfHeatPumpsList;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Double> queryMatchFromHeatpumps() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_MATCH_FROM_HEATPUMPS)) {

            List<Double> matchOfHeatPumpsList = new ArrayList<>();
            while (resultSet.next()) {
                double match = resultSet.getDouble(1);
                matchOfHeatPumpsList.add(match);
            }
            return matchOfHeatPumpsList;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertHeatpumps(String manufacturer, String model, double power, double te, double twmax, double cop, double price) {
        try {
            insertIntoHeatpumps.setString(1, manufacturer);
            insertIntoHeatpumps.setString(2, model);
            insertIntoHeatpumps.setDouble(3, power);
            insertIntoHeatpumps.setDouble(4, te);
            insertIntoHeatpumps.setDouble(5, twmax);
            insertIntoHeatpumps.setDouble(6, cop);
            insertIntoHeatpumps.setDouble(7, price);
            insertIntoHeatpumps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Insert heatpumps failed: " + e.getMessage());
            return false;
        }
    }

    public boolean updateHeatpumpsSetMatch(double match, double id) {
        try {
            updateHeatpumpsSetMatch.setDouble(1, match);
            updateHeatpumpsSetMatch.setDouble(2, id);
            updateHeatpumpsSetMatch.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Insert into heatpumps match failed: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteFromHeatpumps(int id) {
        try {
            deleteFromHeatpumps.setInt(1, id);
            deleteFromHeatpumps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Delete from heatpumps failed: " + e.getMessage());
            return false;
        }
    }

    public boolean updateHeatpump(int id, String manufacturer, String model, double power, double te, double twmax,
                                  double cop, double price) {
        try {
            updateHeatpumps.setString(1, manufacturer);
            updateHeatpumps.setString(2, model);
            updateHeatpumps.setDouble(3, power);
            updateHeatpumps.setDouble(4, te);
            updateHeatpumps.setDouble(5, twmax);
            updateHeatpumps.setDouble(6, cop);
            updateHeatpumps.setDouble(7, price);
            updateHeatpumps.setInt(8, id);
            updateHeatpumps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Update heatpumps failed: " + e.getMessage());
            return false;
        }
    }

    public int queryIdFromHeatpumps(String manufacturer, String model, double power) {
        try {
            queryIdFromHeatpumps.setString(1, manufacturer);
            queryIdFromHeatpumps.setString(2, model);
            queryIdFromHeatpumps.setDouble(3, power);
            ResultSet resultSet = queryIdFromHeatpumps.executeQuery();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return -1;
        }
    }

    public List<BuildingType> queryBuildingsTypes() {

        try (Statement statement = connection.createStatement();
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

    public boolean insertIntoClients(String name, String adress, int heatpump, double requiredPower) {
        try {
            insertIntoClients.setString(1, name);
            insertIntoClients.setString(2, adress);
            insertIntoClients.setInt(3, heatpump);
            insertIntoClients.setDouble(4, requiredPower);
            insertIntoClients.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Insert into clients error: " + e.getMessage());
            return false;
        }
    }

    public List<Project> queryClients() {
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(QUERY_CLIENTS)) {

            List<Project> projectsList = new ArrayList<>();
            while (result.next()) {
                Project project = new Project();
                project.setName(result.getString(1));
                project.setAdress(result.getString(2));
                project.setManufacturer(result.getString(3));
                project.setModel(result.getString(4));
                project.setPower(result.getDouble(5));
                project.setPrice(result.getDouble(6));
                project.setRequiredPower(result.getDouble(7));
                projectsList.add(project);
            }

            return projectsList;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public int queryIdFromClients(String name, String adress, int heatpumpId) {
        try {
            queryIdFromClients.setString(1, name);
            queryIdFromClients.setString(2, adress);
            queryIdFromClients.setInt(3, heatpumpId);
            ResultSet resultSet = queryIdFromClients.executeQuery();
            int id = resultSet.getInt(1);
            resultSet.close();
            return id;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return -1;
        }
    }

    public boolean deleteFromClients(int clientId) {
        try {
            deleteFromClients.setInt(1, clientId);
            deleteFromClients.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error when trying to delete from clients: " + e.getMessage());
            return false;
        }
    }

    public List<Fuel> queryFuels() {
        try (Statement statement = connection.createStatement();
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
}
