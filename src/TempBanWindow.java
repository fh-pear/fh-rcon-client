import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

public class TempBanWindow extends JFrame
{
	private JFrame frame;
	private Client c;
	private JLabel name, guidLabel, reason, duration;
	private JTextField nameField, guidField, reasonField, durationField;
	private JButton kick, cancel;
	private JComboBox<String> durationUnit;

	private Logger logger = Logger.getLogger(TempBanWindow.class.getName());

	public TempBanWindow(Client cl)
	{
		c = cl;

		frame = new JFrame("TempBan Client: " + c.getName());
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
		name.setBounds(10, 10, 70, 25);

		nameField = new JTextField(c.getName());
		nameField.setBackground(null);
		nameField.setBorder(null);
		nameField.setEditable(false);
		nameField.setBounds(80, 10, 270, 25);

		guidLabel = new JLabel("GUID: ");
		guidLabel.setBounds(10, 45, 70, 25);

		guidField = new JTextField(c.getGuid());
		guidField.setBackground(null);
		guidField.setBorder(null);
		guidField.setEditable(false);
		guidField.setBounds(80, 45, 270, 25);

		reason = new JLabel("Reason: ");
		reason.setBounds(10, 80, 70, 25);

		reasonField = new JTextField();
		reasonField.setBounds(80, 80, 270, 25);

		duration = new JLabel("Duration: ");
		duration.setBounds(10, 115, 70, 25);

		durationField = new JTextField("30");
		durationField.setBounds(80, 115, 110, 25);

		String[] array = {"Seconds", "Minutes", "Hours", "Days", "Weeks", "Years"};
		durationUnit = new JComboBox<String>(array);
		durationUnit.setSelectedItem("Minutes");
		durationUnit.setBounds(195, 115, 155, 25);

		kick = new JButton("Temp Ban");
		kick.setBounds(100, 150, 200, 25);
		kick.setMnemonic(KeyEvent.VK_T);

		cancel = new JButton("Cancel");
		cancel.setBounds(100, 185, 200, 25);
		cancel.setMnemonic(KeyEvent.VK_C);

		frame.add(name);
		frame.add(nameField);
		frame.add(guidLabel);
		frame.add(guidField);
		frame.add(reason);
		frame.add(reasonField);
		frame.add(duration);
		frame.add(durationField);
		frame.add(durationUnit);
		frame.add(kick);
		frame.add(cancel);
	}

	public void addListeners()
	{
		kick.addActionListener(new TempBanListener());
		cancel.addActionListener(new TempBanListener());
		durationField.addKeyListener(new TempBanListener());
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
		}

		public void keyTyped(KeyEvent e)
		{
			if (durationField.getText().length() >= 3) // limit textfield to 8 characters
				e.consume();
		}
	}
}