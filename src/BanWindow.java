import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;
import java.util.logging.Level;

public class BanWindow extends JFrame
{
	private JFrame frame;
	private Client c;
	private JLabel name, guidLabel, reason;
	private JTextField nameField, guidField, reasonField;
	private JButton ban, cancel;
	private Logger logger = Logger.getLogger(BanWindow.class.getName());

	public BanWindow(Client cl)
	{
		c = cl;

		frame = new JFrame("Ban Client: " + c.getName());
		frame.setLayout(null);
		frame.setSize(400, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		init();
		addListeners();
		pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		frame.setVisible(true);
	}

	public void init()
	{
		//double width = frame.getWidth() - 20;
		name = new JLabel("Name: ");
		name.setBounds(10, 10, 60, 25);

		nameField = new JTextField(c.getName());
		nameField.setBackground(null);
		nameField.setBorder(null);
		nameField.setEditable(false);
		nameField.setBounds(80, 10, 270, 25);

		guidLabel = new JLabel("GUID: ");
		guidLabel.setBounds(10, 45, 60, 25);

		guidField = new JTextField(c.getGuid());
		guidField.setBackground(null);
		guidField.setBorder(null);
		guidField.setEditable(false);
		guidField.setBounds(80, 45, 270, 25);

		reason = new JLabel("Reason: ");
		reason.setBounds(10, 100, 60, 25);

		reasonField = new JTextField();
		reasonField.setBounds(80, 100, 270, 25);

		ban = new JButton("Ban");
		ban.setBounds(100, 150, 200, 25);
		ban.setMnemonic(KeyEvent.VK_B);

		cancel = new JButton("Cancel");
		cancel.setBounds(100, 185, 200, 25);
		cancel.setMnemonic(KeyEvent.VK_C);

		frame.add(name);
		frame.add(nameField);
		frame.add(guidLabel);
		frame.add(guidField);
		frame.add(reason);
		frame.add(reasonField);
		frame.add(ban);
		frame.add(cancel);
	}

	public void addListeners()
	{
		ban.addActionListener(new BanListener());
		cancel.addActionListener(new BanListener());
	}

	private class BanListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == ban)
			{

				if (reasonField.getText().length() < 2)
				{
					logger.log(Level.INFO, "User tried to ban " + c.getName() + " without a reason");
					JOptionPane.showMessageDialog(null, "You must supply a reason!", "ERROR: No Reason", JOptionPane.ERROR_MESSAGE);
				} else
				{
					logger.log(Level.INFO, "Calling NetProtocol.ban: " + c.getClientId() + " " + c.getShortGuid() + " " + reasonField.getText());
					JOptionPane.showMessageDialog(null, NetProtocol.ban(c.getClientId(), c.getShortGuid(), reasonField.getText()));
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