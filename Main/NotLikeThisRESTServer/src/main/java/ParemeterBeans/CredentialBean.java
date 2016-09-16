package ParemeterBeans;

import javax.ws.rs.QueryParam;

/**
 * Created by Jedd Shneier.
 */
public class CredentialBean {
    @QueryParam("a_key")
    public String a_key;

    @QueryParam("s_key")
    public String s_key;

}
