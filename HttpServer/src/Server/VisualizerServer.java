package Server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by User on 2016/08/20.
 */
public class VisualizerServer{

    private int port ;
    private HttpServer server = null;
        public VisualizerServer(int port) {

            this.port=port;
            InetSocketAddress socketAddress = new InetSocketAddress(port);

            try {
                server = HttpServer.create(socketAddress, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Server started at " + port);
            server.createContext("/", new RootHandler());
            server.createContext("/Header", new HeaderHandler());
            server.createContext("/Get", new GetHandler());
            server.createContext("/Post", new PostHandler());
            server.setExecutor(null);
            server.start();
        }
    public void StopServer()
    {
        server.stop(0);
        System.out.println("Server stopped");
    }
}
