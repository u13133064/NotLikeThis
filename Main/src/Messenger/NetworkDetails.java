package Messenger;

public class NetworkDetails 
{
	private String userName;
	
	private int regionCount;
	
	public NetworkDetails copy() 
	{
		NetworkDetails cloneNetworkdetails= new NetworkDetails();
		cloneNetworkdetails.setUserName(getUserName());
		cloneNetworkdetails.setRegionCount(getRegionCount());
		return cloneNetworkdetails;
	}

	public String getUserName() 
	{
		return userName;
	}

	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public int getRegionCount() 
	{
		return regionCount;
	}

	public void setRegionCount(int regionCount) 
	{
		this.regionCount = regionCount;
	}
}
