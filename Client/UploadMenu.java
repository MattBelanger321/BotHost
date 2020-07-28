import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UploadMenu extends JPanel {
    public static final String serverip = "192.168.0.51";
    public File config = null;
    public File script = null;
    public String name = null;
    public JTextField pageBox;

    public UploadMenu() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
        JButton var1 = new JButton("Choose Config File");
        JButton var3 = new JButton("Choose Script File");
        var1.setBounds(175, 125, 150, 150);
        var1.addActionListener((var1x) -> {
            JFileChooser var2 = new JFileChooser();
            if (var2.showDialog(this, "Send") == 0) {
                this.config = var2.getSelectedFile();
            }

        });
        var3.addActionListener((var3x) -> {
            JFileChooser var2 = new JFileChooser();
            if (var2.showDialog(this, "Send") == 0) {
                this.script = var2.getSelectedFile();
            }

        });
        this.add(var1, "East");
        this.add(var3, "West");
        this.pageBox = new JTextField("Enter Your Name");
        this.pageBox.addActionListener((var1x) -> {
            this.name = this.pageBox.getText();
        });
        this.pageBox.setSize(100, 50);
        this.add(this.pageBox, "North");
    }

    public String getName() {
        this.name = this.pageBox.getText();
        return this.name;
    }
}
