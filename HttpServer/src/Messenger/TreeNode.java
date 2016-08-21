package Messenger;

import java.util.LinkedList;

/**
 * Created by User on 2016/08/20.
 */
public class TreeNode implements NetworkTree{
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
