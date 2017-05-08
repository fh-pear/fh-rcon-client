/**
 * class responsible for "remember me" to store password and username for future use
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordManagement
{
	private static Logger logger = Logger.getLogger(PasswordManagement.class.getName());
	private static String userName, rememberMe = "false", password, passwordFile = ".fh_password.properties";


	public static void init()
	{
		FileReader file = null;
		try
		{
			Properties props = new Properties();
			file = new FileReader(passwordFile);
			props.load(file);

			userName = props.getProperty("username");
			password = props.getProperty("password");
			rememberMe = props.getProperty("remember_me");

		} catch (FileNotFoundException e)
		{
			logger.log(Level.WARNING, "Password file not found.", e);
		} catch (IOException e)
		{
			logger.log(Level.SEVERE, "Error reading from password file", e);
		}
		finally
		{
			if (userName == null)
				userName = "";
			if (password == null)
				password = "";

			try
			{
				if (file != null)
					file.close();
			} catch (IOException e)
			{
				logger.log(Level.SEVERE, "Error closing password file", e);
			}
		}
	}

	public static boolean rememberMe()
	{
		if (rememberMe != null && rememberMe.equals("true"))
			return true;
		else
			return false;
	}

	public static String getUserName()
	{
		return userName;
	}

	public static String getPassword()
	{
		return password;
	}

	public static void savePassword(String user, String pass, boolean remember)
	{
		FileWriter file = null;
		String value = "false";

		if (remember)
			value = "true";


		try
		{
			Properties props = new Properties();
			file = new FileWriter(passwordFile);

			props.setProperty("username", user);
			props.setProperty("password", pass);
			props.setProperty("remember_me", value);
			props.store(file, null);
		} catch (IOException e)
		{
			logger.log(Level.SEVERE, "Error writing password details to password file", e);
		} finally
		{
			try
			{
				if (file != null)
					file.close();
			} catch (IOException e)
			{
				logger.log(Level.SEVERE, "Error closing password file", e);
			}
		}
	}
}
