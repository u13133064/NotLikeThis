package ResponeObjects;

import java.util.List;

import com.amazonaws.services.ec2.model.Subnet;

public class SubnetResponse implements ResponseObject {
	List<Subnet> subnets;
	public void setSubnets(List<Subnet> subnets) {
		this.subnets = subnets;
	}
	
	public List<Subnet> getSubnets() {
		return subnets;
	}
	
	@Override
	public String toString() {
		String result="";
		for(int i =0; i<getSubnets().size();i++)
		{
			result+=getSubnets().get(i).getSubnetId()+",";
		}
		
		if(result.length()>1)
		{
			 result=result.substring(0,result.length()-1);
		}
	
        return  result;
	}
	

}
