package Scanner;

import Messenger.*;
import ResponeObjects.ResponseObject;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import com.amazonaws.services.ec2.model.Region;

import Adapter.*;
import Credentials.Credential;
public class AWSNetworkScanner implements NetworkScanner{
	AWSAdapterAlpha adapter = new AWSAdapterAlpha();
	Credential credentials= new Credential();


	@Override
	public Network scanNetwork() {
		Network network = new Network();
		addRegions(network);
		for(int i = 0;i<network.getAWSRegionList().size();i++)
		{
			addVPC(network.getAWSRegionList().get(i));
		}
		return null;
	}

	private void addVPC(AWSRegion awsRegion) {
		ResponseObject vpcs = adapter.getVPC(credentials);
		System.out.println(vpcs.toString());
		
		
		
		
	}

	private void addRegions(Network network) {
		ResponseObject regions =adapter.getRegions(credentials);
		
		String[]regionList =  regions.toString().split(",");
		LinkedList<AWSRegion> awsRegionList = new LinkedList<>();
		for(int i =0;i<regionList.length;i++ )
		{
			AWSRegion newRegion =new AWSRegion();
			AWSRegionDetails newRegionDetails = new AWSRegionDetails();
			newRegionDetails.setAwsRegionName(regionList[i]);
			newRegionDetails.setVpcCount(0);
			newRegion.setAwsRegionDetails(newRegionDetails);
			awsRegionList.add(newRegion);
			
		}
		network.setAWSRegionList(awsRegionList);
		
	}

	@Override
	public void createCredentials(String access_key, String private_key) {
	
		credentials.setAccess_key(access_key);
		credentials.setPrivate_key(private_key);
		
	}
	

}
