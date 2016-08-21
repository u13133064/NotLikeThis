package Messenger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 2016/08/20.
 */
public class SubnetworkTree implements NetworkTree {
    private  List<NetworkTree> elements;
    private LinkedList<String> additionalInformation;

    @Override
    public String displayAdditionalInformation() {
        return null;
    }

    @Override
    public String displayInformation() {
        return null;
    }
}
