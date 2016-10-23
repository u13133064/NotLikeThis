package ParemeterBeans;
/**
 * <h1>OptionBean</h1>
 * A parameter bean used for sending information to server from interface. Messenger.
 * QueryParam holds the fields in the message
 * scanChoice is  type of requested service
 * level is level of uuid if specified
 * identifier is uud of node if specified
 * @author  Jedd Shneier
 * @version 1.0
 * @since   2016-10-16
 */
public class OptionBean {
    public int getScannChoice() {
        return scannChoice;
    }

    public void setScannChoice(int scannChoice) {
        this.scannChoice = scannChoice;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    private int scannChoice;
    private String level ="";
    private String identifier="";

}
