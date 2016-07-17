package Messenger;

public class VPCDetails {
	private String vpcName;
	private int regionCount;
	

	public VPCDetails copy() {
		VPCDetails cloneVPCDetails = new VPCDetails();
		cloneVPCDetails.setVpcName(getVpcName());
		cloneVPCDetails.setRegionCount(getRegionCount());
		return cloneVPCDetails;
	}


	public String getVpcName() {
		return vpcName;
	}


	public void setVpcName(String vpcName) {
		this.vpcName = vpcName;
	}


	public int getRegionCount() {
		return regionCount;
	}


	public void setRegionCount(int regionCount) {
		this.regionCount = regionCount;
	}

}
