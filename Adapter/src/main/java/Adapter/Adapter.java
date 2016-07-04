package Adapter;

import ResponeObjects.ResponseObject;

/**
 * Created by User on 2016/07/04.
 */
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
public interface Adapter {

    public ResponseObject getAvailibilityZones();
    public ResponseObject getClassicLinks();
    public ResponseObject getDHCP();
    public ResponseObject getGateways();
    public ResponseObject getInstsances();
    public ResponseObject getInternalGateways();
    public ResponseObject getNAT();
    public ResponseObject getNetworkinterfaces();
    public ResponseObject getPlacementGroups();
    public ResponseObject getRegions();
    public ResponseObject getRouteTable();
    public ResponseObject getVPC();
    public ResponseObject getVPNGateWays();


}
