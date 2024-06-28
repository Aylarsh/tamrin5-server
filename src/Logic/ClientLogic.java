package Logic;

import Loader.UserDatabase;
import Model.User;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class ClientLogic extends Thread {
        private final Socket socket;
        private SignUpLogic signUpLogic = new SignUpLogic();
        private LoginLogic loginLogic = new LoginLogic();
        private ReceiveFileLogic receiveFileLogic = new ReceiveFileLogic();
    public ClientLogic(Socket socket) {
        this.socket = socket;
    }
        @Override
        public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (true){String command = in.readLine();
            System.out.println(command);
            if ("SIGNUP".equals(command)) {
                String username = in.readLine();
                String password = in.readLine();
                System.out.println(username);
                System.out.println(password);
                String result = signUpLogic.signUp(username, password);
                out.println(result);
            } else if ("Login".equals(command)) {
                String username = in.readLine();
                String password = in.readLine();
                String result = loginLogic.login(username,password);
                out.println(result);

            } else if ("UPLOAD".equals(command)) {
                String cm = in.readLine();
                long size = Long.parseLong(cm);
                System.out.println(size);
                String name = in.readLine();



//                byte[] receiveBuffer = new byte[1];
//                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
//                DatagramSocket udp_socket = new DatagramSocket(8000);
//                System.out.println("SHIT!");
//                while (true){
//                    udp_socket.receive(receivePacket);
//                    System.out.println("I recieved one you koskesh!");
//                    System.out.println("I Recieved "+String.valueOf(receivePacket.getData()[0]));
//                }



               receiveFileLogic.receiveFile(out,size, name);
            }else{
                break;
            }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

