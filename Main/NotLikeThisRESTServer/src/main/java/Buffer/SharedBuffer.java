package Buffer;


import Composite.NetworkTree;
import Composite.Node;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Jedd Shneier.
 */
public class SharedBuffer implements SmartBufferInterface{
    private BlockingQueue<NetworkTree> frontBuffer;
    private NetworkTree currentTree;
    private LinkedList<String> nodeList = new LinkedList<String>();
    private LinkedList<String> informationQueue = new LinkedList<String>();
    private HashMap<String,NetworkTree> uuidHashMap = new HashMap<String, NetworkTree>();
    public SharedBuffer()
    {
        frontBuffer=new ArrayBlockingQueue<NetworkTree>(10000);
        currentTree = new Node();
        currentTree.setInformation("AWS");
        currentTree.setName("Root");
        currentTree.setUUID("RootAWS");
        currentTree.setLevel(1);
        frontBuffer.add(currentTree);

    }




    public void constructTree() {
        System.out.println(frontBuffer.toString());
        while(!frontBuffer.isEmpty())
        {
            NetworkTree node = frontBuffer.remove();
            if(!encountered(node))
              consructJSON(node);
        }


    }

    private boolean encountered(NetworkTree node) {
        if( uuidHashMap.containsKey(node.getUUID()))
            return true;
        else
        {
            uuidHashMap.put(node.getUUID(),node);
            return false;
        }
    }

    public void addToBuffer(NetworkTree tree) {
        System.out.println(frontBuffer.size());
        try {
            frontBuffer.put(tree);

        } catch (InterruptedException e) {
            System.out.println("Buffer is full");
            e.printStackTrace();
        }
    }



    private void consructJSON(NetworkTree node)
    {
        System.out.println(node.getName());
        String jsonNode="{";
        jsonNode+='"' + "Name" + '"' +":"+ '"' + node.getName() + '"';
        jsonNode+='\n';
        jsonNode+=","+'"' + "UUID" + '"'+ ":"+ '"' + node.getUUID() + '"';
        jsonNode+='\n';
        jsonNode+=","+'"' + "Level" + '"'+ ":"+ '"' + node.getLevel() + '"';
        jsonNode+='\n';
        jsonNode+='\n';
        jsonNode+=","+'"' + "Relationships" + '"'+ ":[";
        jsonNode+='\n';
        jsonNode+=node.getRelationships()+"]}";
        nodeList.add(jsonNode);
        informationQueue.add("{"+'"' + "Information" + '"'+ ":"+ '"' + node.getInformation() + '"'+"}");
    }

    public String getJSONList() {
        constructTree();
        String informationList='"' + "Information"+'"' +":[";
        String output="{"+'"' + "NodesArray"+'"' +":[";
        if(nodeList.isEmpty())
        {
            return null;
        }
        int counter =0;
        while(counter<3&&!nodeList.isEmpty())
        {
            counter++;
            output+=nodeList.removeFirst()+",";
            informationList+=informationQueue.removeFirst()+',';
        }
        output=output.substring(0,output.length()-1);
        informationList=informationList.substring(0,informationList.length()-1);
        informationList+="]";
        output+="],";
        informationList='"' + "Information"+'"' +":[]";
        output+=informationList;
        output+="}";

        return output;
    }
}
