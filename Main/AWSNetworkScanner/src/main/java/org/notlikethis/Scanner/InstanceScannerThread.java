package org.notlikethis.Scanner;

import com.amazonaws.services.ec2.AmazonEC2;

import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import org.notlikethis.Buffer.SharedBuffer;
import org.notlikethis.Composite.NetworkTree;
import org.notlikethis.Composite.Node;

import java.util.List;

/**
 * Created by Jedd Shneier
 */
public class InstanceScannerThread {
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

                    NetworkTree tree = new Node();
                    tree.setUUID("TEMP");
                    tree.setName(regionName);
                    tree.setInformation("{ Region Information : Unscanned");
                    NetworkTree node= new Node();
                    node.setName(instances.get(j).getVpcId());
                    node.setUUID("PlaceHolder");
                    node.setInformation("{Vpc Information : Unscanned}");
                    NetworkTree child= new Node();
                    child.setUUID("PlaceHolder");
                    child.setName(instances.get(j).getSubnetId());
                    child.setInformation("{Sub-network Information : Unscanned");
                    NetworkTree instanceNode= new Node();
                    instanceNode.setUUID("PlaceHolder");
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

}
