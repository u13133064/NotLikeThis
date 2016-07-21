package Messenger;

import java.util.LinkedList;

public class Network {
	private NetworkDetails networkDetails;
	private LinkedList<AWSRegion> awsRegionList; 
	public String toString()
	{
		String result="";
		result+=networkDetails.getUserName()+System.lineSeparator();
		for(int i = 0;i<getAWSRegionList().size();i++)
		{
			result+=getAWSRegionList().get(i).toString()+System.lineSeparator();
		}
		return result;
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

}
