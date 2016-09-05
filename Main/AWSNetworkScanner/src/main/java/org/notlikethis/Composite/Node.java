package org.notlikethis.Composite;

import java.util.LinkedList;

/**
 * Created by Jedd Shneier.
 */
public class Node implements NetworkTree {
    private String information;
    private String uuid;
    private LinkedList<NetworkTree> children = null;
    private String name;

    public String getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public void setName(String name) {
        this.name= name;
    }

    public void setUUID(String id) {
        uuid=id;
    }

    public void setInformation(String info) {
        information=info;

    }

    public void add(NetworkTree child) {
        if(children==null)
        {
            children = new LinkedList<NetworkTree>();
        }
        children.add(child);


    }

    public int getChildrenSize() {
        if(children==null)
            return 0;
        return children.size();
    }

    public NetworkTree getChild(int i) {
        return children.get(i);
    }

    public String toJSON() {
        return traverse(this);
    }

    private String traverse(NetworkTree node) {
        String jsonNetwork="{";
        jsonNetwork+="UUID : "+node.getUUID();
        jsonNetwork+=",Information : "+node.getInformation();
        jsonNetwork+=",Children:[";
        for (int i = 0; i < node.getChildrenSize(); i++)
        {

            jsonNetwork+=traverse(node.getChild(i));

        }
        jsonNetwork+=']';
        jsonNetwork+='}';
        return jsonNetwork;

    }
}
