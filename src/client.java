import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {

    private static String host = "192.168.1.6";
    private static int port = 5050;

    public static void main(String[] args) {

        try{

            Socket s = new Socket(host, port);
            final Scanner scanner = new Scanner(System.in);

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
