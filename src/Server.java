import Loader.UserDatabase;
import Logic.ClientLogic;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static final int PORT = 8080;
    public static void main(String[] args) {
        Server server = new Server();
        UserDatabase.load();
        server.start();
    }
    private void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                new ClientLogic(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        }