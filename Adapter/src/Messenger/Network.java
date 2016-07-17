package Messenger;

import java.util.LinkedList;

public class Network {
	private NetworkDetails networkDetails;
	private LinkedList<VPC> vpcList; 
	
	public NetworkDetails getNetworkDetails()
	{
		return networkDetails.copy();
	}
	
	public void setNetworkDetails(NetworkDetails _networkDetails)
	{
		networkDetails = _networkDetails.copy();
	}
	
	public LinkedList<VPC> getVPCList()
	{
		return vpcList;
	}
	
	public void setVPCList(LinkedList<VPC> _vpcList)
	{
		vpcList = _vpcList;
	}

}
