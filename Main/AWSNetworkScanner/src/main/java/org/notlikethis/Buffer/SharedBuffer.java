package org.notlikethis.Buffer;

import org.notlikethis.Composite.NetworkTree;
import org.notlikethis.Composite.Node;

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
        currentTree.setUUID("1");

    }



    public void constructTree() {
        NetworkTree additionTree = null;
        try {
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
        for(int i =0;i<addition.getChildrenSize();i++)
        {
            boolean found=false;
            for(int j =0;j<current.getChildrenSize();j++)
            {
                if(current.getChild(j).getName().equals(addition.getChild(i).getName()))
                {
                     if (current.getChild(j).getUUID().equals("TEMP"))
                     {
                         current.getChild(j).setUUID(addition.getChild(i).getUUID());
                         current.getChild(j).setInformation(addition.getChild(i).getInformation());
                     }
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
        try {
            frontBuffer.put(tree);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getLatestTree() {

        return currentTree.toJSON();
    }
}
