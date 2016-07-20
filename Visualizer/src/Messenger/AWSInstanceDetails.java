package Messenger;

public class AWSInstanceDetails 
{
	private String instanceName;
	
	public AWSInstanceDetails copy() 
	{
		AWSInstanceDetails cloneAWSInstanceDetails = new AWSInstanceDetails();
		cloneAWSInstanceDetails.setInstanceName(getInstanceName());
		return cloneAWSInstanceDetails;
	}

	public String getInstanceName() 
	{
		return instanceName;
	}

	public void setInstanceName(String instanceName) 
	{
		this.instanceName = instanceName;
	}

}
