import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class MyProfile extends JFrame
{

    private JFrame frame;

    private Logger logger = Logger.getLogger(MyProfile.class.getName());
    private JTextField name, databaseid, rank, connections, guid;
    private JButton chgPasswd;
    private JLabel userLabel, databaseLabel, rankLabel, connectionsLabel, guidLabel;
    private JPanel main, labelPanel, fieldPanel, buttonPanel;

    public MyProfile()
    {
        frame = new JFrame("My Profile");
        frame.setIconImages(IconLoader.getList());
        main = new JPanel();
        main.setLayout(new BorderLayout(5, 5));
        frame.add(main);

        GridLayout layout = new GridLayout(5, 1);
        layout.setVgap(2);
        labelPanel = new JPanel();
        labelPanel.setLayout(layout);
        fieldPanel = new JPanel();
        fieldPanel.setLayout(layout);
        buttonPanel = new JPanel();
        main.add(labelPanel, BorderLayout.WEST);
        main.add(fieldPanel, BorderLayout.CENTER);
        main.add(buttonPanel, BorderLayout.SOUTH);

        userLabel = new JLabel("Name: ");
        databaseLabel = new JLabel("Database ID: ");
        guidLabel = new JLabel("GUID: ");
        connectionsLabel = new JLabel("Connections: ");
        rankLabel = new JLabel("Admin Level: ");
        labelPanel.add(userLabel);
        labelPanel.add(databaseLabel);
        labelPanel.add(guidLabel);
        labelPanel.add(connectionsLabel);
        labelPanel.add(rankLabel);

        name = new JTextField(Profile.getName());
        name.setEditable(false);
        name.setBorder(null);
        name.setBackground(null);
        databaseid = new JTextField(Profile.getClientid());
        databaseid.setEditable(false);
        databaseid.setBorder(null);
        databaseid.setBackground(null);
        guid = new JTextField(Profile.getGuid());
        guid.setEditable(false);
        guid.setBorder(null);
        guid.setBackground(null);
        connections = new JTextField(Profile.getConnections());
        connections.setEditable(false);
        connections.setBorder(null);
        connections.setBackground(null);
        rank = new JTextField(Profile.getTitle() + " (" + Profile.getLevel() + ")");
        rank.setEditable(false);
        rank.setBorder(null);
        rank.setBackground(null);

        fieldPanel.add(name);
        fieldPanel.add(databaseid);
        fieldPanel.add(guid);
        fieldPanel.add(connections);
        fieldPanel.add(rank);

        chgPasswd = new JButton("Change my Password");
        buttonPanel.add(chgPasswd);

        addListeners();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void addListeners()
    {
        chgPasswd.addActionListener(new MyProfileListener());
    }

    private class MyProfileListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == chgPasswd)
            {
                new Password();
            }
        }
    }
}
