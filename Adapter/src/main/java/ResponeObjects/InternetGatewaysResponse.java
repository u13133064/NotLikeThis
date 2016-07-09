package ResponeObjects;

import com.amazonaws.services.ec2.model.InternetGateway;

import java.util.List;

/**
 * Created by User on 2016/07/05.
 */
public class InternetGatewaysResponse implements ResponseObject {
    private List<InternetGateway> internetGateways;

    public List<InternetGateway> getInternetGateways() {
        return internetGateways;
    }

    public void setInternetGateways(List<InternetGateway> internetGateways) {
        this.internetGateways = internetGateways;
    }

    @Override
    public String toString() {
        String result="";
        for (int i = 0;i<getInternetGateways().size();i++)
        {
            result+=getInternetGateways().get(i).getInternetGatewayId();
            result+=",";
        }
        result=result.substring(0,result.length()-1);
        return  result;

    }
}
