package ResponeObjects;

import com.amazonaws.services.ec2.model.ClassicLinkInstance;

import java.util.List;

/**
 * Created by User on 2016/07/11.
 */
public class ClassicLinksResponse implements ResponseObject {
    List<ClassicLinkInstance> classicLinkInstances;

    public List<ClassicLinkInstance> getClassicLinkInstances() {
        return classicLinkInstances;
    }

    public void setClassicLinkInstances(List<ClassicLinkInstance> classicLinkInstances) {
        this.classicLinkInstances = classicLinkInstances;
    }

    @Override
    public String toString() {
        String result="";
        for (int i =0 ; i< getClassicLinkInstances().size();i++)
        {
            result+="["+getClassicLinkInstances().get(i).getInstanceId()+","+getClassicLinkInstances().get(i).getVpcId()+"],";
        }
        result=result.substring(0,result.length()-1);
        return  result;
    }

}
