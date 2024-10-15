package be.kdg.be.programming3.sortify_webapp.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Service
public class ArduinoCommunicationService {
    private static final String SERVER_IP = "192.168.x.x";
    private static final int SERVER_PORT = 80;

    public boolean isBinOpen() {
        boolean isOpen = false;

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);


            String sensorData = input.readLine();//we use this to read the data from arduino
            if ("OPEN".equals(sensorData)) {
                isOpen = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isOpen;
    }
}
