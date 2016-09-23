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
    String getRelationships();
    boolean equals(String uuid);

}
