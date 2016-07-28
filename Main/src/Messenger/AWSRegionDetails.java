package Messenger;

public class AWSRegionDetails 
{
	private String awsRegionName;
	
	private int vpcCount;
	
	public AWSRegionDetails copy() 
	{
		AWSRegionDetails cloneAWSRegionDetails = new AWSRegionDetails();
		cloneAWSRegionDetails.setAwsRegionName(getAwsRegionName());
		cloneAWSRegionDetails.setVpcCount(getVpcCount());
		
		return cloneAWSRegionDetails;
	}
	
	public String getAwsRegionName() 
	{
		return awsRegionName;
	}
	
	public void setAwsRegionName(String awsRegionName) 
	{
		this.awsRegionName = awsRegionName;
	}
	
	public int getVpcCount() 
	{
		return vpcCount;
	}
	
	public void setVpcCount(int vpcCount) 
	{
		this.vpcCount = vpcCount;
	}
}
