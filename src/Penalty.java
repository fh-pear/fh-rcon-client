import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Penalty
{
	private String banid, penaltyType, duration, reason, data, timeAdd, timeExpire;
	private boolean inactive;
	private long penaltyAdd, penaltyExpire, time;

	public Penalty(String str, long t)
	{
		String[] details = str.split("\t");
		time = t;

		banid = details[0];
		penaltyType = details[1];
		duration = details[2];

		setReason(details[4]);
		data = details[5];

		timeAdd = getDate(details[6]);
		setPenaltyAdd(details[6]);

		timeExpire = getDate(details[7]);
		setPenaltyExpire(details[7]);

		setInactive(details[3]); //set inactive last, so we can look at expire etc to do a full
		//analysis to see if the penalty is active or not
	}

	public String getBanId()
	{
		return banid;
	}

	public void setPenaltyType(String str)
	{
		penaltyType = str;
	}

	public String getPenaltyType()
	{
		return penaltyType;
	}

	public String getDuration()
	{
		return duration;
	}

	public void setInactive(String str)
	{
		if (str.equals("1"))
			inactive = true;
		else // inactive is false, but check expire time too!
		{
			inactive = !(penaltyExpire < 0 || penaltyExpire > time);
		}
	}

	public boolean isActive()
	{
		return !inactive;
	}

	public void setReason(String str)
	{
		str = str.replaceAll("\\^0", "");
		str = str.replaceAll("\\^1", "");
		str = str.replaceAll("\\^2", "");
		str = str.replaceAll("\\^3", "");
		str = str.replaceAll("\\^4", "");
		str = str.replaceAll("\\^5", "");
		str = str.replaceAll("\\^6", "");
		str = str.replaceAll("\\^7", "");
		str = str.replaceAll("\\^8", "");
		str = str.replaceAll("\\^9", "");


		reason = str;
	}

	public String getReason()
	{
		return reason;
	}

	public String getData()
	{
		return data;
	}

	public String getTimeAdd()
	{
		return timeAdd;
	}

	public void setPenaltyAdd(String str)
	{
		try
		{
			penaltyAdd = Long.parseLong(str);
		} catch (NumberFormatException e)
		{
			// log it here
		}
	}

	public void setTimeExpire(String str)
	{
		timeExpire = getDate(str);
	}

	public void setPenaltyExpire(String str)
	{
		try
		{
			penaltyExpire = Long.parseLong(str);
		} catch (NumberFormatException e)
		{
			//should do some logging here
		}
	}

	public String getTimeExpire()
	{
		return timeExpire;
	}

	private String getDate(String unixTime)
	{
		long unixSeconds = 0;

		try
		{
			unixSeconds = Long.parseLong(unixTime);
		} catch (NumberFormatException e)
		{
			return "DATE PARSE ERROR";
		}

		Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
		sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));

		return sdf.format(date);
	}
}