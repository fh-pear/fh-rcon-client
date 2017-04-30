import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class KickWindow extends JFrame
{
	private JFrame frame;
	private Client c;
	private JLabel name, guidLabel, reason;
	private JTextField nameField, guidField, reasonField;
	private JButton kick, cancel;

	public KickWindow(Client cl)
	{
		c = cl;

		frame = new JFrame("Kick Client: " + c.getName());
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
		nameField.setBorder(null);
		nameField.setBackground(null);
		nameField.setEditable(false);
		nameField.setBounds(80, 10, 270, 25);

		guidLabel = new JLabel("GUID: ");
		guidLabel.setBounds(10, 45, 60, 25);

		guidField = new JTextField(c.getGuid());
		guidField.setBorder(null);
		guidField.setBackground(null);
		guidField.setEditable(false);
		guidField.setBounds(80, 45, 270, 25);

		reason = new JLabel("Reason: ");
		reason.setBounds(10, 100, 60, 25);

		reasonField = new JTextField();
		reasonField.setBounds(80, 100, 270, 25);

		kick = new JButton("Kick");
		kick.setBounds(100, 150, 200, 25);
		kick.setMnemonic(KeyEvent.VK_K);

		cancel = new JButton("Cancel");
		cancel.setBounds(100, 185, 200, 25);
		cancel.setMnemonic(KeyEvent.VK_C);

		frame.add(name);
		frame.add(nameField);
		frame.add(guidLabel);
		frame.add(guidField);
		frame.add(reason);
		frame.add(reasonField);
		frame.add(kick);
		frame.add(cancel);
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