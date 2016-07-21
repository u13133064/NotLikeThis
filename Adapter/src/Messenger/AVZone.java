package Messenger;

import java.util.LinkedList;

public class AVZone {
	private AVZoneDetails avZoneDetails;
	private LinkedList<SubNetwork> subNetworkList;
	public AVZoneDetails getAvZoneDetails() {
		return avZoneDetails.copy();
	}
	public void setAvZoneDetails(AVZoneDetails avZoneDetails) {
		this.avZoneDetails = avZoneDetails.copy();
	}
	public LinkedList<SubNetwork> getSubNetworkList() {
		return subNetworkList;
	}
	public void setSubNetworkList(LinkedList<SubNetwork> subNetworkList) {
		this.subNetworkList = subNetworkList;
	}
	public String toString()
	{
		String result="";
		result+=getAvZoneDetails().getAvZoneName()+System.lineSeparator();
		for(int i = 0;i<getSubNetworkList().size();i++)
		{
			
			result+=getSubNetworkList().get(i).toString()+System.lineSeparator();
		}
		return result;
	}
	
	
}
