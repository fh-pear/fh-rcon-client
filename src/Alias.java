import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Alias
{
	private String alias, firstUsed, lastUsed;
	private Logger logger = Logger.getLogger(Alias.class.getName());

	public Alias(String str)
	{
		logger.log(Level.FINE, "alias string: " + str);
		String[] details = str.split("\t");

		alias = details[0];
		firstUsed = Function.getDate(details[1]);
		lastUsed = Function.getDate(details[2]);
	}

	public String getAlias()
	{
		return alias;
	}

	public String getFirstUsed()
	{
		return firstUsed;
	}

	public String getLastUsed()
	{
		return lastUsed;
	}

	public void setAlias(String newAlias)
	{
		alias = newAlias;
	}

	public void setFirstUsed(String newDate)
	{
		firstUsed = Function.getDate(newDate);
	}

	public void setLastUsed(String newDate)
	{
		lastUsed = Function.getDate(newDate);
	}
}