package org.notlikethis.Scanner;

import org.notlikethis.Buffer.SharedBuffer;

/**
 * Created by Jedd Shneier.
 */
public interface ThreadScanner extends Runnable {

    void attachBuffer(SharedBuffer buffer);

}
