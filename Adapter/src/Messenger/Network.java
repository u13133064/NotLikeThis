package Messenger;

import java.util.LinkedList;
import java.util.UUID;

public class Network 
{
	private NetworkDetails networkDetails;
	
	private LinkedList<AWSRegion> awsRegionList; 
	
	private UUID networkID;
	
	public Network()
	{
		networkID = UUID.randomUUID();
	}
	
	public NetworkDetails getNetworkDetails()
	{
		return networkDetails.copy();
	}
	
	public void setNetworkDetails(NetworkDetails _networkDetails)
	{
		networkDetails = _networkDetails.copy();
	}
	
	public LinkedList<AWSRegion> getAWSRegionList()
	{
		return awsRegionList;
	}
	
	public void setAWSRegionList(LinkedList<AWSRegion> _awsList)
	{
		awsRegionList = _awsList;
	}

	public String getUsername()
	{
		return networkDetails.getUserName();
	}
	
	public UUID getNetworkID()
	{
		return networkID;
	}
	
	public Boolean hasLevel()
	{
		return true;
	}
	
	public int getLevel()
	{
		return 2;
	}
}
