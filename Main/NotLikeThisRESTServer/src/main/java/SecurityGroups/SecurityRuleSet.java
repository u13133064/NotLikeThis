package SecurityGroups;

import org.apache.commons.net.util.SubnetUtils;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Jedd Shneier.
 */
public class SecurityRuleSet {
    private  LinkedList<SecurityRule> outboundRules;
    private  LinkedList<SecurityRule> inboundRules;
    private String ipAddress;
    private  String id;
    private HashMap<String,LinkedList<SecurityRuleSet>> connections = new HashMap<String, LinkedList<SecurityRuleSet>>();

    @Override
    public String toString() {
        return "SecurityGroup: "  + id +
                "\n"
                +"Outbound rules: "+getOutboundRules()
                + "\n"
                +"Inbound rules: "+getInboundRules()
                +"\n"
                +"#";
    }

    public void addConnection(SecurityRuleSet securityRuleSet)
    {
        String id=securityRuleSet.getId();
        if(!connections.containsKey(id))
        {
            LinkedList<SecurityRuleSet> set = new LinkedList<SecurityRuleSet>();
            set.add(securityRuleSet);
            connections.put(id,set);
        }
        else
        {
            LinkedList<SecurityRuleSet> set = connections.get(id);
            set.add(securityRuleSet);
            connections.put(id,set);
        }

    }
    public String getConnectionInformation(String id)
    {
        if(id.equals(this.id))
        {
            return toString();
        }
        if(!connections.containsKey(id))
        {
            return toString();
        }
        String output = toString();
        LinkedList<SecurityRuleSet> ruleSet= connections.get(id);
        for(int i =0;i<ruleSet.size();i++)
        {
            output+=ruleSet.get(i).toString();
        }
        return output;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutboundRules() {
        String output="";
        for(int i=0;i<outboundRules.size();i++)
        {
            output+=outboundRules.get(i).toString();
        }
        return output;
    }

    public void setOutboundRules(LinkedList<SecurityRule> outboundRules) {
        this.outboundRules = outboundRules;
    }

    public String getInboundRules() {
        String output="";
        for(int i=0;i<inboundRules.size();i++)
        {
            output+=inboundRules.get(i).toString();
        }
        return output;
    }

    public void setInboundRules(LinkedList<SecurityRule> inboundRules) {
        this.inboundRules = inboundRules;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }


    public boolean canSendTo(SecurityRuleSet securityRuleSet) {
        boolean bflag = false;
        for(int i =0; i<outboundRules.size();i++)
        {
            if(outboundRulesConnects(securityRuleSet,outboundRules.get(i)))
            {
                bflag = true;
            }
        }
        return bflag;
    }

    public boolean canRecieveFrom(SecurityRuleSet securityRuleSet) {
        boolean bflag = false;
        for(int i =0; i<inboundRules.size();i++)
        {
            if(inboundRulesConnects(securityRuleSet,inboundRules.get(i)))
            {
                bflag = true;
            }
        }
        return bflag;
    }

    private boolean inboundRulesConnects(SecurityRuleSet securityRuleSet, SecurityRule securityRule) {
        boolean acceptedSecurityGroup = checkSecurityGroupID( securityRuleSet, securityRule);
        boolean acceptedIpAddress = checkIpAddress( securityRuleSet, securityRule);
        return acceptedIpAddress&&acceptedSecurityGroup;

    }

    private boolean outboundRulesConnects(SecurityRuleSet securityRuleSet, SecurityRule outboundSecurityRule) {

        boolean acceptedSecurityGroup = checkSecurityGroupID( securityRuleSet,  outboundSecurityRule);
        boolean acceptedIpAddress = checkIpAddress( securityRuleSet,  outboundSecurityRule);
        return acceptedIpAddress&&acceptedSecurityGroup;

    }

    private boolean checkIpAddress(SecurityRuleSet securityRuleSet, SecurityRule outboundSecurityRule) {
        if (outboundSecurityRule.getIpAdresses()==null)
        {
            return true;
        }
        for (int i = 0;i<outboundSecurityRule.getIpAdresses().size();i++)
        {
            if (matchesIP(securityRuleSet.getIpAddress(),outboundSecurityRule.getIpAdresses().get(i)))
            {
                return true;
            }

        }
        return false;
    }

    private boolean checkSecurityGroupID(SecurityRuleSet securityRuleSet, SecurityRule outboundSecurityRule) {
        if(outboundSecurityRule.getSecurtyGroupIds()==null|| outboundSecurityRule.getSecurtyGroupIds().size()==0)
        {
            return true;
        }
        for (int i =0;i<outboundSecurityRule.getSecurtyGroupIds().size();i++)
        {
            System.out.println(securityRuleSet.getId());
            System.out.println((outboundSecurityRule.getSecurtyGroupIds().get(i)));

            if(securityRuleSet.getId().equals(outboundSecurityRule.getSecurtyGroupIds().get(i))) {
                return true;

            }
        }
        return false;
    }

    private boolean matchesIP(String ip, String address) {

        if (address.equals("0.0.0.0/0"))
        {
            return true;
        }
        SubnetUtils utils = new SubnetUtils(address);
        utils.setInclusiveHostCount(true);
        return utils.getInfo().isInRange(ip);

    }



}
