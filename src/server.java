import java.net.*;
import java.io.*;
import java.util.Scanner;

public class server extends Thread{

    public static void main(String[] args) {

        Socket s;

        final Scanner scanner = new Scanner(System.in);

        try{

            ServerSocket serverSocket = new ServerSocket(5050);
            System.out.println("Server created.");
            s = serverSocket.accept();
            System.out.println("Client connected.");

            DataInputStream receiving = new DataInputStream(s.getInputStream());
            //BufferedReader receiving = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream sending = new DataOutputStream(s.getOutputStream());
            //PrintWriter sending = new PrintWriter(s.getOutputStream());


            Thread send = new Thread(new Runnable() {

                String msgout;

                @Override
                public void run() {

                    while (true){

                        msgout = scanner.nextLine();

                        try {
                            sending.writeUTF(msgout);
                            sending.flush();
                        } catch (Exception e) {}
                    }
                }
            });
            send.start();

            Thread receive = new Thread((new Runnable() {

                String msgin;

                @Override
                public void run() {

                    while (true){

                        try {
                            msgin = receiving.readUTF();

                        } catch (Exception e) {}

                        System.out.println("Received: " + msgin);
                    }
                }
            }));
            receive.start();

        }catch(Exception e){}
    }
}
