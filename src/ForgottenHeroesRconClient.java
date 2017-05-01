import javax.naming.ConfigurationException;
import javax.swing.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ForgottenHeroesRconClient
{
	public static void main(String[] args)
	{
		Logger logger = Logger.getLogger(ForgottenHeroesRconClient.class.getName());

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

