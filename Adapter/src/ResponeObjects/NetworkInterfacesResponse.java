package ResponeObjects;

import com.amazonaws.services.ec2.model.NetworkAcl;

import java.util.List;

/**
 * Created by User on 2016/07/12.
 */
public class NetworkInterfacesResponse implements ResponseObject{
    List<NetworkAcl> networkAcls;

    @Override
    public String toString() {
        String result="";
        for (int i =0 ; i< getNetworkAcls().size();i++)
        {
            result+=getNetworkAcls().get(i).getNetworkAclId()+",";
        }
        result=result.substring(0,result.length()-1);
        return  result;

    }

    public List<NetworkAcl> getNetworkAcls() {
        return networkAcls;
    }

    public void setNetworkAcls(List<NetworkAcl> networkAcls) {
        this.networkAcls = networkAcls;
    }
}
