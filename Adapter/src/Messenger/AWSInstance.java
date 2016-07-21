package Messenger;

public class AWSInstance {
	private AWSInstanceDetails awsInstanceDetails;

	public AWSInstanceDetails getAwsInstanceDetails() {
		return awsInstanceDetails.copy();
	}

	public void setAwsInstanceDetails(AWSInstanceDetails awsInstanceDetails) {
		this.awsInstanceDetails = awsInstanceDetails.copy();
	}
	public String toString()
	{
		String result="";
		result+=getAwsInstanceDetails().getInstanceID()+System.lineSeparator();
		
		return result;
	}
	
}
