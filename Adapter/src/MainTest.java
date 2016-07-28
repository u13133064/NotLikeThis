

import java.io.FileNotFoundException;


import java.io.UnsupportedEncodingException;

import com.amazonaws.regions.Regions;
import Messenger.*;
import Scanner.AWSNetworkScanner;
import Visualizer.Visualizer;
import Adapter.AWSAdapterAlpha;
import Credentials.Credential;

/**
 * Created by User on 2016/07/12.
 */
public class MainTest{


    public static void main(String[] args) {
        AWSAdapterAlpha awsAdapterAlpha = new AWSAdapterAlpha();
        System.out.println("Testing");
        Credential credential = new Credential();
        credential.setAccess_key("AKIAI7CWEMOYBL2FRGTA");
        credential.setPrivate_key("Fi4zVz2Ikk0WDwQ/CuKysn5XJLLaqoe8/RRAgBGe");
        AWSNetworkScanner ns = new AWSNetworkScanner();
        Visualizer visualizer = new Visualizer();
        
        ns.createCredentials("AKIAI7CWEMOYBL2FRGTA", "Fi4zVz2Ikk0WDwQ/CuKysn5XJLLaqoe8/RRAgBGe");
        visualizer.addNetwork(ns.scanNetwork());
        try {
			visualizer.generateHTMLDocument();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        





    }









}
