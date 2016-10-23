package Buffer;


import Composite.NetworkTree;
import RouteTableGroups.RouteTableSet;
import SecurityGroups.SecurityRuleSet;

/**
 * <h1>SmartBufferInterface</h1>
 * Interface for buffers
 * Handle thread coordination
 * Cosntruct trees
 * Hold information
 * Send information to Visualizer
 * @author  Jedd Shneier
 * @version 1.0
 * @since   2016-10-16
 */
public interface SmartBufferInterface {

    /**
     * Constructs tree from what Nodes are currently in buffer
     * 
     *
     */
    void constructTree();
    /**
     * Allows threads to add Nodes to buffer.
     *
     */
    void addToBuffer(NetworkTree tree);
    /**
     * Allows threads to add SecurityRuleSet to buffer.

     *  
     *
     */
    void addToSecurityGroups(String id,SecurityRuleSet securityRuleSet);
    /**
     *  Gets latest tree nodes for visualization
     *  @return String
     *
     */
    String getJSONList();
    /**
     *  Gets the information for a node from informationHashmap

     *  @return String
     *
     */
    String getInformation(String uuid);
    /**
     *  Gets the security group information for a node from securityHashmap

     *  @return String
     *
     */
    String getConnections(String uuid,String otherId);

    /**
     *  Removes root node from buffer
     *  
     *
     */
    void removeRoot();
    /**
     *  Sets state to stop to notify threads
     *  
     *
     */
    void stopThreads();
    /**
     *  Sets state to pause to notify threads
     *  
     *
     */
    void pauseThreads();
    /**
     *  Sets state to resume to notify threads
     *  
     *
     */
    void resumeThreads();
    /**
     *  Gets state of buffer
     *  @return int
     *
     */
    int getState();
    /**
     *  Sets the parent uuid for scan Up

     *  
     *
     */
    void setParentIdentifier(String identifier);
    /**
     *  Sets the parent level for scan Up

     *  
     *
     */
    void setParentLevel(String level);
    /**
     *  Gets Parent uuid for scan up
     *
     *  @return String
     *
     */
    String getParentIdentifier();
    /**
     *  Gets Parent level for scan up
     *
     *  @return String
     *
     */
    String getParentLevel();
    /**
     *  Gets thread notification of state
     *
     *  @return Integer
     *
     */
    Integer getThreadNotifier();
    /**
     *  gets status of buffer
     *
     *  @return String
     *
     */
    String getStatus();
    /**
     *  Allows thread to connect to buffer and notify it as a producer
     *
     *  
     *
     */
    void connect();
    /**
     *  Allows thread to disconnect to buffer and notify it that thread is finished
     *
     *  
     *
     */
    void disconnect();
    /**
     * Allows threads to add RoutetableSets to buffer.

     *  
     *
     */
    void addToRouteTables(String id,RouteTableSet routeTableSet);
}
