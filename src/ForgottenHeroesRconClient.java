import javax.naming.ConfigurationException;
import javax.swing.*;
import java.io.IOException;
import java.util.logging.*;


public class ForgottenHeroesRconClient
{
	private static final Logger logger = Logger.getLogger(ForgottenHeroesRconClient.class.getName());
	private static Handler filehandle = null;
	private static Handler consolehandle = null;

	public static void main(String[] args)
	{

		try
		{
			Config.init("/resources/config/config.properties");
		} catch (ConfigurationException e)
		{
			logger.severe(e.getMessage());
			System.exit(1);
		} catch (Exception e)
		{
			logger.severe("Unexpected exception occurred reading the config file, could not recover.");
			logger.log(Level.SEVERE, e.getMessage(), e);
			System.exit(1);
		}

		System.setProperty("java.util.logging.SimpleFormatter.format", Config.getLoggingFormat());

		try
		{
			SimpleFormatter sf = new SimpleFormatter();

			filehandle = new FileHandler(Config.getLogPath());
			filehandle.setFormatter(sf);

			filehandle.setLevel(Config.getLoggingLevel());

			logger.addHandler(filehandle);
			Logger.getLogger("").setLevel(Config.getLoggingLevel());

			logger.setLevel(Config.getLoggingLevel());
			logger.setUseParentHandlers(Config.logToConsole());
		} catch (IOException e)
		{
			logger.log(Level.WARNING, "Error setting up file stream for logging", e);
		}

		//NetProtocol.init(1);
		SwingUtilities.invokeLater(
				new Runnable()
				{  //Note 1
					public void run()
					{
						new LoginUI();
					}
				});

		if (filehandle != null)
		{
			filehandle.flush();
			filehandle.close();
		}
	}


}

