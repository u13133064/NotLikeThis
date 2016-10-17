package SecurityGroups;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jedd Shneier
 */
public class SecurityRule
{
    private  int[] portRanges;
    private LinkedList<String> securtyGroupIds;
    private  String protocol;
    private List<String> IpAdresses;

    public List<String> getIpAdresses() {
        return IpAdresses;
    }

    public void setIpAdresses(List<String> ipAdresses) {
        IpAdresses = ipAdresses;
    }



    public LinkedList<String> getSecurtyGroupIds() {
        return securtyGroupIds;
    }

    public void setSecurtyGroupIds(LinkedList<String> securtyGroupIds) {
        this.securtyGroupIds = securtyGroupIds;
    }







    public int[] getPortRanges() {
        return portRanges;
    }

    public void setPortRanges(int[] portRanges) {
        this.portRanges = portRanges;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    public void generateRanges(int portFrom,int portTo)
    {
        int counter = 0;
        portRanges=new int[portTo-portFrom+1];
        for(int i = portFrom;i<=portTo;i++)
        {
            portRanges[counter]=i;
            counter++;
        }

    }

    
}
