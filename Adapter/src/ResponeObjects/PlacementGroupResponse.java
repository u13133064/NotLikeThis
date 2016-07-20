package ResponeObjects;


import com.amazonaws.services.ec2.model.PlacementGroup;

import java.util.List;

/**
 * Created by User on 2016/07/05.
 */
public class PlacementGroupResponse implements ResponseObject{
    private List<PlacementGroup> placementGroups;


    @Override
    public String toString() {
        String result="";
        for (int i = 0;i<getPlacementGroups().size();i++)
        {
            result+="["+getPlacementGroups().get(i).getGroupName()+","+getPlacementGroups().get(i).getState()+","+getPlacementGroups().get(i).getStrategy()+"]";
            result+=",";
        }
        if(result.length()>1)
		{
			 result=result.substring(0,result.length()-1);
		}
	
        return  result;
    }

    public List<PlacementGroup> getPlacementGroups() {
        return placementGroups;
    }

    public void setPlacementGroups(List<PlacementGroup> placementGroups) {
        this.placementGroups = placementGroups;
    }
}
