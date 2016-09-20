package Buffer;


import Composite.NetworkTree;
import Composite.Node;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Jedd Shneier.
 */
public class SharedBuffer implements SmartBufferInterface{
    private BlockingQueue<NetworkTree> frontBuffer;
    private NetworkTree currentTree;
    public SharedBuffer()
    {
        frontBuffer=new ArrayBlockingQueue<NetworkTree>(1000);
        currentTree = new Node();
        currentTree.setInformation("AWS");
        currentTree.setName("Root");
        currentTree.setUUID(UUID.randomUUID().toString());

    }



    public void constructTree() {
        NetworkTree additionTree = null;
        try {
            System.out.println("Buffer has " +frontBuffer.size()+" elements");
            additionTree = frontBuffer.take();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        for (int i =0;i< currentTree.getChildrenSize();i++)
        {
            if(currentTree.getChild(i).getName().equals(additionTree.getName()))
            {
                combineTree(currentTree.getChild(i),additionTree);
                return;
            }
        }
        currentTree.add(additionTree);




    } 

    private void combineTree(NetworkTree current, NetworkTree addition) {
        if(current==null||addition==null)
        {
            return;
        }
        if (current.getUUID().contains("TEMP"))
        {
            current.setUUID(addition.getUUID());
            current.setInformation(addition.getInformation());
        }
        for(int i =0;i<addition.getChildrenSize();i++)
        {
            boolean found=false;
            for(int j =0;j<current.getChildrenSize();j++)
            {
                if(current.getChild(j).getName().equals(addition.getChild(i).getName()))
                {

                    combineTree(current.getChild(j),addition.getChild(i));
                    found=true;
                }
            }
            if (!found)
            {
                current.add(addition.getChild(i));
            }
        }
    }

    public void addToBuffer(NetworkTree tree) {
        System.out.println(frontBuffer.size());
        try {
            frontBuffer.put(tree);

        } catch (InterruptedException e) {
            System.out.println("Buffer is full");
            e.printStackTrace();
        }
    }


    public String getLatestTree() {
        System.out.println("Constructing current tree");
        constructTree();
        System.out.println("Returning current tree");
        return currentTree.toJSON();
    }
}
