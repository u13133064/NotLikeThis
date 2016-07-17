package Messenger;

import java.util.LinkedList;

public class VPC {
	private VPCDetails vpcDetails;
	private LinkedList<AWSRegion> awsRegionList;
	public VPCDetails getVpcDetails() {
		return vpcDetails.copy();
	}
	public void setVpcDetails(VPCDetails vpcDetails) {
		this.vpcDetails = vpcDetails.copy();
	} 

}
