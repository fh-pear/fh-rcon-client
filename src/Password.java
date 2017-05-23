/**
 * Window for changing password
 */

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;

public class Password
{
	private JFrame frame;
	private JPanel main, labels, input, buttons;
	private JLabel current, newPass, repeatNewPass;
	private JPasswordField curr, pass, repeat;
	private JButton cancel, submit;

	public Password()
	{
		frame = new JFrame("Password Change");
		frame.setIconImages(IconLoader.getList());
		main = new JPanel();
		frame.add(main);

		main.setLayout(new BorderLayout());
		labels = new JPanel();
		labels.setLayout(new GridLayout(3, 1));
		input = new JPanel();
		input.setLayout(new GridLayout(3, 1));
		main.add(labels, BorderLayout.WEST);
		main.add(input, BorderLayout.CENTER);

		current = new JLabel("Current Password:");
		newPass = new JLabel("New Password:");
		repeatNewPass = new JLabel("Repeat New Password:");
		labels.add(current);
		labels.add(newPass);
		labels.add(repeatNewPass);

		curr = new JPasswordField(20);
		pass = new JPasswordField(20);
		repeat = new JPasswordField(20);
		input.add(curr);
		input.add(pass);
		input.add(repeat);

		buttons = new JPanel();
		frame.add(buttons, BorderLayout.SOUTH);
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		buttons.add(submit);
		buttons.add(cancel);


		addListeners();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void addListeners()
	{
		submit.addActionListener(new PasswordListener());
		cancel.addActionListener(new PasswordListener());
	}

	private class PasswordListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == submit)
			{
				String password = String.valueOf(pass.getPassword());
				String repeatPassword = String.valueOf(repeat.getPassword());

				if (!password.equals(repeatPassword))
				{
					JOptionPane.showMessageDialog(null,
							"New Password and Repeat Password Entries don't match",
							"Password Mismatch", JOptionPane.ERROR_MESSAGE);
				} else
				{
					String response = NetProtocol.changePassword(Function.getMD5(curr.getPassword()), password);
				}
			}

			if (e.getSource() == cancel)
			{
				frame.dispose();
			}
		}
	}
}
