package ap;
 
import org.testng.annotations.Test;
import static org.testng.Assert.*;

@Test
public class TestTerminalNetwork{

    private static final String NAME_ON = "abc";
    private static final int NAME_ON_LENGTH = 3;
    private static final String NAME_OFF_1 = "ab";
    private static final int NAME_OFF_1_LENGTH = 2;
    private static final String NAME_OFF_2 = "zxyabcdef";
    private static final int NAME_OFF_2_LENGTH = 9;

    private static final int MAXCLIENTS_ON = 50000;
    private static final int MAXCLIENTS_OFF = 50001;

    private static final int CLIENTS_SIZE_OFF = 30001;

    @Test(testName = "TC1 -- name.length() == " + NAME_ON_LENGTH + " -- ctor")
    public void testValidNameWith3CharForTerminalNetwork() {
        TerminalNetwork t = new TerminalNetwork(NAME_ON, 50);

        int i = 1;
        while(i <= 30) {
            Client c = new Client(NAME_ON+i, "Normal");
            t.addClient(c);
            i++;
        }
        
        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()) {
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()) {
                if (client1.getName().equals(client2.getName())) {
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1) {
                repeated = true;
                break;
            }
        }

        assertEquals(t.getName(), NAME_ON);
        assertEquals(t.getMaxClients(), 50);
        assertEquals(t.getClients().size(), 30);
        assertEquals(repeated, false);
    }

    @Test(testName = "TC2 -- name.length() == " + NAME_OFF_1_LENGTH + " -- setMaxClients",
            expectedExceptions = IllegalArgumentException.class)
    public void testInvalidNameWith2charForTerminalNetwork() {
        TerminalNetwork t = new TerminalNetwork(NAME_OFF_1, 239);
        t.setMaxClients(240);
        
        int i = 1;
        while(i <= 220) {
            Client c = new Client("ccc"+i, "Normal");
            t.addClient(c);
            i++;
        }
        
        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()) {
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()) {
                if (client1.getName().equals(client2.getName())) {
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1) {
                repeated = true;
                break;
            }
        }

        assertEquals(t.getMaxClients(), 240);
        assertEquals(t.getName(), NAME_OFF_1);
        assertEquals(t.getClients().size(), 220);
        assertEquals(repeated, false);
    }

    @Test(testName = "TC4 -- name.length() == " + NAME_OFF_2_LENGTH + " -- setname")
    public void testValidNameWith9CharForTerminalNetwork() {
        TerminalNetwork t = new TerminalNetwork("23456981", 10000);
        t.setName(NAME_OFF_2);

        int i = 1;
        while(i <= 9900) {
            Client c = new Client("abc"+i, "Normal");
            t.addClient(c);
            i++;
        }

        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()) {
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()) {
                if (client1.getName().equals(client2.getName())) {
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1) {
                repeated = true;
                break;
            }
        }

        assertEquals(repeated, false);
        assertEquals(t.getMaxClients(), 10000);
        assertEquals(t.getClients().size(), 9900);
        assertEquals(t.getName(), NAME_OFF_2);
    }
    
    @Test(testName = "TC5 -- maxClients == " + MAXCLIENTS_ON + " -- ctor")
    public void testValidMaxClientsInTerminalNetwork() {
        TerminalNetwork t = new TerminalNetwork("abcd", MAXCLIENTS_ON);

        int i = 1;
        while(i <= 15000) {
            Client c = new Client("abc"+i, "Normal");
            t.addClient(c);
            i++;
        }

        Client c1 = new Client("abc15001", "Normal");
        t.addClient(c1);
        t.removeClient(c1);

        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()) {
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()){
                if (client1.getName().equals(client2.getName())) {
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1) {
                repeated = true;
                break;
            }
        }

        assertEquals(t.getMaxClients(), MAXCLIENTS_ON);
        assertEquals(t.getName(), "abcd");
        assertEquals(t.getClients().size(), 15000);
        assertEquals(repeated, false);
    }
    
    @Test(testName = "TC6 -- maxClients == " + MAXCLIENTS_OFF + " -- setMaxClients")
    public void testInvalidMaxClientsInTerminalNetwork() {
        TerminalNetwork t = new TerminalNetwork("abcde", 49999);

        int i = 1;
        while(i <= 20000) {
            Client c = new Client("abc"+i, "Normal");
            t.addClient(c);
            i++;
        }

        Client c1 = new Client("abc4000", "Normal");
        t.addClient(c1);
        t.removeClient(c1);
        
        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()) {
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()) {
                if (client1.getName().equals(client2.getName())) {
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1) {
                repeated = true;
                break;
            }
        }

        assertEquals(repeated, false);
        assertThrows(IllegalArgumentException.class, () -> t.setMaxClients(MAXCLIENTS_OFF));
        assertEquals(t.getMaxClients(), 49999);
        assertEquals(t.getClients().size(), 20000);
        assertEquals(t.getName(), "abcde");
    }

    @Test(testName = "TC8 -- clients.size() == " + CLIENTS_SIZE_OFF + " -- addClient")
    public void testInvalidClientsSizeInTerminalNetwork() {
        TerminalNetwork t = new TerminalNetwork("1234567", 30000);

        Client c1 = new Client("abc30001", "Normal");
        
        int i = 1;
        while(i <= 30000) {
            Client c = new Client("123"+i, "Gold");
            t.addClient(c);
            i++;
        }
        
        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()) {
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()) {
                if (client1.getName().equals(client2.getName())) {
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1) {
                repeated = true;
                break;
            }
        }

        assertThrows(InvalidInvocationException.class, () -> t.addClient(c1));
        assertEquals(t.getMaxClients(), 30000);
        assertEquals(repeated, false);
        assertEquals(t.getName(),"1234567");
        assertEquals(t.getClients().size(), 30000);
    }
    
    @Test(testName = "TC9 -- c.getName() == True -- ")
    public void testClientsNamesAreUniqueInTerminalNetwork() {
        TerminalNetwork t = new TerminalNetwork("12345678", 25000);

        int i = 1;
        while(i <= 22000) {
            Client c = new Client("abc"+i, "Normal");
            t.addClient(c);
            i++;
        }
        
        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()) {
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()){
                if (client1.getName().equals(client2.getName())) {
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1) {
                repeated = true;
                break;
            }
        }

        assertEquals(repeated, false);
        assertEquals(t.getMaxClients(), 25000);
        assertEquals(t.getClients().size(), 22000);
        assertEquals(t.getName(),"12345678");
    }
    
    @Test(testName = "TC10 -- c.getName() == False -- ")
    public void testClientsNamesAreNotUniqueInTerminalNetwork() {
        TerminalNetwork t = new TerminalNetwork("12345", 35000);

        int i = 1;
        while(i <= 27999){
            Client c = new Client("abc"+i, "Normal");
            t.addClient(c);
            i++;
        }

        Client c1 = new Client("abc3000", "Normal");
        t.addClient(c1);

        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()) {
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()) {
                if (client1.getName().equals(client2.getName())) {
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1) {
                repeated = true;
                break;
            }
        }

        assertEquals(t.getName(), "12345");
        assertEquals(t.getMaxClients(), 35000);
        assertEquals(repeated, true);
        assertEquals(t.getClients().size(), 28000);
    }
}