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
    public void Test1(){
        TerminalNetwork t = new TerminalNetwork("abc",50);

        int i = 1;
        while(i <= 30){
            Client c = new Client("abc"+i, "Normal");
            Terminal terminal = new Terminal("xyz"+i);
            c.addTerminal(terminal);

            t.addClient(c);
            t.addTerminal(terminal, c.getName());
            i++;
        }

        assertEquals(t.getName(),"abc");
        assertEquals(t.getMaxClients(),50);
        assertEquals(t.getClients().size(),30);
        
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

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void Test2(){
        TerminalNetwork t = new TerminalNetwork("ab",239);

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

        assertEquals(t.getMaxClients(),240);
        assertEquals(t.getName(),"ab");
        assertEquals(t.getClients().size(),180);
        
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

    public void Test4(){
        TerminalNetwork t = new TerminalNetwork("23456981",2099);
        t.setMaxClients(2100);
        t.setName("zxyabcdef");

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
        assertEquals(t.getName(),"zxyabcdef");
        assertEquals(t.getMaxClients(),2100);
        assertEquals(t.getClients().size(),1000);
    }
    
    public void Test5(){
        TerminalNetwork t = new TerminalNetwork("abcd",5000);

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
        assertEquals(t.getName(),"abcd");
        assertEquals(t.getMaxClients(),5000);
        assertEquals(t.getClients().size(),3000);
    }
    
    public void Test6(){
        TerminalNetwork t = new TerminalNetwork("abcde",4999);

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

        assertThrows(IllegalArgumentException.class, () -> t.setMaxClients(5001));
        assertEquals(t.getMaxClients(),4999);

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
        assertEquals(t.getName(),"abcde");
        assertEquals(t.getClients().size(),3500);
    }

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
        assertEquals(t.getMaxClients(),4000);
        
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
        
        assertEquals(t.getClients().size(),3099);
    }
    
    public void Test10(){
        TerminalNetwork t = new TerminalNetwork("123456789",4300);

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

        assertEquals(t.getClients().size(),4100);

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