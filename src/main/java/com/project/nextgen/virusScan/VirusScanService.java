package com.project.nextgen.virusScan;


import java.io.InputStream;
import java.net.Socket;
 
import org.springframework.stereotype.Service;
 
@Service
public class VirusScanService {
 
    public boolean scan(InputStream inputStream) {
        try (Socket socket = new Socket("localhost", 3310)) {
 
            // ClamAV command
            socket.getOutputStream().write("zINSTREAM\0".getBytes());
 
            byte[] buffer = new byte[4096];
            int read;
 
            while ((read = inputStream.read(buffer)) != -1) {
                byte[] size = intToBytes(read);
                socket.getOutputStream().write(size);
                socket.getOutputStream().write(buffer, 0, read);
            }
 
            // End stream
            socket.getOutputStream().write(intToBytes(0));
            socket.getOutputStream().flush();
 
            // Read response
            byte[] response = socket.getInputStream().readAllBytes();
            String result = new String(response);
 
            System.out.println("ClamAV Response: " + result);
 
            return result.contains("OK");
 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
 
    private byte[] intToBytes(int value) {
        return new byte[] {
                (byte)((value >> 24) & 0xff),
                (byte)((value >> 16) & 0xff),
                (byte)((value >> 8) & 0xff),
                (byte)(value & 0xff)
        };
    }
}
