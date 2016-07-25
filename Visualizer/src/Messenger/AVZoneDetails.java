package Messenger;

public class AVZoneDetails {
	private String avZoneName;
	private int subnetworkCount;
	

	public AVZoneDetails copy() {
		AVZoneDetails cloneAVZoneDetails = new AVZoneDetails();
		cloneAVZoneDetails.setAvZoneName(getAvZoneName());
		cloneAVZoneDetails.setSubnetworkCount(getSubnetworkCount());
		return cloneAVZoneDetails;
	}


	public int getSubnetworkCount() {
		return subnetworkCount;
	}


	public void setSubnetworkCount(int subnetworkCount) {
		this.subnetworkCount = subnetworkCount;
	}


	public String getAvZoneName() {
		return avZoneName;
	}


	public void setAvZoneName(String avZoneName) {
		this.avZoneName = avZoneName;
	}

}
