import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KickWindow
{
	private JFrame frame;
	private Client c;
	private JLabel name, guidLabel, reason;
	private JTextField nameField, guidField, reasonField;
	private JButton kick, cancel;
	private JPanel main, labels, input, buttons, buttonPanel;

	private Logger logger = Logger.getLogger(KickWindow.class.getName());

	public KickWindow(Client cl)
	{
		c = cl;

		frame = new JFrame("Kick Client: " + c.getName());
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

		reason = new JLabel("Reason: ");
		reasonField = new JTextField(20);

		kick = new JButton("Kick");
		kick.setMnemonic(KeyEvent.VK_K);

		cancel = new JButton("Cancel");
		cancel.setMnemonic(KeyEvent.VK_C);


		labels.add(name);
		labels.add(guidLabel);
		labels.add(new JLabel());
		labels.add(reason);

		input.add(nameField);
		input.add(guidField);
		input.add(new JLabel());
		input.add(reasonField);

		buttons.setLayout(new GridLayout(1, 2, 5, 5));
		buttons.add(kick);
		buttons.add(cancel);
		buttonPanel.add(buttons);
	}

	public void addListeners()
	{
		kick.addActionListener(new KickListener());
		cancel.addActionListener(new KickListener());
	}

	private class KickListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == kick)
			{

				if (reasonField.getText().length() < 2)
				{
					logger.log(Level.INFO, "Attempt to kick " + c.getName() + ":" + c.getShortGuid() + " without a reason");
					JOptionPane.showMessageDialog(null, "You must supply a reason!", "ERROR: No Reason", JOptionPane.ERROR_MESSAGE);
				} else
				{
					JOptionPane.showMessageDialog(null, NetProtocol.kick(c.getClientId(), c.getShortGuid(), reasonField.getText()));
					frame.dispose();
					System.gc();
				}
			}

			if (e.getSource() == cancel)
			{
				frame.dispose();
				System.gc();
			}
		}
	}
}