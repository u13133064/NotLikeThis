package Scanner;

import Buffer.SharedBuffer;
import Composite.NetworkTree;
import Composite.Node;
import Credentials.Credential;
import ParemeterBeans.OptionBean;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;
import com.amazonaws.services.ec2.model.Region;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by Jedd Shneier.
 */
public class AWSScanner implements ScannerInterface {
    private SharedBuffer buffer;
    private Credential clientCredentials;
    private OptionBean option;
    private AmazonEC2 ec2;
    private  AWSCredentials credentials;

    public AWSScanner(Credential clientCredentials, SharedBuffer sharedBuffer, OptionBean option)
    {
        this.clientCredentials=clientCredentials;
        this.option=option;
        this.buffer=sharedBuffer;
        System.out.println("Authorising credentials ");
        credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        ec2 = new AmazonEC2Client(credentials);
    }
    public void scanFullNetwork() {

        System.out.println("Scanning Regions ");

        DescribeRegionsResult regionsResult= ec2.describeRegions();
        List<Region> regions= regionsResult.getRegions();

        for(int i =0;i<regions.size();i++)
        {
            System.out.println("Setting region: "+regions.get(i).getRegionName());  NetworkTree tree = new Node();
            tree.setUUID(regions.get(i).getRegionName());
            tree.setName(regions.get(i).getRegionName());
            tree.setInformation("Region Information : "+regions.get(i).toString());
            tree.setLevel(2);
            tree.addRelationship("RootAWS");
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
            buffer.addToBuffer(tree);
            AmazonEC2 threadEc2 = new  AmazonEC2Client(credentials);
            threadEc2.setRegion(RegionUtils.getRegion(regions.get(i).getRegionName()));

            //launch a Vpc scanner
            new Thread(new VpcScannerThread(regions.get(i).getRegionName(),threadEc2,buffer)).start();
            //launch a subnetScanner
            new Thread(new SubNetworkScannerThread(regions.get(i).getRegionName(),threadEc2,buffer)).start();
            //launch a instance scanner
            new Thread(new InstanceScannerThread(regions.get(i).getRegionName(),threadEc2,buffer)).start();


        }

    }

    public void scanRegion(String region) {

        System.out.println("Setting region: "+region);
        NetworkTree tree = new Node();
        tree.setUUID(region);
        tree.setName(region);
        tree.setInformation("Region Information : "+RegionUtils.getRegion(region).toString());
        tree.setLevel(2);
        tree.addRelationship("RootAWS");
        buffer.addToBuffer(tree);
        AmazonEC2 threadEc2 = new  AmazonEC2Client(credentials);
        threadEc2.setRegion(RegionUtils.getRegion(region));

        //launch a Vpc scanner
        new Thread(new VpcScannerThread(region,threadEc2,buffer)).start();
        //launch a subnetScanner
        new Thread(new SubNetworkScannerThread(region,threadEc2,buffer)).start();
        //launch a instance scanner
        new Thread(new InstanceScannerThread(region,threadEc2,buffer)).start();


    }

    public void scanNetworkFrom(String level, String identifier) {
        if (level.equals("Vpc"))
        {
            scanVpcs(identifier);
        }
        else if (level.equals("Subnet"))
        {
            scanSubnetworks(identifier);
        }
        else if (level.equals("Instance"))
        {
            scanInstances(identifier);
        }
        else if(level.equals("Region"))
        {
            scanRegion(identifier);
        }
        else
        {
            scanFullNetwork();
        }
    }

    private void scanInstances(String identifier) {
        //Look through each region for instance
        System.out.println("Scanning Regions ");
        buffer.removeRoot();
        DescribeRegionsResult regionsResult= ec2.describeRegions();
        List<Region> regions= regionsResult.getRegions();

        for(int i =0;i<regions.size();i++)
        {
            AmazonEC2 threadEc2 = new  AmazonEC2Client(credentials);
            threadEc2.setRegion(RegionUtils.getRegion(regions.get(i).getRegionName()));

            //launch a Vpc scanner
            new Thread(new InstanceScannerThread(regions.get(i).getRegionName(),identifier,"",threadEc2,buffer)).start();
            //delegate scan to the thread if it finds the id

        }

    }

    private void scanSubnetworks(String identifier) {
        //Look through each region for subnet
        System.out.println("Scanning Regions ");
        buffer.removeRoot();
        DescribeRegionsResult regionsResult= ec2.describeRegions();
        List<Region> regions= regionsResult.getRegions();

        for(int i =0;i<regions.size();i++)
        {
            AmazonEC2 threadEc2 = new  AmazonEC2Client(credentials);
            threadEc2.setRegion(RegionUtils.getRegion(regions.get(i).getRegionName()));

            //launch a subnet scanner
            new Thread(new SubNetworkScannerThread(regions.get(i).getRegionName(),identifier,"",threadEc2,buffer)).start();
            //delegate scan to the thread if it finds the id

        }



    }

    private void scanVpcs(String identifier) {
        //Look through each region for vpc
        System.out.println("Scanning Regions ");
        buffer.removeRoot();

        DescribeRegionsResult regionsResult= ec2.describeRegions();
        List<Region> regions= regionsResult.getRegions();

        for(int i =0;i<regions.size();i++)
        {
            AmazonEC2 threadEc2 = new  AmazonEC2Client(credentials);
            threadEc2.setRegion(RegionUtils.getRegion(regions.get(i).getRegionName()));

            //launch a Vpc scanner
            new Thread(new VpcScannerThread(regions.get(i).getRegionName(),identifier,threadEc2,buffer)).start();
            //delegate scan to the thread if it finds the id

        }



    }


    public NetworkTree resumeScan(LinkedList<String> tokens, Credential credentials) {
        return null;
    }

    public LinkedList<String> pauseScan() {
        return null;
    }

    public void stopScan() {

    }

    public void run() {
        switch (option.getScannChoice())
        {
            case 1:
                scanFullNetwork();
                break;
            case 2:
                scanRegion(option.getIdentifier());
                break;
            case 3:
                scanNetworkFrom(option.getLevel(),option.getIdentifier());
                break;
            case 4:

            break;

            default:;
        }
    }



}
