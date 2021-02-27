import javax.swing.*;


public class frameClass {

    JFrame arayuz;
    public frameClass() {

        arayuz = new JFrame("Crypto Messenger");
        arayuz.setFocusable(false);
        arayuz.setSize(600,800);
        arayuz.setLocationRelativeTo(null);
        arayuz.setResizable(false);
        arayuz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}