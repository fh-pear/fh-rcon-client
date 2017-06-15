/*
 * Window displaying results from map_rotate or map commands
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapRotateResults {

    private JFrame frame, root;
    private JPanel main, buttonPanel;
    private JButton okay;
    private JTextArea response;

    public MapRotateResults(JFrame master, String reply) {
        root = master;

        frame = new JFrame("Map Rotation Server Reply");
        frame.setIconImages(IconLoader.getList());
        main = new JPanel();
        main.setLayout(new BorderLayout());
        frame.add(main);

        textbox(reply);
        southpanel();
        setupListeners();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void textbox(String reply) {
        response = new JTextArea(reply, 30, 50);
        response.setEditable(false);
        JScrollPane sp = new JScrollPane(response);
        main.add(sp, BorderLayout.CENTER);
    }
    
    public void southpanel() {
        buttonPanel = new JPanel();
        okay = new JButton("Okay");
        buttonPanel.add(okay);
        main.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setupListeners() {
        okay.addActionListener(new MapRotateResultsListener());
        okay.addKeyListener(new MapRotateResultsListener());
    }

    private class MapRotateResultsListener extends KeyAdapter implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == okay) {
                if (root != null)
                    root.dispose();
                frame.dispose();
            }

        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (root != null)
                    root.dispose();
                frame.dispose();
            }
        }
    }
}
