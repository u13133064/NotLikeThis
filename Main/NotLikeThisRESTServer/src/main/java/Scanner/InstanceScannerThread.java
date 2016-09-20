package Scanner;

import Buffer.SharedBuffer;
import Composite.NetworkTree;
import Composite.Node;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;

import java.util.List;
import java.util.UUID;

/**
 * Created by Jedd Shneier
 */
public class InstanceScannerThread implements Runnable
{

    private String regionName;
    private AmazonEC2 ec2;
    private SharedBuffer buffer;

   InstanceScannerThread(String regionName, AmazonEC2 ec2,SharedBuffer buffer)
    {
        this.regionName=regionName;
        this.ec2=ec2;
        this.buffer=buffer;

    }
    public void scanContext()
    {


        String nextToken = "";
        do {
            DescribeInstancesRequest newInstanceRequest = new DescribeInstancesRequest().withNextToken(nextToken);
            newInstanceRequest.setMaxResults(100);
            DescribeInstancesResult instancesResult = ec2.describeInstances();

            List<Reservation> reservations= instancesResult.getReservations();
            for(int i =0;i<reservations.size();i++) {
                List<Instance> instances =reservations.get(i).getInstances();
                for(int j =0;j<instances.size();j++)
                {
                    System.out.println("Adding instance for : "+regionName);
                    NetworkTree tree = new Node();
                    tree.setUUID("TEMP"+UUID.randomUUID().toString());
                    tree.setName(regionName);
                    tree.setInformation("{ Region Information : Unscanned");
                    NetworkTree node= new Node();
                    node.setName(instances.get(j).getVpcId());
                    node.setUUID("TEMP"+UUID.randomUUID().toString());
                    node.setInformation("{Vpc Information : Unscanned}");
                    NetworkTree child= new Node();
                    child.setUUID("TEMP"+UUID.randomUUID().toString());
                    child.setName(instances.get(j).getSubnetId());
                    child.setInformation("{Sub-network Information : Unscanned");
                    NetworkTree instanceNode= new Node();
                    instanceNode.setUUID(UUID.randomUUID().toString());
                    instanceNode.setName(instances.get(j).getInstanceId());
                    instanceNode.setInformation("{Instance Information : " + instances.get(j).toString() + " }");
                    child.add(instanceNode);
                    node.add(child);
                    tree.add(node);
                    buffer.addToBuffer(tree);


                }

            }
            nextToken = instancesResult.getNextToken();

        } while (nextToken!=null);
        }

    public void run() {
        System.out.println("Starting Instance thread for : "+regionName);
        scanContext();
    }
}