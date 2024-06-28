package Logic;

import java.io.*;
import java.net.Socket;

import java.io.*;
import java.net.*;

import java.io.*;
import java.net.*;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReceiveFileLogic {
    private static final String UPLOADS_DIRECTORY = "uploads" + File.separator;

    private static final int WORKER_NUM = 10;
    private static final int INPUT_PACKAGE_SIZE = 1000;

    public void receiveFile(PrintWriter out, long size, String name) throws IOException {
        System.out.println("MOTHER FUCKER IM HERE 1");
        long[] workerOffset = new long[WORKER_NUM];
        long eachWorkerSize = size / WORKER_NUM;

        byte[] receiveBuffer = new byte[INPUT_PACKAGE_SIZE + 1];

        // Ensure the uploads directory exists
        try {
            Files.createDirectories(Paths.get(UPLOADS_DIRECTORY));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        File file = new File(UPLOADS_DIRECTORY + name);

        System.out.println("MOTHER FUCKER IM HERE 2");

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
             DatagramSocket socket = new DatagramSocket(8000)) {

            System.out.println("Server is running and waiting for incoming packets...");



            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                System.out.println("IM READING YOU WHORE");
                int bytesRead = receivePacket.getLength();
                if (bytesRead < INPUT_PACKAGE_SIZE + 1) {
                    System.err.println("Received packet is smaller than expected. Discarding.");
                    continue;
                }

                byte[] data = receivePacket.getData();
                int ID = data[INPUT_PACKAGE_SIZE] & 0xFF;

                // Calculate the start position for this worker
                long startPosition = eachWorkerSize * ID + workerOffset[ID];
                workerOffset[ID] += bytesRead - 1; // Update the offset for the worker

                // Write the received data to the file at the calculated position
                randomAccessFile.seek(startPosition);
                randomAccessFile.write(data, 0, bytesRead - 1); // Exclude the last byte (ID)
            }
        }
    }
}


