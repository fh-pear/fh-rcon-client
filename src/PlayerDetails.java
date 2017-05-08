import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Date;
import java.util.logging.Logger;

public class PlayerDetails
{
	private Client c;
	private JLabel dataid, aliasNone, penaltyNone;
	private JFrame frame;
	private JPanel topPanel, aliasPanel, penaltiesPanel;
	private JTable table;
	private AliasTableModel dtm;
	private PenaltyTableModel dtm1;

	private String databaseId;
	private Logger logger = Logger.getLogger(PlayerDetails.class.getName());

	public PlayerDetails(Client cl)
	{
		c = cl;

		frame = new JFrame("Player Details: " + c.getName());
		frame.setLayout(new GridLayout(3, 1));

		init(); //this will get the databaseid
		topPanel();
		aliasPanel();
		penaltiesPanel();

		frame.pack();
		frame.setVisible(true);
	}

	public PlayerDetails(String clientid)
	{
		frame = new JFrame("Player Details: ");
		frame.setLayout(new GridLayout(3, 1));

		databaseId = clientid;
		databaseId = databaseId.trim();

		topPanel();
		aliasPanel();
		penaltiesPanel();

		frame.pack();
		frame.setVisible(true);
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
		topPanel.setLayout(new FlowLayout());

		dataid = new JLabel(databaseId);

		topPanel.add(dataid);
		frame.add(topPanel);
	}

	public void aliasPanel()
	{
		aliasPanel = new JPanel();
		aliasPanel.setLayout(new GridLayout(1, 1));

		aliasPanel.setBorder(BorderFactory.createTitledBorder("Aliases"));

		String str = "";
		if (!databaseId.equals("none"))
			str = NetProtocol.getAliases(databaseId);
		else
			str = "none\n";


		if (str.equals("none\n"))
		{
			aliasNone = new JLabel("No additional aliases in B3 database.");
			aliasPanel.add(aliasNone);
		} else
			aliasTable(str);

		frame.add(aliasPanel);
	}

	public void aliasTable(String str)
	{
		table = new JTable();
		table.setShowGrid(false);
		table.setShowHorizontalLines(true);
		//table.setShowVerticalLines(true);
		table.setGridColor(Color.BLACK);
		table.setPreferredScrollableViewportSize(new Dimension(520, 150));// centerPanel.getPreferredSize());
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
			dtm.addAlias(new Alias(aliasLines[i]));
		}

		aliasPanel.add(new JScrollPane(table));
	}

	public void penaltiesPanel()
	{
		penaltiesPanel = new JPanel();
		penaltiesPanel.setLayout(new GridLayout(1, 1));

		penaltiesPanel.setBorder(BorderFactory.createTitledBorder("Penalties"));


		String str = "";
		if (!databaseId.equals("none"))
			str = NetProtocol.getPenalties(databaseId);
		else
			str = "none\n";


		if (str.equals("none\n"))
		{
			penaltyNone = new JLabel("No penalties found in B3 database.");
			penaltiesPanel.add(penaltyNone);
		} else
			penaltiesTable(str);

		frame.add(penaltiesPanel);
	}

	public void penaltiesTable(String str)
	{
		table = new JTable();
		table.setShowGrid(false);
		table.setShowHorizontalLines(true);
		//table.setShowVerticalLines(true);
		table.setGridColor(Color.BLACK);
		table.setPreferredScrollableViewportSize(new Dimension(520, 150));// centerPanel.getPreferredSize());
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


		dtm1 = new PenaltyTableModel();

		table.setModel(dtm1);
		table.setRowHeight(25);

		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(50);
		tcm.getColumn(1).setPreferredWidth(190);
		tcm.getColumn(2).setPreferredWidth(160);
		tcm.getColumn(3).setPreferredWidth(50);
		//tcm.getColumn(4).setPreferredWidth(100);

		String[] penaltyLines = str.split("\n");

		long time = new Date().getTime() / 1000L;
		for (int i = 0; i < penaltyLines.length; i++)
		{
			dtm1.addPenalty(new Penalty(penaltyLines[i], time));
		}

		penaltiesPanel.add(new JScrollPane(table));
	}
}