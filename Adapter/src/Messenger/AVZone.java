package Messenger;

import java.util.LinkedList;
import java.util.UUID;

public class AVZone 
{
	private AVZoneDetails avZoneDetails;
	
	private LinkedList<SubNetwork> subNetworkList;
	
	private UUID AVZoneID;
	
	public AVZone()
	{
		AVZoneID = UUID.randomUUID();
	}
	
	public AVZoneDetails getAvZoneDetails() 
	{
		return avZoneDetails.copy();
	}
	public void setAvZoneDetails(AVZoneDetails avZoneDetails) 
	{
		this.avZoneDetails = avZoneDetails.copy();
	}
	public LinkedList<SubNetwork> getSubNetworkList() 
	{
		return subNetworkList;
	}
	public void setSubNetworkList(LinkedList<SubNetwork> subNetworkList) 
	{
		this.subNetworkList = subNetworkList;
	}
	
	public String getAVZoneName()
	{
		return avZoneDetails.getAvZoneName();
	}
	
	public UUID getAVZoneID()
	{
		return AVZoneID;
	}
	
	public Boolean hasLevel()
	{
		return true;
	}
	
	public int getLevel()
	{
		return 5;
	}
}
