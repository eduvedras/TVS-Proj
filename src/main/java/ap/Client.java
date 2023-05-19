package ap;

import java.util.ArrayList;
import java.util.List;

/*public enum ClientLevel { 
    NORMAL, 
    GOLD, 
    PLATINUM 
}*/

public class Client {
    private String name;
    private String level;
    private List<Terminal> terminals;

    public Client(String name, String level) {
        this.name = name;
        this.level = level;
        this.terminals = new ArrayList<>();
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    void addTerminal(Terminal t) {
        terminals.add(t);
    }

    List<Terminal> getTerminals() {
        return terminals;
    }
}
