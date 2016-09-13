package org.notlikethis;

import org.notlikethis.Buffer.SharedBuffer;
import org.notlikethis.Credentials.Credential;
import org.notlikethis.Scanner.AWSScanner;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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



    }
}
