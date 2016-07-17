package Messenger;

public class SubNetworkDetails {
	private String subNetworkName;
	private int subNetworkCount;
	private int awsInstanceCount;

	public SubNetworkDetails copy() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSubNetworkName() {
		return subNetworkName;
	}

	public void setSubNetworkName(String subNetworkName) {
		this.subNetworkName = subNetworkName;
	}

	public int getSubNetworkCount() {
		return subNetworkCount;
	}

	public void setSubNetworkCount(int subNetworkCount) {
		this.subNetworkCount = subNetworkCount;
	}

	public int getAwsInstanceCount() {
		return awsInstanceCount;
	}

	public void setAwsInstanceCount(int awsInstanceCount) {
		this.awsInstanceCount = awsInstanceCount;
	}

}
