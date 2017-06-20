import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.logging.Logger;
/*
 * TODO: find/implement a way to handle when no user is present in database
 */

public class PlayerDetails
{
	private Client c;
	private JTextField dataid, guid, name, connections, level, firstSeen, lastSeen;
	private JFrame frame;
	private JPanel topPanel, aliasPanel, penaltiesPanel, centerPanel;
	private JTable table;
	private AliasTableModel dtm;
	private PenaltyTableModel dtm1;
        private JMenuBar menu;
        private JMenuItem ban, tempban, addrcon, addclan, penaltyinfo;
        private JMenu admin, penalties;

	private String databaseId;
	private Logger logger = Logger.getLogger(PlayerDetails.class.getName());

	public PlayerDetails(Client cl)
	{
		c = cl;

		frame = new JFrame("Player Details: " + c.getName());
		frameSetup();

		try
		{
			init(); //this will get the databaseid
			topPanel();
			aliasPanel();
			penaltiesPanel();

			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		} catch (ArrayIndexOutOfBoundsException e)
		{
			JOptionPane.showMessageDialog(null,
					"It appears there is not a database entry for " + c.getName() + " yet",
					"No Database Record Found",
					JOptionPane.ERROR_MESSAGE);

			frame.dispose();
		}
	}

	public PlayerDetails(String clientid)
	{
		frame = new JFrame("Player Details: ");
		frameSetup();

		databaseId = clientid;
		databaseId = databaseId.trim();

		try
		{
			topPanel();
			aliasPanel();
			penaltiesPanel();

			frame.setTitle(frame.getTitle() + name.getText());

			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		} catch (ArrayIndexOutOfBoundsException e)
		{
			JOptionPane.showMessageDialog(null,
					"It appears there is not a database entry for @" + clientid + " yet",
					"No Database Record Found",
					JOptionPane.ERROR_MESSAGE);

			frame.dispose();
		}
	}

	private void frameSetup()
	{
		frame.setIconImages(IconLoader.getList());
		frame.setLayout(new BorderLayout(5, 5));
                
                menu = new JMenuBar();
                admin = new JMenu("Admin");
                menu.add(admin);
                
                addrcon = new JMenuItem("Set b3 Level");
                addclan = new JMenuItem("Add tagprotect");
                admin.add(addrcon);
                
                

		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 1));
                frame.setJMenuBar(menu);
                menu.setVisible(true);
		frame.add(centerPanel, BorderLayout.CENTER);
	}

	public void init()
	{

		databaseId = new String();
		databaseId = NetProtocol.getDatabaseId(c.getClientId(), c.getShortGuid());

		databaseId = databaseId.trim();
	}

	public void topPanel()
	{
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(4, 2));

		//<id>:<name>:<guid>:<connections>:<level (String title)>:<level (int value)>:<first seen>:<last seen>
		String[] results = NetProtocol.getProfile(databaseId);

		JPanel idPanel = new JPanel();
		idPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel connectionPanel = new JPanel();
		connectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel levelPanel = new JPanel();
		levelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel guidPanel = new JPanel();
		guidPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel firstSeenPanel = new JPanel();
		firstSeenPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel lastSeenPanel = new JPanel();
		lastSeenPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel atid = new JLabel("@ID:");
		JLabel con = new JLabel("Connections:");
		JLabel n = new JLabel("Name:");
		JLabel lev = new JLabel("Level:");
		JLabel g = new JLabel("GUID:");
		JLabel first = new JLabel("First Seen:");
		JLabel last = new JLabel("Last Seen:");

		name = new JTextField(results[1]);
		name.setEditable(false);
		name.setBorder(null);
		name.setBackground(null);
		namePanel.add(n);
		namePanel.add(name);

		level = new JTextField(results[4] + " (" + results[5].replaceAll("\\s", "") + ")");
		level.setEditable(false);
		level.setBorder(null);
		level.setBackground(null);
		levelPanel.add(lev);
		levelPanel.add(level);

		connections = new JTextField(results[3]);
		connections.setEditable(false);
		connections.setBorder(null);
		connections.setBackground(null);
		connectionPanel.add(con);
		connectionPanel.add(connections);

		dataid = new JTextField(databaseId);
		dataid.setEditable(false);
		dataid.setBorder(null);
		dataid.setBackground(null);
		idPanel.add(atid);
		idPanel.add(dataid);

		guid = new JTextField(results[2]);
		guid.setEditable(false);
		guid.setBorder(null);
		guid.setBackground(null);
		guidPanel.add(g);
		guidPanel.add(guid);

		firstSeen = new JTextField(Function.getDate(results[6]));
		firstSeen.setEditable(false);
		firstSeen.setBorder(null);
		firstSeen.setBackground(null);
		firstSeenPanel.add(first);
		firstSeenPanel.add(firstSeen);

		lastSeen = new JTextField(Function.getDate(results[7]));
		lastSeen.setEditable(false);
		lastSeen.setBorder(null);
		lastSeen.setBackground(null);
		lastSeenPanel.add(last);
		lastSeenPanel.add(lastSeen);

		topPanel.add(namePanel);
		topPanel.add(idPanel);
		topPanel.add(levelPanel);
		topPanel.add(connectionPanel);
		topPanel.add(guidPanel);
		topPanel.add(new JLabel()); //blank tile
		topPanel.add(firstSeenPanel);
		topPanel.add(lastSeenPanel);
		frame.add(topPanel, BorderLayout.NORTH);
	}

	public void aliasPanel()
	{
		aliasPanel = new JPanel();
		aliasPanel.setLayout(new GridLayout(1, 1));

		String str = "";
		if (!databaseId.equals("none"))
			str = NetProtocol.getAliases(databaseId);
		else
			str = "none\n";


		if (str.equals("none\n"))
		{
			aliasTable("");
		} else
			aliasTable(str);


		aliasPanel.setBorder(BorderFactory.createTitledBorder("Aliases - " + dtm.getRowCount() + " total"));
		centerPanel.add(aliasPanel);
	}

	public void aliasTable(String str)
	{
		table = new JTable();
		table.setShowGrid(false);
		table.setShowHorizontalLines(true);
		//table.setShowVerticalLines(true);
		table.setGridColor(Color.BLACK);
		//table.setPreferredScrollableViewportSize(new Dimension(520, 150));// centerPanel.getPreferredSize());
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		dtm = new AliasTableModel();

		table.setModel(dtm);
		table.setRowHeight(25);

		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(150);
		tcm.getColumn(1).setPreferredWidth(150);
		tcm.getColumn(2).setPreferredWidth(150);
		//tcm.getColumn(3).setPreferredWidth(100);
		//tcm.getColumn(4).setPreferredWidth(100);

		String[] aliasLines = str.split("\n");

		for (int i = 0; i < aliasLines.length; i++)
		{
			if (!aliasLines[i].equals(""))
				dtm.addAlias(new Alias(aliasLines[i]));
		}

		aliasPanel.add(new JScrollPane(table));
	}

	public void penaltiesPanel()
	{
		penaltiesPanel = new JPanel();
		penaltiesPanel.setLayout(new GridLayout(1, 1));


		String str = "";
		if (!databaseId.equals("none"))
			str = NetProtocol.getPenalties(databaseId);
		else
			str = "none\n";


		if (str.equals("none\n"))
		{
			penaltiesTable("");
		} else
			penaltiesTable(str);

		penaltiesPanel.setBorder(BorderFactory.createTitledBorder("Penalties - " + dtm1.getRowCount() + " total"));
		centerPanel.add(penaltiesPanel);
	}

	public void penaltiesTable(String str)
	{
		table = new JTable();
		table.setShowGrid(false);
		table.setShowHorizontalLines(true);
		//table.setShowVerticalLines(true);
		table.setGridColor(Color.BLACK);
		//table.setPreferredScrollableViewportSize(new Dimension(520, 150));// centerPanel.getPreferredSize());
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


		dtm1 = new PenaltyTableModel();

		table.setModel(dtm1);
		table.setRowHeight(25);

		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(50);
		tcm.getColumn(1).setPreferredWidth(220);
		tcm.getColumn(2).setPreferredWidth(160);
		tcm.getColumn(3).setPreferredWidth(20);
		//tcm.getColumn(4).setPreferredWidth(100);

		String[] penaltyLines = str.split("\n");

		long time = new Date().getTime() / 1000L;
		for (int i = 0; i < penaltyLines.length; i++)
		{
			if (!penaltyLines[i].equals(""))
				dtm1.addPenalty(new Penalty(penaltyLines[i], time));
		}

		penaltiesPanel.add(new JScrollPane(table));
	}
        
        private void implementListeners() {
            addrcon.addActionListener(new PlayerDetailsListener());
        }
        
        private class PlayerDetailsListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addrcon) {
                    
                }
            }
        }
        
}