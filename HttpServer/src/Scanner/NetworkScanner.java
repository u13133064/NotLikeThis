package Scanner;


import Messenger.NetworkTree;


public interface NetworkScanner extends Runnable {
	public void createCredentials(String access_key, String private_key);
	public NetworkTree scanNetwork();
	

}
