package Scanner;

import Messenger.NetworkTree;
import Messenger.SubnetworkTree;
import Messenger.TreeNode;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by User on 2016/08/21.
 */
public class MockScanner implements NetworkScanner {
    private Thread me;
    private ConcurrentLinkedQueue<NetworkTree> buffer;
    public MockScanner(ConcurrentLinkedQueue<NetworkTree> buffer)
    {
        this.buffer=buffer;
    }
    @Override
    public void createCredentials(String access_key, String private_key) {
        System.out.println("Verifying Credentials");
        System.out.println("Account has been verified for Mock user");
    }

    @Override
    public NetworkTree scanNetwork() {
        SubnetworkTree test = new SubnetworkTree();
        LinkedList<NetworkTree> list = new LinkedList<NetworkTree>();

        for(int i =0; i<100;i++)
        {
            TreeNode t = new TreeNode();
            LinkedList<String> info = new LinkedList<String>();
            info.add("Node: "+i);

            t.setNodeInformation(info);
            list.add(t);
        }
        test.setElements(list);
        LinkedList<String> info = new LinkedList<String>();
        info.add("Subnetwork ");
        test.setNodeInformation(info);
        return test;

    }

    @Override
    public void run() {
        createCredentials("","");
        System.out.println("Producer: Adding to buffer" );
        buffer.add(scanNetwork());
        System.out.println("Producer: Buffer size is" +buffer.size());


    }
    public void start ()
    {
        System.out.println("Starting producer" );
        if (me== null)
        {
            me = new Thread (this, "producer");
            me.start ();
        }
    }
}
