package sample.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static sample.model.HeatPumpRepository.*;

public class ClientRepository {

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
    //    Declaring PreparedStatements to prevent SQL Injection attack
    private PreparedStatement insertIntoClients;

    private PreparedStatement deleteFromClients;

    private PreparedStatement queryIdFromClients;

    private static ClientRepository instance = new ClientRepository();

    private ClientRepository() {

    }

    public static ClientRepository getInstance() {
        return instance;
    }

    public void openPreparedStatementsFromClientRepository() throws SQLException {
        insertIntoClients = DataSource.getInstance().getConnection().prepareStatement(INSERT_INTO_CLIENTS);
        deleteFromClients = DataSource.getInstance().getConnection().prepareStatement(DELETE_FROM_CLIENTS);
        queryIdFromClients = DataSource.getInstance().getConnection().prepareStatement(QUERY_ID_FROM_CLIENTS);
    }

    public void closePreparedStatementsFromClientRepository() throws SQLException {
        insertIntoClients.close();
        deleteFromClients.close();
        queryIdFromClients.close();
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
        try (Statement statement = DataSource.getInstance().getConnection().createStatement();
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
 }
