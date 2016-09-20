package Composite;

/**
 * Created by Jedd Shneier
 */
public interface NetworkTree {
    String getUUID();
    String getName();
    String getInformation();
    void setName(String name);
    void setUUID(String id);
    void setInformation(String info);
    void add(NetworkTree child);
    int getChildrenSize();
    NetworkTree getChild(int i);
    String toJSON();

}
