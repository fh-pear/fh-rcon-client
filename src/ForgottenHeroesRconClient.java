import javax.naming.ConfigurationException;
import javax.swing.*;
import java.io.IOException;
import java.util.logging.*;


public class ForgottenHeroesRconClient
{
	public static void main(String[] args)
	{
		Logger logger = Logger.getLogger(ForgottenHeroesRconClient.class.getName());

		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tc] %n%2$s %4$s: %5$s%n%n");

		Handler filehandle = null;
		Formatter format = null;
		try
		{
			format = new SimpleFormatter();
			filehandle = new FileHandler("fhrcon_debug.log");
			filehandle.setFormatter(format);

			logger.addHandler(filehandle);

			logger.setLevel(Level.ALL);
			filehandle.setLevel(Level.ALL);
		} catch (IOException e)
		{
			logger.log(Level.WARNING, "Error setting up file stream for logging", e);
		}

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
			e.printStackTrace();
			System.exit(1);
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
			filehandle.close();
	}


}

