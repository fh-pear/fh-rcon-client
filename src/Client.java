//import java.util.List;
//import java.util.ArrayList;

import java.io.IOException;
import java.util.logging.*;

public class Client
{
	private String cid, name, guid, score, dataid;
	boolean guidValid = true; // assume true, checkGuid() will set false if otherwise
	private String exactName = "";
	private Logger logger = Logger.getLogger(Client.class.getName());

	/**
	 * Client object constructor.
	 * Creates a Client object based on a single line from a rcon "status" command. Parses out the
	 * guid, name, and client id.
	 *
	 * @param str The string will be a single line from a status command sent to the custom rcon server implementation.
	 *            Example line:	0 20 name exactname^7 1234567890
	 */
	public Client(String str)
	{
		logger.log(Level.FINE, "New Client Object: " + str);
		String[] parse = str.split("\t");
		
		/*for (int i = 0; i < parse.length; i++)
		{
			System.out.print(parse[i] + "\t");
		}*/
		//System.out.println("client: " + parse[4]);

		cid = parse[0];
		score = parse[1];
		name = parse[2];
		exactName = parse[3];
		guid = parse[4];
		//dataid = parse[5];

	}

	/**
	 * Client object constructor
	 * This constructor is for when the variables have already been parsed from a rcon reply.
	 * Use case ex creating a deep copy
	 *
	 * @param id will be the cid
	 * @param s the value for score
	 * @param n  will be the name
	 * @param eN becomes the exactName
	 * @param g  becomes the guid
	 */
	public Client(String id, String s, String n, String eN, String g)
	{
		cid = id;
		score = s;
		name = n;
		exactName = eN;
		guid = g;

	}

	public String toString(boolean fullDetails)
	{
		if (fullDetails)
		{
			String str = cid + "\t" +
					score + "\t" +
					name + "\t" +
					exactName + "\t" +
					guid + "\n";

			return str;
		} else
		{
			String str = cid + "\t" +
					score + "\t" +
					name + "\t" +
					exactName + "\t" +
					guid.substring(guid.length() - 8);

			return str;
		}
	}

	/**
	 * guid mutator
	 *
	 * @param g set the guid field to the value in g
	 */
	public void setGuid(String g)
	{
		guid = g;
	}

	/**
	 * guid accessor
	 *
	 * @return the guid field
	 */
	public String getGuid()
	{
		return guid;
	}

	/**
	 * short guid ( 8 char ) accessor
	 *
	 * @return last 8 characters of the guid field
	 */
	public String getShortGuid()
	{
		return guid.substring(guid.length() - 8);
	}

	/**
	 * exactName accessor
	 *
	 * @return the exactName field
	 */
	public String getExactName()
	{
		return exactName;
	}

	public void setExactName(String en)
	{
		exactName = en;
	}

	/**
	 * name accessor
	 *
	 * @return the name field
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String n)
	{
		name = n;
	}

	/**
	 * cid accessor
	 *
	 * @return the cid field
	 */
	public String getClientId()
	{
		return cid;
	}

	/**
	 * cid mutator
	 *
	 * @param id new cid
	 */
	public void setClientId(String id)
	{
		cid = id;
	}

	/**
	 * dataid accessor
	 *
	 * @return dataid
	 */
	public String getDatabaseId()
	{
		return dataid;
	}

	/**
	 * dataid mutator
	 *
	 * @param id set the dataid field to the value id
	 */
	public void setDatabaseId(String id)
	{
		dataid = id;
	}

	/**
	 * score accessor
	 *
	 * @return score
	 */
	public String getScore()
	{
		return score;
	}

	/**
	 * score mutator
	 *
	 * @param s set the score field to the value in s
	 */
	public void setScore(String s)
	{
		score = s;
	}

	/**
	 * equals() method used to compare two client objects
	 *
	 * @param c Client object to compare against
	 * @return true if the objects are equal, false if not
	 */
	public boolean equals(Client c)
	{
		return guid.equals(c.getGuid()) && exactName.equals(c.getExactName()) && cid.equals(c.getClientId());

	}
}