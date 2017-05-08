import javax.naming.ConfigurationException;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.*;


public class ForgottenHeroesRconClient
{
	private static final Logger logger = Logger.getLogger(ForgottenHeroesRconClient.class.getName());

	public static void main(String[] args)
	{
		try (InputStream in = ForgottenHeroesRconClient.class.getResourceAsStream("resources/config/logging.properties"))
		{
			LogManager.getLogManager().readConfiguration(in);
		} catch (IOException ex)
		{
			Logger.getLogger(ForgottenHeroesRconClient.class.getName()).log(Level.SEVERE, null, ex);
		}

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


		//System.setProperty("java.util.logging.SimpleFormatter.format", Config.getLoggingFormat());

		logger.log(Level.INFO, "test message");

		//NetProtocol.init(1);
		SwingUtilities.invokeLater(
				new Runnable()
				{  //Note 1
					public void run()
					{
						new LoginUI();
					}
				});

	}


}

