package Scanner;

import Buffer.SharedBuffer;
import Composite.NetworkTree;
import Composite.Node;
import InformationDecorator.HTMLEncoder;
import SecurityGroups.SecurityRule;
import SecurityGroups.SecurityRuleSet;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.*;

import java.util.LinkedList;
import java.util.List;
/**
 * <h1>InstanceScannerThread</h1>
 * Concrete implementation of ThreadedScannerInterface
 * Scans Instance of a particular region.
 * @author  Jedd Shneier
 * @version 1.0
 * @since   2016-10-16
 */
public class InstanceScannerThread implements ThreadedScannerInterface
{

    private String identifier="";
    private  String uuid="";
    private String regionName;
    private AmazonEC2 ec2;
    private SharedBuffer buffer;

   InstanceScannerThread(String regionName, AmazonEC2 ec2,SharedBuffer buffer)
    {
        this.regionName=regionName;
        this.ec2=ec2;
        this.buffer=buffer;

    }

    public InstanceScannerThread(String regionName, String uuid, String identifier, AmazonEC2 ec2, SharedBuffer buffer) {
        this.regionName=regionName;
        this.ec2=ec2;
        this.buffer=buffer;
        this.uuid=uuid;
        this.identifier=identifier;

    }

    public void scanContext()
    {


        String nextToken = "";
        do
        {
            DescribeInstancesRequest newInstanceRequest = new DescribeInstancesRequest().withNextToken(nextToken);
            newInstanceRequest.setMaxResults(100);
            DescribeInstancesResult instancesResult = ec2.describeInstances(newInstanceRequest);
            List<Reservation> reservations= instancesResult.getReservations();

            addInstances(reservations);
            nextToken = instancesResult.getNextToken();

        }
        while (nextToken!=null);
        }

    public void run() {
        buffer.connect();
        System.out.println("Starting Instance thread for : "+regionName);
        if(uuid.equals("")) {
            scanContext();
        }
        else
        {
            scanOnly();
        }
        buffer.disconnect();
    }

    public void scanOnly() {
        DescribeInstancesRequest describeInstancesRequest;
        if(identifier.equals("Vpc"))
        {
            Filter vpcFilter = new Filter("vpc-id").withValues(uuid);
            describeInstancesRequest=new DescribeInstancesRequest().withFilters(vpcFilter);
        }
        else if(identifier.equals("Subnet"))
        {
            Filter subnetFilter = new Filter("subnet-id").withValues(uuid);
            describeInstancesRequest=new DescribeInstancesRequest().withFilters(subnetFilter);
        }
        else{
            describeInstancesRequest= new DescribeInstancesRequest().withInstanceIds(uuid);
        }
        DescribeInstancesResult instancesResult;
        try
        {
         instancesResult = ec2.describeInstances(describeInstancesRequest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }

        List<Reservation> reservations= instancesResult.getReservations();
        addInstances(reservations);



        }

    private void addInstances(List<Reservation> reservations) {

        for (int i = 0; i < reservations.size(); i++) {
            List<Instance> instances = reservations.get(i).getInstances();
            for (int j = 0; j < instances.size(); j++) {
                System.out.println("Adding instance for : " + regionName);
                System.out.println("Instances size : " +  instances.size());
                NetworkTree instanceNode = new Node();
                instanceNode.setUUID(instances.get(j).getInstanceId());
                instanceNode.setName(instances.get(j).getInstanceId());
                LinkedList<String>information=new LinkedList<String>();
                information.add("InstanceId:"+instances.get(j).getInstanceId());
                information.add("InstanceType:"+instances.get(j).getInstanceType());
                information.add("PrivateIpAddress:"+instances.get(j).getPrivateIpAddress());
                information.add("PublicIpAddress:"+instances.get(j).getPublicIpAddress());
                information.add("ImageId:"+instances.get(j).getImageId());
                information.add("State:Code["+instances.get(j).getState().getCode()+"] Name["+instances.get(j).getState().getName()+"]");
                information.add("PrivateDnsName:"+instances.get(j).getPrivateDnsName());
                information.add("PublicDnsName:"+instances.get(j).getPublicDnsName());
                information.add("StateTransitionReason:"+instances.get(j).getStateTransitionReason());
                information.add("KeyName:"+instances.get(j).getKeyName());
                information.add("AmiLaunchIndex:"+instances.get(j).getAmiLaunchIndex());
                information.add("LaunchTime:"+instances.get(j).getLaunchTime());
                information.add("AvailabilityZone:"+instances.get(j).getPlacement().getAvailabilityZone());
                information.add("GroupName:"+instances.get(j).getPlacement().getGroupName());
                information.add("Tenancy:"+instances.get(j).getPlacement().getTenancy());
                information.add("Affinity:"+instances.get(j).getPlacement().getAffinity());
                information.add("HostId:"+instances.get(j).getPlacement().getHostId());
                information.add("KernelId:"+instances.get(j).getKernelId());
                information.add("Monitoring:"+instances.get(j).getMonitoring().getState());
                information.add("Architecture:"+instances.get(j).getArchitecture());
                information.add("RootDeviceType:"+instances.get(j).getRootDeviceType());
                information.add("RootDeviceName:"+instances.get(j).getRootDeviceName());
                information.add("VirtualizationType:"+instances.get(j).getVirtualizationType());
                information.add("InstanceLifecycle:"+instances.get(j).getInstanceLifecycle());
                information.add("InstanceLifecycle:"+instances.get(j).getInstanceLifecycle());
                information.add("Hypervisor:"+instances.get(j).getHypervisor());


                instanceNode.setInformation(new HTMLEncoder().informationToHtml(information));
                instanceNode.setLevel(5);
                instanceNode.addRelationship(instances.get(j).getSubnetId());
                if(identifier.equals("Instance"))
                {
                    buffer.setParentLevel("Subnet");
                    buffer.setParentIdentifier(instances.get(i).getVpcId());
                }
                addSecurityRuleSets(instances.get(j));

                if(buffer.getState()==1)
                {
                    synchronized(buffer.getThreadNotifier())
                    {
                        try {
                            buffer.getThreadNotifier().wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(buffer.getState()==2)
                {
                    return;
                }
                buffer.addToBuffer(instanceNode);
            }
        }
    }

    private void addSecurityRuleSets(Instance instance) {
        LinkedList<String> securtiyGroupIds = new LinkedList<String>();
        for(int i = 0;i< instance.getSecurityGroups().size();i++)
        {
            securtiyGroupIds.add(instance.getSecurityGroups().get(i).getGroupId());

        }


        DescribeSecurityGroupsRequest describeSecurityGroupsRequest = new DescribeSecurityGroupsRequest().withGroupIds(securtiyGroupIds);
        DescribeSecurityGroupsResult describeSecurityGroupsResult = ec2.describeSecurityGroups(describeSecurityGroupsRequest);
        List<SecurityGroup> securityGroups= describeSecurityGroupsResult.getSecurityGroups();
        for (int i =0;i<securityGroups.size();i++)
        {
            List<IpPermission> ipPermisions= securityGroups.get(i).getIpPermissions();

            LinkedList<SecurityRule> inboundRules = new LinkedList<SecurityRule>();

            for (int j =0;j<ipPermisions.size();j++)
            {
                SecurityRule inboundRule = new SecurityRule();
                inboundRule.setIpAdresses(ipPermisions.get(j).getIpRanges());

                inboundRule.setProtocol(ipPermisions.get(j).getIpProtocol());
                LinkedList<String> securityGroupIds= new LinkedList<String>();
                List<UserIdGroupPair> userIdGroupPairs = ipPermisions.get(j).getUserIdGroupPairs();
                inboundRule.setPortRanges(ipPermisions.get(j).getFromPort(),ipPermisions.get(j).getToPort());
                for(int k = 0;k<userIdGroupPairs.size();k++)
                {
                    securityGroupIds.add(userIdGroupPairs.get(k).getGroupId());
                }
                inboundRule.setSecurtyGroupIds(securityGroupIds);
                inboundRules.add(inboundRule);
            }
            ipPermisions= securityGroups.get(i).getIpPermissionsEgress();
            LinkedList<SecurityRule>outboundRules = new LinkedList<SecurityRule>();

            for (int j =0;j<ipPermisions.size();j++)
            {
                SecurityRule outboundRule = new SecurityRule();
                outboundRule.setIpAdresses(ipPermisions.get(j).getIpRanges());

                outboundRule.setProtocol(ipPermisions.get(j).getIpProtocol());
                LinkedList<String> securityGroupIds= new LinkedList<String>();
                List<UserIdGroupPair> userIdGroupPairs = ipPermisions.get(j).getUserIdGroupPairs();
                for(int k = 0;k<userIdGroupPairs.size();k++)
                {
                    securityGroupIds.add(userIdGroupPairs.get(k).getGroupId());
                }
                outboundRule.setSecurtyGroupIds(securityGroupIds);
                outboundRules.add(outboundRule);
            }
            SecurityRuleSet securityRuleSet = new SecurityRuleSet();
            securityRuleSet.setId(securityGroups.get(i).getGroupId());
            securityRuleSet.setInboundRules(inboundRules);
            securityRuleSet.setPrivateIpAddress(instance.getPrivateIpAddress());
            securityRuleSet.setPublicIpAddress(instance.getPublicIpAddress());
            securityRuleSet.setVpcID(instance.getVpcId());

            securityRuleSet.setOutboundRules(outboundRules);

            buffer.addToSecurityGroups(instance.getInstanceId(),securityRuleSet);

        }
    }


}
