import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;

class BotWebClient extends JFrame{
    private JTextField textBox;
    private JPanel panel;
    private DataInputStream in;
    private Socket sock;
    private String name = null;
    private File file = null;
    private DataOutputStream submissionSender;
    private JFrame uploader;
    private UploadMenu upload;

    public static void main(String[] args) throws IOException {
        System.out.println("<program launched>");
        new BotWebClient();
    }

    public BotWebClient(){
        super("Client");
        uploader = new JFrame("Uploader");
        uploader.setLayout(new BorderLayout());
        uploader.setBounds(500,0,500,500);
        upload = new UploadMenu();
        uploader.add(upload);

        //Add Open Client Button
        JButton button1 = new JButton("Open Client");
        button1.setSize(100,25);
        uploader.add(button1,BorderLayout.SOUTH);
        button1.addActionListener(e->{
            buildFrames(); // Builds main client frame
            addTextBoxActionListener();
        });
        uploader.setVisible(true);
    }

    //Submits IP Address in the textbox and connects to server
    private void addTextBoxActionListener() {
        textBox.addActionListener(actionEvent -> {
            panel.removeAll();
            send();
            System.exit(0);
        });
    }

    //Sends submission to server
    private void send(){
        try {
            sock = new Socket(textBox.getText(), 55588);
            submissionSender = new DataOutputStream(sock.getOutputStream());
            DataInputStream confirmReceiver = new DataInputStream(sock.getInputStream());

            submissionSender.writeUTF(upload.script.getName().substring(0,upload.script.getName().indexOf('.')));

            submissionSender.writeUTF(upload.script.getName());
            file = upload.script;
            writeFile();

            System.out.println(confirmReceiver.readUTF());

            submissionSender.writeUTF("config.txt");
            file = upload.config;
            writeFile();

            System.out.println(confirmReceiver.readUTF());
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This methods sends file to the server
    private void writeFile() throws IOException {
        FileInputStream fis = new FileInputStream(file);
        long size = file.length();
        byte[] byteArray = new byte[(int) size];
        fis.read(byteArray);
        System.err.println("SENDING SUBMISSION...");
        submissionSender.writeInt((int)size);
        System.err.println("File Size Sent");
        submissionSender.write(byteArray, 0, (int)size);
        System.err.println("File Sent");
        //submissionSender.flush();
    }

    //This function builds UI frame
    private void buildFrames() {//Main frame
        setSize(500,500);
        setLayout(new BorderLayout());
        textBox = new JTextField("ENTER THE IP ADDRESS");
        add(textBox, BorderLayout.NORTH);
        setVisible(true);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(this.getBounds());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}