package Buffer;


import Composite.NetworkTree;
import Composite.Node;
import SecurityGroups.SecurityRuleSet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Jedd Shneier.
 */
public class SharedBuffer implements SmartBufferInterface{
    private BlockingQueue<NetworkTree> frontBuffer;
    private int state=0;
    private NetworkTree currentTree;
    private LinkedList<String> nodeList = new LinkedList<String>();
    private LinkedList<String> instanceList = new LinkedList<String>();
    private HashMap<String,String> informationHashMap = new HashMap<String,String>();
    private HashMap<String,NetworkTree> uuidHashMap = new HashMap<String, NetworkTree>();
    private HashMap<String, LinkedList<SecurityRuleSet>> securityGroupHashMap = new HashMap<String, LinkedList<SecurityRuleSet>>();
    Integer threadNotifier;
    private boolean finished=false;

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
            if(node.getLevel()==5) {
                instanceList.add(node.getUUID());
            }
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

    public void addToSecurityGroups(String id, SecurityRuleSet securityRuleSet) {
        if(securityGroupHashMap.containsKey(id)==false)
        {
            LinkedList<SecurityRuleSet> newSet = new LinkedList<SecurityRuleSet>();
            newSet.add(securityRuleSet);
            securityGroupHashMap.put(id,newSet);
        }
        else
        {
            LinkedList<SecurityRuleSet> newSet = securityGroupHashMap.get(id);
            newSet.add(securityRuleSet);
            securityGroupHashMap.put(id,newSet);
        }

    }


    private void consructJSON(NetworkTree node)
    {
        String jsonNode="{";
        jsonNode+='"' + "Name" + '"' +":"+ '"' + node.getName() + '"';
        jsonNode+='\n';
        jsonNode+=","+'"' + "UUID" + '"'+ ":"+ '"' + node.getUUID() + '"';
        jsonNode+='\n';
        jsonNode+=","+'"' + "Level" + '"'+ ":"+ '"' + node.getLevel() + '"';
        jsonNode+='\n';
        jsonNode+=","+'"' + "Relationships" + '"'+ ":[";
        jsonNode+=node.getRelationships()+"]}";
        nodeList.add(jsonNode);
        informationHashMap.put(node.getUUID(),"{"+'"' + "Information" + '"'+ ":"+ '"' + node.getInformation() + '"'+"}");
    }

    public String getJSONList() {
        constructTree();

        String output="{"+'"' + "NodesArray"+'"' +":[";
        if(nodeList.isEmpty())
        {
            if(finished)
             return "null";
            else
            {
                createConnections();
                finished=true;
            }
        }
        int counter =0;
        int max=1;

        while(counter<max&&!nodeList.isEmpty())
        {
            counter++;
            output+=nodeList.removeFirst()+",";
        }
        output=output.substring(0,output.length()-1);
        output+="]";

        output+="}";

        return output;
    }

    private void createConnections() {

        for(int i = 0;i<instanceList.size();i++)
        {
            Node node = new Node();
            node.setName(instanceList.get(i));
            node.setUUID(instanceList.get(i)+"/2");
            node.setLevel(5);

            for(int j = 0;j<instanceList.size();j++)
            {
                if(checkConnection(securityGroupHashMap.get(instanceList.get(i)),securityGroupHashMap.get(instanceList.get(j))))
                {

                    node.addRelationship(instanceList.get(j)+"/2");
                }
            }
            node.setInformation(informationHashMap.get(instanceList.get(i)));
            consructJSON(node);
        }

    }

    private boolean checkConnection(LinkedList<SecurityRuleSet> securityRuleSets, LinkedList<SecurityRuleSet> securityRuleSets2) {
        if(securityRuleSets==null || securityRuleSets2==null)
        {
            System.out.println("No security Group");
            return false;
        }
        for(int i =0;i<securityRuleSets.size();i++)
        {
            for(int j =0;j<securityRuleSets2.size();j++)
            {
                if (securityRuleSets.get(i).canSendTo(securityRuleSets2.get(j)))
                {
                    if (securityRuleSets2.get(j).canRecieveFrom(securityRuleSets.get(i)))
                    {
                        return  true;
                    }
                }
            }
        }
        return false;
    }

    public String getInformation(String uuid) {
        //JSONObject json = new JSONObject(informationHashMap.get(uuid));
        //String xml = XML.toString(json);
        return informationHashMap.get(uuid);
    }

    public String getConnections() {
        return null;
    }

    public void removeRoot() {
        frontBuffer.remove();
    }

    public void stopThreads() {
        state=2;

    }

    public void pauseThreads() {
        state=1;

    }

    public void resumeThreads() {
        state =0;
        synchronized (threadNotifier)
        {
            threadNotifier.notifyAll();
        }

    }

    public int getState() {
        return state;
    }


    public Integer getThreadNotifier() {
        return threadNotifier;
    }
}
