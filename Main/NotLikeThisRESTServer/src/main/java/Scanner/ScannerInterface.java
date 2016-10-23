/**
 * <h1>ScannerInterface</h1>
 * An interface that  master scanners must implement.
 * Extends Runnable
 * @author  Jedd Shneier
 * @version 1.0
 * @since   2016-10-16
 */
package Scanner;



public interface ScannerInterface extends Runnable {
    /**
     * Launches  a full scan of the network
     */
    void scanFullNetwork();
    /**
     * Launches a regional scan of the network
     *
     */
    void scanRegion(String region);
    /**
     * Launches a scan of the nentwork from specific point
     *
     */
    void scanNetworkFrom(String level, String identifier);





}
