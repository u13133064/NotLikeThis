package Buffer;


import Composite.NetworkTree;
import RouteTableGroups.RouteTableSet;
import SecurityGroups.SecurityRuleSet;

/**
 * Created by Jedd Shneier.
 */
public interface SmartBufferInterface {
    void constructTree();
    void addToBuffer(NetworkTree tree);
    void addToSecurityGroups(String id,SecurityRuleSet securityRuleSet);
    String getJSONList();
    String getInformation(String uuid);
    String getConnections(String uuid,String otherId);
    void removeRoot();
    void stopThreads();
    void pauseThreads();
    void resumeThreads();
    int getState();
    Integer getThreadNotifier();

    void connect();

    void disconnect();

    void addToRouteTables(String id,RouteTableSet routeTableSet);
}
