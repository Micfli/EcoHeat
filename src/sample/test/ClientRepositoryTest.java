package sample.test;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import sample.model.ClientRepository;
import sample.model.DataSource;

import static org.junit.Assert.*;

public class ClientRepositoryTest {

    @Before
    public void openDB() {
        DataSource.getInstance().open();
    }

    @Test
    public void insertIntoClientsWithNull() {
        assertFalse(ClientRepository.getInstance().insertIntoClients(null, null, -1, -1));
        assertFalse(ClientRepository.getInstance().insertIntoClients("A", "B", 1, 1));
    }

    @Test
    public void queryClientsWithClosedDB() {
        DataSource.getInstance().close();
        assertNull(ClientRepository.getInstance().queryClients());
    }

    @Test
    public void queryIdFromClientsWithClosedDB() {
        DataSource.getInstance().close();
        assertEquals(-1, ClientRepository.getInstance().queryIdFromClients("a","b",3));
    }

    @Test
    public void deleteFromClientsWithClosedDB() {
        DataSource.getInstance().close();
        assertFalse(ClientRepository.getInstance().deleteFromClients(1));
    }

    @AfterClass
    public static void closeDB() {
        DataSource.getInstance().close();
    }
}