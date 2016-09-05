package org.notlikethis.Buffer;

import org.notlikethis.Composite.NetworkTree;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Jedd Shneier.
 */
public interface SmartBufferInterface {
    void constructTree();
    BlockingQueue<NetworkTree> shareBuffer();
    String getLatestTree();

}
