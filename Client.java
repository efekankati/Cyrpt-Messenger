import javax.crypto.SecretKey;
import java.net.Socket;
import java.io.*;
import java.util.Base64;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Client implements Runnable {

    Socket socketConnection;
    DataOutputStream outToServer;
    DataInputStream din;
    frameClass frameClass;
    Panel anaPanel;
    String usernamesi;
    public static SecretKey key;
    public static IvParameterSpec iv;
    Base64.Decoder decoder = Base64.getDecoder();

    Client() throws IOException {
        socketConnection = new Socket("127.0.0.1", 8000);
        outToServer = new DataOutputStream(socketConnection.getOutputStream());
        din = new DataInputStream(socketConnection.getInputStream());
        frameClass = new frameClass();
        anaPanel = new Panel(outToServer);
        frameClass.arayuz.add(anaPanel);
        frameClass.arayuz.setVisible(true);

        Thread thread;
        thread = new Thread(this);
        thread.start();
    }

    public static void main(String[] arg) throws IOException {
        new Client();
    }

    public void run() {
        while (true) {
            try {
                String message = din.readUTF();
                String des_key;
                String aes_key;
                String des_iv;
                String aes_iv;
                byte[] des_arr_key;
                byte[] des_arr_iv;
                byte[] aes_arr_key;
                byte[] aes_arr_iv;
                if (message.startsWith("kdes")) {
                    des_key = message.substring(4);
                    des_arr_key = decoder.decode(des_key);
                    key = new SecretKeySpec(des_arr_key, 0, des_arr_key.length, "DES");
                } else if (message.startsWith("ivdes")) {
                    des_iv = message.substring(5);
                    des_arr_iv = decoder.decode(des_iv);
                    iv = new IvParameterSpec(des_arr_iv);
                } else if (message.startsWith("kaes")) {
                    aes_key = message.substring(4);
                    aes_arr_key = decoder.decode(aes_key);
                    key = new SecretKeySpec(aes_arr_key, 0, aes_arr_key.length, "AES");
                } else if (message.startsWith("ivaes")) {
                    aes_iv = message.substring(5);
                    aes_arr_iv = decoder.decode(aes_iv);
                    iv = new IvParameterSpec(aes_arr_iv);
                } else {
                    anaPanel.messageArea.setText(anaPanel.messageArea.getText() + "\n" + din.readUTF());
                    usernamesi = anaPanel.getUsername();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}