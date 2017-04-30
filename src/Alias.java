import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Alias
{
   private String alias, firstUsed, lastUsed;
	
   public Alias(String str)
   {
		//System.out.println("alias string: " + str);
      String[] details = str.split("\t");
   	
      alias = details[0];
      firstUsed = getDate(details[1]);
      lastUsed = getDate(details[2]);
   }
	
   private String getDate(String unixTime)
   {
		long unixSeconds = 0;
		
		try {
			unixSeconds = Long.parseLong(unixTime);
		}
		catch(NumberFormatException e) {
			return "DATE PARSE ERROR";
		}
		
      Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
      sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		
		return sdf.format(date);
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
		firstUsed = getDate(newDate);
	}
	
	public void setLastUsed(String newDate)
	{
		lastUsed = getDate(newDate);
	}
}