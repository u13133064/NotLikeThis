package Scanner;


/**
 * Created by Jedd Shneier.
 */
public interface ScannerInterface extends Runnable {
    void scanFullNetwork();
    void scanRegion(String region);
    void scanNetworkFrom(String level, String identifier);





}
