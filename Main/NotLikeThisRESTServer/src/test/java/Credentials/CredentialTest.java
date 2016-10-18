package Credentials;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Daniel King
 */
public class CredentialTest {
    
    private String validAccessKey = "";
    private String validSecretKey = "";

    @Test
    public void validAccessKeyValidSecretKey() {
        Credential tester = new Credential();
        
        tester.setPrivate_key(validSecretKey);
        tester.setAccess_key(validAccessKey);
        tester.validate();
        
        assertTrue("Valid keys were accepted.", tester.isValid());
    }
    
    @Test
    public void invalidAccessKeyValidSecretKey() {
        Credential tester = new Credential();
        
        tester.setPrivate_key(validSecretKey);
        tester.setAccess_key("ljkhasodhoiashdo");
        tester.validate();
        
        assertFalse("Invalid access key rejected.", tester.isValid());
    }
    
    @Test
    public void validAccessKeyInvalidSecretKey() {
        Credential tester = new Credential();
        
        tester.setPrivate_key("auishdhasdhasoihdo");
        tester.setAccess_key(validAccessKey);
        tester.validate();
        
        assertFalse("Invalid secret key rejected.", tester.isValid());
    }
    
    @Test
    public void invalidAccessKeyInvalidSecretKey() {
        Credential tester = new Credential();
        
        tester.setPrivate_key("asijdijsadoijasijdas");
        tester.setAccess_key("hasdohasodhiashdoi");
        tester.validate();
        
        assertFalse("Invalid keys were rejected.", tester.isValid());
    }
}
