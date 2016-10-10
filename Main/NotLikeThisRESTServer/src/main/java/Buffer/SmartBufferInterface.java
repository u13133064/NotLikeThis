package Buffer;


import Composite.NetworkTree;

/**
 * Created by Jedd Shneier.
 */
public interface SmartBufferInterface {
    void constructTree();
    void addToBuffer(NetworkTree tree);
    String getJSONList();
    String getInformation(String uuid);
    void removeRoot();
    void stopThreads();
    void pauseThreads();
    void resumeThreads();
    int getState();
    Integer getThreadNotifier();
}
