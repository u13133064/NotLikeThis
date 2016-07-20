package Visualizer;

import java.util.ArrayList;
import java.util.UUID;

public class Visualizer 
{
	public ArrayList<Node> nodes = new ArrayList<Node>();
	
	public ArrayList<Relationship> relatioships = new ArrayList<Relationship>();
	
	public Node awsNode;
	
	public Visualizer()
	{
		awsNode = new Node("AWS", UUID.randomUUID());
		nodes.add(awsNode);
	}
	
	public void addNetwork(Messenger.Network network)
	{
		nodes.add(new Node(network.getUsername(), network.getNetworkID()));
		relatioships.add(new Relationship(awsNode.getID(), network.getNetworkID()));
		
		if(network.getAWSRegionList() != null)
		{
			for(int i = 0; i < network.getAWSRegionList().size(); i++)
			{
				relatioships.add(new Relationship(network.getNetworkID(), network.getAWSRegionList().get(i).getAWSRegionID()));
				addRegions(network.getAWSRegionList().get(i));
			}
		}
	}
	
	public void addRegions(Messenger.AWSRegion region)
	{
		nodes.add(new Node(region.getAWSRegionName(), region.getAWSRegionID()));
		
		if(region.getVpcList() != null)
		{
			for(int i = 0; i < region.getVpcList().size(); i++)
			{
				relatioships.add(new Relationship(region.getAWSRegionID(), region.getVpcList().get(i).getVPCID()));
				addVPCs(region.getVpcList().get(i));
			}
		}
	}
	
	public void addVPCs(Messenger.VPC vpc)
	{
		nodes.add(new Node(vpc.getVPCName(), vpc.getVPCID()));
		
		if(vpc.getAVZoneList() != null)
		{
			for(int i = 0; i < vpc.getAVZoneList().size(); i++)
			{
				relatioships.add(new Relationship(vpc.getVPCID(), vpc.getAVZoneList().get(i).getAVZoneID()));
				addAVZones(vpc.getAVZoneList().get(i));
			}
		}
	}
	
	public void addAVZones(Messenger.AVZone zone)
	{
		nodes.add(new Node(zone.getAVZoneName(), zone.getAVZoneID()));
		
		if(zone.getSubNetworkList() != null)
		{
			for(int i = 0; i < zone.getSubNetworkList().size(); i++)
			{
				relatioships.add(new Relationship(zone.getAVZoneID(), zone.getSubNetworkList().get(i).getSubNetworkID()));
				addSubNetworks(zone.getSubNetworkList().get(i));
			}
		}
	}
	
	public void addSubNetworks(Messenger.SubNetwork subnet)
	{
		nodes.add(new Node(subnet.getSubNetworkName(), subnet.getSubNetworkID()));
		
		if(subnet.getSubNetworkList() != null)
		{
			for(int i = 0; i < subnet.getSubNetworkList().size(); i++)
			{
				relatioships.add(new Relationship(subnet.getSubNetworkID(), subnet.getSubNetworkList().get(i).getSubNetworkID()));
				addSubNetworksAndInstances(subnet.getSubNetworkList().get(i));
			}
		}
		
		if(subnet.getAWSInstanceList() != null)
		{
			for(int i = 0; i < subnet.getAWSInstanceList().size(); i++)
			{
				relatioships.add(new Relationship(subnet.getSubNetworkID(), subnet.getAWSInstanceList().get(i).getAWSInstanceID()));
				nodes.add(new Node(subnet.getAWSInstanceList().get(i).getAWSInstanceName(), subnet.getAWSInstanceList().get(i).getAWSInstanceID()));
			}
		}
	}
	
	public void addSubNetworksAndInstances(Messenger.SubNetwork subnet)
	{
		nodes.add(new Node(subnet.getSubNetworkName(), subnet.getSubNetworkID()));
		
		if(subnet.getSubNetworkList() != null)
		{
			for(int i = 0; i < subnet.getSubNetworkList().size(); i++)
			{
				relatioships.add(new Relationship(subnet.getSubNetworkID(), subnet.getSubNetworkList().get(i).getSubNetworkID()));
				addSubNetworksAndInstances(subnet.getSubNetworkList().get(i));
			}
		}
		
		if(subnet.getAWSInstanceList() != null)
		{
			for(int i = 0; i < subnet.getAWSInstanceList().size(); i++)
			{
				relatioships.add(new Relationship(subnet.getSubNetworkID(), subnet.getAWSInstanceList().get(i).getAWSInstanceID()));
				nodes.add(new Node(subnet.getAWSInstanceList().get(i).getAWSInstanceName(), subnet.getAWSInstanceList().get(i).getAWSInstanceID()));
			}
		}
	}
	
	public void printNodes()
	{
		System.out.println("This is the list of nodes:");
		
		for(int i = 0; i < nodes.size(); i++)
		{
			System.out.println(nodes.get(i).getName() + "	" + nodes.get(i).getID());
		}
	}
	
	public void printRelationships()
	{
		System.out.println("This is the list of relationships:");
		
		for(int i = 0; i < relatioships.size(); i++)
		{
			System.out.println(relatioships.get(i).getFrom() + "	" + relatioships.get(i).getTo());
		}
	}

	public ArrayList<Node> getNodes()
	{
		return nodes;
	}
	
	public ArrayList<Relationship> getRelationships()
	{
		return relatioships;
	}
}
