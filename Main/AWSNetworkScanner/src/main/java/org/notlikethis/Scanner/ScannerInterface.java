package org.notlikethis.Scanner;

import org.notlikethis.Buffer.SharedBuffer;
import org.notlikethis.Composite.NetworkTree;
import org.notlikethis.Credentials.Credential;

import java.util.LinkedList;

/**
 * Created by Jedd Shneier.
 */
public interface ScannerInterface {
    void scanFullNetwork(Credential credentials, SharedBuffer buffer);

    NetworkTree scanNetworkFrom(String level, String identifier, Credential credentials);

    NetworkTree resumeScan(LinkedList<String> tokens, Credential credentials);

    LinkedList<String> pauseScan();

    void stopScan();


}
