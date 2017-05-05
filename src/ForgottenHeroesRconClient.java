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
		} catch (NumberFormatException e)
		{
			logger.severe(e.getMessage());
			logger.severe("Could not parse argument to a number format. Please check your config");
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

			filehandle = new FileHandler("fhrcon_debug.log");
			filehandle.setFormatter(sf);
			consolehandle = new ConsoleHandler();
			consolehandle.setFormatter(sf);

			filehandle.setLevel(Config.getLoggingLevel());
			consolehandle.setLevel(Config.getLoggingLevel());

			logger.addHandler(filehandle);
			logger.addHandler(consolehandle);

			logger.setLevel(Config.getLoggingLevel());

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

