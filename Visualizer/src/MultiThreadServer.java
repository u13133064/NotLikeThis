import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer implements Runnable 
{
    Socket csocket;
   
    MultiThreadServer(Socket csocket) 
    {
        this.csocket = csocket;
    }

    public static void main(String args[]) throws Exception 
    {
        ServerSocket ssock = new ServerSocket(1234);
        System.out.println("Listening");

        while (true) 
        {
            Socket sock = ssock.accept();
            System.out.println("Connected");
            new Thread(new MultiThreadServer(sock)).start();
        }
    }
   
    String nodeID = "nodes";
    String[] ids = new String[]{"1", "2", "3", "4", "5"};
    String[] label = new String[]{"1", "2", "3", "4", "5"};

   public static String createNode(String nodeID, String[] ids, String[] labels, String shape)
   {
        String out = "  var " + nodeID + "= new vis.DataSet([\n";
        
        for(int i = 0; i < ids.length; i++)
        {
            out = out + "    {id: " + ids[i] + ", label: '" + labels[i] + "', shape: 'circle' },";
        }
        
        out = out +"  ]);";
        
        return out;
   }
   
   String[] idFrom = new String[]{"1", "1", "2", "2"};
   String[] idTo = new String[]{"3", "2", "4", "5"};
   
   /**
    * Throw exeption if arrays are different lengths
    * @param idFrom
    * @param idTo
    * @return 
    */
   public static String createRelationship(String[] idFrom, String[] idTo)
   {
        String out = "  var edges = new vis.DataSet([\n";
        
        for(int i = 0; i < idFrom.length; i++)
        {
            out = out + "    {from: '" + idFrom[i] + "', to: '" + idTo[i] + "'},\n";
        }
        
        out = out +"  ]);";
        
        return out;
   }

   public void run() 
   {
        try 
        {
            PrintStream pstream = new PrintStream(csocket.getOutputStream());
            PrintWriter out = new PrintWriter(csocket.getOutputStream());

    out.println("HTTP/1.1 200 OK");
    out.println("Content-Type: text/html");
    out.println("\r\n");
    out.println(
    "<!doctype html>\n" +
"<html>\n" +
"<head>\n" +
"  <title>Network | Basic usage</title>\n" +
"\n" +
"  <script type=\"text/javascript\" src=\"http://visjs.org/dist/vis.js\"></script>\n" +
"  <link href=\"http://visjs.org/dist/vis.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
"\n" +
"  <style type=\"text/css\">\n" +
"    #mynetwork {\n" +
"      width: 600px;\n" +
"      height: 400px;\n" +
"      border: 1px solid lightgray;\n" +
"    }\n" +
"  </style>\n" +
"</head>\n" +
"<body>\n" +
"\n" +
"<p>\n" +
"  Create a simple network with some nodes and edges.\n" +
"</p>\n" +
"\n" +
"<div id=\"mynetwork\"></div>\n" +
"\n" +
"<script type=\"text/javascript\">\n" +
"  // create an array with nodes\n" +
            
 createNode(nodeID, ids, label, nodeID)
            +
            
createRelationship(idFrom, idTo)+
"\n" +
"  // create a network\n" +
"  var container = document.getElementById('mynetwork');\n" +
"  var data = {\n" +
"    nodes: nodes,\n" +
"    edges: edges\n" +
"  };\n" +
"  var options = {};\n" +
"  var network = new vis.Network(container, data, options);\n" +
"\n" +
"</script>\n" +
"\n" +
"<script src=\"../googleAnalytics.js\"></script>\n" +
"</body>\n" +
"</html>");
    out.flush();

    out.close();

            pstream.println("");
         
         pstream.close();
         csocket.close();
      }
      catch (IOException e) {
         System.out.println(e);
      }
   }
}