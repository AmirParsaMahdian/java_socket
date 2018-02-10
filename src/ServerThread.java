import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread {

    Socket socket;
    ServerThread(Socket socket){
        this.socket = socket;
    }

    static String IV = "AAAAAAAAAAAAAAAA";
    static String encryptionKey = "0123456789abcdef";

    public void run(){

        final Scanner scanner = new Scanner(System.in);

        try{

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            //BufferedReader receiving = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //PrintWriter sending = new PrintWriter(socket.getOutputStream());


            Thread send = new Thread(new Runnable() {

                String msgout = " ";
                boolean conn = true;


                @Override
                public void run() {

                    while (conn){

                        msgout = scanner.nextLine();
                        try {
                            byte[] cipher = encrypt(msgout, encryptionKey);

                            dataOutputStream.writeInt(cipher.length);
                            dataOutputStream.write(cipher);
                            dataOutputStream.flush();

                        } catch (Exception e) {
                            System.out.println("error sending");
                            conn = false;
                            try {
                                socket.close();
                            } catch (Exception E){
                                System.out.println("error closing socket connection");
                            }
                        }
                    }
                }
            });
            send.start();

            Thread receive = new Thread((new Runnable() {

                @Override
                public void run() {

                    boolean conn = true;

                    while (conn){

                        try {
                            int len = dataInputStream.readInt();
                            byte[] cipher = new byte[len];

                            if (len > 0) {
                                dataInputStream.readFully(cipher, 0, len);
                            }

                            System.out.println("Decrypted Info: " + decrypt(cipher, encryptionKey));

                        } catch (Exception e) {
                            System.out.println("error receiving");
                            conn = false;
                            try {
                                socket.close();
                            } catch (Exception E){
                                System.out.println("error closing socket connection");
                            }
                        }
                    }
                }
            }));
            receive.start();

        }catch(Exception e){}
    }

    public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec secretKeySpec = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(IV.getBytes("UTF-8")));
        return cipher.doFinal(plainText.getBytes("UTF-8"));
    }

    public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception{

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec secretKeySpec = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(cipher.doFinal(cipherText),"UTF-8");
    }

}
