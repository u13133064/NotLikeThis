package org.notlikethis.Scanner;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import org.notlikethis.Composite.NetworkTree;
import org.notlikethis.Credentials.Credential;

import java.util.List;

/**
 * Created by Jedd Shneier
 */
public class RegionScannerThread implements Runnable {
    private Credential clientCredentials;
    private String regionName;
    public RegionScannerThread(Credential credentials,String regionName)
    {
        this.clientCredentials=credentials;
        this.regionName=regionName;
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
                }

            }
            nextToken = instancesResult.getNextToken();

        } while (nextToken.length()>0);







    }

    private NetworkTree convertInstanceToTree(Instance instance) {
        instance.
        return;
    }
}
