package Scanner;

import Buffer.SharedBuffer;
import Composite.NetworkTree;
import Composite.Node;
import Credentials.Credential;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;
import com.amazonaws.services.ec2.model.Region;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Jedd Shneier.
 */
public class AWSScanner implements ScannerInterface {
    SharedBuffer buffer;
    Credential clientCredentials;
    int option;
    private AmazonEC2 ec2;

    public AWSScanner(Credential clientCredentials, SharedBuffer sharedBuffer, int option)
    {
        this.clientCredentials=clientCredentials;
        this.option=option;
        buffer=sharedBuffer;
        System.out.println("Authorising credentials ");
        AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        ec2 = new AmazonEC2Client(credentials);
    }
    public void scanFullNetwork() {

        System.out.println("Scanning Regions ");

        DescribeRegionsResult regionsResult= ec2.describeRegions();
        List<Region> regions= regionsResult.getRegions();

        for(int i =0;i<regions.size();i++)
        {
            System.out.println("Setting region: "+regions.get(i).getRegionName());  NetworkTree tree = new Node();
            tree.setUUID(UUID.randomUUID().toString());
            tree.setName(regions.get(i).getRegionName());
            tree.setInformation("Region Information : "+regions.get(i).toString());
            buffer.addToBuffer(tree);
            ec2.setEndpoint(regions.get(i).getEndpoint());
            //launch a Vpc scanner
            new Thread(new VpcScannerThread(regions.get(i).getRegionName(),ec2,buffer)).start();
            //launch a subnetScanner
            new Thread(new SubNetworkScannerThread(regions.get(i).getRegionName(),ec2,buffer)).start();
            //launch a instance scanner
            new Thread(new InstanceScannerThread(regions.get(i).getRegionName(),ec2,buffer)).start();


        }

    }

    public NetworkTree scanNetworkFrom(String level,String identifier, Credential clientCredentials) {
        String region;
         if(level.equals("Vpc"))
         {

         }
        return null;
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
        switch (option)
        {
            case 1:
                scanFullNetwork();
            default:;
        }
    }
}