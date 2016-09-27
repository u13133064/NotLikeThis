package Scanner;

import Buffer.SharedBuffer;
import Composite.NetworkTree;
import Composite.Node;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeVpcsResult;
import com.amazonaws.services.ec2.model.Vpc;

import java.util.List;

/**
 * Created by Jedd Shneier.
 */
public class VpcScannerThread implements Runnable{
    private String regionName;
    private  AmazonEC2 ec2;
    private SharedBuffer buffer;

    VpcScannerThread(String regionName, AmazonEC2 ec2,SharedBuffer buffer)
    {
        this.regionName=regionName;
        this.ec2=ec2;
        this.buffer=buffer;

    }
    public void scanContext()
    {

        DescribeVpcsResult describeVpcsResult = ec2.describeVpcs();
        List<Vpc> vpcs = describeVpcsResult.getVpcs();

        for(int i = 0; i<vpcs.size();i++) {
            System.out.println("Adding Vpc for : "+regionName);
            NetworkTree node= new Node();
            node.setName(vpcs.get(i).getVpcId());
            node.setUUID(vpcs.get(i).getVpcId());
            node.setInformation("{Vpc Information : " + vpcs.get(i).toString() + " }");
            node.setLevel(3);
            node.addRelationship(regionName);
            buffer.addToBuffer(node);

        }

    }

    public void run() {
        System.out.println("Starting VPC thread for : "+regionName);
        scanContext();

    }
}
