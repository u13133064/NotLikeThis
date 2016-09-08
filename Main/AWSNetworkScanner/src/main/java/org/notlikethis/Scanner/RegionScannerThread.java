package org.notlikethis.Scanner;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.*;
import org.notlikethis.Buffer.SharedBuffer;
import org.notlikethis.Composite.NetworkTree;
import org.notlikethis.Composite.Node;
import org.notlikethis.Credentials.Credential;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jedd Shneier
 */
public class RegionScannerThread implements Runnable {
    private Credential clientCredentials;
    private String regionName;
    private SharedBuffer buffer;
    public RegionScannerThread(Credential credentials,String regionName,SharedBuffer buffer)
    {
        this.clientCredentials=credentials;
        this.regionName=regionName;
        this.buffer=buffer;
    }


    public void run() {
        AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);

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
                    NetworkTree generatedTree =convertInstanceToTree(instances.get(i));
                    buffer.addToBuffer(generatedTree);

                }

            }
            nextToken = instancesResult.getNextToken();

        } while (nextToken.length()>0);







    }

    private NetworkTree convertInstanceToTree(Instance instance) {
        NetworkTree tree = createRegionLevel();
        tree.add(createVpcLevel(instance.getVpcId()));
        tree.getChild(0).add(createSubnetworkLevel(instance.getSubnetId()));
        tree.getChild(0).getChild(0).add(createInstanceLevel(instance));


        return tree;
    }

    private NetworkTree createInstanceLevel(Instance instance) {
        NetworkTree tree = new Node();
        tree.setUUID("PlaceHolderID");
        tree.setName(instance.getInstanceId());
        tree.setInformation("{Instance Information : " + instance.toString() + " }");
        return tree;

    }

    private NetworkTree createSubnetworkLevel(String subnetId) {
        AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);
        DescribeSubnetsRequest describeSubnetsRequest = new DescribeSubnetsRequest();

        LinkedList<String>subnetIds = new LinkedList<String>();
        subnetIds.add(subnetId);
        describeSubnetsRequest.setSubnetIds(subnetIds);
        DescribeSubnetsResult describeSubnetResult = ec2.describeSubnets(describeSubnetsRequest);
        List<Subnet> subents = describeSubnetResult.getSubnets();

        NetworkTree tree = new Node();
        tree.setUUID("PlaceHolderID");
        tree.setName(subents.get(0).getVpcId());
        tree.setInformation("{Subnet Information : " + subents.get(0).toString() + " }");
        return tree;

    }

    private NetworkTree createVpcLevel(String vpcId) {
        AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);
        DescribeVpcsRequest describeVpcsRequest = new DescribeVpcsRequest();
        LinkedList<String>vpcIds = new LinkedList<String>();
        vpcIds.add(vpcId);
        describeVpcsRequest.setVpcIds(vpcIds);
        DescribeVpcsResult describeVpcsResult = ec2.describeVpcs(describeVpcsRequest);
        List<Vpc> vpcs = describeVpcsResult.getVpcs();

        NetworkTree tree = new Node();
        tree.setUUID("PlaceHolderID");
        tree.setName(vpcs.get(0).getVpcId());
        tree.setInformation("{Vpc Information : " + vpcs.get(0).toString() + " }");
        return tree;





    }

    private NetworkTree createRegionLevel() {
        NetworkTree tree = new Node();
        tree.setUUID("PlaceHolderID");
        tree.setName(regionName);
        tree.setInformation("{ RegionName : "+regionName+" }");
        return tree;

    }
}
