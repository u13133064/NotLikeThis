package Messenger;

import java.util.LinkedList;

public class AWSRegion {
	private AWSRegionDetails awsRegionDetails;
	private LinkedList<AVZone> AVZoneList;
	public AWSRegionDetails getAwsRegionDetails() {
		return awsRegionDetails.copy();
	}
	public void setAwsRegionDetails(AWSRegionDetails awsRegionDetails) {
		this.awsRegionDetails = awsRegionDetails.copy();
	}
	public LinkedList<AVZone> getAVZoneList() {
		return AVZoneList;
	}
	public void setAVZoneList(LinkedList<AVZone> aVZoneList) {
		AVZoneList = aVZoneList;
	}

}
