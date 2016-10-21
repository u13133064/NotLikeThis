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
    void setParentIdentifier(String identifier);
    void setParentLevel(String level);
    String getParentIdentifier();
    String getParentLevel();
    Integer getThreadNotifier();
    String getStatus();
    void connect();

    void disconnect();

    void addToRouteTables(String id,RouteTableSet routeTableSet);
}
