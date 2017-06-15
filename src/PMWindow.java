import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

public class PMWindow
{
	private JFrame frame;
	private Client c;
	private JLabel name, guidLabel, message;
	private JTextField nameField, guidField, messageField;
	private JButton pm, cancel;
	private JPanel main, labels, input, buttons, buttonPanel;

	private Logger logger = Logger.getLogger(PMWindow.class.getName());

	public PMWindow(Client cl)
	{
		c = cl;

		frame = new JFrame("Private Message Client: " + c.getName());
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

		message = new JLabel("Message: ");
		messageField = new JTextField(20);

		pm = new JButton("Send");
		pm.setMnemonic(KeyEvent.VK_S);

		cancel = new JButton("Cancel");
		cancel.setMnemonic(KeyEvent.VK_C);


		labels.add(name);
		labels.add(guidLabel);
		labels.add(new JLabel());
		labels.add(message);

		input.add(nameField);
		input.add(guidField);
		input.add(new JLabel());
		input.add(messageField);

		buttons.setLayout(new GridLayout(1, 2, 5, 5));
		buttons.add(pm);
		buttons.add(cancel);
		buttonPanel.add(buttons);
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