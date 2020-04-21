package client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private String hostname;
    private int port;
    private String userName;

    public ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 8080;
        ChatClient client = new ChatClient(hostname, port);
        client.execute();
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");

            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found");
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    String getUserName() {
        return this.userName;
    }


    /******************************************************************************************************************/
    public class ReadThread extends Thread {
        private BufferedReader reader;
        private Socket socket;
        private ChatClient client;

        public ReadThread(Socket socket, ChatClient client) {
            this.socket = socket;
            this.client = client;

            try {
                InputStream input = socket.getInputStream();
                reader = new BufferedReader(new InputStreamReader(input));
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        public void run() {
            while (true) {
                try {
                    String response = reader.readLine();
                    System.out.println("\n" + response);

                    if (client.getUserName() != null) {
                        System.out.print(client.getUserName() + ">>> ");
                    }
                } catch (IOException ex) {
                    System.out.println("Error reading from server: " + ex.getMessage());
                    ex.printStackTrace();
                    break;
                }
            }
        }
    }

    /******************************************************************************************************************/
    public class WriteThread extends Thread {
        private PrintWriter writer;
        private Socket socket;
        private ChatClient client;

        public WriteThread(Socket socket, ChatClient client) {
            this.socket = socket;
            this.client = client;

            try {
                OutputStream output = socket.getOutputStream();
                writer = new PrintWriter(output, true);
            } catch (IOException ex) {
                System.out.println("Error readeing from server stream: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        public void run() {
            Console console = System.console();
            String userName = console.readLine("\nEnter your name: ");
            client.setUserName(userName);
            writer.println(userName);
            String text;

            do {
                text = console.readLine(userName + ">>> ");
                writer.println(text);

            } while (!text.equals("EXIT"));

            try {
                socket.close();
            } catch (IOException ex) {

                System.out.println("Error writing to server: " + ex.getMessage());
            }
        }
    }
}
