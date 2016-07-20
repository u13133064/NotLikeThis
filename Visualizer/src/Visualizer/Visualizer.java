package Visualizer;

public class Visualizer 
{
	public void printNetwork(Messenger.Network network)
	{
		System.out.println("These are the children of :" + network.getUsername());
		
		if(network.getAWSRegionList() != null)
		{
			for(int i = 0; i < network.getAWSRegionList().size(); i++)
			{
				printRegion(network.getAWSRegionList().get(i));
			}
		}
	}
	
	public void printRegion(Messenger.AWSRegion region)
	{
		System.out.println("	These are the children of :" + region.getAWSRegionName());
		
		if(region.getVpcList() != null)
		{
			for(int i = 0; i < region.getVpcList().size(); i++)
			{
				printVPC(region.getVpcList().get(i));
			}
		}
	}
	
	public void printVPC(Messenger.VPC vpc)
	{
		System.out.println("			These are the children of :" + vpc.getVPCName());
		
		if(vpc.getAVZoneList() != null)
		{
			for(int i = 0; i < vpc.getAVZoneList().size(); i++)
			{
				printAVZone(vpc.getAVZoneList().get(i));
				//System.out.println("		These are the children of :"	+	zone..get(i).getVPCName());
			}
		}
	}
	
	public void printAVZone(Messenger.AVZone zone)
	{
		System.out.println("				These are the children of :" + zone.getAVZoneName());
		
		if(zone.getSubNetworkList() != null)
		{
			for(int i = 0; i < zone.getSubNetworkList().size(); i++)
			{
				printSubNetworks(zone.getSubNetworkList().get(i));
			}
		}
	}
	
	public void printSubNetworks(Messenger.SubNetwork subnet)
	{
		System.out.println("					These are the children of :" + subnet.getSubNetworkName());
		
		if(subnet.getSubNetworkList() != null)
		{
			for(int i = 0; i < subnet.getSubNetworkList().size(); i++)
			{
				printSubNetworksAndInstances(subnet.getSubNetworkList().get(i));
				//System.out.println("		These are the children of :"	+	zone..get(i).getVPCName());
			}
		}
		
		if(subnet.getAWSInstanceList() != null)
		{
			for(int i = 0; i < subnet.getAWSInstanceList().size(); i++)
			{
				System.out.println("a" + subnet.getAWSInstanceList().get(i).getInstanceID());
				//System.out.println("						These are the children of :" + subnet.getSubNetworkName());
			}
		}
	}
	
	public void printSubNetworksAndInstances(Messenger.SubNetwork subnet)
	{
		System.out.println("						These are the children of :" + subnet.getSubNetworkName());
		
		if(subnet.getSubNetworkList() != null)
		{
			for(int i = 0; i < subnet.getSubNetworkList().size(); i++)
			{
				printSubNetworksAndInstances(subnet.getSubNetworkList().get(i));
			}
		}
		
		if(subnet.getAWSInstanceList() != null)
		{
			for(int i = 0; i < subnet.getAWSInstanceList().size(); i++)
			{
				System.out.println("b" + subnet.getAWSInstanceList().get(i).getInstanceID());
				//System.out.println("						These are the children of :" + subnet.getSubNetworkName());
			}
		}
		
	}

}
