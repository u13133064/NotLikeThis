import Server.VisualizerServer;

/**
 * Created by User on 2016/08/20.
 */
public class MainServerTest {
    public static void main(String args[])
    {
        VisualizerServer server = new VisualizerServer(8000,"test");
        server.start();
        VisualizerServer server2 = new VisualizerServer(8001,"test2");
        server2.start();

        server.beginVisualize();

    }
}
