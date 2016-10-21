package Credentials;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesRequest;

/**
 * Created by Jedd Shneier.
 */
public class Credential {
    private String access_key;
    private String private_key;
    private boolean validated=false;
    public boolean isValid()
    {
        return validated;
    }
    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {

        this.private_key = private_key;
        if(this.private_key.contains(" "))
        {
            this.private_key= this.private_key.replaceAll(" ","+");
        }
    }

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
        if(this.access_key.contains(" "))
        {
            this.access_key=this.access_key.replaceAll(" ","+");
        }
    }

    public void validate() {
        AWSCredentials credentials = new BasicAWSCredentials(getAccess_key(),getPrivate_key());
        AmazonEC2 ec2 = new AmazonEC2Client(credentials);
        try{
            ec2.dryRun(new DescribeAvailabilityZonesRequest());
            validated=true;
        }
        catch (Exception e)
        {
            validated=false;
            System.out.println(access_key);
            System.out.println(private_key);
            e.printStackTrace();

        }

    }
}
