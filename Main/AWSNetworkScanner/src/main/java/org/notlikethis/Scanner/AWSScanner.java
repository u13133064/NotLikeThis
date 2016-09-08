package org.notlikethis.Scanner;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;
import com.amazonaws.services.ec2.model.Region;
import org.notlikethis.Buffer.SharedBuffer;
import org.notlikethis.Composite.NetworkTree;
import org.notlikethis.Credentials.Credential;

import java.util.LinkedList;
import java.util.List;
/**
 * Created by Jedd Shneier.
 */
public class AWSScanner implements ScannerInterface {
    public void scanFullNetwork(Credential clientCredentials,SharedBuffer buffer) {


        AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);

        DescribeRegionsResult regionsResult= ec2.describeRegions();
        List<Region> regions= regionsResult.getRegions();

        for(int i =0;i<regions.size();i++)
        {
            new RegionScannerThread(clientCredentials,regions.get(i).getRegionName(),buffer).run();

        }

    }

    public NetworkTree scanNetworkFrom(String identifier, Credential credentials) {
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
}
