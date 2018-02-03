import java.net.*;
import java.io.*;
import java.util.Scanner;

public class server{

    public static void main(String[] args) {

        try {

            ServerSocket serverSocket = new ServerSocket(5050);
            System.out.println("Server created.");

            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("Client connected.: " + socket);
                new ServerThread(socket).start();
            }

        } catch (Exception e){}

    }
}
