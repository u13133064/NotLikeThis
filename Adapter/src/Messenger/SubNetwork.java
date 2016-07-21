package Messenger;

import java.util.LinkedList;

public class SubNetwork {
	private SubNetworkDetails subnetworkDetails;
	private LinkedList<SubNetwork> subNetworkList=null;
	private LinkedList<AWSInstance> AWSInstanceList=new LinkedList<AWSInstance>() ;
	public String getSubNetworkName()
	{
		return subnetworkDetails.getSubNetworkName();
	}
	public SubNetworkDetails getSubnetworkDetails() {
		return subnetworkDetails.copy();
	}
	public void setSubnetworkDetails(SubNetworkDetails subnetworkDetails) {
		this.subnetworkDetails = subnetworkDetails;
	}
	public LinkedList<SubNetwork> getSubNetworkList() {
		return subNetworkList;
	}
	public void setSubNetworkList(LinkedList<SubNetwork> subNetworkList) {
		this.subNetworkList = subNetworkList;
	}
	public LinkedList<AWSInstance> getAWSInstanceList() {
		return AWSInstanceList;
	}
	public void setAWSInstanceList(LinkedList<AWSInstance> aWSInstanceList) {
		AWSInstanceList = aWSInstanceList;
	}
	public String toString()
	{
		String result="";
		result+=getSubNetworkName()+System.lineSeparator();
		for(int i = 0;i<getAWSInstanceList().size();i++)
		{
			result+=getAWSInstanceList().get(i).toString()+System.lineSeparator();
		}
		return result;
	}
	
	
}
