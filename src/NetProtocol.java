import javax.swing.*;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.*;


public final class NetProtocol
{
	public static final String UNIT_SEPARATOR = "\t";

	private static Socket socket = null;
	private static BufferedReader in = null;
	private static PrintWriter out = null;

	private static final int FFA = 21300;
	private static final int TDM = 21301;
	private static final int PROMOD = 21302;
	private static final int FFA2 = 21303;
	private static final String host = Config.getServerHost();

	private static int serverPort;
	private static String password;
	private static boolean loggedIn = false;
	private static ArrayList<Client> clients = new ArrayList<Client>();
	private static ArrayList<Client> clientBuffer = new ArrayList<Client>();
	private static Logger logger = null;

	public static String map = "";

	public static void init(int num)
	{
		if (logger == null)
		{
			logger = Logger.getLogger(NetProtocol.class.getName());

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

		clear();

		if (num == 1)
			serverPort = FFA;
		else if (num == 2)
			serverPort = TDM;
		else if (num == 3)
			serverPort = PROMOD;
		else if (num == 4)
			serverPort = FFA2;
		else // wtf? this shouldn't happen... but i know me. put this here anyways
			serverPort = FFA;

		logger.log(Level.INFO, "Initalizing connection using port number: " + serverPort);

		try
		{
			socket = new Socket(host, serverPort);
			in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (UnknownHostException e)
		{
			logger.log(Level.WARNING, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (IOException e)
		{
			logger.log(Level.WARNING, "Could not connect to the server: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(null, "Could not connect to the server: " + e.getMessage());
		}

	}

	public static int getPort()
	{
		return serverPort;
	}

	public static ArrayList<Client> getClients()
	{
		getStatus();
		return clients;
	}

	private static String send(String command)
	{
		String inputLine;
		String result = "";

		logger.log(Level.INFO, "sending - " + command);
		try
		{
			out.println(command);
			Thread.sleep(10);
			while (!(inputLine = in.readLine()).equals("..."))
			{
				if (inputLine.equals(""))
				{
					logger.log(Level.FINEST, "breaking, input ended");
					continue;
				}

				result += inputLine + "\n";
			}

		} catch (InterruptedException e)
		{
			logger.log(Level.WARNING, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (IOException e)
		{
			logger.log(Level.WARNING, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		logger.log(Level.FINE, "RECEIVED: " + result);
		return result;
	}

	public static String mapRotate(String mapname)
	{
		String command = "map" + UNIT_SEPARATOR + mapname;

		return send(command);
	}

	public static String getDatabaseId(String cid, String shortGuid)
	{
		String command = "getdataid" + UNIT_SEPARATOR + cid + UNIT_SEPARATOR + shortGuid;

		return send(command);
	}

	public static String getClient(String id)
	{
		String command = "getclient" + UNIT_SEPARATOR + id;

		return send(command);
	}

	public static String getAliases(String id)
	{
		String command = "aliases" + UNIT_SEPARATOR + id;

		return send(command);
	}

	public static String getPenalties(String id)
	{
		String command = "penalties" + UNIT_SEPARATOR + id;

		return send(command);
	}

	public static String sendMessage(String message)
	{
		String command = "say" + UNIT_SEPARATOR + message;

		return send(command);
	}

	public static String pm(String cid, String shortGuid, String message)
	{
		String command = "pm" + UNIT_SEPARATOR + cid + UNIT_SEPARATOR + shortGuid + UNIT_SEPARATOR + message;

		return send(command);
	}

	public static String kick(String cid, String shortGuid, String reason)
	{
		String command = "kick" + UNIT_SEPARATOR + cid + UNIT_SEPARATOR + shortGuid + UNIT_SEPARATOR + reason;

		return send(command);
	}

	public static String tempBan(String cid, String shortGuid, String duration, String reason)
	{
		String command = "kick" + UNIT_SEPARATOR + cid + UNIT_SEPARATOR + shortGuid + UNIT_SEPARATOR + duration + UNIT_SEPARATOR + reason;

		return send(command);
	}

	public static String ban(String cid, String shortGuid, String reason)
	{
		String command = "ban" + UNIT_SEPARATOR + cid + UNIT_SEPARATOR + shortGuid + UNIT_SEPARATOR + reason;

		return send(command);
	}

	public static void getMap()
	{
		map = send("getmap");
		map = map.replace("\n", "");
	}

	public static ArrayList<Client> getStatus()
	{
		String inputLine;
		clients.clear();
		getMap();

		try
		{
			out.println("status");

			while (!(inputLine = in.readLine()).equals("..."))
			{
				if (inputLine.equals(""))
				{
					logger.log(Level.FINEST, "breaking, input ended");
					continue;
				}
				logger.log(Level.FINEST, "adding client: " + inputLine);
				clients.add(new Client(inputLine));
			}

		} catch (IOException e)
		{
			logger.log(Level.WARNING, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return clients;
	}

	public static void clear()
	{
		try
		{
			out.println("exit");
			in.close();
			out.close();
			socket.close();
			loggedIn = false;
			clients.clear();

			socket = null;
			in = null;
			out = null;

			System.gc();

		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (NullPointerException e)
		{
			// nothing has been initalized yet, this is fine. we don't really care about that
			// will happen on the first initalization call
		}

	}

	public static void login(String user, char[] pass) throws VersionError
	{
		String str = new String(pass);
		byte[] bytesOfMessage = null;
		byte[] thedigest = null;

		try
		{
			bytesOfMessage = str.getBytes("UTF-8");
			str = "";

			MessageDigest md = MessageDigest.getInstance("MD5");
			thedigest = md.digest(bytesOfMessage);

			BigInteger bigInt = new BigInteger(1, thedigest);
			String hashtext = bigInt.toString(16);

			while (hashtext.length() < 32)
			{
				hashtext = "0" + hashtext;
			}

			password = new String(hashtext);

			out.println(user + UNIT_SEPARATOR + password);
			logger.log(Level.FINEST, "Password hash: " + password);

			String inputLine, input = "";
			while (!(inputLine = in.readLine()).equals("..."))
			{
				if (inputLine.equals(""))
					continue;
				input += inputLine;
			}
			logger.log(Level.FINER, input);

			//Logged in. Waiting for command(s)
			if (input.equals("Verify version"))
			{
				//System.out.println("verifying version");
				out.println(Version.VERSION);

				String inputVersion, inputV = "";
				while (!(inputVersion = in.readLine()).equals("..."))
				{
					if (inputVersion.equals(""))
						continue;
					inputV += inputVersion;
				}

				if (inputV.equals("Logged in. Waiting for command(s)."))
				{
					loggedIn = true;
				} else
				{
					clear();
					logger.log(Level.SEVERE, "Version mismatch, throwing VersionError");
					throw new VersionError(inputV);
				}
			} else if (input.equals("exit"))
			{
				loggedIn = false;
				clear();
			} else
			{
				loggedIn = false;
				clear();
			}
		} catch (UnsupportedEncodingException e)
		{
			logger.log(Level.SEVERE, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, "Your machine does not support UTF-8 encoding. \n\n" + e.getMessage());
		} catch (NoSuchAlgorithmException e)
		{
			logger.log(Level.SEVERE, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, "Your machine does not support MD5 hashing. \n\n" + e.getMessage());
		} catch (IOException e)
		{
			logger.log(Level.SEVERE, e.getMessage(), e);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		if (loggedIn)
			getStatus();
	}

	public static boolean loggedIn()
	{
		return loggedIn;
	}
}