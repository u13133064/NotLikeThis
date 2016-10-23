package ParemeterBeans;

import javax.ws.rs.QueryParam;

/**
 * <h1>ConnectionBean</h1>
 * A parameter bean used for sending information to server from interface. Messenger.
 * QueryParam holds the fields in the message
 * uuid is uuid of node
 * otherId is uuid of node that first node is checking connection with
 * @author  Jedd Shneier
 * @version 1.0
 * @since   2016-10-16
 */
public class ConnectionBean {
    @QueryParam("uuid")
    public String uuid;
    @QueryParam("otherId")
    public String otherId;
}
