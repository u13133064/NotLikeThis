package RouteTableGroups;

import java.util.LinkedList;

/**
 * Created by Jedd Shneier
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
