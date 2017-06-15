/*
 * custom rcon console for high level admins
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

public class RconConsole {

    private Logger logger = Logger.getLogger(RconConsole.class.getName());

    private JFrame frame;
    private JPanel main, entryPanel, outputPanel, northPanel;
    private JTextArea console;
    private JLabel input;
    private JButton submit, clear;
    private JTextField command;

    public RconConsole() {
        frame = new JFrame("RCon Console");

        init();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void init() {
        main = new JPanel();
        main.setLayout(new BorderLayout());
        frame.add(main);

        northPanel = new JPanel();
        entryPanel = new JPanel();
        outputPanel = new JPanel();

        outputPanel();
        northPanel();
        entryPanel();

        implementListeners();
    }
    
    public void entryPanel() {
        main.add(entryPanel, BorderLayout.SOUTH);
        submit = new JButton("Submit");
        input = new JLabel("Command:");
        command = new JTextField(30);
        
        entryPanel.add(input);
        entryPanel.add(command);
        entryPanel.add(submit);
    }

    public void northPanel() {
        main.add(northPanel, BorderLayout.NORTH);
        clear = new JButton("Clear Console");
        northPanel.add(clear);
    }

    public void outputPanel() {
        main.add(outputPanel, BorderLayout.CENTER);
        console = new JTextArea(20, 70);
        console.setEditable(false);
        JScrollPane sp = new JScrollPane(console);

        outputPanel.add(sp);
    }

    private void implementListeners() {
        submit.addActionListener(new RconConsoleListener());
        submit.addKeyListener(new RconConsoleListener());
        command.addKeyListener(new RconConsoleListener());
        clear.addActionListener(new RconConsoleListener());
    }

    private class RconConsoleListener extends KeyAdapter implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == submit ) {
                sendCommand();
            }
            if (e.getSource() == clear) {
                console.setText(null);
            }
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                sendCommand();
            }
        }
    }
    
    private void sendCommand() {
        console.append(NetProtocol.sendPlainRcon(command.getText()) + "\n");
    }
}
