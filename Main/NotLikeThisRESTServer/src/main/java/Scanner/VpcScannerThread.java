package Scanner;

import Buffer.SharedBuffer;
import Composite.NetworkTree;
import Composite.Node;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeVpcsRequest;
import com.amazonaws.services.ec2.model.DescribeVpcsResult;
import com.amazonaws.services.ec2.model.Vpc;

import java.util.List;

/**
 * Created by Jedd Shneier.
 */
public class VpcScannerThread implements ThreadedScannerInterface{
    private String regionName;
    private  AmazonEC2 ec2;
    private SharedBuffer buffer;
    private String  uuid="";
    VpcScannerThread(String regionName, AmazonEC2 ec2,SharedBuffer buffer)
    {
        this.regionName=regionName;
        this.ec2=ec2;
        this.buffer=buffer;

    }

    public VpcScannerThread(String regionName, String identifier, AmazonEC2 threadEc2, SharedBuffer buffer) {

        this.regionName=regionName;
        this.ec2= threadEc2;
        this.buffer=buffer;
        this.uuid=identifier;
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
            buffer.addToBuffer(node);

        }

    }
    public void scanOnly()
    {
        DescribeVpcsRequest describeVpcsRequest = new DescribeVpcsRequest().withVpcIds(uuid);
        DescribeVpcsResult describeVpcsResult;
        try
        {
            describeVpcsResult = ec2.describeVpcs(describeVpcsRequest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
        List<Vpc> vpcs = describeVpcsResult.getVpcs();
        if (vpcs.size()>0)
        {
            //launch a subnetScanner
            new Thread(new SubNetworkScannerThread(this.regionName,uuid,"Vpc", this.ec2,buffer)).start();
            //launch a instance scanner
            new Thread(new InstanceScannerThread(this.regionName,uuid,"Vpc",this.ec2,buffer)).start();
        }
        for(int i = 0; i<vpcs.size();i++) {
            System.out.println("Found Vpc at : "+regionName);
            NetworkTree node= new Node();
            node.setName(vpcs.get(i).getVpcId());
            node.setUUID(vpcs.get(i).getVpcId());
            node.setInformation("{Vpc Information : " + vpcs.get(i).toString() + " }");
            node.setLevel(3);
            node.addRelationship(regionName);
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
            buffer.addToBuffer(node);

        }

    }

    public void run() {
        System.out.println("Starting VPC thread for : "+regionName);
        if(uuid.equals("")) {
            scanContext();

        }
        else
        {
            scanOnly();
        }
    }
}
