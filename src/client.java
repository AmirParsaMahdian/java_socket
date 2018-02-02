import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {

    public static void main(String[] args) {
        try{
            Socket s = new Socket("192.168.1.2",7070);
            String data = " ";
            
            while (!data.equals("exit") ){
                DataInputStream clientin = new DataInputStream(s.getInputStream());
                data = clientin.readUTF();
                System.out.println("Received: " + data);

                DataOutputStream clientout = new DataOutputStream(s.getOutputStream());
                Scanner usrin = new Scanner(System.in);
                clientout.writeUTF(usrin.nextLine());
            }

        }catch(Exception e){}


    }
}
