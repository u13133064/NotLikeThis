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
	public String toString()
	{
		String result="";
		result+=getAwsRegionDetails().getAwsRegionName()+System.lineSeparator();
		for(int i = 0;i<getVpcList().size();i++)
		{
			result+=getVpcList().get(i).toString()+System.lineSeparator();
		}
		return result;
	}
	

}
