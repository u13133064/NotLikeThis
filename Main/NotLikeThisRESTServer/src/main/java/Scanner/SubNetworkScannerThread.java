package Scanner;

import Buffer.SharedBuffer;
import Composite.NetworkTree;
import Composite.Node;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeSubnetsResult;
import com.amazonaws.services.ec2.model.Subnet;

import java.util.List;
import java.util.UUID;

/**
 * Created by Jedd Sheneier.
 */
public class SubNetworkScannerThread implements Runnable {
    private String regionName;
    private AmazonEC2 ec2;
    private SharedBuffer buffer;

   SubNetworkScannerThread(String regionName, AmazonEC2 ec2,SharedBuffer buffer)
    {
        this.regionName=regionName;
        this.ec2=ec2;
        this.buffer=buffer;

    }
    public void scanContext()
    {

        DescribeSubnetsResult describeSubnetsResult = ec2.describeSubnets();
        List<Subnet> subnets = describeSubnetsResult.getSubnets();

        for(int i = 0; i<subnets.size();i++) {
            System.out.println("Adding subent for : "+regionName);
            NetworkTree tree = new Node();
            tree.setUUID("TEMP"+UUID.randomUUID().toString());
            tree.setName(regionName);
            tree.setInformation("{ Region Information : Unscanned");
            NetworkTree node= new Node();
            node.setName(subnets.get(i).getVpcId());
            node.setUUID("TEMP"+UUID.randomUUID().toString());
            node.setInformation("{Vpc Information : Unscanned}");
            NetworkTree child= new Node();
            child.setUUID(UUID.randomUUID().toString());
            child.setName(subnets.get(i).getSubnetId());
            child.setInformation("{Sub-network Information : " + subnets.get(i).toString() + " }");
            node.add(child);
            tree.add(node);
            buffer.addToBuffer(tree);

        }

    }

    public void run() {
        System.out.println("Starting Subnet thread for : "+regionName);
        scanContext();
    }
}
