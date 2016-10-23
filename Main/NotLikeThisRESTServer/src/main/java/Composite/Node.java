package Composite;

import java.util.LinkedList;

/**
 * <h1>Node</h1>
 * Concrete implementation of network Interface
 * Composite design pattern
 * Used as communication object between threads and buffer
 * @author  Jedd Shneier
 * @version 1.0
 * @since   2016-10-16
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

    private String getList(LinkedList<String> list)
    {
        if(list==null)
        {
            return "";
        }
        else
        {
            String output="";
            for(int i = 0;i<list.size();i++)
            {
                output+="{"+'"' + "UUID" + '"'+ ":"+'"' +list.get(i)+ '"'+"},";
            }
            output=output.substring(0,output.length()-1);
            return output;
        }

    }



    public String getRelationships() {
        return  getList(relationships);
    }


}
