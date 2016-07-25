

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.UUID;

import com.amazonaws.regions.Regions;

import Adapter.AWSAdapterAlpha;
import Credentials.Credential;
import Messenger.AWSRegion;
import Messenger.AWSRegionDetails;
import Messenger.Network;
import Messenger.NetworkDetails;
import Visualizer.MultiThreadServer;
import Visualizer.Visualizer;

/**
 * Created by User on 2016/07/12.
 */
public class MainTest{


    public static void main(String[] args) throws IOException 
    {
        //-----------Dummy network one-----------
        Messenger.NetworkDetails nd = new NetworkDetails();
        nd.setUserName("A 1");
        Messenger.Network networkOne = new Network();
        networkOne.setNetworkDetails(nd);
        //---------------------------------------
        
        //-----------Dummy network two-----------
        Messenger.NetworkDetails nd2 = new NetworkDetails();
        nd2.setUserName("A 2");
        Messenger.Network networkTwo = new Network();
        networkTwo.setNetworkDetails(nd2);
        //---------------------------------------
        
        //----------Dummy AWSRegion One-----------
        Messenger.AWSRegionDetails rd = new Messenger.AWSRegionDetails();
        rd.setAwsRegionName("B 1");
        Messenger.AWSRegion regionOne = new Messenger.AWSRegion();
        regionOne.setAwsRegionDetails(rd);
        //---------------------------------------
        
        //----------Dummy AWSRegion two-----------
        Messenger.AWSRegionDetails rd2 = new Messenger.AWSRegionDetails();
        rd2.setAwsRegionName("B 2");
        Messenger.AWSRegion regionTwo = new Messenger.AWSRegion();
        regionTwo.setAwsRegionDetails(rd2);
        //---------------------------------------
        
        //----------Dummy AWSRegion three-----------
        Messenger.AWSRegionDetails rd3 = new Messenger.AWSRegionDetails();
        rd3.setAwsRegionName("B 3");
        Messenger.AWSRegion regionThree = new Messenger.AWSRegion();
        regionThree.setAwsRegionDetails(rd3);
        //---------------------------------------
        
        //----------Dummy AWSRegion four-----------
        Messenger.AWSRegionDetails rd4 = new Messenger.AWSRegionDetails();
        rd4.setAwsRegionName("B 4");
        Messenger.AWSRegion regionFour = new Messenger.AWSRegion();
        regionFour.setAwsRegionDetails(rd4);
        //---------------------------------------
        
        //----------VPC one-----------
        Messenger.VPCDetails vpc1 = new Messenger.VPCDetails();
        vpc1.setVpcName("C 1");
        Messenger.VPC VPCOne = new Messenger.VPC();
        VPCOne.setVpcDetails(vpc1);
        //---------------------------------------
        
        //----------VPC two-----------
        Messenger.VPCDetails vpc2 = new Messenger.VPCDetails();
        vpc2.setVpcName("C 2");
        Messenger.VPC VPCTwo = new Messenger.VPC();
        VPCTwo.setVpcDetails(vpc2);
        //---------------------------------------
        
        //----------VPC three-----------
        Messenger.VPCDetails vpc3 = new Messenger.VPCDetails();
        vpc3.setVpcName("C 3");
        Messenger.VPC VPCThree = new Messenger.VPC();
        VPCThree.setVpcDetails(vpc3);
        //---------------------------------------
        
        //----------VPC four-----------
        Messenger.VPCDetails vpc4 = new Messenger.VPCDetails();
        vpc4.setVpcName("C 4");
        Messenger.VPC VPCFour = new Messenger.VPC();
        VPCFour.setVpcDetails(vpc4);
        //---------------------------------------
        
        //----------VPC five-----------
        Messenger.VPCDetails vpc5 = new Messenger.VPCDetails();
        vpc5.setVpcName("C 5");
        Messenger.VPC VPCFive = new Messenger.VPC();
        VPCFive.setVpcDetails(vpc5);
        //---------------------------------------
        
        //----------VPC six-----------
        Messenger.VPCDetails vpc6 = new Messenger.VPCDetails();
        vpc6.setVpcName("C 6");
        Messenger.VPC VPCSix = new Messenger.VPC();
        VPCSix.setVpcDetails(vpc6);
        //---------------------------------------
        
        //----------VPC seven-----------
        Messenger.VPCDetails vpc7 = new Messenger.VPCDetails();
        vpc7.setVpcName("C 7");
        Messenger.VPC VPCSeven = new Messenger.VPC();
        VPCSeven.setVpcDetails(vpc7);
        //--------------------------------------- 
        
        //----------VPC eight-----------
        Messenger.VPCDetails vpc8 = new Messenger.VPCDetails();
        vpc8.setVpcName("C 8");
        Messenger.VPC VPCEight = new Messenger.VPC();
        VPCEight.setVpcDetails(vpc8);
        //--------------------------------------- 
        
        
        //----------AVZONE-----------
        Messenger.AVZoneDetails avz = new Messenger.AVZoneDetails();
        avz.setAvZoneName("D 1");
        Messenger.AVZone AVZone = new Messenger.AVZone();
        AVZone.setAvZoneDetails(avz);
        //--------------------------------------- 
        
        //------SubNetworks----------
        Messenger.SubNetworkDetails sub1 = new Messenger.SubNetworkDetails();
        sub1.setSubNetworkName("E 1");
        Messenger.SubNetwork subNetworkOne = new Messenger.SubNetwork();
        subNetworkOne.setSubnetworkDetails(sub1);
        
        Messenger.SubNetworkDetails sub2 = new Messenger.SubNetworkDetails();
        sub2.setSubNetworkName("F 1");
        Messenger.SubNetwork subNetworkTwo = new Messenger.SubNetwork();
        subNetworkTwo.setSubnetworkDetails(sub2);
        
        Messenger.SubNetworkDetails sub3 = new Messenger.SubNetworkDetails();
        sub3.setSubNetworkName("F 2");
        Messenger.SubNetwork subNetworkThree = new Messenger.SubNetwork();
        subNetworkThree.setSubnetworkDetails(sub3);
       
        Messenger.SubNetworkDetails sub4 = new Messenger.SubNetworkDetails();
        sub4.setSubNetworkName("G 1");
        Messenger.SubNetwork subNetworkFour = new Messenger.SubNetwork();
        subNetworkFour.setSubnetworkDetails(sub4);
      //---------------------------------------
        
      //------Instance----------
        Messenger.AWSInstanceDetails i1 = new Messenger.AWSInstanceDetails();
        i1.setInstanceName("i E 2");
        Messenger.AWSInstance instanceOne = new Messenger.AWSInstance();
        instanceOne.setAwsInstanceDetails(i1);
        
        Messenger.AWSInstanceDetails i2 = new Messenger.AWSInstanceDetails();
        i2.setInstanceName("i F 3");
        Messenger.AWSInstance instanceTwo = new Messenger.AWSInstance();
        instanceTwo.setAwsInstanceDetails(i2);
        
        Messenger.AWSInstanceDetails i3 = new Messenger.AWSInstanceDetails();
        i3.setInstanceName("i G 1");
        Messenger.AWSInstance instanceThree = new Messenger.AWSInstance();
        instanceThree.setAwsInstanceDetails(i3);
      //---------------------------------------
        
        
        
        //---------------------------
        
        LinkedList<Messenger.SubNetwork> SubNetworkList = new LinkedList <Messenger.SubNetwork>();
        SubNetworkList.add(subNetworkOne);
        AVZone.setSubNetworkList(SubNetworkList);
        
        LinkedList<Messenger.SubNetwork> SubNetworkList2 = new LinkedList <Messenger.SubNetwork>();
        SubNetworkList2.add(subNetworkTwo);
        SubNetworkList2.add(subNetworkThree);
        subNetworkOne.setSubNetworkList(SubNetworkList2);
        
        LinkedList<Messenger.SubNetwork> SubNetworkList3 = new LinkedList <Messenger.SubNetwork>();
        SubNetworkList3.add(subNetworkFour);
        subNetworkThree.setSubNetworkList(SubNetworkList3);
        
        
        //------set instance
        LinkedList<Messenger.AWSInstance>  InstanceListOne = new LinkedList <Messenger.AWSInstance>();
        InstanceListOne.add(instanceOne);
        subNetworkOne.setAWSInstanceList(InstanceListOne);
        
        LinkedList<Messenger.AWSInstance>  InstanceListTwo = new LinkedList <Messenger.AWSInstance>();
        InstanceListTwo.add(instanceTwo);
        subNetworkTwo.setAWSInstanceList(InstanceListTwo);
        
        //---------------------------------------
        LinkedList<AWSRegion> awsRegionListOne = new LinkedList <AWSRegion>();
        awsRegionListOne.add(regionOne);
        awsRegionListOne.add(regionTwo);
        
        LinkedList<AWSRegion> awsRegionListTwo = new LinkedList <AWSRegion>();
        awsRegionListTwo.add(regionThree);
        awsRegionListTwo.add(regionFour);   
        
        LinkedList<Messenger.VPC> VPCListOne = new LinkedList <Messenger.VPC>();
        VPCListOne.add(VPCOne);
        VPCListOne.add(VPCTwo);
        
        LinkedList<Messenger.VPC> VPCListTwo = new LinkedList <Messenger.VPC>();
        VPCListTwo.add(VPCThree);
        VPCListTwo.add(VPCFour);
        
        LinkedList<Messenger.VPC> VPCListThree = new LinkedList <Messenger.VPC>();
        VPCListThree.add(VPCFive);
        VPCListThree.add(VPCSix);
        
        LinkedList<Messenger.VPC> VPCListFour = new LinkedList <Messenger.VPC>();
        VPCListFour.add(VPCSeven);
        VPCListFour.add(VPCEight);
        
        LinkedList<Messenger.AVZone> AVZoneList = new LinkedList <Messenger.AVZone>();
        AVZoneList.add(AVZone);
        VPCOne.setAVZoneList(AVZoneList);
        
        networkOne.setAWSRegionList(awsRegionListOne);
        networkTwo.setAWSRegionList(awsRegionListTwo);
        
        regionOne.setVpcList(VPCListOne);
        regionTwo.setVpcList(VPCListTwo);
        regionThree.setVpcList(VPCListThree);
        regionFour.setVpcList(VPCListFour);
        
        //---------------------------------------
        
        Visualizer visualizer = new Visualizer();
        visualizer.addNetwork(networkOne);
        visualizer.addNetwork(networkTwo);

        System.out.println(visualizer.generateHTML());
        visualizer.generateHTMLDocument();
   
        }

    }










