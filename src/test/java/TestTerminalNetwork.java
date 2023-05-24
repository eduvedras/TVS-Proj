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
    public void testValidNameWith3CharForTerminal() {
        TerminalNetwork t = new TerminalNetwork(NAME_ON, 50);

        int i = 1;
        while(i <= 30) {
            Client c = new Client(NAME_ON+i, "Normal");
            t.addClient(c);
            i++;
        }

        assertEquals(t.getName(), NAME_ON);
        assertEquals(t.getMaxClients(), 50);
        assertEquals(t.getClients().size(), 30);
        
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
    }

    @Test(testName = "TC2 -- name.length() == " + NAME_OFF_1_LENGTH + " -- setMaxClients",
            expectedExceptions = IllegalArgumentException.class)
    public void testInvalidNameWith2charForTerminal() {
        TerminalNetwork t = new TerminalNetwork(NAME_OFF_1, 239);

        t.setMaxClients(240);
        
        int i = 1;
        while(i <= 980) {
            Client c = new Client("ccc"+i, "Normal");
            t.addClient(c);
            i++;
        }

        assertEquals(t.getMaxClients(), 240);
        assertEquals(t.getName(), NAME_OFF_1);
        assertEquals(t.getClients().size(), 980);
        
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
    }

    @Test(testName = "TC4 -- name.length() == " + NAME_OFF_2_LENGTH + " -- setname")
    public void testValidNameWith9CharForTerminal() {
        TerminalNetwork t = new TerminalNetwork("23456981", 10000);
        
        t.setName(NAME_OFF_2);

        int i = 1;
        while(i <= 10000) {
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

        assertEquals(t.getName(), NAME_OFF_2);
        assertEquals(t.getMaxClients(), 10000);
        assertEquals(t.getClients().size(), 10000);
    }
    
    @Test(testName = "TC5 -- maxClients == " + MAXCLIENTS_ON + " -- ctor")
    public void testValidMaxClients() {
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
        assertEquals(repeated, false);

        assertEquals(t.getName(), "abcd");
        assertEquals(t.getMaxClients(), MAXCLIENTS_ON);
        assertEquals(t.getClients().size(), 15000);
    }
    
    @Test(testName = "TC6 -- maxClients == " + MAXCLIENTS_OFF + " -- setMaxClients")
    public void testInvalidMaxClients() {
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

        assertEquals(t.getName(), "abcde");
        assertEquals(t.getClients().size(), 20000);
    }

    @Test(testName = "TC8 -- clients.size() == " + CLIENTS_SIZE_OFF + " -- addClient")
    public void testInvalidClientsSize() {
        TerminalNetwork t = new TerminalNetwork("1234567", 30000);
        
        int i = 1;
        while(i <= 30000) {
            Client c = new Client("123"+i, "Gold");
            t.addClient(c);
            i++;
        }

        Client c1 = new Client("abc30001", "Normal");

        assertThrows(InvalidInvocationException.class, () -> t.addClient(c1));
        
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

        assertEquals(t.getName(),"1234567");
        assertEquals(t.getMaxClients(), 30000);
        assertEquals(t.getClients().size(), 30000);
    }
    
    @Test(testName = "TC9 -- c.getName() == True -- ")
    public void testClientsNamesAreUniqueInTerminalNetwork() {
        TerminalNetwork t = new TerminalNetwork("12345678", 30000);

        int i = 1;
        while(i <= 22000) {
            Client c = new Client("abc"+i, "Normal");
            t.addClient(c);
            i++;
        }

        assertEquals(t.getName(),"12345678");
        assertEquals(t.getMaxClients(), 30000);
        
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
        
        assertEquals(t.getClients().size(), 22000);
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

        assertEquals(t.getName(),"12345");
        assertEquals(t.getMaxClients(),35000);

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
        assertEquals(repeated, true);

        assertEquals(t.getClients().size(), 28000);
    }
}