package Messenger;

public class VPCDetails 
{
	private String vpcName;
	
	private int avZoneCount;
	
	public VPCDetails copy() 
	{
		VPCDetails cloneVPCDetails = new VPCDetails();
		cloneVPCDetails.setVpcName(getVpcName());
		cloneVPCDetails.setAvZoneCount(getAvZoneCount());
		return cloneVPCDetails;
	}

	public String getVpcName() 
	{
		return vpcName;
	}

	public void setVpcName(String vpcName) 
	{
		this.vpcName = vpcName;
	}

	public int getAvZoneCount() 
	{
		return avZoneCount;
	}

	public void setAvZoneCount(int avZoneCount) 
	{
		this.avZoneCount = avZoneCount;
	}
}
