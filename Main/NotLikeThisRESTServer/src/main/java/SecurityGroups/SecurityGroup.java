package SecurityGroups;

import com.amazonaws.services.ec2.model.IpPermission;

import java.util.LinkedList;

/**
 * Created by Jedd Shneier.
 */
public class SecurityGroup {
    String [] listOfPaths;
    String id;
    LinkedList<IpPermission> inboundRules;
    LinkedList<IpPermission> outboundRules;


}
