/**
 * Datastructure representing a client that is returned from the database query
 */
public class ClientResult
{
	private String name, guid, connections, title, level, firstSeen, lastSeen;
	private int databaseid;

	//<id>:<name>:<guid>:<connections>:<level (String title)>:<level (int value)>:<first seen>:<last seen>
	public ClientResult(String str)
	{
		try
		{
			String[] details = str.split("\t");
			setDatabaseid(details[0]);
			setName(details[1]);
			setGuid(details[2]);
			setConnections(details[3]);
			setTitle(details[4]);
			setLevel(details[5]);
			setFirstSeen(details[6]);
			setLastSeen(details[7]);
		} catch (ArrayIndexOutOfBoundsException e)
		{
			databaseid = 0;
			name = "";
			guid = "";
			connections = "";
			title = "";
			level = "";
			firstSeen = "";
			lastSeen = "";
		}
	}

	public int getDatabaseid()
	{
		return databaseid;
	}

	public String getStringDatabaseid()
	{
		return String.valueOf(databaseid);
	}

	public String getName()
	{
		return name;
	}

	public String getGuid()
	{
		return guid;
	}

	public String getConnections()
	{
		return connections;
	}

	public String getTitle()
	{
		return title;
	}

	public String getLevel()
	{
		return level;
	}

	public String getFirstSeen()
	{
		return firstSeen;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setGuid(String guid)
	{
		this.guid = guid;
	}

	public void setConnections(String connections)
	{
		this.connections = connections;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setLevel(String level)
	{
		this.level = level;
	}

	public void setFirstSeen(String firstSeen)
	{
		this.firstSeen = Function.getDate(firstSeen);
	}

	public void setLastSeen(String lastSeen)
	{
		this.lastSeen = Function.getDate(lastSeen);
	}

	public void setDatabaseid(int databaseid)
	{
		this.databaseid = databaseid;
	}

	private void setDatabaseid(String databaseid)
	{
		try
		{
			this.databaseid = Integer.parseInt(databaseid);
		} catch (NumberFormatException e)
		{
			this.databaseid = 0;
		}
	}

	public String getLastSeen()
	{
		return lastSeen;

	}
}
