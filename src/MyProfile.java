import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.*;
import java.util.Properties;
import java.util.logging.Logger;

public class MyProfile extends JFrame
{
	private JFrame frame;

	private Logger logger = Logger.getLogger(MyProfile.class.getName());
	private JTextField name, databaseid, rank, connections, guid;
	private JLabel userLabel, databaseLabel, rankLabel, connectionsLabel, guidLabel;
	private JPanel labelPanel, fieldPanel;

	public MyProfile()
	{
		frame = new JFrame("My Profile");
		frame.setLayout(new GridLayout(1, 2));

		GridLayout layout = new GridLayout(5, 1);
		layout.setVgap(2);
		labelPanel = new JPanel();
		labelPanel.setLayout(layout);
		fieldPanel = new JPanel();
		fieldPanel.setLayout(layout);
		frame.add(labelPanel);
		frame.add(fieldPanel);

		userLabel = new JLabel("Name: ");
		databaseLabel = new JLabel("Database ID: ");
		guidLabel = new JLabel("GUID: ");
		connectionsLabel = new JLabel("Number of Connections: ");
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


		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
