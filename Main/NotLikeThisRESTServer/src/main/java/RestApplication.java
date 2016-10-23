/**
 * <h1>RestApplication</h1>
 * Sets up the Rest Server and deploys it
 * @author  Jedd Shneier
 * @version 1.0
 * @since   2016-10-16
 */
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
/**
 Defines the base URI for all resource URIs.
 */

@ApplicationPath("/")
/**
 The java class declares root resource and provider classes
 */

public class RestApplication extends Application{
    /**
     The method returns a non-empty collection with classes, that must be included in the published JAX-RS application
     */


    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add( ServerAPI.class );
        return h;
    }
}