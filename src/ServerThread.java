import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread {

    Socket socket;
    ServerThread(Socket socket){
        this.socket = socket;
    }

    public void run(){

        final Scanner scanner = new Scanner(System.in);

        try{

            DataInputStream receiving = new DataInputStream(socket.getInputStream());
            //BufferedReader receiving = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream sending = new DataOutputStream(socket.getOutputStream());
            //PrintWriter sending = new PrintWriter(socket.getOutputStream());

            Thread send = new Thread(new Runnable() {

                String msgout = " ";

                @Override
                public void run() {

                    while (msgout != null){

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

                String msgin = " ";

                @Override
                public void run() {

                    while (msgin != null){

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
