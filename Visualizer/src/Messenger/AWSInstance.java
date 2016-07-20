package Messenger;

import java.util.UUID;

public class AWSInstance 
{
	private AWSInstanceDetails awsInstanceDetails;
	
	private UUID AWSInstanceID;
	
	public AWSInstance()
	{
		AWSInstanceID = UUID.randomUUID();
	}

	public AWSInstanceDetails getAwsInstanceDetails() 
	{
		return awsInstanceDetails.copy();
	}

	public void setAwsInstanceDetails(AWSInstanceDetails awsInstanceDetails) 
	{
		this.awsInstanceDetails = awsInstanceDetails.copy();
	}
	
	public String getAWSInstanceName()
	{
		return awsInstanceDetails.getInstanceName();
	}
	
	public UUID getAWSInstanceID()
	{
		return AWSInstanceID;
	}
	
}
