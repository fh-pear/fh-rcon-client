/**
 * class responsible for "remember me" to store password and username for future use
 */
import javax.naming.ConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.*;

public class PasswordManagement
{
	private Logger logger = Logger.getLogger(PasswordManagement.class.getName());
	private String userName, password;

	public PasswordManagement()
	{
		try
		{
			SimpleFormatter sf = new SimpleFormatter();

			Handler filehandle = new FileHandler(Config.getLogPath(), true);
			filehandle.setFormatter(sf);
			filehandle.setLevel(Config.getLoggingLevel());

			logger.addHandler(filehandle);
			logger.setLevel(Config.getLoggingLevel());

			logger.setUseParentHandlers(Config.logToConsole());
		} catch (IOException e)
		{
			logger.log(Level.WARNING, "Error setting up file stream for logging", e);
		}

		FileReader file = null;
		try
		{
			Properties props = new Properties();
			file = new FileReader(".fh_password.txt");
			props.load(file);

			userName = props.getProperty("username");
			password = props.getProperty("password_hash");

		} catch (FileNotFoundException e)
		{
			logger.log(Level.WARNING, "Password file not found.");
		} catch (IOException e)
		{
			logger.log(Level.SEVERE, "Error reading from password file");
		}
		finally
		{
			try
			{
				if (file != null)
					file.close();
			} catch (IOException e)
			{
				logger.log(Level.SEVERE, "Error closing password file");
			}
		}
	}
}
