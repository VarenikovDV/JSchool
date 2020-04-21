package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private final int PORT;
    private Set<String> usersNames = new HashSet<>();
    private Set<UserHandlerThread> userHandlerThreadSet = new HashSet<>();

    public ChatServer(int port) {
        this.PORT = port;
    }

    public static void main(String[] args) {
        int port = 8080;
        System.out.println( "Start server on port: " + port );
        ChatServer server = new ChatServer(port);
        server.startListener();
    }

    public void startListener() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Listening port: " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected.");
                UserHandlerThread userHandlerThread = new UserHandlerThread(socket, this);
                userHandlerThreadSet.add(userHandlerThread);
                userHandlerThread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    void sendMessages(String message, UserHandlerThread excludeUser) {
        for (UserHandlerThread u : userHandlerThreadSet) {
            if (u != excludeUser) {
                u.sendMessage(message);
            }
        }
    }

    void addUserName(String userName) {
        usersNames.add(userName);
    }

    Set<String> getUsersNames() {
        return this.usersNames;
    }

    void removeUser(String userName, UserHandlerThread userHandlerThread) {
        boolean removed = usersNames.remove(userName);
        userHandlerThreadSet.remove(userHandlerThread);
        System.out.println("User " + userName + " exit");
    }

    boolean hasUsers() {
        return !this.usersNames.isEmpty();
    }
}
