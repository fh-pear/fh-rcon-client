import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.*;

public class PMWindow extends JFrame
{
	private JFrame frame;
	private Client c;
	private JLabel name, guidLabel, message;
	private JTextField nameField, guidField, messageField;
	private JButton pm, cancel;

	private Logger logger = Logger.getLogger(PMWindow.class.getName());

	public PMWindow(Client cl)
	{
		try
		{
			SimpleFormatter sf = new SimpleFormatter();

			Handler filehandle = new FileHandler(Config.getLogPath(), true);
			filehandle.setFormatter(sf);
			filehandle.setLevel(Config.getLoggingLevel());

			logger.addHandler(filehandle);
			logger.setLevel(Config.getLoggingLevel());

			logger.setUseParentHandlers(true);
		} catch (IOException e)
		{
			logger.log(Level.WARNING, "Error setting up file stream for logging", e);
		}

		c = cl;

		frame = new JFrame("Private Message Client: " + c.getName());
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

		message = new JLabel("Message: ");
		message.setBounds(10, 100, 65, 25);

		messageField = new JTextField();
		messageField.setBounds(80, 100, 270, 25);

		pm = new JButton("Send");
		pm.setBounds(100, 150, 200, 25);

		cancel = new JButton("Cancel");
		cancel.setBounds(100, 185, 200, 25);

		frame.add(name);
		frame.add(nameField);
		frame.add(guidLabel);
		frame.add(guidField);
		frame.add(message);
		frame.add(messageField);
		frame.add(pm);
		frame.add(cancel);
	}

	public void addListeners()
	{
		pm.addActionListener(new PMListener());
		cancel.addActionListener(new PMListener());
	}

	private class PMListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == pm)
			{

				if (messageField.getText().length() < 1)
				{
					JOptionPane.showMessageDialog(null, "You must supply a message!", "ERROR: No Message", JOptionPane.ERROR_MESSAGE);
				} else
				{
					JOptionPane.showMessageDialog(null, NetProtocol.pm(c.getClientId(), c.getShortGuid(), messageField.getText()));
					frame.dispose();
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