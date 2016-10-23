package Scanner;

/**
 * <h1>ThreadedScannerInterface</h1>
 * An interface that all sub-scanners must implement.
 * Extends Runnable
 * @author  Jedd Shneier
 * @version 1.0
 * @since   2016-10-16
 */
public interface ThreadedScannerInterface extends Runnable {
    /**
     * Launches a sub-scanner to only scan at specific point
     * 

    void scanOnly();
    /**
     * Launches a default sub-scanner that sub-scans entire region.
     * 

     */
    void scanContext();
    
}
