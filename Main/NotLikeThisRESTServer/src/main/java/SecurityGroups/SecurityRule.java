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
    private Integer portFrom;
    private Integer portTo;

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

    @Override
    public String toString() {
        return "\n"
                +"Source :"+getSecurtyGroupIds()+getIpAdresses()+
                "\n"+"Protocol: "+getProtocol()+
                "\n"+"PortRanges: " + getPortRanges();
    }

    public String getPortRanges() {
        if(portFrom==null || portTo==null)
        {
            return "All";
        }
        if(portTo==portFrom)
        {
            return String.valueOf(portTo);
        }
        return portFrom +" - "+portTo;
    }



    public String getProtocol() {
        if(protocol.equals("-1"))
        {
            return "All";
        }
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


    public void setPortRanges(Integer fromPort, Integer toPort) {
        this.portFrom=fromPort;
        this.portTo=toPort;
    }
}
