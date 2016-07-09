package Adapter;

import Credentials.Credential;
import ResponeObjects.*;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.*;

import java.util.LinkedList;
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
    public ResponseObject getClassicLinks(Credential clientCredentials) {
        return null;
    }

    @Override
    public ResponseObject getDHCP(Credential clientCredentials) {
        return null;
    }

    @Override
    public ResponseObject getGateways(Credential clientCredentials) {
        return null;
    }

    @Override
    public ResponseObject getInstsances(Credential clientCredentials) {
        AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);

        DescribeInstancesResult instancesResult =ec2.describeInstances();
        List<Reservation> reservations = instancesResult.getReservations();
        InstancesResponse instancesResponse = new InstancesResponse();
        List<Instance> instancesCollection = new LinkedList<Instance>();



        for(int i =0;i<reservations.size();i++)
        {
            List<Instance> instances= reservations.get(i).getInstances();
            for(int j =0;j<instances.size();j++)
            {
                 instancesCollection.add(instances.get(i));

            }

        }
        instancesResponse.setInstances(instancesCollection);



        return instancesResponse;
    }

    @Override
    public ResponseObject getInternalGateways(Credential clientCredentials) {
        AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);

        DescribeInternetGatewaysResult internetGatewaysResult=ec2.describeInternetGateways();
        List<InternetGateway> internetGateways=internetGatewaysResult.getInternetGateways();

        InternetGatewaysResponse internetGatewaysResponse = new InternetGatewaysResponse();
        internetGatewaysResponse.setInternetGateways(internetGateways);


        return internetGatewaysResponse;
    }

    @Override
    public ResponseObject getNAT(Credential clientCredentials) {
        return null;
    }

    @Override
    public ResponseObject getNetworkinterfaces(Credential clientCredentials) {
        return null;
    }

    @Override
    public ResponseObject getPlacementGroups(Credential clientCredentials) {
        AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);

        DescribePlacementGroupsResult placementGroupsResult = ec2.describePlacementGroups();
        List<PlacementGroup> placementGroups= placementGroupsResult.getPlacementGroups();

        PlacementGroupResponse placementGroupResponse = new PlacementGroupResponse();
        placementGroupResponse.setPlacementGroups(placementGroups);

        return placementGroupResponse;
    }

    @Override
    public ResponseObject getRegions(Credential clientCredentials) {
        AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);

        DescribeRegionsResult regionsResult= ec2.describeRegions();
        List<Region> regions= regionsResult.getRegions();


        RegionResponse regionResponse= new RegionResponse();
        regionResponse.setRegions(regions);

        return regionResponse;
    }

    @Override
    public ResponseObject getRouteTable(Credential clientCredentials) {
        AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);

        DescribeRouteTablesResult routeTableResult= ec2.describeRouteTables();
        List<RouteTable> routeTables= routeTableResult.getRouteTables();


        RouteTableResponse routeTableResponse = new RouteTableResponse();
        routeTableResponse.setRouteTables(routeTables);

        return routeTableResponse;

    }

    @Override
    public ResponseObject getVPC(Credential clientCredentials) {
        return null;
    }

    @Override
    public ResponseObject getVPNGateWays(Credential clientCredentials) {
        return null;
    }


}
