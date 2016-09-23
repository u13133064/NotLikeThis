package Composite;

import java.util.LinkedList;

/**
 * Created by Jedd Shneier.
 */
public class Node implements NetworkTree {
    private String information;
    private String uuid;
    private int level;
    private LinkedList<String> relationships=null;
    private String name;

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


    public void setUUID(String id) {
        uuid=id;
    }

    public void setInformation(String info) {
        information=info;

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
                output+="{"+'"' + "UUID" + '"'+ ":"+'"' +relationships.get(i)+ '"'+"},";
            }
            output=output.substring(0,output.length()-1);
            return output;
        }
    }

    public boolean equals(String uuid) {
        return this.uuid.equals(uuid);
    }


}
