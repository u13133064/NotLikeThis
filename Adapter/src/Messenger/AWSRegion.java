package Messenger;

import java.util.LinkedList;

public class AWSRegion {
	private AWSRegionDetails awsRegionDetails;
	private LinkedList<VPC> vpcList;
	public AWSRegionDetails getAwsRegionDetails() {
		return awsRegionDetails.copy();
	}
	public void setAwsRegionDetails(AWSRegionDetails awsRegionDetails) {
		this.awsRegionDetails = awsRegionDetails.copy();
	}
	public LinkedList<VPC> getVpcList() {
		return vpcList;
	}
	public void setVpcList(LinkedList<VPC> vpcList) {
		this.vpcList = vpcList;
	}
	

}
