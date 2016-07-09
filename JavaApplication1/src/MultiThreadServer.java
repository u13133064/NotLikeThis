import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer implements Runnable {
   Socket csocket;
   MultiThreadServer(Socket csocket) {
      this.csocket = csocket;
   }

   public static void main(String args[]) 
   throws Exception {
      ServerSocket ssock = new ServerSocket(1234);
      System.out.println("Listening");
      while (true) {
         Socket sock = ssock.accept();
         System.out.println("Connected");
         new Thread(new MultiThreadServer(sock)).start();
      }
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
"    <title>Network | DataSet</title>\n" +
"\n" +
"    <style type=\"text/css\">\n" +
"        html, body {\n" +
"            font: 11pt arial;\n" +
"        }\n" +
"\n" +
"        h1 {\n" +
"            font-size: 150%;\n" +
"            margin: 5px 0;\n" +
"        }\n" +
"\n" +
"        h2 {\n" +
"            font-size: 100%;\n" +
"            margin: 5px 0;\n" +
"        }\n" +
"\n" +
"        table.view {\n" +
"            width: 100%;\n" +
"        }\n" +
"\n" +
"        table td {\n" +
"            vertical-align: top;\n" +
"        }\n" +
"\n" +
"        table table {\n" +
"            background-color: #f5f5f5;\n" +
"            border: 1px solid #e5e5e5;\n" +
"        }\n" +
"\n" +
"        table table td {\n" +
"            vertical-align: middle;\n" +
"        }\n" +
"\n" +
"        input[type=text], pre {\n" +
"            border: 1px solid lightgray;\n" +
"        }\n" +
"\n" +
"        pre {\n" +
"            margin: 0;\n" +
"            padding: 5px;\n" +
"            font-size: 10pt;\n" +
"        }\n" +
"\n" +
"        #network {\n" +
"            width: 100%;\n" +
"            height: 400px;\n" +
"            border: 1px solid lightgray;\n" +
"        }\n" +
"    </style>\n" +
"\n" +
"   \n" +
"</head>\n" +
"\n" +
"<body>\n" +
"\n" +
"<p>\n" +
"    This example demonstrates dynamically adding, updating and removing nodes\n" +
"    and edges using a DataSet.\n" +
"</p>\n" +
"\n" +
"<h1>Adjust</h1>\n" +
"<table>\n" +
"    <tr>\n" +
"        <td>\n" +
"            <h2>Node</h2>\n" +
"            <table>\n" +
"                <tr>\n" +
"                    <td></td>\n" +
"                    <td><label for=\"node-id\">Id</label></td>\n" +
"                   \n" +
"                </tr>\n" +
"                <tr>\n" +
"                    <td></td>\n" +
"                    <td><label for=\"node-label\">Label</label></td>\n" +
"                    \n" +
"                </tr>\n" +
"                <tr>\n" +
"                    <td></td>\n" +
"                    <td>Action</td>\n" +
"                    <td>\n" +
"                       \n" +
"                        \n" +
"                    </td>\n" +
"                </tr>\n" +
"            </table>\n" +
"        </td>\n" +
"        <td>\n" +
"            <h2>Edge</h2>\n" +
"            <table>\n" +
"                <tr>\n" +
"                    <td></td>\n" +
"                    <td><label for=\"edge-id\">Id</label></td>\n" +
"                    <td><input id=\"edge-id\" type=\"text\" value=\"5\"></td>\n" +
"                </tr>\n" +
"                <tr>\n" +
"                    <td></td>\n" +
"                    <td><label for=\"edge-from\">From</label></td>\n" +
"                    <td><input id=\"edge-from\" type=\"text\" value=\"3\"></td>\n" +
"                </tr>\n" +
"                <tr>\n" +
"                    <td></td>\n" +
"                    <td><label for=\"edge-to\">To</label></td>\n" +
"                    <td><input id=\"edge-to\" type=\"text\" value=\"4\"></td>\n" +
"                </tr>\n" +
"                <tr>\n" +
"                    <td></td>\n" +
"                    <td>Action</td>\n" +
"                    <td>\n" +
"                     \n" +
"                        \n" +
"                </tr>\n" +
"            </table>\n" +
"        </td>\n" +
"    </tr>\n" +
"\n" +
"</table>\n" +
"\n" +
"<h1>View</h1>\n" +
"<table class=\"view\">\n" +
"    <colgroup>\n" +
"        <col width=\"25%\">\n" +
"        <col width=\"25%\">\n" +
"        <col width=\"50%\">\n" +
"    </colgroup>\n" +
"    <tr>\n" +
"        <td>\n" +
"            <h2>Nodes</h2>\n" +
"            <pre id=\"nodes\"></pre>\n" +
"        </td>\n" +
"\n" +
"        <td>\n" +
"            <h2>Edges</h2>\n" +
"            <pre id=\"edges\"></pre>\n" +
"        </td>\n" +
"\n" +
"        <td>\n" +
"            <h2>Network</h2>\n" +
"\n" +
"            <div id=\"network\"></div>\n" +
"        </td>\n" +
"    </tr>\n" +
"</table>\n" +
"\n" +
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