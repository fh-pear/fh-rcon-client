import javax.naming.ConfigurationException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
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
	private static String logFormat;
	private static Level logLevel;
	private static String logPath;
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
		logFormat = props.getProperty("log_format");
		String level = props.getProperty("log_level").toUpperCase();
		logPath = props.getProperty("log_path");
		String console = props.getProperty("log_to_console");

		if (level.equals("ALL"))
			logLevel = Level.ALL;
		else if (level.equals("FINEST"))
			logLevel = Level.FINEST;
		else if (level.equals("FINER"))
			logLevel = Level.FINER;
		else if (level.equals("FINE"))
			logLevel = Level.FINE;
		else if (level.equals("CONFIG"))
			logLevel = Level.CONFIG;
		else if (level.equals("INFO"))
			logLevel = Level.INFO;
		else if (level.equals("WARNING"))
			logLevel = Level.WARNING;
		else if (level.equals("SEVERE"))
			logLevel = Level.SEVERE;
		else
			logLevel = Level.OFF;

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
		if (logPath == null || logPath.equals(""))
			logPath = "fhrcon_debug.log";

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

	public static Level getLoggingLevel()
	{
		return logLevel;
	}

	public static String getLoggingFormat()
	{
		return logFormat;
	}

	public static String getLogPath()
	{
		return logPath;
	}

	public static boolean logToConsole()
	{
		return logToConsole;
	}

}