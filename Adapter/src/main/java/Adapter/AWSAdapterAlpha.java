package Adapter;

import Credentials.Credential;
import ResponeObjects.AvailabilityZonesResponse;
import ResponeObjects.ResponseObject;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.*;

import java.util.List;

/**
 * Created by User on 2016/07/04.
 */
public class AWSAdapterAlpha implements Adapter{

    @Override
    public ResponseObject getAvailabilityZones(Credential clientCredentials) {
        AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);

        DescribeAvailabilityZonesResult availabilityZonesResult=ec2.describeAvailabilityZones();
        List<AvailabilityZone> availabilityZones=availabilityZonesResult.getAvailabilityZones();

        AvailabilityZonesResponse availabilityZonesResponse = new AvailabilityZonesResponse();
        availabilityZonesResponse.setAvailabilityZones(availabilityZones);

        return availabilityZonesResponse;
    }

    @Override
    public ResponseObject getClassicLinks() {
        return null;
    }

    @Override
    public ResponseObject getDHCP() {
        return null;
    }

    @Override
    public ResponseObject getGateways() {
        return null;
    }

    @Override
    public ResponseObject getInstsances() {
        return null;
    }

    @Override
    public ResponseObject getInternalGateways() {
        return null;
    }

    @Override
    public ResponseObject getNAT() {
        return null;
    }

    @Override
    public ResponseObject getNetworkinterfaces() {
        return null;
    }

    @Override
    public ResponseObject getPlacementGroups() {
        return null;
    }

    @Override
    public ResponseObject getRegions() {
        return null;
    }

    @Override
    public ResponseObject getRouteTable() {
        return null;
    }

    @Override
    public ResponseObject getVPC() {
        return null;
    }

    @Override
    public ResponseObject getVPNGateWays() {
        return null;
    }
}
