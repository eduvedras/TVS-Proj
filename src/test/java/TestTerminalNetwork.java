package ap;
 
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Test
public class TestTerminalNetwork{

    private static final String NAME_ON = "abc";
    private static final int NAME_ON_LENGTH = 3;
    private static final String NAME_OFF_1 = "ab";
    private static final int NAME_OFF_1_LENGTH = 2;
    private static final String NAME_OFF_2 = "zxyabcdef";
    private static final int NAME_OFF_2_LENGTH = 9;

    private static final int MAXCLIENTS_ON = 5000;
    private static final int MAXCLIENTS_OFF = 5001;

    @Test(testName = "TC1 -- name.length() == " + NAME_ON_LENGTH + " -- ctor")
    public void Test1(){
        TerminalNetwork t = new TerminalNetwork(NAME_ON, 50);

        int i = 1;
        while(i <= 30) {
            Client c = new Client(NAME_ON+i, "Normal");
            Terminal terminal = new Terminal("xyz"+i);
            c.addTerminal(terminal);

            t.addClient(c);
            t.addTerminal(terminal, c.getName());
            i++;
        }

        assertEquals(t.getName(), NAME_ON);
        assertEquals(t.getMaxClients(), 50);
        assertEquals(t.getClients().size(), 30);
        assertEquals(t.getTerminal().size(), 30);
        
        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()){
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()){
                if (client1.getName().equals(client2.getName())){
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1){
                repeated = true;
                break;
            }
        }
        assertEquals(repeated, false);

        boolean aux = false;
        for (Terminal t1 : t.getTerminal()){
            aux = false;
            for (Client c : t.getClients()){
                if (c.getTerminals().contains(t1)){
                    aux = true;
                    break;
                }
            }
            if (aux == false){
                fail("Terminal does not have a client");
            }
        }
    }

    @Test(testName = "TC2 -- name.length() == " + NAME_OFF_1_LENGTH + " -- setMaxClients",
            expectedExceptions = IllegalArgumentException.class)
    public void Test2(){
        TerminalNetwork t = new TerminalNetwork(NAME_OFF_1, 239);

        t.setMaxClients(240);
        
        int i = 1;
        while(i <= 180){
            Client c = new Client("ccc"+i, "Normal");
            Terminal terminal = new Terminal("ttt"+i);
            c.addTerminal(terminal);

            t.addClient(c);
            t.addTerminal(terminal, c.getName());
            i++;
        }

        assertEquals(t.getMaxClients(), 240);
        assertEquals(t.getName(), NAME_OFF_1);
        assertEquals(t.getClients().size(), 180);
        
        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()){
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()){
                if (client1.getName().equals(client2.getName())){
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1){
                repeated = true;
                break;
            }
        }
        assertEquals(repeated, false);

        boolean aux = false;
        for (Terminal t1 : t.getTerminal()){
            aux = false;
            for (Client c : t.getClients()){
                if (c.getTerminals().contains(t1)){
                    aux = true;
                    break;
                }
            }
            if(aux == false){
                fail("Terminal does not have a client");
            }
        }
    }

    @Test(testName = "TC4 -- name.length() == " + NAME_OFF_2_LENGTH + " -- setname")
    public void Test4(){
        TerminalNetwork t = new TerminalNetwork("23456981",2100);
        //t.setMaxClients(2100);
        t.setName(NAME_OFF_2);

        int i = 1;
        while(i <= 1000){
            Terminal terminal = new Terminal("xyz"+i);
            Client c = new Client("abc"+i, "Normal");
            c.addTerminal(terminal);

            t.addClient(c);
            t.addTerminal(terminal, c.getName());
            i++;
        }

        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()){
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()){
                if (client1.getName().equals(client2.getName())){
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1){
                repeated = true;
                break;
            }
        }
        assertEquals(repeated, false);

        boolean aux = false;
        for (Terminal t1 : t.getTerminal()){
            aux = false;
            for (Client c : t.getClients()){
                if (c.getTerminals().contains(t1)){
                    aux = true;
                    break;
                }
            }
            if(aux == false){
                fail("Terminal does not have a client");
            }
        }
        assertEquals(t.getName(), NAME_OFF_2);
        assertEquals(t.getMaxClients(), 2100);
        assertEquals(t.getClients().size(), 1000);
    }
    
    @Test(testName = "TC5 -- maxClients == " + MAXCLIENTS_ON + " -- ctor")
    public void Test5(){
        TerminalNetwork t = new TerminalNetwork("abcd", MAXCLIENTS_ON);

        int i = 1;
        while(i <= 3000){
            Terminal terminal = new Terminal("xyz"+i);
            Client c = new Client("abc"+i, "Normal");
            c.addTerminal(terminal);

            t.addClient(c);
            t.addTerminal(terminal, c.getName());
            i++;
        }

        Client c1 = new Client("abc3001", "Normal");
        t.addClient(c1);
        t.removeClient(c1);

        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()){
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()){
                if (client1.getName().equals(client2.getName())){
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1){
                repeated = true;
                break;
            }
        }
        assertEquals(repeated, false);

        boolean aux = false;
        for (Terminal t1 : t.getTerminal()){
            aux = false;
            for (Client c : t.getClients()){
                if (c.getTerminals().contains(t1)){
                    aux = true;
                    break;
                }
            }
            if(aux == false){
                fail("Terminal does not have a client");
            }
        }
        assertEquals(t.getName(), "abcd");
        assertEquals(t.getMaxClients(), MAXCLIENTS_ON);
        assertEquals(t.getClients().size(), 3000);
    }
    
    @Test(testName = "TC6 -- maxClients == " + MAXCLIENTS_OFF + " -- setMaxClients")
    public void Test6(){
        TerminalNetwork t = new TerminalNetwork("abcde", 4999);

        int i = 1;
        while(i <= 3500){
            Client c = new Client("abc"+i, "Normal");
            Terminal terminal = new Terminal("xyz"+i);
            c.addTerminal(terminal);

            t.addClient(c);
            t.addTerminal(terminal, c.getName());
            i++;
        }

        Client c1 = new Client("abc4000", "Normal");
        t.addClient(c1);
        t.removeClient(c1);
        
        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()){
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()){
                if (client1.getName().equals(client2.getName())){
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1){
                repeated = true;
                break;
            }
        }
        assertEquals(repeated, false);

        assertThrows(IllegalArgumentException.class, () -> t.setMaxClients(MAXCLIENTS_OFF));
        assertEquals(t.getMaxClients(), 4999);

        boolean aux = false;
        for (Terminal t1 : t.getTerminal()){
            aux = false;
            for (Client c : t.getClients()){
                if (c.getTerminals().contains(t1)){
                    aux = true;
                    break;
                }
            }
            if(aux == false){
                fail("Terminal does not have a client");
            }
        }
        assertEquals(t.getName(), "abcde");
        assertEquals(t.getClients().size(), 3500);
    }

    @Test(testName = "TC9 -- clients.size() == 3001 -- ")
    public void Test8(){
        TerminalNetwork t = new TerminalNetwork("1234567",3000);
        
        int i = 1;
        while(i <= 3000){
            Client c = new Client("123"+i, "Gold");
            Terminal terminal = new Terminal("321"+i);
            c.addTerminal(terminal);

            t.addClient(c);
            t.addTerminal(terminal, c.getName());
            i++;
        }

        Client c1 = new Client("abc3001", "Normal");

        assertThrows(InvalidInvocationException.class, () -> t.addClient(c1));
        
        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()){
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()){
                if (client1.getName().equals(client2.getName())){
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1){
                repeated = true;
                break;
            }
        }
        assertEquals(repeated, false);

        assertEquals(t.getName(),"1234567");
        assertEquals(t.getMaxClients(),3000);
        assertEquals(t.getClients().size(),3000);

        boolean aux = false;
        for (Terminal t1 : t.getTerminal()){
            aux = false;
            for (Client c : t.getClients()){
                if (c.getTerminals().contains(t1)){
                    aux = true;
                    break;
                }
            }
            if(aux == false){
                fail("Terminal does not have a client");
            }
        }
    }
    
    @Test(testName = "TC9 -- c.getName() == True -- ")
    public void Test9(){
        TerminalNetwork t = new TerminalNetwork("12345678",4000);

        int i = 1;
        while(i <= 3099){
            Client c = new Client("abc"+i, "Normal");
            Terminal terminal = new Terminal("xyz"+i);
            c.addTerminal(terminal);

            t.addClient(c);
            t.addTerminal(terminal, c.getName());
            i++;
        }

        assertEquals(t.getName(),"12345678");
        boolean aux = false;
        for (Terminal t1 : t.getTerminal()){
            aux = false;
            for (Client c : t.getClients()){
                if (c.getTerminals().contains(t1)){
                    aux = true;
                    break;
                }
            }
            if(aux == false){
                fail("Terminal does not have a client");
            }
        }
        assertEquals(t.getMaxClients(), 4000);
        
        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()){
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()){
                if (client1.getName().equals(client2.getName())){
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1){
                repeated = true;
                break;
            }
        }
        assertEquals(repeated, false);
        
        assertEquals(t.getClients().size(), 3099);
    }
    
    @Test(testName = "TC10 -- c.getName() == False -- ")
    public void Test10(){
        TerminalNetwork t = new TerminalNetwork("123456789", 4300);

        int i = 1;
        while(i <= 4099){
            Client c = new Client("abc"+i, "Normal");
            Terminal terminal = new Terminal("xyz"+i);
            c.addTerminal(terminal);

            t.addClient(c);
            t.addTerminal(terminal, c.getName());
            i++;
        }

        Client c1 = new Client("abc3000", "Normal");
        t.addClient(c1);

        assertEquals(t.getName(),"123456789");
        assertEquals(t.getMaxClients(),4300);

        boolean repeated = false;
        int clients_with_same_name = 0;
        for (Client client1 : t.getClients()){
            clients_with_same_name = 0;
            for (Client client2 : t.getClients()){
                if (client1.getName().equals(client2.getName())){
                    clients_with_same_name++;
                }
            }
            if (clients_with_same_name > 1){
                repeated = true;
                break;
            }
        }
        assertEquals(repeated, true);

        assertEquals(t.getClients().size(), 4100);

        boolean aux = false;
        for (Terminal t1 : t.getTerminal()){
            aux = false;
            for (Client c : t.getClients()){
                if (c.getTerminals().contains(t1)){
                    aux = true;
                    break;
                }
            }
            if(aux == false){
                fail("Terminal does not have a client");
            }
        }
    }
}