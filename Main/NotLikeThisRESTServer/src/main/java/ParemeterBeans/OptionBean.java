package ParemeterBeans;

/**
 * Created by User1 on 2016/10/01.
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
