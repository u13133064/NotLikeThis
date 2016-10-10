package Scanner;

import Buffer.SharedBuffer;
import Composite.NetworkTree;
import Composite.Node;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.ec2.model.SecurityGroup;

import java.util.List;

/**
 * Created by Jedd Shneier.
 */
public class SecurityGroupScannerThread implements ThreadedScannerInterface{
    private String identifier="";
    private  String uuid="";
    private String regionName;
    private AmazonEC2 ec2;
    private SharedBuffer buffer;

    SecurityGroupScannerThread(String regionName, AmazonEC2 ec2,SharedBuffer buffer)
    {
        this.regionName=regionName;
        this.ec2=ec2;
        this.buffer=buffer;

    }

   SecurityGroupScannerThread(String regionName, String uuid, String identifier, AmazonEC2 ec2, SharedBuffer buffer)
   {
        this.regionName=regionName;
        this.ec2=ec2;
        this.buffer=buffer;
        this.uuid=uuid;
        this.identifier=identifier;

    }

    public void scanOnly() {

    }

    public void scanContext() {
        DescribeSecurityGroupsResult describeSecurityGroupsResult = new DescribeSecurityGroupsResult();
        List<SecurityGroup> securityGroups = describeSecurityGroupsResult.getSecurityGroups();

        for(int i = 0; i<securityGroups.size();i++) {
            System.out.println("Adding Security Groups for : "+regionName);
            NetworkTree node= new Node();
           // node.setName(securityGroups.get(i).getIpPermissions());
            node.setUUID(securityGroups.get(i).getVpcId());
            node.setInformation("{Security Group Information : " + securityGroups.get(i).toString() + " }");
            node.setLevel(3);
            node.addRelationship(regionName);
            buffer.addToBuffer(node);

        }

    }

    public void run() {
        System.out.println("Starting SecurityGroup thread for : "+regionName);
        if(uuid.equals("")) {
            scanContext();
        }
        else
        {
            scanOnly();
        }

    }
}
