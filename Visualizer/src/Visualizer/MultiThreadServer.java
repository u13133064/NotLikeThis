package Visualizer;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MultiThreadServer implements Runnable 
{
    Socket csocket;
    static ArrayList<Relationship> relatioshipsa;
    static ArrayList<Node> nodesa;
   
   public MultiThreadServer(Socket csocket) 
   {
	   this.csocket = csocket;
   }

   public static String createNode(String nodeID, ArrayList<Node> nodes)
   {
	   nodesa = new ArrayList<Node>(nodes);
        String out = "  var " + nodeID + "= new vis.DataSet([\n";
        
        for(int i = 0; i < nodes.size(); i++)
        {
        //	if(nodes.get(i).getLevel() != -1)
        //		out = out + "    {id: '" + nodes.get(i).getID() + "' , label: '" + nodes.get(i).getName() + "', shape: 'circle', level: " + nodes.get(i).getLevel() +"  },\n";
        //	else
        		out = out + "    {id: '" + nodes.get(i).getID() + "' , label: '" + nodes.get(i).getName() + "', shape: 'circle'},\n";
        }
        
        out = out +"  ]);";
        
        return out;
   }
   
   public static String createNode(String nodeID, String[] ids, String[] labels, String shape)
   {
        String out = "  var " + nodeID + "= new vis.DataSet([\n";
        
        for(int i = 0; i < ids.length; i++)
        {
            out = out + "    {id: " + ids[i] + ", label: '" + labels[i] + "', shape: 'circle'},\n";
        }
        
        out = out +"  ]);";
        
        return out;
   }

   public static String createRelationship(ArrayList<Relationship> relatioships)
   {	   relatioshipsa = new ArrayList<Relationship>(relatioships);
        String out = "  var edges = new vis.DataSet([\n";
        
        for(int i = 0; i < relatioships.size(); i++)
        {
            out = out + "    {from: '" + relatioships.get(i).getFrom() + "', to: '" + relatioships.get(i).getTo() + "'},\n";
        }
        
        out = out +"  ]);";
        
        return out;
   }
   
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
			"      width: 1200px;\n" +
			"      height: 800px;\n" +
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
			            
			 createNode("nodes", nodesa)
			            +
			            
			createRelationship(relatioshipsa)+
			"\n" +
			"  // create a network\n" +
			"  var container = document.getElementById('mynetwork');\n" +
			"  var data = {\n" +
			"    nodes: nodes,\n" +
			"    edges: edges\n" +
			"  };\n" +
			"  var options = {layout: {" +
            "        hierarchical: {" +
            "            direction: 'LR'" +
            "        }" +
            "    }};\n" +
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