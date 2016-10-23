package RouteTableGroups;

import java.util.LinkedList;

/**
 * <h1>RouteTableSet</h1>
 * Holds information on  route table rules for Vpcs
 * @author  Jedd Shneier
 * @version 1.0
 * @since   2016-10-16
 */
public class RouteTableSet {
    private LinkedList<String> associations=new LinkedList<String>();
    private LinkedList<String> routes=new LinkedList<String>();
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<String> getRoutes() {
        return routes;
    }

    public LinkedList<String> getAssociations() {
        return associations;
    }

    public void setAssociations(LinkedList<String> associations) {
        this.associations = associations;
    }

    public void setRoutes(LinkedList<String> routes) {
        this.routes = routes;
    }

    public void setID(String ID) {
        this.id = ID;
    }
}
