package SecurityGroups;

import org.apache.commons.net.util.SubnetUtils;

import java.util.LinkedList;

/**
 * Created by Jedd Shneier.
 */
public class SecurityRuleSet {
    LinkedList<SecurityRule> outboundRules;
    LinkedList<SecurityRule> inboundRules;
    String ipAddress;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<SecurityRule> getOutboundRules() {
        return outboundRules;
    }

    public void setOutboundRules(LinkedList<SecurityRule> outboundRules) {
        this.outboundRules = outboundRules;
    }

    public LinkedList<SecurityRule> getInboundRules() {
        return inboundRules;
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
