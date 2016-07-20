package Adapter;

import Credentials.Credential;
import ResponeObjects.*;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
        AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);

        DescribeClassicLinkInstancesResult classicLinkInstancesResult = ec2.describeClassicLinkInstances();
        List<ClassicLinkInstance> classicLinkInstances = classicLinkInstancesResult.getInstances();

        ClassicLinksResponse classicLinksResponse =new ClassicLinksResponse();
        classicLinksResponse.setClassicLinkInstances(classicLinkInstances);

        return classicLinksResponse;


    }

    @Override
    public ResponseObject getDHCP(Credential clientCredentials) {
        AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);

        DescribeDhcpOptionsResult dhcpOptionsResult = ec2.describeDhcpOptions();
        List<DhcpOptions> dhcpOptionses = dhcpOptionsResult.getDhcpOptions();

        DhcpOptionsResponse dhcpOptionsResponse = new DhcpOptionsResponse();
        dhcpOptionsResponse.setDhcpOptionses(dhcpOptionses);

        return dhcpOptionsResponse;

    }

    @Override
    public ResponseObject getGateways(Credential clientCredentials) {
        return null;
    }

    @Override
    public ResponseObject getInstsances(Credential clientCredentials,Regions region) {
        AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);
        ec2.setRegion(com.amazonaws.regions.Region.getRegion(region));

        DescribeInstancesResult instancesResult =ec2.describeInstances();
        List<Reservation> reservations = instancesResult.getReservations();
        InstancesResponse instancesResponse = new InstancesResponse();
        List<Instance> instancesCollection = new LinkedList<Instance>();
        



        for(int i =0;i<reservations.size();i++)
        {
            List<Instance> instances= reservations.get(i).getInstances();
            for(int j =0;j<instances.size();j++)
            {
                 instancesCollection.add(instances.get(j));

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
    public ResponseObject getNetworkInterfaces(Credential clientCredentials) {
        AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);

        DescribeNetworkAclsResult networkAclsResult = ec2.describeNetworkAcls();
        List<NetworkAcl> networkAcls=networkAclsResult.getNetworkAcls();

        NetworkInterfacesResponse networkInterfacesResponse= new NetworkInterfacesResponse();
        networkInterfacesResponse.setNetworkAcls(networkAcls);
        return networkInterfacesResponse;
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
    public ResponseObject getVPC(Credential clientCredentials,Regions region) {
    	AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);
        ec2.setRegion(com.amazonaws.regions.Region.getRegion(region));
        
        
     
        DescribeVpcsResult describeVpcsResult = ec2.describeVpcs();
        List<Vpc>vpcs=describeVpcsResult.getVpcs();
        VPCResponse vpcResponse = new VPCResponse();
        vpcResponse.setVpcs(vpcs);
        return vpcResponse;
    }

    @Override
    public ResponseObject getVPNGateWays(Credential clientCredentials) {
        return null;
    }

	@Override
	public ResponseObject getSubnets(Credential clientCredentials) {
		AWSCredentials credentials = new BasicAWSCredentials(clientCredentials.getAccess_key(),clientCredentials.getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);
        
        DescribeSubnetsResult describeSubnetsResult = ec2.describeSubnets();
        List<Subnet>subnets=describeSubnetsResult.getSubnets();
        
        SubnetResponse subnetresponse = new SubnetResponse();
        
        subnetresponse.setSubnets(subnets);
        
        

        return subnetresponse;
        
		

	}


}
