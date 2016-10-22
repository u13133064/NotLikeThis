package Buffer;


import Composite.NetworkTree;
import Composite.Node;
import InformationDecorator.HTMLEncoder;
import RouteTableGroups.RouteTableSet;
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
    private String status="Scanning";
    private NetworkTree currentTree;
    private LinkedList<String> nodeList = new LinkedList<String>();
    private LinkedList<String> instanceList = new LinkedList<String>();
    private LinkedList<String> vpcList = new LinkedList<String>();
    private LinkedList<String> subnetList = new LinkedList<String>();
    private LinkedList<String> regionList = new LinkedList<String>();
    private HashMap<String,String> informationHashMap = new HashMap<String,String>();
    private HashMap<String,NetworkTree> uuidHashMap = new HashMap<String, NetworkTree>();
    private HashMap<String, LinkedList<RouteTableSet>> routeTableHashMap = new HashMap<String, LinkedList<RouteTableSet>>();
    private HashMap<String, LinkedList<SecurityRuleSet>> securityGroupHashMap = new HashMap<String, LinkedList<SecurityRuleSet>>();
    private LinkedList<Integer>connections= new LinkedList<Integer>();
    Integer threadNotifier;
    private boolean finished=false;
    private String parentIdentifier;
    private String parentLevel;
    public SharedBuffer()
    {
        frontBuffer=new ArrayBlockingQueue<NetworkTree>(100000);
        currentTree = new Node();
        LinkedList<String> information=new LinkedList<String>();
        information.add("Welcome to Nimbus:The AWS network visualizer");
        currentTree.setInformation(new HTMLEncoder().informationToHtml(information));
        currentTree.setName("Root");
        currentTree.setUUID("Root");
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
            switch(node.getLevel()) {
                case 2:
                    regionList.add(node.getUUID());
                    break;
                case 3:
                    vpcList.add(node.getUUID());
                    break;
                case 4:
                    subnetList.add(node.getUUID());
                    break;
                case 5:
                    instanceList.add(node.getUUID());
                    break;


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
        informationHashMap.put(node.getUUID(),node.getInformation());
    }

    public String getJSONList() {
        constructTree();

        String output="{"+'"' + "NodesArray"+'"' +":[";
        if(nodeList.isEmpty())
        {

            if(finished && connections.size()==0)
            {
                status="Complete";
                return "null";
            }

            else if(!finished && connections.size()==0)
            {
                status="Scanning";
                Node communicationNode = new Node();
                communicationNode.setName("LOADING_SECURITY_GROUPS");
                communicationNode.setUUID("LOADING_SECURITY_GROUPS");
                consructJSON(communicationNode);
                createConnections();
                finished=true;
            }
            else
            {
                status="Waiting";
                return "waiting";
            }
        }
        status="Scanning";
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
            node.setUUID(instanceList.get(i));
            node.setLevel(5);

            for(int j = 0;j<instanceList.size();j++) {
                if (!instanceList.get(i).equals(instanceList.get(j))) {


                    if (checkConnection(securityGroupHashMap.get(instanceList.get(i)), securityGroupHashMap.get(instanceList.get(j)))) {

                        node.addRelationship(instanceList.get(j));
                    }
                }
            }
            node.setInformation(informationHashMap.get(instanceList.get(i)));
            consructJSON(node);
        }
        for(int i = 0;i<vpcList.size();i++)
        {
            Node node = new Node();
            node.setName(vpcList.get(i));
            node.setUUID(vpcList.get(i));
            node.setLevel(3);
            LinkedList<RouteTableSet>routeTableSets= routeTableHashMap.get(vpcList.get(i));
            if(routeTableSets!=null)
            {
                for (int j = 0; j <routeTableSets.size();j++)
                {
                    LinkedList<String>routeTableAssociations=routeTableSets.get(j).getAssociations();
                    for(int k=0;k<routeTableAssociations.size();k++)
                    {
                        if(routeTableAssociations.get(k)!=null)
                        {
                            Node subnetNode=new Node();
                            subnetNode.setUUID(routeTableAssociations.get(k));
                            subnetNode.setName(routeTableAssociations.get(k));
                            subnetNode.setLevel(4);
                            subnetNode.setInformation(informationHashMap.get(routeTableAssociations.get(k)));
                            consructJSON(subnetNode);
                            node.addRelationship(routeTableAssociations.get(k));
                        }

                    }
                }


            }
            node.setInformation(informationHashMap.get(vpcList.get(i)));
            consructJSON(node);
        }

    }

    private boolean checkConnection(LinkedList<SecurityRuleSet> securityRuleSets, LinkedList<SecurityRuleSet> securityRuleSets2) {
        if(securityRuleSets==null || securityRuleSets2==null)
        {
            System.out.println("No security Group");
            return false;
        }
        boolean bflag= false;
        for(int i =0;i<securityRuleSets.size();i++)
        {
            for(int j =0;j<securityRuleSets2.size();j++)
            {
                if (securityRuleSets.get(i).canSendTo(securityRuleSets2.get(j)))
                {
                    if (securityRuleSets2.get(j).canRecieveFrom(securityRuleSets.get(i)))
                    {
                        securityRuleSets.get(i).addConnection(securityRuleSets2.get(j));
                        bflag= true;
                    }
                }
            }
        }
        return bflag;
    }

    public String getInformation(String uuid) {
        return informationHashMap.get(uuid);
    }

    public String getConnections(String uuid,String otherID)
    {
        String output="";
        if(securityGroupHashMap.containsKey(uuid))
        {
            LinkedList<SecurityRuleSet> securityRuleSets = securityGroupHashMap.get(uuid);
            LinkedList<SecurityRuleSet> otherSetRules= securityGroupHashMap.get(otherID);
            for(int i=0;i<securityRuleSets.size();i++)
            {
                for(int j=0;j<otherSetRules.size();j++)
                {
                    output+= securityRuleSets.get(i).getConnectionInformation(otherSetRules.get(j).getId())+"\n";

                }

            }
        }
        return new HTMLEncoder().securityGroupToHtml(output);
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

    public void setParentIdentifier(String identifier) {
        parentIdentifier=identifier;
    }

    public void setParentLevel(String level) {
        parentLevel=level;
    }

    public String getParentIdentifier() {
        return parentIdentifier;
    }

    public String getParentLevel() {
        return parentLevel;
    }


    public Integer getThreadNotifier() {
        return threadNotifier;
    }

    public String getStatus() {


        return   "<span style='background-color:#09689E;size:20px'class='label label-default'>Status: "+status
                +" Regions Scanned: "+regionList.size()+
                " Vpcs Scanned: "+vpcList.size()
                +" Subnets Scanned: "+subnetList.size()
                +" Instances Scanned: "+instanceList.size()+"</span>";
    }

    public void connect() {
        connections.add(0);

    }

    public void disconnect() {
        connections.remove();

    }

    public void addToRouteTables(String id,RouteTableSet routeTableSet) {
        if(routeTableHashMap.containsKey(id)==false)
        {
            LinkedList<RouteTableSet> newSet = new LinkedList<RouteTableSet>();
            newSet.add(routeTableSet);
            routeTableHashMap.put(id,newSet);
        }
        else
        {
            LinkedList<RouteTableSet> newSet = routeTableHashMap.get(id);
            newSet.add(routeTableSet);
            routeTableHashMap.put(id,newSet);
        }


    }
}
