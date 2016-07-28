package Messenger;

import java.util.LinkedList;
import java.util.UUID;

public class AWSRegion 
{
	private AWSRegionDetails awsRegionDetails;
	
	private LinkedList<VPC> vpcList;
	
	private UUID AWSRegionID;
	
	public AWSRegion()
	{
		AWSRegionID = UUID.randomUUID();
	}
	public AWSRegionDetails getAwsRegionDetails() 
	{
		return awsRegionDetails.copy();
	}
	
	public void setAwsRegionDetails(AWSRegionDetails awsRegionDetails) 
	{
		this.awsRegionDetails = awsRegionDetails.copy();
	}
	
	public LinkedList<VPC> getVpcList() 
	{
		return vpcList;
	}
	
	public void setVpcList(LinkedList<VPC> vpcList) 
	{
		this.vpcList = vpcList;
	}
	//===================================
	
	public String getAWSRegionName()
	{
		return awsRegionDetails.getAwsRegionName();
	}
	
	public UUID getAWSRegionID()
	{
		return AWSRegionID;
	}
	
	public Boolean hasLevel()
	{
		return true;
	}
	
	public int getLevel()
	{
		return 3;
	}
}
