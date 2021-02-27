import javax.crypto.Cipher;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.Base64;

public class Panel extends JPanel implements ActionListener {

    JTextArea messageArea = new JTextArea();
    JTextArea textArea = new JTextArea();
    JTextArea cryptedTextArea = new JTextArea();
    JScrollPane scrollPaneMessage;
    JScrollPane scrollPaneText;
    JScrollPane scrollPaneCrypted;
    JButton connect = new JButton("► Connect");
    JButton disconnect = new JButton("■ Disconnect");
    JButton encrypt = new JButton("Encrypt");
    JButton send = new JButton("→Send");
    JLabel server = new JLabel("Server");
    JLabel method = new JLabel("Method");
    JLabel mode = new JLabel("Mode");
    JLabel notConnected = new JLabel("Not Connected");
    JLabel connected = new JLabel("Connected:");
    JLabel text = new JLabel("Text");
    JLabel cryptedText = new JLabel("Crypted Text");
    JRadioButton AES = new JRadioButton("AES");
    JRadioButton DES = new JRadioButton("DES");
    JRadioButton CBC = new JRadioButton("CBC");
    JRadioButton OFB = new JRadioButton("OFB");
    DataOutputStream outToServer;
    ButtonGroup group_method = new ButtonGroup();
    ButtonGroup group_mode = new ButtonGroup();
    Base64.Encoder encoder = Base64.getEncoder();
    Base64.Decoder decoder = Base64.getDecoder();

    public String getUsername() {
        return username;
    }

    String username = "";

    public Panel(DataOutputStream outToServer) {
        setLayout(null);
        setBackground(new java.awt.Color(225, 225, 225));
        initComponents();
        this.outToServer = outToServer;
    }

    private void initComponents() {
        scrollPaneMessage = new JScrollPane();
        scrollPaneMessage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneText = new JScrollPane();
        scrollPaneText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneCrypted = new JScrollPane();
        scrollPaneCrypted.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        server.setForeground(new java.awt.Color(0, 0, 0));
        server.setBounds(7, 4, 70, 20);
        server.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 13));
        method.setForeground(new java.awt.Color(0, 0, 0));
        method.setBounds(300, 28, 70, 25);
        method.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 13));
        mode.setForeground(new java.awt.Color(0, 0, 0));
        mode.setBounds(433, 28, 70, 25);
        mode.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 13));
        connect.setForeground(new java.awt.Color(0, 0, 0));
        connect.setBounds(35, 45, 100, 25);
        connect.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
        disconnect.setForeground(new java.awt.Color(0, 0, 0));
        disconnect.setBounds(138, 45, 100, 25);
        disconnect.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
        disconnect.setEnabled(false);
        encrypt.setForeground(new java.awt.Color(0, 0, 0));
        encrypt.setBounds(444, 680, 70, 25);
        encrypt.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
        send.setForeground(new java.awt.Color(0, 0, 0));
        send.setBounds(515, 675, 68, 35);
        send.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
        send.setEnabled(false);
        connected.setForeground(new java.awt.Color(0, 0, 0));
        connected.setBounds(2, 740, 598, 20);
        connected.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 13));
        notConnected.setForeground(new java.awt.Color(0, 0, 0));
        notConnected.setBounds(2, 740, 598, 20);
        notConnected.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 13));
        text.setForeground(new java.awt.Color(0, 0, 0));
        text.setBounds(15, 633, 50, 25);
        text.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 13));
        cryptedText.setForeground(new java.awt.Color(0, 0, 0));
        cryptedText.setBounds(235, 633, 90, 25);
        cryptedText.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 13));
        cryptedTextArea.setEditable(false);
        AES.setForeground(new java.awt.Color(0, 0, 0));
        AES.setBounds(297, 47, 50, 25);
        AES.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
        DES.setForeground(new java.awt.Color(0, 0, 0));
        DES.setBounds(350, 47, 50, 25);
        DES.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
        CBC.setForeground(new java.awt.Color(0, 0, 0));
        CBC.setBounds(430, 47, 50, 25);
        CBC.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
        OFB.setForeground(new java.awt.Color(0, 0, 0));
        OFB.setBounds(483, 47, 50, 25);
        OFB.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
        messageArea.setForeground(new java.awt.Color(0, 0, 0));
        messageArea.setBounds(0, 90, 587, 550);
        messageArea.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 13));
        messageArea.setEditable(false);
        textArea.setBounds(4, 652, 210, 80);
        textArea.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
        cryptedTextArea.setBounds(225, 652, 210, 80);
        cryptedTextArea.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
        scrollPaneMessage.setBounds(0, 90, 587, 550);
        scrollPaneMessage.getViewport().setBackground(Color.WHITE);
        scrollPaneMessage.getViewport().add(messageArea);
        scrollPaneText.setBounds(4, 652, 210, 80);
        scrollPaneText.getViewport().setBackground(Color.WHITE);
        scrollPaneText.getViewport().add(textArea);
        scrollPaneCrypted.setBounds(225, 652, 210, 80);
        scrollPaneCrypted.getViewport().setBackground(Color.WHITE);
        scrollPaneCrypted.getViewport().add(cryptedTextArea);
        group_method.add(AES);
        group_method.add(DES);
        group_mode.add(CBC);
        group_mode.add(OFB);
        AES.setEnabled(false);
        DES.setEnabled(false);
        OFB.setEnabled(false);
        CBC.setEnabled(false);

        connect.addActionListener(this);
        disconnect.addActionListener(this);
        encrypt.addActionListener(this);
        send.addActionListener(this);
        AES.addActionListener(this);
        DES.addActionListener(this);
        CBC.addActionListener(this);
        OFB.addActionListener(this);

        add(connect);
        add(disconnect);
        add(encrypt);
        add(send);
        add(server);
        add(method);
        add(mode);
        add(text);
        add(cryptedText);
        add(notConnected);
        add(AES);
        add(DES);
        add(CBC);
        add(OFB);
        add(scrollPaneMessage);
        add(scrollPaneText);
        add(scrollPaneCrypted);
    }

    public void paint(Graphics gp) {
        super.paint(gp);
        setForeground(new java.awt.Color(195, 195, 195));
        Graphics2D graphics = (Graphics2D) gp;
        Line2D serverAltina = new Line2D.Float(0, 25, 600, 25);
        Line2D method1 = new Line2D.Float(296, 38, 290, 38);
        Line2D method2 = new Line2D.Float(290, 38, 290, 76);
        Line2D method3 = new Line2D.Float(290, 76, 403, 76);
        Line2D method4 = new Line2D.Float(403, 76, 403, 38);
        Line2D method5 = new Line2D.Float(403, 38, 345, 38);
        Line2D mode1 = new Line2D.Float(429, 38, 423, 38);
        Line2D mode2 = new Line2D.Float(423, 38, 423, 76);
        Line2D mode3 = new Line2D.Float(423, 76, 536, 76);
        Line2D mode4 = new Line2D.Float(536, 76, 536, 38);
        Line2D mode5 = new Line2D.Float(536, 38, 468, 38);
        Line2D text1 = new Line2D.Float(11, 644, 2, 644);
        Line2D text2 = new Line2D.Float(2, 644, 2, 735);
        Line2D text3 = new Line2D.Float(2, 735, 220, 735);
        Line2D text4 = new Line2D.Float(220, 735, 220, 644);
        Line2D text5 = new Line2D.Float(220, 644, 45, 644);
        Line2D cryptedText1 = new Line2D.Float(231, 644, 223, 644);
        Line2D cryptedText2 = new Line2D.Float(223, 644, 223, 735);
        Line2D cryptedText3 = new Line2D.Float(223, 735, 440, 735);
        Line2D cryptedText4 = new Line2D.Float(440, 735, 440, 644);
        Line2D cryptedText5 = new Line2D.Float(440, 644, 310, 644);
        Line2D connectedUstune = new Line2D.Float(0, 737, 600, 737);

        draw(graphics, serverAltina, method1, method2, method3, method4, method5, mode1, mode2, mode3, mode4, mode5);
        draw(graphics, text1, text2, text3, text4, text5, cryptedText1, cryptedText2, cryptedText3, cryptedText4, cryptedText5, connectedUstune);


    }

    private void draw(Graphics2D graphics, Line2D serverAltina, Line2D method1, Line2D method2, Line2D method3, Line2D method4, Line2D method5, Line2D mode1, Line2D mode2, Line2D mode3, Line2D mode4, Line2D mode5) {
        graphics.draw(serverAltina);
        graphics.draw(method1);
        graphics.draw(method2);
        graphics.draw(method3);
        graphics.draw(method4);
        graphics.draw(method5);
        graphics.draw(mode1);
        graphics.draw(mode2);
        graphics.draw(mode3);
        graphics.draw(mode4);
        graphics.draw(mode5);
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        byte[] decoded_crypted_text;
        String decoded_decrypted_text = "";
        if (src == connect) {
            username = JOptionPane.showInputDialog("Enter your name: ");
            connect.setEnabled(false);
            disconnect.setEnabled(true);
            notConnected.setText(connected.getText() + " " + username);
            AES.setEnabled(true);
            DES.setEnabled(true);
            OFB.setEnabled(true);
            CBC.setEnabled(true);
        }

        if (src == send) {
            decoded_crypted_text = decoder.decode(cryptedTextArea.getText());
            try {
                if (AES.isSelected() && CBC.isSelected()) {
                    try {
                        decoded_decrypted_text = doDecryption(decoded_crypted_text, "AES/CBC/PKCS5Padding");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                if (AES.isSelected() && OFB.isSelected()) {
                    try {
                        decoded_decrypted_text = doDecryption(decoded_crypted_text, "AES/OFB/PKCS5Padding");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
                if (DES.isSelected() && CBC.isSelected()) {
                    try {
                        decoded_decrypted_text = doDecryption(decoded_crypted_text, "DES/CBC/PKCS5Padding");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
                if (DES.isSelected() && OFB.isSelected()) {
                    try {
                        decoded_decrypted_text = doDecryption(decoded_crypted_text, "DES/OFB/PKCS5Padding");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
                try {
                    File f1 = new File("log.txt");
                    if(!f1.exists()) {
                        f1.createNewFile();
                    }

                    FileWriter fileWritter = new FileWriter(f1.getName(),true);
                    BufferedWriter bw = new BufferedWriter(fileWritter);
                    bw.write("\n"+username+"> " + cryptedTextArea.getText());
                    bw.close();
                } catch(IOException t){
                    t.printStackTrace();
                }
                outToServer.writeUTF(cryptedTextArea.getText() + "\n" + username + "> " + decoded_decrypted_text);
                send.setEnabled(false);
                textArea.setText("");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if (src == encrypt) {
            if (!(AES.isSelected() || DES.isSelected())) {
                JOptionPane.showMessageDialog(this, "Choose Method Please", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (!(CBC.isSelected() || OFB.isSelected())) {
                JOptionPane.showMessageDialog(this, "Choose Mode Please", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            if (((AES.isSelected()) || DES.isSelected()) && ((CBC.isSelected()) || OFB.isSelected())) {
                String text_written = textArea.getText();
                byte[] encrypted = new byte[0];
                if (AES.isSelected() && CBC.isSelected()) {
                    try {
                        //encrypt = new DESCBC("AES","AES/CBC/PKCS5Padding");
                        outToServer.writeUTF("AES");
                        encrypted = doEncryption(text_written, "AES/CBC/PKCS5Padding");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                if (AES.isSelected() && OFB.isSelected()) {
                    try {
                        //encrypt = new DESCBC("AES","AES/OFB/PKCS5Padding");
                        outToServer.writeUTF("AES");
                        encrypted = doEncryption(text_written, "AES/OFB/PKCS5Padding");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
                if (DES.isSelected() && CBC.isSelected()) {
                    try {
                        //encrypt = new DESCBC("DES","DES/CBC/PKCS5Padding");
                        outToServer.writeUTF("DES");
                        encrypted = doEncryption(text_written, "DES/CBC/PKCS5Padding");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
                if (DES.isSelected() && OFB.isSelected()) {
                    try {
                        //encrypt = new DESCBC("DES","DES/OFB/PKCS5Padding");
                        outToServer.writeUTF("DES");
                        encrypted = doEncryption(text_written, "DES/OFB/PKCS5Padding");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
                String encoded = encoder.encodeToString(encrypted);
                try {
                    outToServer.writeUTF( username + "-->" + encoded);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                //System.out.println(encoded);

                cryptedTextArea.setText(String.valueOf(encoded));
                send.setEnabled(true);
            }
        }
        if (src == disconnect) {
            System.exit(0);
        }
    }

    public byte[] doEncryption(String s, String mode) throws Exception {
        // Initialize the cipher for encryption
        Cipher c = Cipher.getInstance(mode);
        c.init(Cipher.ENCRYPT_MODE, Client.key, Client.iv);

        //sensitive information
        byte[] text = s.getBytes();

        // Encrypt the text

        return (c.doFinal(text));
    }

    public String doDecryption(byte[] s, String mode) throws Exception {
        Cipher c = Cipher.getInstance(mode);

        // Initialize the same cipher for decryption
        c.init(Cipher.DECRYPT_MODE, Client.key, Client.iv);

        // Decrypt the text
        byte[] textDecrypted = c.doFinal(s);

        return (new String(textDecrypted));
    }

}
