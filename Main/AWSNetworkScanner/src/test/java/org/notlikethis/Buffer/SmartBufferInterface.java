package org.notlikethis.Buffer;

import org.notlikethis.Composite.NetworkTree;

/**
 * Created by Jedd Shneier.
 */
public interface SmartBufferInterface {
    void constructTree();

    void addToBuffer(NetworkTree tree);
    String getLatestTree();

}
