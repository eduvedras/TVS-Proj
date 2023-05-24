package ap;

import java.util.ArrayList;
import java.util.List;

public class TerminalNetwork {
    private String name;
    private int maxClients;
    private List<Client> clients;
    private List<Terminal> terminals;

    public TerminalNetwork(String name, int maxClients) {
        if (name.length() < 3 || name.length() >= 10) {
            throw new IllegalArgumentException("Name must be between 3 and 20 characters long");
        }
        if (maxClients > 50000) {
            throw new IllegalArgumentException("Clients must be less than 50001");
        }
        this.name = name;
        this.maxClients = maxClients;
        this.clients = new ArrayList<>();
        this.terminals = new ArrayList<>();
    }

    public void setMaxClients(int maxClients) {
        if (maxClients > 50000) {
            throw new IllegalArgumentException("Clients must be less than 50001");
        }
        this.maxClients = maxClients;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        if (n.length() < 3 || n.length() >= 10) {
            throw new IllegalArgumentException("Name must be between 3 and 20 characters long");
        }
        name = n;
    }

    public void addClient(Client cl) {
        if (clients.size() == maxClients) {
            throw new InvalidInvocationException("Max number of clients reached");
        }
        clients.add(cl);
    }

    public void removeClient(Client cl) {
        clients.remove(cl);
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Terminal> getTerminal() {
        return terminals;
    }

    public void addTerminal(Terminal t, String clientId) {
        for (Client cl : clients) {
            if (cl.getName().equals(clientId)) {
                cl.addTerminal(t);
                terminals.add(t);
                break;
            }
        }
    }
}
