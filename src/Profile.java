/**
 * Datastructure for holding user info. Username, admin level etc.
 */

import java.io.IOException;
import java.util.logging.*;

public class Profile
{
	private static Logger logger = Logger.getLogger(LoginUI.class.getName());

	public Profile()
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

	}
}
