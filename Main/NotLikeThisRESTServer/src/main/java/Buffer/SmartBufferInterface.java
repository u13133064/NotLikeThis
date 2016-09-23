package Buffer;


import Composite.NetworkTree;

/**
 * Created by Jedd Shneier.
 */
public interface SmartBufferInterface {
    void constructTree();
    void addToBuffer(NetworkTree tree);
    String getJSONList();

}
