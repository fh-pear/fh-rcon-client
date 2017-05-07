import javax.swing.*;
import java.io.IOException;
import java.util.logging.*;

public class MyProfile extends JFrame
{
	private JFrame frame;

	private Logger logger = Logger.getLogger(MyProfile.class.getName());

	public MyProfile()
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

		frame = new JFrame("My Profile");
	}
}
