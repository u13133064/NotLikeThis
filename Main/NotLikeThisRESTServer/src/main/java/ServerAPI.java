import Buffer.SharedBuffer;
import Credentials.Credential;
import ParemeterBeans.CredentialBean;
import ParemeterBeans.OptionBean;
import ParemeterBeans.UUIDBean;
import Scanner.AWSScanner;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.annotation.Annotation;
// The Java class will be hosted at the URI path "/services"
@Path("/services")
public class ServerAPI {
    // The Java method will process HTTP GET requests
    static SharedBuffer sharedBuffer = new SharedBuffer();
    @GET
    @Path("/startScanner")
    @Produces(MediaType.TEXT_PLAIN)
    public Object ScanNetwork(@BeanParam CredentialBean paramBean) {
        sharedBuffer=new SharedBuffer();
        Credential credentials= new Credential();
        credentials.setAccess_key(paramBean.a_key);
        credentials.setPrivate_key(paramBean.s_key);
        System.out.println(paramBean.a_key);
        System.out.println(paramBean.s_key);
        OptionBean options = new OptionBean();
        options.setScannChoice(1);
        new Thread(new AWSScanner(credentials,sharedBuffer,options)).start();


        return Response.ok() //200
                .entity("Scan Started",new Annotation[0])
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }

    @GET
    @Path("/regionScan")
    @Produces(MediaType.TEXT_PLAIN)
    public Object ScanRegion(@BeanParam CredentialBean paramBean,@BeanParam UUIDBean uuidBean) {
        sharedBuffer=new SharedBuffer();
        Credential credentials= new Credential();
        credentials.setAccess_key(paramBean.a_key);
        credentials.setPrivate_key(paramBean.s_key);
        System.out.println(paramBean.a_key);
        System.out.println(paramBean.s_key);
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

    @GET
    @Path("/scanFrom")
    @Produces(MediaType.TEXT_PLAIN)
    public Object ScanFrom(@BeanParam CredentialBean paramBean,@BeanParam UUIDBean uuidBean) {
        sharedBuffer=new SharedBuffer();
        Credential credentials= new Credential();
        credentials.setAccess_key(paramBean.a_key);
        credentials.setPrivate_key(paramBean.s_key);
        System.out.println(paramBean.a_key);
        System.out.println(paramBean.s_key);
        OptionBean options = new OptionBean();
        options.setScannChoice(3);
        options.setIdentifier(uuidBean.uuid);
        options.setLevel(uuidBean.level);
        new Thread(new AWSScanner(credentials,sharedBuffer,options)).start();


        return Response.ok() //200
                .entity("Region Scan Started",new Annotation[0])
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();

    }

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/validate")
    @Produces(MediaType.APPLICATION_XML)

    public Response createCredentials(@BeanParam CredentialBean paramBean) {
        Credential credentials= new Credential();
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