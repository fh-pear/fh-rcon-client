/**
 * Datastructure for holding user info. Username, admin level etc.
 */

import java.util.logging.Logger;

public class Profile
{
	private static Logger logger = Logger.getLogger(Profile.class.getName());
	private static String name, clientid, guid, level, connections, title;

	public static void init()
	{
		//getProfile() raw string format before splitting in token
		//<id>:<name>:<guid>:<connections>:<level (String title)>:<level (int value)>
		String[] details = NetProtocol.getProfile();

		setClientid(details[0]);
		setName(details[1]);
		setGuid(details[2]);
		setConnections(details[3]);
		setTitle(details[4]);
		setLevel(details[5]);

	}

	private static void setName(String user)
	{
		name = user;
	}

	public static String getName()
	{
		return name;
	}

	private static void setGuid(String key)
	{
		guid = key;
	}

	public static String getGuid()
	{
		return guid;
	}

	private static void setClientid(String id)
	{
		clientid = id;
	}

	public static String getClientid()
	{
		return clientid;
	}

	private static void setTitle(String t)
	{
		title = t;
	}

	public static String getTitle()
	{
		return title;
	}

	private static void setLevel(String l)
	{
		level = l.replaceAll("\\s", "");
	}

	public static String getLevel()
	{
		return level;
	}

	private static void setConnections(String con)
	{
		connections = con;
	}

	public static String getConnections()
	{
		return connections;
	}
}
