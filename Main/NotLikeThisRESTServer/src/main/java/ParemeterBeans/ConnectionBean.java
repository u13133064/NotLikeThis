package ParemeterBeans;

import javax.ws.rs.QueryParam;

/**
 * Created by Jedd Shneier
 */
public class ConnectionBean {
    @QueryParam("uuid")
    public String uuid;
    @QueryParam("otherId")
    public String otherId;
}
