package org.notlikethis.Composite;

/**
 * Created by Jedd Shneier
 */
public interface NetworkTree {
    String getUUID();

    String getInformation();
    void setUUID(String id);
    void setInformation(String info);
    void add(NetworkTree child);
    int getChildrenSize();
    NetworkTree getChild(int i);
    String toJSON();

}
