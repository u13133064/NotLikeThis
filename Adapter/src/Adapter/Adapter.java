package Adapter;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.model.Region;

import Credentials.Credential;
import ResponeObjects.ResponseObject;

/**
 * Created by User on 2016/07/04.
 */

public interface Adapter {
    public ResponseObject getAvailabilityZones(Credential clientCredentials);
    public ResponseObject getClassicLinks(Credential clientCredentials);
    public ResponseObject getDHCP(Credential clientCredentials);
    public ResponseObject getGateways(Credential clientCredentials);
    public ResponseObject getInstsances(Credential clientCredentials,Regions region);
    public ResponseObject getInternalGateways(Credential clientCredentials);
    public ResponseObject getNAT(Credential clientCredentials);
    public ResponseObject getNetworkInterfaces(Credential clientCredentials);
    public ResponseObject getPlacementGroups(Credential clientCredentials);
    public ResponseObject getRegions(Credential clientCredentials);
    public ResponseObject getRouteTable(Credential clientCredentials);
    public ResponseObject getVPC(Credential clientCredentials);
    public ResponseObject getVPNGateWays(Credential clientCredentials);


}
