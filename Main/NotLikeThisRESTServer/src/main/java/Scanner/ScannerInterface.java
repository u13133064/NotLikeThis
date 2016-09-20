package Scanner;


import Composite.NetworkTree;
import Credentials.Credential;

import java.util.LinkedList;

/**
 * Created by Jedd Shneier.
 */
public interface ScannerInterface extends Runnable {
    void scanFullNetwork();

    NetworkTree scanNetworkFrom(String level, String identifier, Credential credentials);

    NetworkTree resumeScan(LinkedList<String> tokens, Credential credentials);

    LinkedList<String> pauseScan();

    void stopScan();


}