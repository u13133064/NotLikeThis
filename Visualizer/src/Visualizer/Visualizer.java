package Visualizer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.UUID;

public class Visualizer 
{
	public ArrayList<Node> nodes = new ArrayList<Node>();
	
	public ArrayList<Relationship> relatioships = new ArrayList<Relationship>();
	
	public Node awsNode;
	
	public Visualizer()
	{
		awsNode = new Node("AWS", UUID.randomUUID(), 1);
		nodes.add(awsNode);
	}
	
	public void addNetwork(Messenger.Network network)
	{
		nodes.add(new Node(network.getUsername(), network.getNetworkID(), network.getLevel()));
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
		nodes.add(new Node(region.getAWSRegionName(), region.getAWSRegionID(), region.getLevel()));
		
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
		nodes.add(new Node(vpc.getVPCName(), vpc.getVPCID(), vpc.getLevel()));
		
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
		nodes.add(new Node(zone.getAVZoneName(), zone.getAVZoneID(), zone.getLevel()));
		
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
		nodes.add(new Node(subnet.getSubNetworkName(), subnet.getSubNetworkID(), 6));
		
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
			System.out.println(nodes.get(i).getName() + "	" + nodes.get(i).getID() + "		" + nodes.get(i).getLevel());
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

	 public static String createNodesHTML(String nodeID, ArrayList<Node> nodes)
	 {
		 String out = "  var " + nodeID + "= new vis.DataSet([\n";
	        
	     for(int i = 0; i < nodes.size(); i++)
	     {
	    	 //	if(nodes.get(i).getLevel() != -1)
	    	 //		out = out + "    {id: '" + nodes.get(i).getID() + "' , label: '" + nodes.get(i).getName() + "', shape: 'circle', level: " + nodes.get(i).getLevel() +"  },\n";
	    	 //	else
	        	out = out + "    {id: '" + nodes.get(i).getID() + "' , label: '" + nodes.get(i).getName() + "', shape: 'circle'},\n";
	     }
	        
	     out = out +"  ]);";
	        
	     return out;
	  }
	 
	 public static String createRelationshipsHTML(ArrayList<Relationship> relatioships)
	 {	   
		 String out = "  var edges = new vis.DataSet([\n";
	        
		 for(int i = 0; i < relatioships.size(); i++)
		 {
			 	out = out + "    {from: '" + relatioships.get(i).getFrom() + "', to: '" + relatioships.get(i).getTo() + "'},\n";
		 }
	        
		 out = out +"  ]);";
	        
		 return out;
	}

	 public String generateHTML()
	 {
		    	String out =
				    "<!doctype html>\n" +
				"<html>\n" +
				"<head>\n" +
				"  <title>Network | Basic usage</title>\n" +
				"\n" +
				"  <script type=\"text/javascript\" src=\"http://visjs.org/dist/vis.js\"></script>\n" +
				"  <link href=\"http://visjs.org/dist/vis.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
				"\n" +
				"  <style type=\"text/css\">\n" +
				"    #mynetwork {\n" +
				"      width: 1200px;\n" +
				"      height: 800px;\n" +
				"      border: 1px solid lightgray;\n" +
				"    }\n" +
				"  </style>\n" +
				"</head>\n" +
				"<body>\n" +
				"\n" +
				"\n" +
				"<div id=\"mynetwork\"></div>\n" +
				"\n" +
				"<script type=\"text/javascript\">\n" +
				"  // create an array with nodes\n" +
				            
				 createNodesHTML("nodes", nodes)
				            +
				            
				createRelationshipsHTML(relatioships)+
				"\n" +
				"  // create a network\n" +
				"  var container = document.getElementById('mynetwork');\n" +
				"  var data = {\n" +
				"    nodes: nodes,\n" +
				"    edges: edges\n" +
				"  };\n" +
				"  var options = {layout: {" +
	            "        hierarchical: {" +
	            "            direction: 'LR'" +
	            "        }" +
	            "    }};\n" +
				"  var network = new vis.Network(container, data, options);\n" +
				"\n" +
				"</script>\n" +
				"\n" +
				"<script src=\"../googleAnalytics.js\"></script>\n" +
				"</body>\n" +
				"</html>";
		    	
		    	return out;
	 }

	 public void generateHTMLDocument() throws FileNotFoundException, UnsupportedEncodingException
	 {
		 PrintWriter writer = new PrintWriter("visualizer.html", "UTF-8");
		 writer.write(generateHTML());
		 writer.close();
	 }
}
