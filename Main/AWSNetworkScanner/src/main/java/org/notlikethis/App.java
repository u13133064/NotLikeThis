package org.notlikethis;

import org.notlikethis.Buffer.SharedBuffer;
import org.notlikethis.Credentials.Credential;
import org.notlikethis.Scanner.AWSScanner;
import org.notlikethis.Server.HttpRequestHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */

public class App 
{
    private static class VisualizerMock implements Runnable
    {
        private SharedBuffer buffer;

        VisualizerMock(SharedBuffer buffer)
        {
            this.buffer=buffer;
        }

        public void run() {
            while (true)
            {
                try {
                    Thread.sleep(10);
                    buffer.constructTree();
                    PrintWriter writer = new PrintWriter("test.json", "UTF-8");
                    writer.println(buffer.getLatestTree());
                    writer.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public static void main( String[] args )
    {
        SharedBuffer buffer= new SharedBuffer();
        AWSScanner scanner = new AWSScanner();
        VisualizerMock viz = new VisualizerMock(buffer);
         new Thread(viz).start();
        Credential credential = new Credential();
        credential.setAccess_key(args[0]);
        credential.setPrivate_key(args[1]);
        System.out.println( "Starting scanner!" );
        scanner.scanFullNetwork(credential,buffer);
        int port;
        ServerSocket server_socket;
        try {
            port = 8888;
        } catch (Exception e) {
            port = 1500;
        }
        try {

            server_socket = new ServerSocket(port);
            System.out.println("httpServer running on port "
                    + server_socket.getLocalPort());

            // server infinite loop
            while (true) {
                Socket socket = server_socket.accept();
                System.out.println("New connection accepted "
                        + socket.getInetAddress() + ":" + socket.getPort());

                // Construct handler to process the HTTP request message.
                try {
                    HttpRequestHandler request = new HttpRequestHandler(socket);
                    // Create a new thread to process the request.
                    Thread thread = new Thread(request);

                    // Start the thread.
                    thread.start();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}





