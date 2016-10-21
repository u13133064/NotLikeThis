package Scanner;

import Buffer.SharedBuffer;
import Composite.NetworkTree;
import Composite.Node;
import InformationDecorator.HTMLEncoder;
import RouteTableGroups.RouteTableSet;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jedd Shneier.
 */
public class VpcScannerThread implements ThreadedScannerInterface{
    private String regionName;
    private  AmazonEC2 ec2;
    private SharedBuffer buffer;
    private String  uuid="";
    VpcScannerThread(String regionName, AmazonEC2 ec2,SharedBuffer buffer)
    {
        this.regionName=regionName;
        this.ec2=ec2;
        this.buffer=buffer;

    }

    public VpcScannerThread(String regionName, String identifier, AmazonEC2 threadEc2, SharedBuffer buffer) {

        this.regionName=regionName;
        this.ec2= threadEc2;
        this.buffer=buffer;
        this.uuid=identifier;
    }

    public void scanContext()
    {

        DescribeVpcsResult describeVpcsResult = ec2.describeVpcs();
        List<Vpc> vpcs = describeVpcsResult.getVpcs();
        LinkedList<Vpc> vpclist= new LinkedList<Vpc>();
        for(int i = 0; i<vpcs.size();i++) {
            System.out.println("Adding Vpc for : "+regionName);
            NetworkTree node= new Node();
            node.setName(vpcs.get(i).getVpcId());
            node.setUUID(vpcs.get(i).getVpcId());
            LinkedList<String> information=new LinkedList<String>();
            information.add("VpcId:"+vpcs.get(i).getVpcId());
            information.add("CidrBlock:"+vpcs.get(i).getCidrBlock());
            information.add("State:"+vpcs.get(i).getState());
            information.add("Default:"+vpcs.get(i).getIsDefault());
            information.add("InstanceTenancy:"+vpcs.get(i).getInstanceTenancy());
            information.add("DhcpOptionsId:"+vpcs.get(i).getDhcpOptionsId());
            node.setInformation(new HTMLEncoder().informationToHtml(information));
            node.setLevel(3);
            node.addRelationship(regionName);
            vpclist.add(vpcs.get(i));
            if(buffer.getState()==1)
            {
                synchronized(buffer.getThreadNotifier())
                {
                    try {
                        buffer.getThreadNotifier().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(buffer.getState()==2)
            {
                return;
            }
            buffer.addToBuffer(node);

        }
        addRoutetableSets(vpclist);

    }
    public void scanOnly()
    {
        DescribeVpcsRequest describeVpcsRequest = new DescribeVpcsRequest().withVpcIds(uuid);
        DescribeVpcsResult describeVpcsResult;
        try
        {
            describeVpcsResult = ec2.describeVpcs(describeVpcsRequest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
        List<Vpc> vpcs = describeVpcsResult.getVpcs();
        if (vpcs.size()>0)
        {
            //launch a subnetScanner
            new Thread(new SubNetworkScannerThread(this.regionName,uuid,"Vpc", this.ec2,buffer)).start();
            //launch a instance scanner
            new Thread(new InstanceScannerThread(this.regionName,uuid,"Vpc",this.ec2,buffer)).start();
        }
        LinkedList<Vpc> vpclist= new LinkedList<Vpc>();
        buffer.setParentLevel("Region");
        buffer.setParentIdentifier(regionName);
        for(int i = 0; i<vpcs.size();i++) {

            System.out.println("Found Vpc at : "+regionName);
            NetworkTree node= new Node();
            node.setName(vpcs.get(i).getVpcId());
            node.setUUID(vpcs.get(i).getVpcId());
            LinkedList<String> information=new LinkedList<String>();
            information.add("VpcId:"+vpcs.get(i).getVpcId());
            information.add("CidrBlock:"+vpcs.get(i).getCidrBlock());
            information.add("State:"+vpcs.get(i).getState());
            information.add("Default:"+vpcs.get(i).getIsDefault());
            information.add("InstanceTenancy:"+vpcs.get(i).getInstanceTenancy());
            information.add("DhcpOptionsId:"+vpcs.get(i).getDhcpOptionsId());
            node.setInformation(new HTMLEncoder().informationToHtml(information));
            node.setLevel(3);
            node.addRelationship(regionName);
            vpclist.add(vpcs.get(i));

            if(buffer.getState()==1)
            {
                synchronized(buffer.getThreadNotifier())
                {
                    try {
                        buffer.getThreadNotifier().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(buffer.getState()==2)
            {
                return;
            }
            buffer.addToBuffer(node);

        }
        addRoutetableSets(vpclist);

    }

    private void addRoutetableSets(LinkedList<Vpc> vpcs) {
        DescribeRouteTablesResult describeRouteTablesResult = ec2.describeRouteTables();

        List<RouteTable> routeTables = describeRouteTablesResult.getRouteTables();
        for (int vpc = 0; vpc < vpcs.size(); vpc++) {
            for (int i = 0; i < routeTables.size(); i++) {

                if (vpcs.get(vpc).getVpcId().equals(routeTables.get(i).getVpcId())) {
                    RouteTableSet routeTableSet = new RouteTableSet();
                    List<RouteTableAssociation> routeTableAssociations = routeTables.get(i).getAssociations();
                    LinkedList<String> associations = new LinkedList<String>();
                    for (int j = 0; j < routeTableAssociations.size(); j++) {
                        associations.add(routeTableAssociations.get(j).getSubnetId());
                    }
                    routeTableSet.setAssociations(associations);
                    List<Route> routes = routeTables.get(i).getRoutes();
                    LinkedList<String> routeList = new LinkedList<String>();
                    for (int j = 0; j < routes.size(); j++) {
                        routeList.add(routes.get(j).getDestinationCidrBlock());
                    }
                    routeTableSet.setRoutes(routeList);
                    routeTableSet.setID(routeTables.get(i).getRouteTableId());
                    buffer.addToRouteTables(vpcs.get(vpc).getVpcId(), routeTableSet);
                }
            }
        }
    }

    public void run() {
        buffer.connect();
        System.out.println("Starting VPC thread for : "+regionName);
        if(uuid.equals("")) {
            scanContext();

        }
        else
        {
            scanOnly();
        }
        buffer.disconnect();
    }
}
