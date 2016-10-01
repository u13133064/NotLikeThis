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
public class InstanceScannerThread implements Runnable
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
        do {
            DescribeInstancesRequest newInstanceRequest = new DescribeInstancesRequest().withNextToken(nextToken);
            newInstanceRequest.setMaxResults(100);
            DescribeInstancesResult instancesResult = ec2.describeInstances(newInstanceRequest);
            List<Reservation> reservations= instancesResult.getReservations();
            System.out.println(reservations.size());
            for(int i =0;i<reservations.size();i++) {
                List<Instance> instances =reservations.get(i).getInstances();
                for(int j =0;j<instances.size();j++)
                {
                    System.out.println("Adding instance for : "+regionName);
                    NetworkTree instanceNode= new Node();
                    instanceNode.setUUID(instances.get(j).getInstanceId());
                    instanceNode.setName(instances.get(j).getInstanceId());
                    instanceNode.setInformation("{Instance Information : " + instances.get(j).toString() + " }");
                    instanceNode.setLevel(5);
                    instanceNode.addRelationship(instances.get(j).getSubnetId());
                    buffer.addToBuffer(instanceNode);


                }

            }
            nextToken = instancesResult.getNextToken();

        } while (nextToken!=null);
        }

    public void run() {
        System.out.println("Starting Instance thread for : "+regionName);
        if(uuid.equals("")) {
            scanContext();
        }
        else
        {
            scanOnly();
        }
    }

    private void scanOnly() {
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
        DescribeInstancesResult instancesResult = ec2.describeInstances(describeInstancesRequest);
        List<Reservation> reservations= instancesResult.getReservations();
        for(int i =0;i<reservations.size();i++) {
            List<Instance> instances =reservations.get(i).getInstances();
            for(int j =0;j<instances.size();j++)
            {
                System.out.println("Adding instance for : "+regionName);
                NetworkTree instanceNode= new Node();
                instanceNode.setUUID(instances.get(j).getInstanceId());
                instanceNode.setName(instances.get(j).getInstanceId());
                instanceNode.setInformation("{Instance Information : " + instances.get(j).toString() + " }");
                instanceNode.setLevel(5);
                instanceNode.addRelationship(instances.get(j).getSubnetId());
                buffer.addToBuffer(instanceNode);

            }

        }

    }
}
