import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Vector;
import java.io.*;
import java.util.*;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static Vector<Socket> ClientSockets;
    int clientCount;

    public static KeyGenerator DESkeygenerator = null;
    public static SecretKey myDesKey = null;
    public static IvParameterSpec DESiv = null;
    public static KeyGenerator AESkeygenerator = null;
    public static SecretKey myAesKey = null;
    public static IvParameterSpec AESiv = null;

    Base64.Encoder encoder = Base64.getEncoder();
    String encoded_aeskey;
    String encoded_deskey;
    String encoded_desiv;
    String encoded_aesiv;


    Server() throws IOException, NoSuchAlgorithmException {
        ServerSocket server = new ServerSocket(8000);
        ClientSockets = new Vector<>();

        DESkeygenerator = KeyGenerator.getInstance("DES");
        DESkeygenerator.init(new SecureRandom());
        myDesKey = DESkeygenerator.generateKey();
        byte[] iv_arr_des = new byte[8];
        SecureRandom random_des = new SecureRandom();
        random_des.nextBytes(iv_arr_des);
        DESiv = new IvParameterSpec(iv_arr_des);

        byte[] deskey_arr_to_be_encoded = myDesKey.getEncoded();
        encoded_deskey = encoder.encodeToString(deskey_arr_to_be_encoded);

        byte[] desiv_arr_to_be_encoded = DESiv.getIV();
        encoded_desiv = encoder.encodeToString(desiv_arr_to_be_encoded);

        AESkeygenerator = KeyGenerator.getInstance("AES");
        AESkeygenerator.init(new SecureRandom());
        myAesKey = AESkeygenerator.generateKey();
        byte[] iv_arr_aes = new byte[16];
        SecureRandom random_aes = new SecureRandom();
        random_aes.nextBytes(iv_arr_aes);
        AESiv = new IvParameterSpec(iv_arr_aes);

        byte[] aeskey_arr_to_be_encoded = myAesKey.getEncoded();
        encoded_aeskey = encoder.encodeToString(aeskey_arr_to_be_encoded);

        byte[] aesiv_arr_to_be_encoded = AESiv.getIV();
        encoded_aesiv = encoder.encodeToString(aesiv_arr_to_be_encoded);
        System.out.println("Base64 Encoded DES Key = " + encoded_deskey);
        System.out.println("Base64 Encoded DES IV = " + encoded_desiv);
        System.out.println("Base64 Encoded AES Key = " + encoded_aeskey);
        System.out.println("Base64 Encoded AES IV = " + encoded_aesiv);
        try {
            File f1 = new File("log.txt");
            if (!f1.exists()) {
                f1.createNewFile();
            }

            FileWriter fileWritter = new FileWriter(f1.getName(), true);
            BufferedWriter bw = new BufferedWriter(fileWritter);
            bw.write("\nA new server has been created\n");
            bw.write("Base64 Encoded DES Key = " + encoded_deskey + "\n");
            bw.write("Base64 Encoded DES IV = " + encoded_desiv + "\n");
            bw.write("Base64 Encoded AES Key = " + encoded_aeskey + "\n");
            bw.write("Base64 Encoded AES IV = " + encoded_aesiv + "\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            Socket client = server.accept();
            new AcceptClient(client);
        }
        //server.close();
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        new Server();
    }

    class AcceptClient extends Thread {
        Socket ClientSocket;
        DataInputStream din;
        DataOutputStream dout;

        AcceptClient(Socket client) throws IOException {
            ClientSocket = client;
            din = new DataInputStream(ClientSocket.getInputStream());
            dout = new DataOutputStream(ClientSocket.getOutputStream());

            clientCount++;
            ClientSockets.add(ClientSocket);

            start();
        }

        public void run() {
            try {
                while (true) {
                    String msgFromClient = din.readUTF();
                    if (msgFromClient.equals("AES")) {
                        for (int i = 0; i < ClientSockets.size(); i++) {
                            Socket pSocket = ClientSockets.elementAt(i);
                            if (pSocket.isClosed()) {
                                ClientSockets.remove(i);
                            }
                            DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
                            pOut.writeUTF("kaes" + encoded_aeskey);
                            pOut.writeUTF("ivaes" + encoded_aesiv);
                            pOut.writeUTF(msgFromClient);
                            pOut.flush();
                        }
                    } else if (msgFromClient.equals("DES")) {
                        for (int i = 0; i < ClientSockets.size(); i++) {
                            Socket pSocket = ClientSockets.elementAt(i);
                            if (pSocket.isClosed()) {
                                ClientSockets.remove(i);
                            }
                            DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
                            pOut.writeUTF("kdes" + encoded_deskey);
                            pOut.writeUTF("ivdes" + encoded_desiv);
                            pOut.writeUTF(msgFromClient);
                            pOut.flush();
                        }
                    } else {
                        for (int i = 0; i < ClientSockets.size(); i++) {
                            Socket pSocket = ClientSockets.elementAt(i);
                            DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
                            if (!msgFromClient.contains("-->")) {
                                pOut.writeUTF(msgFromClient);
                            }
                            pOut.flush();
                        }
                        if (msgFromClient.contains("-->")) {
                            System.out.println(msgFromClient);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}