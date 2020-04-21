package server;

import java.io.*;
import java.net.Socket;

public class UserHandlerThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;

    public UserHandlerThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    void printUsers() {
        if (server.hasUsers()) {
            writer.println("Users in chat: " + server.getUsersNames());
        } else {
            writer.println("Not others users in chat.");
        }
    }

    void sendMessage(String message) {
        writer.println(message);
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            printUsers();

            sendMessage("Print your name:");
            String userName = reader.readLine();
            server.addUserName(userName);

            String serverMessage = "New user connected: " + userName;
            server.sendMessages(serverMessage, this);

            String clientMessage;
            do {
                clientMessage = reader.readLine();
                serverMessage =  userName + ">>> " + clientMessage;
                server.sendMessages(serverMessage, this);
            } while (!clientMessage.equals("EXIT"));

            server.removeUser(userName, this);
            socket.close();

            serverMessage = userName + " - EXIT";
            server.sendMessages(serverMessage, this);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
