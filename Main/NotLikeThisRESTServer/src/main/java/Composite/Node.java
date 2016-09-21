package Composite;

import java.util.LinkedList;

/**
 * Created by Jedd Shneier.
 */
public class Node implements NetworkTree {
    private String information;
    private String uuid;
    private int level;
    private LinkedList<NetworkTree> children = null;
    private LinkedList<String> relationships=null;
    private String name;
    private boolean checked = false;

    public String getUUID() {
        return uuid;
    }

    public void setLevel(int level) {
        this.level=level;
    }

    public int getLevel() {
        return level;
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

    public void setChecked() {
        this.checked=true;
    }

    public boolean isChecked() {
        return checked;
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

    public void addRelationship(String UUID) {
        if(relationships==null)
        {
            relationships= new LinkedList<String>();
        }
        relationships.add(UUID);
    }

    public String getRelationships() {
        if(relationships==null)
        {
            return "";
        }
        else
        {
            String output="";
            for(int i = 0;i<relationships.size();i++)
            {
                output+=relationships.get(i)+",";
            }
            output=output.substring(0,output.length()-1);
            return output;
        }
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
        return "{"+'"' + "NodesArray"+'"' +": ["+traverse(this)+"]}";
    }

    private String traverse(NetworkTree node) {
        String jsonNetwork="{";
        jsonNetwork+='"' + "Name" + '"' +":"+ '"' + node.getName() + '"';
        jsonNetwork+='\n';
        jsonNetwork+=","+'"' + "UUID" + '"'+ ":"+ '"' + node.getUUID() + '"';
        jsonNetwork+='\n';
        jsonNetwork+=","+'"' + "Information" + '"'+ ":"+ '"' + node.getInformation() + '"';
        jsonNetwork+='\n';
        jsonNetwork+=","+'"' + "Children" + '"'+ ":[";
        jsonNetwork+='\n';
        for (int i = 0; i < node.getChildrenSize(); i++)
        {
            jsonNetwork+='\n';
            jsonNetwork+=traverse(node.getChild(i))+",";


        }
        if (jsonNetwork.endsWith(","))
        {
            jsonNetwork=jsonNetwork.substring(0,jsonNetwork.length()-1);
        }
        jsonNetwork+=']';
        jsonNetwork+='}';
        jsonNetwork+='\n';

        return jsonNetwork;

    }
}
