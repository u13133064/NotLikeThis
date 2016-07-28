package Scanner;


import Credentials.Credential;
import Messenger.Network;

public interface NetworkScanner {
	public void createCredentials(String access_key,String private_key);
	
	public Network scanNetwork();
	

}
