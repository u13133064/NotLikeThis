
/**
 * <h1>ServerAPI</h1>
 * The list of API calls to the REST server
 * matched to their respective functions
 * @author  Jedd Shneier
 * @version 1.0
 * @since   2016-10-16
 */
import Buffer.SharedBuffer;
import Credentials.Credential;
import ParemeterBeans.ConnectionBean;
import ParemeterBeans.CredentialBean;
import ParemeterBeans.OptionBean;
import ParemeterBeans.UUIDBean;
import Scanner.AWSScanner;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.annotation.Annotation;

/** The Java class will be hosted at the URI path "/services"
 *
 */
@Path("/services")
public class ServerAPI {

    static SharedBuffer sharedBuffer = new SharedBuffer();
    static Credential credentials;
    /**
     * This is the  method which calls for a full scan of the network
     * @return Http response object
     *.
     * @see Exception
     */
    @GET
    @Path("/startScanner")
    @Produces(MediaType.TEXT_PLAIN)
    public Object ScanNetwork() {
        sharedBuffer=new SharedBuffer();
        OptionBean options = new OptionBean();
        options.setScannChoice(1);
        new Thread(new AWSScanner(credentials,sharedBuffer,options)).start();


        return Response.ok() //200
                .entity("Scan Started",new Annotation[0])
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }
    /**
     * This is the method which calls for a region scan

     * @return Http response object
     *
     * @see Exception
     */
    @GET
    @Path("/regionScan")
    @Produces(MediaType.TEXT_PLAIN)
    public Object ScanRegion(@BeanParam UUIDBean uuidBean) {
        sharedBuffer=new SharedBuffer();
        OptionBean options = new OptionBean();
        options.setScannChoice(2);
        options.setIdentifier(uuidBean.uuid);
        new Thread(new AWSScanner(credentials,sharedBuffer,options)).start();


        return Response.ok() //200
                .entity("Region Scan Started",new Annotation[0])
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }
    /**
     * This is the method which calls for a scan of all instances

     * @return Http resposne object.
     *.
     * @see Exception
     */
    @GET
    @Path("/scanAllInstances")
    @Produces(MediaType.TEXT_PLAIN)
    public Object ScanAllInstances(@BeanParam UUIDBean uuidBean) {
        sharedBuffer=new SharedBuffer();
        OptionBean options = new OptionBean();
        options.setScannChoice(4);
        new Thread(new AWSScanner(credentials,sharedBuffer,options)).start();


        return Response.ok() //200
                .entity("All Instance Scan Started",new Annotation[0])
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }
    /**
     * This is the method which calls for all active scans to pause

     * @return Http resposne object.
     *.
     * @see Exception
     */

    @GET
    @Path("/pauseScan")
    @Produces(MediaType.TEXT_PLAIN)
    public Object PauseScan(@BeanParam UUIDBean uuidBean) {
        sharedBuffer.pauseThreads();

        return Response.ok() //200
                .entity("Scan Paused",new Annotation[0])
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }
    /**
     * This is the method which calls for all paused scanners to resume

     * @return Http resposne object.
     *.
     * @see Exception
     */
    @GET
    @Path("/resumeScan")
    @Produces(MediaType.TEXT_PLAIN)
    public Object ResumeScan(@BeanParam UUIDBean uuidBean) {
        sharedBuffer.resumeThreads();

        return Response.ok() //200
                .entity("Scan Resumed",new Annotation[0])
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }
    /**
     * This is the method which calls for all active scans to stop

     * @return Http resposne object.
     *.
     * @see Exception
     */
    @GET
    @Path("/stopScan")
    @Produces(MediaType.TEXT_PLAIN)
    public Object StopScan(@BeanParam UUIDBean uuidBean) {
        sharedBuffer.stopThreads();

        return Response.ok() //200
                .entity("Scan Stopped",new Annotation[0])
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }

    /**
     * This is the method which calls for a scan beginning at specified uuid

     * @return Http resposne object.
     *.
     * @see Exception
     */

    @GET
    @Path("/scanFrom")
    @Produces(MediaType.TEXT_PLAIN)
    public Object ScanFrom(@BeanParam UUIDBean uuidBean) {
        sharedBuffer=new SharedBuffer();
        OptionBean options = new OptionBean();
        options.setScannChoice(3);
        options.setIdentifier(uuidBean.uuid);
        options.setLevel(uuidBean.level);
        new Thread(new AWSScanner(credentials,sharedBuffer,options)).start();


        return Response.ok() //200
                .entity("Scanning from started",new Annotation[0])
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }
    /**
     * This is the method which calls for scan to continue from where scanFrom ended off.
     * @return Http resposne object.
     *.
     * @see Exception
     */
    @GET
    @Path("/scanUp")
    @Produces(MediaType.TEXT_PLAIN)
    public Object ScanUp() {
        OptionBean options = new OptionBean();
        String parentIdentifier =sharedBuffer.getParentIdentifier();
        String parentLevel =sharedBuffer.getParentLevel();
        sharedBuffer=new SharedBuffer();
        sharedBuffer.setParentIdentifier(parentIdentifier);
        sharedBuffer.setParentLevel(parentLevel);
        System.out.println("Parent identifer: "+parentIdentifier);
        System.out.println("Parent Level: "+parentLevel);
        options.setScannChoice(3);
        options.setIdentifier(parentIdentifier);
        options.setLevel(parentLevel);
        new Thread(new AWSScanner(credentials,sharedBuffer,options)).start();


        return Response.ok() //200
                .entity("Scan up Started",new Annotation[0])
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }
    /**
     * This is the method which requests the next node for visualization
     * @return Http resposne object.
     *.
     * @see Exception
     */

    @GET
    @Path("/getLatestTree")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getCurrentTree() {
        return Response.ok() //200
                .entity(sharedBuffer.getJSONList(),new Annotation[0])
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }
    /**
     * This is the method which asks for the information about a particular node specified by uuid
     * @param paramBean
     * @return Http resposne object.
     *.
     * @see Exception
     */
    @GET
    @Path("/getInformation")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getInformation(@BeanParam UUIDBean paramBean) {
        return Response.ok() //200
                .entity(sharedBuffer.getInformation(paramBean.uuid),new Annotation[0])
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }
    /**
     * This is the method which requests curretn summary of the ongoing scans
     * @return Http resposne object.
     *.
     * @see Exception
     */
    @GET
    @Path("/getStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getStatus() {
        return Response.ok() //200
                .entity(sharedBuffer.getStatus(),new Annotation[0])
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();
    }
    /**
     * This is the method which calls for the connections fora  node specified by uuid
     * @param paramBean
     * @return Http resposne object.
     *.
     * @see Exception
     */
    @GET
    @Path("/getConnection")
    @Produces(MediaType.TEXT_PLAIN)
    public Object getConnection(@BeanParam ConnectionBean paramBean) {
        return Response.ok() //200
                .entity(sharedBuffer.getConnections(paramBean.uuid,paramBean.otherId),new Annotation[0])
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }
    /**
     * This is the method which valdiates user keys

     * @return Http resposne object.
     *.
     * @see Exception
     */

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/validate")
    @Produces(MediaType.APPLICATION_XML)

    public Response createCredentials(@BeanParam CredentialBean paramBean) {
        credentials= new Credential();
        credentials.setAccess_key(paramBean.a_key);
        credentials.setPrivate_key(paramBean.s_key);
        credentials.validate();
        if(credentials.isValid()) {

            return Response.ok() //200
                    .entity("Success : Account keys have been received and verified", new Annotation[0])
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .allow("OPTIONS").build();
        }
        else
        {
            return Response.ok()
                    .entity("Error : Account keys were not validated ", new Annotation[0])
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .allow("OPTIONS").build();
        }

    }


}