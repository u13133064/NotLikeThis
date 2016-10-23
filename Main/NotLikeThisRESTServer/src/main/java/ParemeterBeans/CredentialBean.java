package ParemeterBeans;

import javax.ws.rs.QueryParam;

/**
 * <h1>CredentialBean</h1>
 * A parameter bean used for sending information to server from interface. Messenger.
 * QueryParam holds the fields in the message
 * a_key is client access key
 * s_key is client secret key
 * @author  Jedd Shneier
 * @version 1.0
 * @since   2016-10-16
 */
public class CredentialBean {
    @QueryParam("a_key")
    public String a_key;

    @QueryParam("s_key")
    public String s_key;

}
