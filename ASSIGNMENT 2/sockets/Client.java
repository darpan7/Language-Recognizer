//package sockets;

import java.net.*;
import java.util.Map;
import java.io.*;

public class Client{
  public static void main (String [] args ) throws IOException {
    int filesize=6022386; // filesize temporary hardcoded
    
    long start = System.currentTimeMillis();
    int bytesRead;
    int current = 0;
    String value;
    //  155.35.255.118
    
    //  192.168.220.131
    
    
    // 192.168.1.102
    System.out.println("About to Connect...");
    Socket sock = new Socket("192.168.0.108",9750);
    System.out.println("Connecting...");
    Map map = System.getenv();
    value = (String) map.get("windir");
    // receive file
    byte [] mybytearray  = new byte [filesize];
    InputStream is = sock.getInputStream();
    FileOutputStream fos = new FileOutputStream("C:"+File.separator+"upload"+File.separator+ "dummy.txt");
    BufferedOutputStream bos = new BufferedOutputStream(fos);
    bytesRead = is.read(mybytearray,0,mybytearray.length);
    current = bytesRead;

    // thanks to A. Cádiz for the bug fix
    do {
       bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
       if(bytesRead >= 0) current += bytesRead;
    } while(bytesRead > -1);

    bos.write(mybytearray, 0 , current);
    bos.flush();
    long end = System.currentTimeMillis();
    System.out.println(end-start);
    bos.close();
    sock.close();
  }
}
