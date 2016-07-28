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
		NetworkDetails networkDetails = new NetworkDetails();
		networkDetails.setUserName("Jedd");
		network.setNetworkDetails(networkDetails);
		addRegions(network);
		for(int i = 0;i<network.getAWSRegionList().size();i++)
		{
			addVPC(network.getAWSRegionList().get(i));
			addAvZone(network.getAWSRegionList().get(i).getVpcList(),network.getAWSRegionList().get(i));
			for(int j =0; j<network.getAWSRegionList().get(i).getVpcList().size();j++)
			{
				addSubnets(network.getAWSRegionList().get(i).getVpcList().get(j),network.getAWSRegionList().get(i));
				for(int k =0; k<network.getAWSRegionList().get(i).getVpcList().get(j).getAVZoneList().size();k++)
				{
					
					for(int l =0; l<network.getAWSRegionList().get(i).getVpcList().get(j).getAVZoneList().get(k).getSubNetworkList().size();l++)
					{
						
						addInstances(network.getAWSRegionList().get(i).getVpcList().get(j).getAVZoneList().get(k).getSubNetworkList().get(l),network.getAWSRegionList().get(i));
						
					}
					
				}
				
			}
		
			
			
		}
		return network;
	}

	private void addInstances(SubNetwork subNetwork, AWSRegion awsRegion) {
		
		ResponseObject instances =adapter.getInstsances(credentials,com.amazonaws.regions.Regions.fromName(awsRegion.getAwsRegionDetails().getAwsRegionName()));
		
		String[] instanceList = instances.toString().split(",");
	
		for(int i = 0;i<instanceList.length;i++)
		{
			String[]instanceParts =  instanceList[i].toString().split(":");
			String instanceID = instanceParts[0];
			String subnetID = instanceParts[1];
		
				if(subNetwork.getSubNetworkName().equals(subnetID))
				{
					LinkedList<AWSInstance> awsinstances = subNetwork.getAWSInstanceList();
					if(awsinstances==null)
					{
						awsinstances=new LinkedList<AWSInstance>();
					}
					AWSInstance newInstance = new AWSInstance();
					AWSInstanceDetails newDetails = new AWSInstanceDetails();
					newDetails.setInstanceName(instanceID);
					newInstance.setAwsInstanceDetails(newDetails);
					awsinstances.add(newInstance);
					subNetwork.setAWSInstanceList(awsinstances);
					
				
			}
		}
			
	
	}

	private void addSubnets(VPC vpc, AWSRegion awsRegion) {
		ResponseObject subnets = adapter.getSubnets(credentials,com.amazonaws.regions.Regions.fromName(awsRegion.getAwsRegionDetails().getAwsRegionName()));
		String[]subnetList =  subnets.toString().split(",");
		for(int i =0; i<subnetList.length;i++)
		{
			
			String[]subnetParts =  subnetList[i].toString().split(":");
			String subnetID = subnetParts[0];
			String avZone = subnetParts[1];
			String vpcID = subnetParts[2];
			for(int j =0; j<vpc.getAVZoneList().size();j++)
			{
				
				if(vpc.getVpcDetails().getVpcName().equals(vpcID))
				{
					if(vpc.getAVZoneList().get(j).getAvZoneDetails().getAvZoneName().equals(avZone))
					{
						SubNetwork newSubnetwork = new SubNetwork();
						SubNetworkDetails newSubnetworkDetails = new SubNetworkDetails();
						newSubnetworkDetails.setSubNetworkName(subnetID);
						newSubnetwork.setSubnetworkDetails(newSubnetworkDetails);
						LinkedList<SubNetwork> subnetworkList = vpc.getAVZoneList().get(j).getSubNetworkList();
						if(subnetworkList==null)
						{
							subnetworkList=new LinkedList<SubNetwork>();
						}
						
						subnetworkList.add(newSubnetwork);
						vpc.getAVZoneList().get(j).setSubNetworkList(subnetworkList);
						
						
						
			
					}
				}
			}
		}
		
		
	}

	private void addAvZone(LinkedList<VPC> vpcList, AWSRegion awsRegion) {
		ResponseObject subnets = adapter.getSubnets(credentials,com.amazonaws.regions.Regions.fromName(awsRegion.getAwsRegionDetails().getAwsRegionName()));
		String[]subnetList =  subnets.toString().split(",");
		for(int i =0; i<subnetList.length;i++)
		{
			String[]subnetParts =  subnetList[i].toString().split(":");
			String subnetID = subnetParts[0];
			String avZone = subnetParts[1];
			String vpcID = subnetParts[2];
			for(int j =0;j<vpcList.size();j++)
			{
				if(vpcList.get(j).getVpcDetails().getVpcName().equals(vpcID))
				{
					LinkedList<AVZone> avList = vpcList.get(j).getAVZoneList();
					if(avList==null)
					{
						avList=new LinkedList<AVZone>();
					}
					AVZone newZone = new AVZone();
					AVZoneDetails newDetails = new AVZoneDetails();
					newDetails.setAvZoneName(avZone);
					newZone.setAvZoneDetails(newDetails);
					avList.add(newZone);
					vpcList.get(j).setAVZoneList(avList);
					
				
				}
			}
		}
		
	}

	private void addVPC(AWSRegion awsRegion) {
		ResponseObject vpcs = adapter.getVPC(credentials,com.amazonaws.regions.Regions.fromName(awsRegion.getAwsRegionDetails().getAwsRegionName()));
		String[]vpcList =  vpcs.toString().split(",");
		LinkedList<VPC> awsVPCList = new LinkedList<>();
		for(int i =0;i<vpcList.length;i++ )
		{
			VPC newVPC =new VPC();
			VPCDetails newVPCDetails = new VPCDetails();
			newVPCDetails.setVpcName(vpcList[i]);
			newVPCDetails.setAvZoneCount(0);
			newVPC.setVpcDetails(newVPCDetails);
			awsVPCList.add(newVPC);
			
		}
		awsRegion.setVpcList(awsVPCList);
		
		
		
		
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
