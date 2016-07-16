package ResponeObjects;

import java.util.List;

import com.amazonaws.services.ec2.model.*;
/**
 * Created by User on 2016/07/04.
 */
public class AvailabilityZonesResponse implements ResponseObject{
    List<AvailabilityZone> availabilityZones;

    public List<AvailabilityZone> getAvailabilityZones() {
        return availabilityZones;
    }

    public void setAvailabilityZones(List<AvailabilityZone> availabilityZones) {
        this.availabilityZones = availabilityZones;
    }

    @Override
    public String toString() {
        String result="";
        for (int i =0 ; i< getAvailabilityZones().size();i++)
        {
            result+=getAvailabilityZones().get(i).getRegionName()+",";
        }
        result=result.substring(0,result.length()-1);
        return  result;
    }
}
