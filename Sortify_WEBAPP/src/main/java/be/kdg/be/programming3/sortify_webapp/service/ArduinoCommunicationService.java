package be.kdg.be.programming3.sortify_webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

@Service
public class ArduinoCommunicationService {
    private static final String SERVER_IP = "192.168.x.x"; // Update with actual IP address
    private static final int SERVER_PORT = 80; // Update if a different port is being used
    private static final int TIMEOUT = 5000; // Timeout in milliseconds (5 seconds)

    private static final Logger logger = LoggerFactory.getLogger(ArduinoCommunicationService.class);

    public boolean isBinOpen() {
        boolean isOpen = false;

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {
            // Set a timeout for reading data
            socket.setSoTimeout(TIMEOUT);

            // Communication streams
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Send request to Arduino (if needed)
            output.println("STATUS_REQUEST");

            // Read the response
            String sensorData = input.readLine(); // Adjust this based on how your Arduino sends data
            logger.info("Received data from Arduino: {}", sensorData);

            // Check the received data
            if ("OPEN".equals(sensorData)) {
                isOpen = true;
            } else if ("CLOSED".equals(sensorData)) {
                isOpen = false;
            } else {
                logger.warn("Unrecognized response from Arduino: {}", sensorData);
            }

        } catch (SocketTimeoutException e) {
            logger.error("Connection to Arduino timed out", e);
        } catch (Exception e) {
            logger.error("Error communicating with Arduino", e);
        }

        return isOpen;
    }
}

