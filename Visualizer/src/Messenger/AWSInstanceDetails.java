package Messenger;

public class AWSInstanceDetails 
{
	private String instanceID;
	

	public AWSInstanceDetails copy() 
	{
		AWSInstanceDetails cloneAWSInstanceDetails = new AWSInstanceDetails();
		cloneAWSInstanceDetails.setInstanceID(getInstanceID());
		return cloneAWSInstanceDetails;
	}


	public String getInstanceID() 
	{
		return instanceID;
	}


	public void setInstanceID(String instanceID) 
	{
		this.instanceID = instanceID;
	}

}
