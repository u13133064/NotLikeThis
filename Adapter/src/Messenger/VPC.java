package Messenger;

import java.util.LinkedList;
import java.util.UUID;

public class VPC 
{
	private VPCDetails vpcDetails;
	
	private LinkedList<AVZone> AVZoneList;
	
	private UUID vpcID;
	
	public VPC()
	{
		vpcID = UUID.randomUUID();
	}
	
	public VPCDetails getVpcDetails() 
	{
		return vpcDetails.copy();
	}
	
	public void setVpcDetails(VPCDetails vpcDetails) 
	{
		this.vpcDetails = vpcDetails.copy();
	}
	
	public LinkedList<AVZone> getAVZoneList() 
	{
		return AVZoneList;
	}
	
	public void setAVZoneList(LinkedList<AVZone> aVZoneList) 
	{
		AVZoneList = aVZoneList;
	} 
	
	public String getVPCName()
	{
		return vpcDetails.getVpcName();
	}
	
	public UUID getVPCID()
	{
		return vpcID;
	}
	
	public Boolean hasLevel()
	{
		return true;
	}
	
	public int getLevel()
	{
		return 4;
	}

}
