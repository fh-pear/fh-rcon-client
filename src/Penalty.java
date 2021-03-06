import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Penalty
{

    private String banid, penaltyType, duration, reason, data, timeAdd, timeExpire;
    private boolean inactive;
    private long penaltyAdd, penaltyExpire, time;

    private Logger logger = Logger.getLogger(Penalty.class.getName());

    public Penalty(String str, long t)
    {
        String[] details = str.split("\t");
        if (!str.isEmpty())
        {
            time = t;

            banid = details[0];
            penaltyType = details[1];
            duration = details[2];

            setReason(details[4]);
            data = details[5];

            timeAdd = Function.getDate(details[6]);
            setPenaltyAdd(details[6]);

            timeExpire = Function.getDate(details[7]);
            setPenaltyExpire(details[7]);

            setInactive(details[3]);
            /*set inactive last, so we can look at expire etc to do a full
			analysis to see if the penalty is active or not*/
        }
        else
        {
            time = t;
            banid = "";
            penaltyType = "";
            duration = "";
            reason = "";
            data = "";
            timeAdd = "";
            timeExpire = "";
        }
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
        {
            inactive = true;
        }
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
        }
        catch (NumberFormatException e)
        {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public void setTimeExpire(String str)
    {
        timeExpire = Function.getDate(str);
    }

    public void setPenaltyExpire(String str)
    {
        try
        {
            penaltyExpire = Long.parseLong(str);
        }
        catch (NumberFormatException e)
        {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public String getTimeExpire()
    {
        return timeExpire;
    }

}
