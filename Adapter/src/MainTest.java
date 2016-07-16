

import com.amazonaws.regions.Regions;

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


        System.out.println(awsAdapterAlpha.getInstsances(credential,Regions.US_WEST_2));



    }









}
