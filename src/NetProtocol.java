import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


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
	private static boolean priorLogin = false;
	private static ArrayList<Client> clients = new ArrayList<Client>();
	private static ArrayList<Client> clientBuffer = new ArrayList<Client>();
	private static Logger logger = Logger.getLogger(NetProtocol.class.getName());

	public static String map = "";

	public static void init(int num)
	{
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
			JOptionPane.showMessageDialog(null, "Could not connect to the server: " + e.getMessage(),
					"Connection Error", JOptionPane.ERROR_MESSAGE);
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

	public static String tempBan(String cid, String shortGuid, String sec, String reason, String duration)
	{
		String command = "tempban" + UNIT_SEPARATOR + cid + UNIT_SEPARATOR + shortGuid 
                        + UNIT_SEPARATOR + sec + UNIT_SEPARATOR + reason + UNIT_SEPARATOR + duration;

		return send(command);
	}

	public static String ban(String cid, String shortGuid, String reason)
	{
		String command = "ban" + UNIT_SEPARATOR + cid + UNIT_SEPARATOR + shortGuid + UNIT_SEPARATOR + reason;

		return send(command);
	}

	public static String[] getProfile(String id)
	{
		String command = "getprofile" + UNIT_SEPARATOR + id;
		String response = send(command);

		return response.split(UNIT_SEPARATOR);
		//<id>:<name>:<guid>:<connections>:<level (String title)>:<level (int value)>
	}

	public static String searchForClient(String search, String type)
	{
		String command = "search" + UNIT_SEPARATOR + type + UNIT_SEPARATOR + search;

		return send(command);
	}

	public static String changePassword(String curr, String newPass)
	{
		String command = "changepassword" + UNIT_SEPARATOR + curr + UNIT_SEPARATOR + newPass;

		return send(command);
	}
        
        public static String sendPlainRcon(String rcon) {
            String command = "rcon" + UNIT_SEPARATOR + rcon;
            
            return send(command);
        }

	public static String getMap()
	{
		map = send("getmap");
		map = Function.extractMapName(map);
                
                return map;
	}
        
        public static String getServerName() {
            return Function.extractServerName(send("servername"));
        }
        
        public static String getServerInfo() {
            String str = send("serverinfo");
            str = str.replaceAll("sv_", "");
            str = str.replaceAll("g_", "");
            return str;
        }

	public static ArrayList<Client> getStatus()
	{
		String inputLine;
		clients.clear();

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

	public static void login(String user, char[] pass, boolean rememberMeSelector) throws VersionError
	{
		try
		{
			String str = new String(pass);

			if (!PasswordManagement.getPassword().equals(str))
				password = Function.getMD5(pass);
			else // provide char array is a hashed password
				password = str;


			out.println(user + UNIT_SEPARATOR + password);
			logger.log(Level.FINEST, "Password hash: " + password);

			if (rememberMeSelector)
				PasswordManagement.savePassword(user, password, true);
			else
				PasswordManagement.savePassword("", "", false);

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
					priorLogin = true;
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

	public static boolean previousLogin()
	{
		return priorLogin;
	}
}