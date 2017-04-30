import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginUI extends JFrame
{
	JFrame frame;
	JButton loginButton;
	JPasswordField passwordText;
	JTextField userText;
	JComboBox<String> serverSelect;
	ImageIcon img;

	public LoginUI()
	{
		frame = new JFrame("Forgotten Heroes || Login");
		init();

		frame.setSize(300, 150);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		pack();
		frame.setVisible(true);

	}


	private void init()
	{
		frame.setLayout(null);

		JLabel userLabel = new JLabel("User:");
		userLabel.setBounds(10, 10, 80, 25);
		frame.add(userLabel);

		userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		frame.add(userText);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 40, 80, 25);
		frame.add(passwordLabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		frame.add(passwordText);

		String[] array = {"FFA", "TDM", "Promod", "FFA2"};
		serverSelect = new JComboBox<String>(array);
		serverSelect.setBounds(100, 80, 160, 25);
		frame.add(serverSelect);

		loginButton = new JButton("Login");
		loginButton.setBounds(10, 80, 80, 25);
		frame.add(loginButton);

		loginButton.addActionListener(new LoginListener());
		loginButton.addKeyListener(new LoginListener());
		passwordText.addKeyListener(new LoginListener());
		userText.addKeyListener(new LoginListener());
		serverSelect.addKeyListener(new LoginListener());
	}

	private class LoginListener extends KeyAdapter implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == loginButton)
				login();
		}

		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
				login();
		}

		public void login()
		{
			if (serverSelect.getSelectedItem().equals("FFA"))
				NetProtocol.init(1);
			else if (serverSelect.getSelectedItem().equals("TDM"))
				NetProtocol.init(2);
			if (serverSelect.getSelectedItem().equals("Promod"))
				NetProtocol.init(4);
			if (serverSelect.getSelectedItem().equals("FFA2"))
				NetProtocol.init(4);

			try
			{
				NetProtocol.login(userText.getText(), passwordText.getPassword());

				if (NetProtocol.loggedIn())
				{
					//success
					frame.dispose();
					new MainWindow();
				} else
				{
					// error, bad password/username etc
					JOptionPane.showMessageDialog(null, "Incorrect username or password.", "Authentication Failure", JOptionPane.ERROR_MESSAGE);
				}
			} catch (VersionError e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}
}