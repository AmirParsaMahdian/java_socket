
import java.net.*;

public class server {
    public static void main(String[] args ){

        try{
            ServerSocket serverSocket = new ServerSocket(5050);
            System.out.println("Socket server created on port 5050" );

            while (true) {
                Socket socket = serverSocket.accept( );
                System.out.println("Client connected: " + socket);
                new ServerThread(socket).start();
            }
        } catch (Exception e){ System.out.println(e); }
    }
}
