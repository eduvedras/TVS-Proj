package ap;

public class Terminal {
    private String id;
    private boolean isOn;
    private boolean isSilent;
    private boolean isInCall;

    public Terminal(String id) {
        this.id = id;
        this.isOn = false;
        this.isSilent = false;
        this.isInCall = false;
    }

    public void turnOff() {
        isOn = false;
        isSilent = false;
        isInCall = false;
    }

    public void turnOn() {
        isOn = true;
    }

    public void setSilence() {
        if (isOn) {
            isSilent = true;
        }
    }

    public boolean sendSMS(String msg, Terminal to) {
        if (!isOn || !to.isOn || msg.length() == 0 || msg.length() > 160) {
            throw new IllegalArgumentException("Invalid message");
        }

        to.receiveSMS(msg, this);
        return true;
    }

    void receiveSMS(String msg, Terminal from) {
        if (isOn) {
            System.out.println("Received SMS from " + from.getId() + ": " + msg);
        }
    }

    public void makeCall(Terminal t) {
        if (isOn && !isInCall && t.isOn) {
            System.out.println("Calling " + t.getId());
            isInCall = true;
            t.acceptCall(this);
        }
    }

    void acceptCall(Terminal t) {
        if (isOn && !isInCall && t.isOn) {
            System.out.println("Call accepted from " + t.getId());
            isInCall = true;
        }
    }

    public void endCall() {
        if (isOn && isInCall) {
            System.out.println("Call ended");
            isInCall = false;
        }
    }

    public String getId() {
        return id;
    }
}