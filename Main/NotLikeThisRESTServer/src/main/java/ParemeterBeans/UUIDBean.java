package ParemeterBeans;

import javax.ws.rs.QueryParam;

/**
 * <h1>UUIDbean</h1>
 * A parameter bean used for sending information to server from interface. Messenger.
 * QueryParam holds the fields in the message
 * level is level of uuid if specified
 * identifier is uud of node if specified
 * @author  Jedd Shneier
 * @version 1.0
 * @since   2016-10-16
 */
public class UUIDBean {
    @QueryParam("uuid")
    public String uuid;
    @QueryParam("level")
    public String level;
}
