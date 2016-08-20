package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by User on 2016/08/20.
 */
public class RootHandler implements HttpHandler {
    @Override

    public void handle(HttpExchange he) throws IOException {
        String response = "<h1>Server currently operational </h1>" + "<h1>Port: " + he.getLocalAddress().getPort()+ "</h1>";
        he.sendResponseHeaders(200, response.length());
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
