package Server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by User on 2016/08/20.
 */
public class VisualizerServer implements Runnable{

    private int port ;
    private String threadName;
    private HttpServer server = null;
    private Thread me;
        public VisualizerServer(int port,String threadName) {

            this.port=port;
            this.threadName=threadName;

        }
    public void StopServer()
    {
        server.stop(0);
        System.out.println("Server stopped");
    }

    @Override
    public void run() {
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

    public void start ()
    {
        System.out.println("Starting " +  threadName );
        if (me== null)
        {
            me = new Thread (this, threadName);
            me.start ();

        }
    }

}
