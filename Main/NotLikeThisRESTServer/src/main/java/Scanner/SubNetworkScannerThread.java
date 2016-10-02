package Scanner;

import Buffer.SharedBuffer;
import Composite.NetworkTree;
import Composite.Node;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeSubnetsRequest;
import com.amazonaws.services.ec2.model.DescribeSubnetsResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Subnet;

import java.util.List;

/**
 * Created by Jedd Sheneier.
 */
public class SubNetworkScannerThread implements Runnable {
    private String regionName;
    private AmazonEC2 ec2;
    private SharedBuffer buffer;
    private String identifier = "";
    private String uuid ="";

   SubNetworkScannerThread(String regionName, AmazonEC2 ec2,SharedBuffer buffer)
    {
        this.regionName=regionName;
        this.ec2=ec2;
        this.buffer=buffer;

    }

    public SubNetworkScannerThread(String regionName, String uuid,String identifier, AmazonEC2 ec2, SharedBuffer buffer) {
        this.regionName=regionName;
        this.ec2=ec2;
        this.buffer=buffer;
        this.uuid=uuid;
        this.identifier=identifier;
    }

    public void scanContext()
    {

        DescribeSubnetsResult describeSubnetsResult = ec2.describeSubnets();
        List<Subnet> subnets = describeSubnetsResult.getSubnets();

        for(int i = 0; i<subnets.size();i++) {
            System.out.println("Adding subnet for : "+regionName);
            NetworkTree tree= new Node();
            tree.setUUID(subnets.get(i).getSubnetId());
            tree.setName(subnets.get(i).getSubnetId());
            tree.setInformation("{Sub-network Information : " + subnets.get(i).toString() + " }");
            tree.setLevel(4);
            tree.addRelationship(subnets.get(i).getVpcId());
            buffer.addToBuffer(tree);

        }

    }

    public void run() {
        System.out.println("Starting Subnet thread for : "+regionName);
        if(uuid.equals("")) {
            scanContext();
        }
        else
        {
            scanOnly();
        }
    }

    private void scanOnly() {
        DescribeSubnetsRequest describeSubnetsRequest;
        if(identifier.equals("Vpc"))
        {
            Filter vpcFilter = new Filter("vpc-id").withValues(uuid);
            try {
                describeSubnetsRequest = new DescribeSubnetsRequest().withFilters(vpcFilter);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return;
            }
        }
        else
        {
             describeSubnetsRequest = new DescribeSubnetsRequest().withSubnetIds(uuid);
        }

        DescribeSubnetsResult describeSubnetsResult = ec2.describeSubnets(describeSubnetsRequest);
        List<Subnet> subnets = describeSubnetsResult.getSubnets();
        if(identifier.equals("") && subnets.size()>0)
        {
            new Thread(new InstanceScannerThread(this.regionName,uuid,"Subnet",this.ec2,buffer)).start();
        }

        for(int i = 0; i<subnets.size();i++) {
            System.out.println("Found subnet at : "+regionName);
            NetworkTree tree= new Node();
            tree.setUUID(subnets.get(i).getSubnetId());
            tree.setName(subnets.get(i).getSubnetId());
            tree.setInformation("{Sub-network Information : " + subnets.get(i).toString() + " }");
            tree.setLevel(4);
            tree.addRelationship(subnets.get(i).getVpcId());
            buffer.addToBuffer(tree);

        }

    }
}
