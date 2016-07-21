package Messenger;

import java.util.LinkedList;

public class VPC {
	private VPCDetails vpcDetails;
	private LinkedList<AVZone> AVZoneList;
	public VPCDetails getVpcDetails() {
		return vpcDetails.copy();
	}
	public void setVpcDetails(VPCDetails vpcDetails) {
		this.vpcDetails = vpcDetails.copy();
	}
	public LinkedList<AVZone> getAVZoneList() {
		return AVZoneList;
	}
	public void setAVZoneList(LinkedList<AVZone> aVZoneList) {
		AVZoneList = aVZoneList;
	} 
	public String toString()
	{
		String result="";
		result+=getVpcDetails().getVpcName()+System.lineSeparator();
		for(int i = 0;i<getAVZoneList().size();i++)
		{
			result+=getAVZoneList().get(i).toString()+System.lineSeparator();
		}
		return result;
	}

}
