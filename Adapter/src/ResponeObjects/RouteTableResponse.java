package ResponeObjects;

import com.amazonaws.services.ec2.model.RouteTable;

import java.util.List;

/**
 * Created by User on 2016/07/05.
 */
public class RouteTableResponse implements ResponseObject {
    List<RouteTable> routeTables;

    @Override
    public String toString() {
        String result="";
        for (int i = 0;i<getRouteTables().size();i++)
        {
            result+="["+getRouteTables().get(i).getRouteTableId()+','+getRouteTables().get(i).getVpcId()+"]";
            result+=",";
        }
        result=result.substring(0,result.length()-1);
        return  result;
    }

    public List<RouteTable> getRouteTables() {
        return routeTables;
    }

    public void setRouteTables(List<RouteTable> routeTables) {
        this.routeTables = routeTables;
    }
}
