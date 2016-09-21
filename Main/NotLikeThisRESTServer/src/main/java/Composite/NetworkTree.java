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
    void setChecked();
    boolean isChecked();
    void setUUID(String id);
    void setInformation(String info);
    void add(NetworkTree child);
    void addRelationship(String UUID);
    String getRelationships();
    int getChildrenSize();

    NetworkTree getChild(int i);
    String toJSON();

}
