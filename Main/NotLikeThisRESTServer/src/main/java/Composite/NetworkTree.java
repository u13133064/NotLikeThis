package Composite;

/**
 * Created by Jedd Shneier
 */
public interface NetworkTree {
    String getUUID();
    void setLevel(int level);
    int getLevel();
    String getName();
    String getInformation();
    void setName(String name);
    void setUUID(String id);
    void setInformation(String info);
    void addRelationship(String UUID);
    void addSecurityGroup(String UUID);
    String getSecurityGroups();
    void addNetworkInterface(String UUID);
    String getNetworkInterfaces();
    String getRelationships();

}
