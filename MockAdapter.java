package Adapter;
import Credentials.Credential;
import ResponeObjects.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MockAdapter implements Adapter{
	public ResponseObject getAvailabilityZones(Credential clientCredentials){
		MockResponse availabilityZonesResponse = new MockResponse(9);
		return availabilityZonesResponse;
	}
    public ResponseObject getClassicLinks(Credential clientCredentials){
		MockResponse classicLinksResponse =new MockResponse(2);
		return classicLinksResponse;
	}
    public ResponseObject getDHCP(Credential clientCredentials){
		MockResponse dhcpOptionsResponse = new MockResponse(3);
		return dhcpOptionsResponse;
	}
    public ResponseObject getGateways(Credential clientCredentials){
		return null;
	}
    public ResponseObject getInstsances(Credential clientCredentials,Regions region){
		MockResponse instancesResponse = new MockResponse(6);
		return instancesResponse;
	}
    public ResponseObject getInternalGateways(Credential clientCredentials){
		MockResponse internetGatewaysResponse = new MockResponse(16);
		return internetGatewaysResponse;
	}
    public ResponseObject getNAT(Credential clientCredentials){
		return null;
	}
    public ResponseObject getNetworkInterfaces(Credential clientCredentials){
		MockResponse networkInterfacesResponse= new MockResponse(4);
		return networkInterfacesResponse;
	}
    public ResponseObject getPlacementGroups(Credential clientCredentials){
		MockResponse placementGroupResponse = new MockResponse(1);
		return placementGroupResponse;
	}
    public ResponseObject getRegions(Credential clientCredentials){
		MockResponse regionResponse= new MockResponse(13);
		return regionResponse;
	}
    public ResponseObject getRouteTable(Credential clientCredentials){
		MockResponse routeTableResponse = new MockResponse(7);
		return routeTableResponse;
	}
	public ResponseObject getSubnets(Credential clientCredentials,Regions region){
		MockResponse subnetresponse = new MockResponse(5);
		return subnetresponse;
	}
    public ResponseObject getVPC(Credential clientCredentials){
		return null;
	}
    public ResponseObject getVPNGateWays(Credential clientCredentials){
		return null;
	}
}