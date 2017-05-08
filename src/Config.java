import javax.naming.ConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;


public final class Config
{
	//constants
	public static final int DEFAULT_PORT = 21300;

	// server variables
	private static int serverPort;
	private static String serverHost;
	private static String mapImages;

	private static String configFile;

	// logging variables
	private static boolean logToConsole;
	private static Logger logger = Logger.getLogger(Config.class.getName());

	public static void init(String fileName) throws IOException, ConfigurationException
	{
		configFile = fileName;
		Properties props = new Properties();
		InputStream is = Config.class.getResourceAsStream(configFile);

		props.load(is);

		serverHost = props.getProperty("hostname");
		mapImages = props.getProperty("map_images");

		String console = props.getProperty("log_to_console");


		if (console.equals("true"))
			logToConsole = true;
		else
			logToConsole = false;

		checkValues();
		is.close();
	}

	// some settings can be blank, and we can assign defaults
	// others we absolutely need (ex serverHost, dbHost)
	private static void checkValues() throws ConfigurationException
	{
		String message = " configuration property is required. Shutting down program...";
		/* REQUIRED settings for the app */
		if (serverHost == null || serverHost.equals(""))
			throw new ConfigurationException("hostname" + message);

		if (mapImages == null || mapImages.equals(""))
			mapImages = "resources/images/";

	}


	public static String getServerHost()
	{
		return serverHost;
	}

	public static int getServerPort()
	{
		return serverPort;
	}

	public static String getMaps()
	{
		return mapImages;
	}

	public static boolean logToConsole()
	{
		return logToConsole;
	}

}