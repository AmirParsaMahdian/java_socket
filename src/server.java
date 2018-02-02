import java.net.*;
import java.io.*;
import java.util.Scanner;

public class server {

    public static void main(String[] args) {

        try{
            ServerSocket server = new ServerSocket(7070);
            Socket s = server.accept();
            System.out.println("Connected");
            String data = " ";

            while (data != "exit") {
                DataOutputStream serverout = new DataOutputStream(s.getOutputStream());
                Scanner usrin = new Scanner(System.in);
                serverout.writeUTF(usrin.nextLine());

                DataInputStream serverin = new DataInputStream(s.getInputStream());
                data = serverin.readUTF();
                System.out.println("received: " + data);

            }

        }catch(Exception e){}

    }
}
