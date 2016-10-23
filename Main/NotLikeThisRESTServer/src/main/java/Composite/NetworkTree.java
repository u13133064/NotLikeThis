package Composite;

/**
 * <h1>NetworkTree</h1>
 * Interface for Node objects
 * Composite design pattern
 * Used as communication object between threads and buffer
 * @author  Jedd Shneier
 * @version 1.0
 * @since   2016-10-16
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

}
