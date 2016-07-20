package Messenger;

import java.util.LinkedList;
import java.util.UUID;

public class SubNetwork 
{
	private SubNetworkDetails subnetworkDetails;
	
	private LinkedList<SubNetwork> subNetworkList;
	private LinkedList<AWSInstance> AWSInstanceList;
	
	private UUID SubNetworkID;
	
	public SubNetworkDetails getSubnetworkDetails() 
	{
		return subnetworkDetails.copy();
	}
	
	public void setSubnetworkDetails(SubNetworkDetails subnetworkDetails) 
	{
		this.subnetworkDetails = subnetworkDetails.copy();
	}
	
	public LinkedList<SubNetwork> getSubNetworkList() 
	{
		return subNetworkList;
	}
	
	public void setSubNetworkList(LinkedList<SubNetwork> subNetworkList) 
	{
		this.subNetworkList = subNetworkList;
	}
	
	public LinkedList<AWSInstance> getAWSInstanceList() 
	{
		return AWSInstanceList;
	}
	
	public void setAWSInstanceList(LinkedList<AWSInstance> aWSInstanceList) 
	{
		AWSInstanceList = aWSInstanceList;
	}	
	
	public String getSubNetworkName()
	{
		return subnetworkDetails.getSubNetworkName();
	}
	
	public UUID getSubNetworkID()
	{
		return SubNetworkID;
	}
}
