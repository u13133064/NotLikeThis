package ResponeObjects;

import java.util.List;

import com.amazonaws.services.ec2.model.Vpc;

public class VPCResponse implements ResponseObject {
	  List<Vpc>vpcs;

	public List<Vpc> getVpcs() {
		return vpcs;
	}

	public void setVpcs(List<Vpc> vpcs) {
		this.vpcs = vpcs;
	}

	@Override
	public String toString() {
		String result ="";
		for(int i =0;i<getVpcs().size();i++)
		{
			result+=getVpcs().get(i).getVpcId()+",";
		}
		if(result.length()>1)
		{
			 result=result.substring(0,result.length()-1);
		}
	
        return  result;
		
	}

	  
	  

}
