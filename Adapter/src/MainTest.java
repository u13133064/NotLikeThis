

import com.amazonaws.regions.Regions;
import Messenger.*;
import Scanner.AWSNetworkScanner;
import Adapter.AWSAdapterAlpha;
import Credentials.Credential;

/**
 * Created by User on 2016/07/12.
 */
public class MainTest{


    public static void main(String[] args) {
        AWSAdapterAlpha awsAdapterAlpha = new AWSAdapterAlpha();
        Credential credential = new Credential();
        credential.setAccess_key("AKIAI7CWEMOYBL2FRGTA");
        credential.setPrivate_key("Fi4zVz2Ikk0WDwQ/CuKysn5XJLLaqoe8/RRAgBGe");
        AWSNetworkScanner ns = new AWSNetworkScanner();
        
        ns.createCredentials("AKIAI7CWEMOYBL2FRGTA", "Fi4zVz2Ikk0WDwQ/CuKysn5XJLLaqoe8/RRAgBGe");
        ns.scanNetwork();





    }









}
