package Scanner;

import Buffer.SharedBuffer;
import Composite.NetworkTree;
import Composite.Node;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.*;

import java.util.List;

/**
 * Created by Jedd Shneier
 */
public class InstanceScannerThread implements ThreadedScannerInterface
{

    private String identifier="";
    private  String uuid="";
    private String regionName;
    private AmazonEC2 ec2;
    private SharedBuffer buffer;
	private String typeScan="";

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
	
	public InstanceScannerThread(String regionName, String uuid, String identifier, AmazonEC2 ec2, SharedBuffer buffer, String typeScan) {
        this.regionName=regionName;
        this.ec2=ec2;
        this.buffer=buffer;
        this.uuid=uuid;
        this.identifier=identifier;
		this.typeScan=typeScan;
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
        System.out.println("Starting Instance thread for : "+regionName);
        if(uuid.equals("")) {
            scanContext();
        }
		else if(typeScan.equals("up")
		{
			scanUp();
		}
        else
        {
            scanOnly();
        }
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
		
	public void scanUp() {
		
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
		
		DescribeInstancesResult describeInstancesResult = ec2.describeInstances(describeInstancesResult);
        List<Instance> instances = describeInstancesResult.getInstances();
		uuid = instances.get(0).getSubnetId();
		
		new Thread(new VpcScannerThread(regionName,uuid,ec2,buffer)).start();
    }

    private void addInstances(List<Reservation> reservations) {

        for (int i = 0; i < reservations.size(); i++) {
            List<Instance> instances = reservations.get(i).getInstances();
            for (int j = 0; j < instances.size(); j++) {
                System.out.println("Adding instance for : " + regionName);
                NetworkTree instanceNode = new Node();
                instanceNode.setUUID(instances.get(j).getInstanceId());
                instanceNode.setName(instances.get(j).getInstanceId());
                instanceNode.setInformation("{Instance Information : " + instances.get(j).toString() + " }");
                instanceNode.setLevel(5);
                instanceNode.addRelationship(instances.get(j).getSubnetId());
                List<GroupIdentifier> securityGroups = instances.get(j).getSecurityGroups();
                for (int k = 0; k < securityGroups.size(); k++) {
                    instanceNode.addSecurityGroup(securityGroups.get(k).getGroupId());
                }
                List<InstanceNetworkInterface> networkInterfaces = instances.get(j).getNetworkInterfaces();
                for (int k = 0; k < networkInterfaces.size(); k++) {
                    instanceNode.addNetworkInterface(networkInterfaces.get(k).getNetworkInterfaceId());
                }
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
}
