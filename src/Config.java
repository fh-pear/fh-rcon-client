import javax.naming.ConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public final class Config
{
	//constants
	public static final int DEFAULT_PORT = 21300;

	// server variables
	private static int serverPort;
	private static String serverHost;
	private static String mapImages;
	private static int logLevel;

	public static void init(String fileName) throws NumberFormatException, IOException, ConfigurationException
	{
		Properties props = new Properties();
		//InputStream is = new FileInputStream(fileName);
		InputStream is = Config.class.getResourceAsStream(fileName);

		props.load(is);

		serverHost = props.getProperty("hostname");
		mapImages = props.getProperty("map_images");
		logLevel = Integer.parseInt(props.getProperty("log_level"));


		checkValues();
	}

	// some settings can be blank, and we can assign defaults
	// others we absolutely need (ex serverHost, dbHost)
	public static void checkValues() throws ConfigurationException
	{
		String message = " configuration property is required. Shutting down program...";
		/* REQUIRED settings for the gameserver */
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

}