package ResponeObjects;


import com.amazonaws.services.ec2.model.Region;

import java.util.List;

/**
 * Created by User on 2016/07/05.
 */
public class RegionResponse implements ResponseObject {
    private List<Region> regions;

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    @Override
    public String toString() {
        String result="";
        for (int i = 0;i<getRegions().size();i++)
        {
            result+=getRegions().get(i).getRegionName();
            result+=",";
        }
        result=result.substring(0,result.length()-1);
        return  result;
    }

}
