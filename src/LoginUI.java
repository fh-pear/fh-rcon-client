import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginUI
{
	private JFrame frame;
	private JLabel warning = new JLabel();
	private JPanel panel, userPanel, passwordPanel, buttonPanel, rememberPanel;
	private JButton loginButton;
	private JPasswordField passwordText;
	private JTextField userText;
	private JCheckBox remember;
	private JComboBox<String> serverSelect;
	private ImageIcon img;

	private String warningLabel = "You are responsible for actions taken on your account.";

	private Logger logger = Logger.getLogger(LoginUI.class.getName());

	public LoginUI()
	{
		frame = new JFrame("Forgotten Heroes || Login");
		PasswordManagement.init();

		init();

		frame.setPreferredSize(new Dimension(500, 150));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);

		login();

	}


	private void init()
	{
		panel = new JPanel();
		userPanel = new JPanel();
		passwordPanel = new JPanel();
		buttonPanel = new JPanel();
		rememberPanel = new JPanel();
		panel.setLayout(new GridLayout(4, 1));

		JLabel userLabel = new JLabel("User:        ");
		//userLabel.setBounds(10, 10, 80, 25);
		userPanel.add(userLabel);

		userText = new JTextField(20);
		//userText.setBounds(100, 10, 160, 25);
		userPanel.add(userText);
		if (PasswordManagement.rememberMe())
		{
			userText.setEnabled(false);
			userText.setText(PasswordManagement.getUserName());
		}

		JLabel passwordLabel = new JLabel("Password:");
		//passwordLabel.setBounds(10, 40, 80, 25);
		passwordPanel.add(passwordLabel);

		passwordText = new JPasswordField(20);
		//passwordText.setBounds(100, 40, 160, 25);
		passwordPanel.add(passwordText);
		if (PasswordManagement.rememberMe())
		{
			passwordText.setEnabled(false);
			passwordText.setText(PasswordManagement.getPassword());
		}
		passwordText.setToolTipText("If 'Remember Me' was enabled last login, this field will be left blank");

		String[] array = {"FFA", "TDM", "Promod", "FFA2"};
		serverSelect = new JComboBox<String>(array);
		//serverSelect.setBounds(100, 80, 160, 25);
		buttonPanel.add(serverSelect);

		loginButton = new JButton("Login");
		//loginButton.setBounds(10, 80, 80, 25);
		buttonPanel.add(loginButton);

		remember = new JCheckBox("Remember Me");
		remember.setSelected(PasswordManagement.rememberMe());
		//remember.setBounds(10, 100, 10, 10);
		rememberPanel.add(remember);
		rememberPanel.add(warning);

		warning.setForeground(Color.RED);
		if (PasswordManagement.rememberMe())
			warning.setText(warningLabel);
		else
			warning.setText("");

		panel.add(userPanel);
		panel.add(passwordPanel);
		panel.add(rememberPanel);
		panel.add(buttonPanel);

		frame.add(panel);

		loginButton.addActionListener(new LoginListener());
		loginButton.addKeyListener(new LoginListener());
		passwordText.addKeyListener(new LoginListener());
		userText.addKeyListener(new LoginListener());
		serverSelect.addKeyListener(new LoginListener());
		remember.addActionListener(new LoginListener());
	}

	private class LoginListener extends KeyAdapter implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == loginButton)
				login();
			if (e.getSource() == remember)
			{
				if (remember.isSelected())
				{
					warning.setText(warningLabel);
					passwordText.setEnabled(false);
					userText.setEnabled(false);
				} else
				{
					warning.setText("");
					passwordText.setText("");
					passwordText.setEnabled(true);
					userText.setEnabled(true);
				}
			}
		}

		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
				login();
		}
	}

	private void login()
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
			NetProtocol.login(userText.getText(), passwordText.getPassword(), remember.isSelected());

			if (NetProtocol.loggedIn())
			{
				//success
				frame.dispose();
				new MainWindow();
			} else if (!NetProtocol.loggedIn() && remember.isSelected())
			{
				JOptionPane.showMessageDialog(null,
						"Did you change your password recently? If so, 'Remember Me' is not up-to-date. Update it now!",
						"Authentication Failure", JOptionPane.ERROR_MESSAGE);
			} else
			{
				// error, bad password/username etc
				JOptionPane.showMessageDialog(null, "Incorrect username or password.",
						"Authentication Failure", JOptionPane.ERROR_MESSAGE);
			}
		} catch (VersionError e)
		{
			logger.log(Level.INFO, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}