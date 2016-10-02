package ParemeterBeans;

import javax.ws.rs.QueryParam;

/**
 * Created by Jedd Shnier.
 */
public class UUIDBean {
    @QueryParam("uuid")
    public String uuid;
    @QueryParam("level")
    public String level;
}
