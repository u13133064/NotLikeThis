package Scanner;

import Buffer.SharedBuffer;
import Composite.NetworkTree;
import Composite.Node;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeSubnetsResult;
import com.amazonaws.services.ec2.model.Subnet;

import java.util.List;

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
            System.out.println("Adding subnet for : "+regionName);
            NetworkTree tree= new Node();
            tree.setUUID(subnets.get(i).getSubnetId());
            //tree.setUUID(UUID.randomUUID().toString());
            tree.setName(subnets.get(i).getSubnetId());
            tree.setInformation("{Sub-network Information : " + subnets.get(i).toString() + " }");
            tree.setLevel(4);
            tree.addRelationship(subnets.get(i).getVpcId());
            buffer.addToBuffer(tree);

        }

    }

    public void run() {
        System.out.println("Starting Subnet thread for : "+regionName);
        scanContext();
    }
}
