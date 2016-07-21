package ResponeObjects;

import com.amazonaws.services.ec2.model.Instance;

import java.util.List;

/**
 * Created by User on 2016/07/05.
 */
public class InstancesResponse implements ResponseObject{
    private List<Instance> instances;

    public List<Instance> getInstances() {
        return instances;
    }

    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }

    @Override
    public String toString() {
        String result="";
        for (int i = 0;i<getInstances().size();i++)
        {
            result+=getInstances().get(i).getInstanceId()+":"+getInstances().get(i).getSubnetId()+",";
            
        }
        if(result.length()>1)
     		{
     			 result=result.substring(0,result.length()-1);
     		}
     	
             return  result;

    }
}
