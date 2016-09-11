import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


// The Java class will be hosted at the URI path "/services"
@Path("/services")
public class ServerAPI {
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)
    public  Object getClichedMessage() {

        return "Hello world";

    }
}