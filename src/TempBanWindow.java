import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

public class TempBanWindow
{
	private JFrame frame;
	private Client c;
	private JLabel name, guidLabel, reason, duration;
	private JTextField nameField, guidField, reasonField, durationField;
	private JButton kick, cancel;
	private JComboBox<String> durationUnit;
	private JPanel main, labels, input, buttons, buttonPanel, selectionPanel, reasonPanel;

	private int durationLength = 8;

	private Logger logger = Logger.getLogger(TempBanWindow.class.getName());

	public TempBanWindow(Client cl)
	{
		c = cl;

		frame = new JFrame("TempBan Client: " + c.getName());
		frame.setIconImages(IconLoader.getList());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		init();
		addListeners();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		frame.setVisible(true);
	}

	public void init()
	{
		main = new JPanel();
		main.setLayout(new BorderLayout(5, 5));
		labels = new JPanel();
		GridLayout grid = new GridLayout(4, 1);
		labels.setLayout(grid);
		input = new JPanel();
		input.setLayout(grid);
		buttons = new JPanel();
		buttonPanel = new JPanel();
		frame.add(main);
		main.add(labels, BorderLayout.WEST);
		main.add(input, BorderLayout.CENTER);
		main.add(buttonPanel, BorderLayout.SOUTH);

		name = new JLabel("Name: ");

		nameField = new JTextField(c.getName());
		nameField.setBorder(null);
		nameField.setBackground(null);
		nameField.setEditable(false);

		guidLabel = new JLabel("GUID: ");
		guidField = new JTextField(c.getGuid());
		guidField.setBorder(null);
		guidField.setBackground(null);
		guidField.setEditable(false);

		reasonPanel = new JPanel();
		reason = new JLabel("Reason: ");
		reasonField = new JTextField(20);
		reasonPanel.add(reasonField);

		duration = new JLabel("Duration: ");
		durationField = new JTextField(10);
		durationField.setText("30");

		String[] array = {"Seconds", "Minutes", "Hours", "Days", "Weeks", "Years"};
		durationUnit = new JComboBox<String>(array);
		durationUnit.setSelectedItem("Minutes");
		selectionPanel = new JPanel();
		selectionPanel.add(durationField);
		selectionPanel.add(durationUnit);

		kick = new JButton("Temp Ban");
		kick.setMnemonic(KeyEvent.VK_T);

		cancel = new JButton("Cancel");
		cancel.setMnemonic(KeyEvent.VK_C);


		labels.add(name);
		labels.add(guidLabel);
		labels.add(reason);
		labels.add(duration);

		input.add(nameField);
		input.add(guidField);
		input.add(reasonPanel);
		input.add(selectionPanel);

		buttons.setLayout(new GridLayout(1, 2, 5, 5));
		buttons.add(kick);
		buttons.add(cancel);
		buttonPanel.add(buttons);
	}

	public void addListeners()
	{
		kick.addActionListener(new TempBanListener());
		cancel.addActionListener(new TempBanListener());
		durationField.addKeyListener(new TempBanListener());
		durationUnit.addActionListener(new TempBanListener());
	}

	private class TempBanListener extends KeyAdapter implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == kick)
			{

				if (reasonField.getText().length() < 2)
				{
					JOptionPane.showMessageDialog(null, "You must supply a reason!", "ERROR: No Reason", JOptionPane.ERROR_MESSAGE);
				}
			}

			if (e.getSource() == cancel)
			{
				frame.dispose();
				System.gc();
			}
			//TODO: check length of duration string and cut string if need be
			if (e.getSource() == durationUnit)
			{
				if (durationUnit.getSelectedItem().equals("Seconds"))
					durationLength = 10;
				else if (durationUnit.getSelectedItem().equals("Minutes"))
					durationLength = 8;
				else if (durationUnit.getSelectedItem().equals("Hours"))
					durationLength = 6;
				else if (durationUnit.getSelectedItem().equals("Days"))
					durationLength = 5;
				else if (durationUnit.getSelectedItem().equals("Weeks"))
					durationLength = 4;
				else
					durationLength = 2;

			}
		}

		public void keyTyped(KeyEvent e)
		{
			if (durationField.getText().length() >= durationLength) // limit textfield to 8 characters
				e.consume();
		}
	}
}