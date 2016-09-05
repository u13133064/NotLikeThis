package org.notlikethis.Buffer;

import org.notlikethis.Composite.NetworkTree;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Jedd Shneier.
 */
public class SharedBuffer implements SmartBufferInterface{
    private BlockingQueue<NetworkTree> frontBuffer;

    public void constructTree() {

    }

    public BlockingQueue<NetworkTree> shareBuffer() {
        return frontBuffer;
    }

    public String getLatestTree() {
        return null;
    }
}
