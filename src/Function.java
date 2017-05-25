import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * static service function class
 */
public class Function
{
	private static Logger logger = Logger.getLogger(Function.class.getName());

	public static String getMD5(char[] array)
	{
		String str = new String(array), hashtext = "";
		byte[] bytesOfMessage = null;
		byte[] thedigest = null;

		try
		{

			bytesOfMessage = str.getBytes("UTF-8");
			str = "";

			MessageDigest md = MessageDigest.getInstance("MD5");
			thedigest = md.digest(bytesOfMessage);

			BigInteger bigInt = new BigInteger(1, thedigest);
			hashtext = bigInt.toString(16);

			while (hashtext.length() < 32)
			{
				hashtext = "0" + hashtext;
			}

		} catch (UnsupportedEncodingException e)
		{
			JOptionPane.showMessageDialog(null, "Your machine does not support UTF-8 encoding. \n\n" + e.getMessage());
		} catch (NoSuchAlgorithmException e)
		{
			JOptionPane.showMessageDialog(null, "Your machine does not support MD5 hashing. \n\n" + e.getMessage());
		}

		return hashtext;
	}

	public static String getMD5(String str)
	{
		String hashtext = "";
		byte[] bytesOfMessage = null;
		byte[] thedigest = null;

		try
		{

			bytesOfMessage = str.getBytes("UTF-8");
			str = "";

			MessageDigest md = MessageDigest.getInstance("MD5");
			thedigest = md.digest(bytesOfMessage);

			BigInteger bigInt = new BigInteger(1, thedigest);
			hashtext = bigInt.toString(16);

			while (hashtext.length() < 32)
			{
				hashtext = "0" + hashtext;
			}

		} catch (UnsupportedEncodingException e)
		{
			System.out.println("Your machine does not support UTF-8 encoding: " + e.getMessage());
		} catch (NoSuchAlgorithmException e)
		{
			System.out.println("Your machine does not support MD5 hashing: " + e.getMessage());
		}

		return hashtext;
	}

	public static String getDate(String unixTime)
	{
		long unixSeconds = 0;

		try
		{
			unixSeconds = Long.parseLong(unixTime.replaceAll("\\s", ""));
		} catch (NumberFormatException e)
		{
			logger.log(Level.WARNING, "Error parsing date:" + e.getMessage(), e);
			return "DATE PARSE ERROR";
		}

		if (unixSeconds < 0)
			return "Never";

		Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
		sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));

		return sdf.format(date);
	}
}
