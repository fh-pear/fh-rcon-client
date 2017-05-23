import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URL;
import java.io.File;

public class MainWindow
{
	private JFrame frame;
	private JPanel eastPanel, westPanel, centerPanel, southPanel, east1, east2, east3, mapButtons, messages, mapPanel, serverInfo;
	private JMenuItem menuExit, menuLogout, searchForPlayer, myProfile;
	private JMenuBar menubar;
	private JMenu file, search;
	private JTable table;
	private JLabel action;
	private ClientTableModel dtm;
	private JButton updateList, pm, globalMessage, kick, tempban, ban, mapButton, mapRotate, playerInfo;
	private BufferedImage map;
	private JLabel mapLabel, currentMap;
	private InputStream mapFile;

	private Logger logger = Logger.getLogger(MainWindow.class.getName());

	public MainWindow()
	{
		frame = new JFrame("Forgotten Heroes | RCon");
		//frame.setPreferredSize(new Dimension(900, 600));
		frame.setIconImages(IconLoader.getList());

		BorderLayout bl = new BorderLayout();
		//bl.setVgap(3);
		bl.setHgap(10);
		frame.setLayout(bl);

		init();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}

	public void init()
	{
		mapLabel = new JLabel();
		mapPanel = new JPanel();
		mapPanel.setLayout(new GridLayout(1, 1));
		serverInfo = new JPanel();
		mapPanel.setLayout(new GridLayout(1, 1));

		createMenuBar();
		//westPanel();
		createButtons();
		tablePanel();
		createPlayerList();
		southPanel();

		implementListeners();
	}

	private void southPanel()
	{
		southPanel = new JPanel();
		//southPanel.setPreferredSize(new Dimension(600, 30));
		action = new JLabel("Action: Waiting...");
		southPanel.add(action);

		frame.add(southPanel, BorderLayout.SOUTH);
	}

	private void westPanel()
	{
		westPanel = new JPanel();
		westPanel.setPreferredSize(new Dimension(30, 600));
		frame.add(westPanel);
	}

	private void createMenuBar()
	{

		menubar = new JMenuBar();
		//ImageIcon icon = new ImageIcon("exit.png");

		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		myProfile = new JMenuItem("My Profile");

		menuLogout = new JMenuItem("Logout");
		menuLogout.setToolTipText("Log out, return to the login screen");

		menuExit = new JMenuItem("Exit", KeyEvent.VK_E);
		menuExit.setToolTipText("Exit application");

		file.add(myProfile);
		file.add(menuLogout);
		file.addSeparator();
		file.add(menuExit);
		menubar.add(file);

		search = new JMenu("Database");
		searchForPlayer = new JMenuItem("Search for player");
		searchForPlayer.setToolTipText("Search for a player in the b3 database");
		search.add(searchForPlayer);
		menubar.add(search);


		menubar.setVisible(true);
		frame.setJMenuBar(menubar);
	}

	private void createButtons()
	{
		eastPanel = new JPanel();
		eastPanel.setPreferredSize(new Dimension(350, 600));
		frame.add(eastPanel, BorderLayout.EAST);

		updateList = new JButton("Update List");
		updateList.setMnemonic(KeyEvent.VK_U);
		updateList.setToolTipText("Updates the Client list");

		playerInfo = new JButton("Player Details");
		playerInfo.setToolTipText("Gets detailed information about the selected client");

		pm = new JButton("Private Message");
		pm.setToolTipText("Send a private message to the currently selected player");
		//pm.setRolloverEnabled(true);

		globalMessage = new JButton("Global Message");
		globalMessage.setToolTipText("Send a global message for everyone to see");

		mapButton = new JButton("Maps");
		mapButton.setToolTipText("Select a map to change the server to");

		mapRotate = new JButton("Map Rotate");
		mapRotate.setToolTipText("Rotate to the next map in the server rotation");
		mapRotate.setEnabled(false);

		kick = new JButton("Kick");
		kick.setToolTipText("Kick the currently selected client");
		kick.setMnemonic(KeyEvent.VK_K);

		tempban = new JButton("TempBan");
		tempban.setToolTipText("Temporarily ban the currently selected client");
		tempban.setMnemonic(KeyEvent.VK_T);
		tempban.setEnabled(false);

		ban = new JButton("Ban");
		ban.setToolTipText("Permanently ban the currently selected client");
		ban.setMnemonic(KeyEvent.VK_B);

		eastPanel.setLayout(new GridLayout(3, 1));
		east1 = new JPanel();
		east1.setBorder(BorderFactory.createTitledBorder("Tasks"));
		east1.setLayout(new GridLayout(4, 1));
		east2 = new JPanel();
		east2.setBorder(BorderFactory.createTitledBorder("Server Info"));
		east2.setLayout(new GridLayout(4, 1));
		east3 = new JPanel();
		east3.setBorder(BorderFactory.createTitledBorder("Penalties"));
		east3.setLayout(new GridLayout(4, 1));

		mapButtons = new JPanel();
		mapButtons.setLayout(new GridLayout(1, 2));
		mapButtons.add(mapRotate);
		mapButtons.add(mapButton);

		messages = new JPanel();
		messages.setLayout(new GridLayout(1, 2));
		messages.add(pm);
		messages.add(globalMessage);

		east1.add(updateList);
		east1.add(playerInfo);
		east1.add(messages);
		east1.add(mapButtons);

		east2.setLayout(new GridLayout(1, 2));
		currentMap = new JLabel("Map: " + NetProtocol.map);

		mapPanel.add(mapLabel);
		serverInfo.add(currentMap);

		east2.add(serverInfo);
		east2.add(mapPanel);
		//mapLabel.setBounds(0, 50, 100, 100);

		east3.add(kick);
		east3.add(tempban);
		east3.add(ban);

		eastPanel.add(east1);
		eastPanel.add(east3);
		eastPanel.add(east2);
	}

	private void map()
	{
		try
		{
			String fileLocation = Config.getMaps() + NetProtocol.map + ".jpg";
			logger.log(Level.INFO, "Setting location for map image: " + fileLocation);

			mapFile = Config.class.getResourceAsStream(fileLocation);
			map = ImageIO.read(mapFile);

			mapLabel.setIcon(new ImageIcon(map.getScaledInstance(175, 150, Image.SCALE_SMOOTH)));
		} catch (IOException e)
		{
			logger.log(Level.WARNING, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, "Error fetching map file: " + e.getMessage(), "ERROR: Couldn't fetch map File", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void tablePanel()
	{
		centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(520, 600));
		centerPanel.setLayout(new GridLayout(1, 1));

		frame.add(centerPanel, BorderLayout.CENTER);
	}

	private void createPlayerList()
	{
		table = new JTable();
		table.setShowGrid(false);
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		table.setGridColor(Color.BLACK);
		table.setPreferredScrollableViewportSize(new Dimension(520, 500));// centerPanel.getPreferredSize());
		table.setFillsViewportHeight(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


		//DefaultTableModel model = (DefaultTableModel) table.getModel()
		dtm = new ClientTableModel();

		//JOptionPane.showMessageDialog(null, NetProtocol.getStatus());
		table.setModel(dtm);
		table.setRowHeight(25);

		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(70);
		tcm.getColumn(1).setPreferredWidth(70);
		tcm.getColumn(2).setPreferredWidth(150);
		tcm.getColumn(3).setPreferredWidth(100);
		//tcm.getColumn(4).setPreferredWidth(100);


		ArrayList<Client> c = new ArrayList<Client>(NetProtocol.getStatus());
		map();


		for (int i = 0; i < c.size(); i++)
		{
			dtm.addClient(c.get(i));
		}

		centerPanel.add(new JScrollPane(table));
	}

	private void updateTable()
	{
		ArrayList<Client> c = new ArrayList<Client>(NetProtocol.getStatus());
		map();
		currentMap.setText("Map: " + NetProtocol.map);

		while (dtm.getRowCount() > 0)
		{
			dtm.removeClient(0);
		}

		for (int i = 0; i < c.size(); i++)
		{
			dtm.addClient(c.get(i));
		}


		action.setText("Updated player list.   " + time());
	}

	private void implementListeners()
	{
		myProfile.addActionListener(new MainWindowListener());
		menuExit.addActionListener(new MainWindowListener());
		menuLogout.addActionListener(new MainWindowListener());
		searchForPlayer.addActionListener(new MainWindowListener());
		updateList.addActionListener(new MainWindowListener());
		playerInfo.addActionListener(new MainWindowListener());
		pm.addActionListener(new MainWindowListener());
		globalMessage.addActionListener(new MainWindowListener());
		mapRotate.addActionListener(new MainWindowListener());
		mapButton.addActionListener(new MainWindowListener());
		kick.addActionListener(new MainWindowListener());
		tempban.addActionListener(new MainWindowListener());
		ban.addActionListener(new MainWindowListener());
	}

	private class MainWindowListener extends KeyAdapter implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == menuExit) // exit button in file menubar
			{
				NetProtocol.clear();
				System.exit(0);
			}
			if (e.getSource() == menuLogout) // logout, return to login window
			{
				NetProtocol.clear();
				frame.dispose();
				new LoginUI();
				System.gc();
			}

			if (e.getSource() == myProfile)
			{
				new MyProfile();
			}
			if (e.getSource() == searchForPlayer)
			{
				new SearchWindow();
			}
			if (e.getSource() == updateList)
			{
				updateTable();
			}
			if (e.getSource() == playerInfo)
			{
				int rowi = table.getSelectedRow();
				if (rowi >= 0)
				{
					new PlayerDetails(dtm.getClient(table.getSelectedRow()));
				}
			}
			if (e.getSource() == pm)
			{
				int rowi = table.getSelectedRow();
				if (rowi >= 0)
				{
					new PMWindow(dtm.getClient(table.getSelectedRow()));
				}
			}
			if (e.getSource() == globalMessage)
			{
				String m = JOptionPane.showInputDialog(null, "Enter a message to send to the server: ", "Send Message", JOptionPane.PLAIN_MESSAGE);

				if (m != null)
				{
					String reply = NetProtocol.sendMessage(m);
					JOptionPane.showMessageDialog(null, reply);
				}
			}
			if (e.getSource() == mapRotate)
			{
				JOptionPane.showMessageDialog(null, NetProtocol.mapRotate("map_rotate"));
			}
			if (e.getSource() == mapButton)
			{
				new MapWindow();
			}
			if (e.getSource() == kick)
			{
				int rowi = table.getSelectedRow();
				if (rowi >= 0)
				{
					new KickWindow(dtm.getClient(table.getSelectedRow()));
				}
			}
			if (e.getSource() == tempban)
			{
				int rowi = table.getSelectedRow();
				if (rowi >= 0)
				{
					new TempBanWindow(dtm.getClient(table.getSelectedRow()));
				}
			}
			if (e.getSource() == ban)
			{
				int rowi = table.getSelectedRow();
				if (rowi >= 0)
				{
					new BanWindow(dtm.getClient(table.getSelectedRow()));
				}
			}
		}
	}

	public String time()
	{
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
	}
}