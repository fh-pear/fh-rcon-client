import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.*;

public class MapWindow
{
	private JFrame frame;
	private JScrollPane jsp;
	private JPanel northPanel, westPanel, eastPanel, southPanel, innerSPanel1, innerSPanel2;
	private JTextField customMapField;
	private JLabel customMap, mapLabel;
	private JButton submit, cancel;
	private JList<String> mapOptions;
	private JRadioButton normal, custom;
	private ButtonGroup group;

	private InputStream mapFile;
	private BufferedImage map;

	private Logger logger = Logger.getLogger(MapWindow.class.getName());

	public MapWindow()
	{
		try
		{
			SimpleFormatter sf = new SimpleFormatter();

			Handler filehandle = new FileHandler(Config.getLogPath(), true);
			filehandle.setFormatter(sf);
			filehandle.setLevel(Config.getLoggingLevel());

			logger.addHandler(filehandle);
			logger.setLevel(Config.getLoggingLevel());

			logger.setUseParentHandlers(Config.logToConsole());
		} catch (IOException e)
		{
			logger.log(Level.WARNING, "Error setting up file stream for logging", e);
		}

		frame = new JFrame("Map Selection: ");
		frame.setLayout(new BorderLayout());
		init();

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void init()
	{
		mapLabel = new JLabel();
		northPanel();
		//eastPanel();
		westPanel();
		southPanel();
		eastPanel();

		implementListeners();
	}

	private void map(String mapName)
	{
		try
		{
			String fileLocation = Config.getMaps() + mapName + ".jpg";
			logger.log(Level.FINE, "Using " + fileLocation + " for mapfile image location");
			mapFile = Config.class.getResourceAsStream(fileLocation);

			map = ImageIO.read(mapFile);

			mapLabel.setIcon(new ImageIcon(map.getScaledInstance(400, 225, Image.SCALE_SMOOTH)));

		} catch (IOException e)
		{
			logger.log(Level.WARNING, "Error fetching map file: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error fetching map file: " + e.getMessage(), "ERROR: Couldn't fetch map File", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void northPanel()
	{
		northPanel = new JPanel();
		group = new ButtonGroup();

		normal = new JRadioButton("Default Maps");
		normal.setSelected(true);
		custom = new JRadioButton("Custom Map");

		group.add(normal);
		group.add(custom);

		northPanel.add(normal);
		northPanel.add(custom);

		frame.add(northPanel, BorderLayout.NORTH);
	}

	public void eastPanel()
	{
		eastPanel = new JPanel();
		eastPanel.add(mapLabel);

		frame.add(eastPanel, BorderLayout.EAST);
	}

	public void westPanel()
	{
		westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(1, 1));

		String[] maps = {"Ambush", "Backlot", "Bloc", "Bog", "Broadcast",
				"Chinatown", "Countdown", "Crash", "Creek", "Crossfire",
				"District", "Downpour", "Killhouse", "Overgrown",
				"Pipeline", "Shipment", "Showdown", "Strike",
				"Vacant", "Wet Work", "Winter Crash"};

		mapOptions = new JList<String>(maps);
		mapOptions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mapOptions.setSelectedIndex(0);
		map(getMapName(mapOptions.getSelectedValue()));
		jsp = new JScrollPane(mapOptions);
		westPanel.add(jsp);

		frame.add(westPanel, BorderLayout.WEST);
	}

	public void southPanel()
	{
		southPanel = new JPanel();

		innerSPanel1 = new JPanel();
		innerSPanel1.setBorder(BorderFactory.createTitledBorder("Custom Map Input"));
		innerSPanel1.setLayout(new GridLayout(2, 1));
		customMap = new JLabel("Custom map (ex. mp_backlot): ");
		customMap.setEnabled(false);
		innerSPanel1.add(customMap);

		customMapField = new JTextField();
		customMapField.setEnabled(false);
		innerSPanel1.add(customMapField);

		innerSPanel2 = new JPanel();
		innerSPanel2.setLayout(new GridLayout(2, 1));

		submit = new JButton("Submit");
		submit.setEnabled(false);
		innerSPanel2.add(submit);

		cancel = new JButton("Cancel");
		innerSPanel2.add(cancel);

		southPanel.add(innerSPanel1);
		southPanel.add(innerSPanel2);
		frame.add(southPanel, BorderLayout.SOUTH);
	}

	public void implementListeners()
	{
		normal.addActionListener(new MapWindowListener());
		custom.addActionListener(new MapWindowListener());
		submit.addActionListener(new MapWindowListener());
		cancel.addActionListener(new MapWindowListener());

		mapOptions.addListSelectionListener(new MapWindowListener());
	}

	private class MapWindowListener implements ActionListener, ListSelectionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == normal || e.getSource() == custom)
			{
				if (custom.isSelected())
				{
					mapOptions.clearSelection();
					mapOptions.setEnabled(false);

					customMapField.setEnabled(true);
					customMap.setEnabled(true);
				} else
				{
					mapOptions.setEnabled(true);

					customMapField.setText("");
					customMapField.setEnabled(false);
					customMap.setEnabled(false);
				}
			}

			if (e.getSource() == cancel)
			{
				frame.dispose();
				System.gc();
			}
		}

		public void valueChanged(ListSelectionEvent e)
		{
			if (e.getSource() == mapOptions)
			{
				if (mapOptions.isSelectionEmpty())
				{

				} else
				{
					map(getMapName(mapOptions.getSelectedValue()));
				}
			}

			frame.pack();
		}
	}

	public String getMapName(String mapString)
	{
		if (mapString.equals("Ambush"))
			return "mp_convoy";
		else if (mapString.equals("Backlot"))
			return "mp_backlot";
		else if (mapString.equals("Bloc"))
			return "mp_bloc";
		else if (mapString.equals("Bog"))
			return "mp_bog";
		else if (mapString.equals("Broadcast"))
			return "mp_broadcast";
		else if (mapString.equals("Chinatown"))
			return "mp_carentan";
		else if (mapString.equals("Countdown"))
			return "mp_countdown";
		else if (mapString.equals("Crash"))
			return "mp_crash";
		else if (mapString.equals("Creek"))
			return "mp_creek";
		else if (mapString.equals("Crossfire"))
			return "mp_crossfire";
		else if (mapString.equals("District"))
			return "mp_citystreets";
		else if (mapString.equals("Downpour"))
			return "mp_farm";
		else if (mapString.equals("Killhouse"))
			return "mp_killhouse";
		else if (mapString.equals("Overgrown"))
			return "mp_overgrown";
		else if (mapString.equals("Pipeline"))
			return "mp_pipeline";
		else if (mapString.equals("Shipment"))
			return "mp_shipment";
		else if (mapString.equals("Showdown"))
			return "mp_showdown";
		else if (mapString.equals("Strike"))
			return "mp_strike";
		else if (mapString.equals("Vacant"))
			return "mp_vacant";
		else if (mapString.equals("Wet Work"))
			return "mp_cargoship";
		else if (mapString.equals("Winter Crash"))
			return "mp_crash_snow";
		else
			return "unknown";
	}
}