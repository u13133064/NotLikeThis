package Messenger;

public class NetworkDetails {
	private String userName;
	private int vpcCount;
	
	

	public NetworkDetails copy() {
		NetworkDetails cloneNetworkdetails= new NetworkDetails();
		cloneNetworkdetails.setUserName(getUserName());
		cloneNetworkdetails.setVpcCount(getVpcCount());
		return cloneNetworkdetails;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public int getVpcCount() {
		return vpcCount;
	}



	public void setVpcCount(int vpcCount) {
		this.vpcCount = vpcCount;
	}

}
