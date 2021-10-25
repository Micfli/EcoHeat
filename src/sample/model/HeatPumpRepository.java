package sample.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HeatPumpRepository {

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
    //    Declaring PreparedStatements to prevent SQL Injection attack
    private PreparedStatement insertIntoHeatpumps;

    private PreparedStatement deleteFromHeatpumps;

    private PreparedStatement updateHeatpumps;

    private PreparedStatement updateHeatpumpsSetMatch;

    private PreparedStatement queryIdFromHeatpumps;

    private static HeatPumpRepository instance = new HeatPumpRepository();

    private HeatPumpRepository() {

    }

    public static HeatPumpRepository getInstance() {
        return instance;
    }

    public void openPreparedStatementsFromHeatPumpRepository() throws SQLException {
        insertIntoHeatpumps = DataSource.getInstance().getConnection().prepareStatement(INSERT_INTO_HEATPUMPS);
        deleteFromHeatpumps = DataSource.getInstance().getConnection().prepareStatement(DELETE_FROM_HEATPUMPS);
        updateHeatpumps = DataSource.getInstance().getConnection().prepareStatement(UPDATE_HEATPUMPS);
        updateHeatpumpsSetMatch = DataSource.getInstance().getConnection().prepareStatement(UPDATE_HEATPUMPS_SET_MATCH);
        queryIdFromHeatpumps = DataSource.getInstance().getConnection().prepareStatement(QUERY_ID_FROM_HEATPUMPS);
    }

    public void closePreparedStatementsFromHeatPumpRepository() throws SQLException {
        insertIntoHeatpumps.close();
        deleteFromHeatpumps.close();
        updateHeatpumps.close();
        updateHeatpumpsSetMatch.close();
        queryIdFromHeatpumps.close();
    }

    public List<HeatPump> queryHeatpumps() {

        try (Statement statement = DataSource.getInstance().getConnection().createStatement();
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
        try (Statement statement = DataSource.getInstance().getConnection().createStatement();
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
        try (Statement statement = DataSource.getInstance().getConnection().createStatement();
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
}
