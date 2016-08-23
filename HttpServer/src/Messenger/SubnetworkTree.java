package Messenger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 2016/08/20.
 */
public class SubnetworkTree implements NetworkTree {
    private  List<NetworkTree> elements;
    private LinkedList<String> additionalInformation;
    private LinkedList<String> nodeInformation;

    public LinkedList<String> getNodeInformation() {
        return nodeInformation;
    }

    public void setNodeInformation(LinkedList<String> nodeInformation) {
        this.nodeInformation = nodeInformation;
    }

    public List<NetworkTree> getElements() {
        return elements;
    }

    public void setElements(List<NetworkTree> elements) {
        this.elements = elements;
    }

    public LinkedList<String> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(LinkedList<String> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    @Override
    public String displayAdditionalInformation() {
        return null;
    }

    @Override
    public String displayInformation() {
        return null;
    }
}
