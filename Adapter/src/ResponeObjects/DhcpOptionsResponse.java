package ResponeObjects;

import com.amazonaws.services.ec2.model.DhcpOptions;

import java.util.List;

/**
 * Created by User on 2016/07/11.
 */
public class DhcpOptionsResponse implements ResponseObject {
    List<DhcpOptions> dhcpOptionses;

    public List<DhcpOptions> getDhcpOptionses() {
        return dhcpOptionses;
    }

    public void setDhcpOptionses(List<DhcpOptions> dhcpOptionses) {
        this.dhcpOptionses = dhcpOptionses;
    }

    @Override
    public String toString() {
        String result="";
        for (int i = 0;i<getDhcpOptionses().size();i++)
        {
            result+=getDhcpOptionses().get(i).getDhcpOptionsId()+",";

        }
        if(result.length()>1)
		{
			 result=result.substring(0,result.length()-1);
		}
	
        return  result;




    }
}
