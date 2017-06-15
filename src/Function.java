
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * static service function class
 */
public class Function {

    private static Logger logger = Logger.getLogger(Function.class.getName());
    private static final Pattern MAPNAME = Pattern.compile("mp_[a-z0-9_]*\\^7");
    private static final Pattern SERVERNAME = 
            Pattern.compile("(?<svhostname>\".*?\" is: )(?<name>\".*?\")(?<default>.*)");

    public static String getMD5(char[] array) {
        String str = new String(array), hashtext = "";
        byte[] bytesOfMessage = null;
        byte[] thedigest = null;

        try {

            bytesOfMessage = str.getBytes("UTF-8");
            str = "";

            MessageDigest md = MessageDigest.getInstance("MD5");
            thedigest = md.digest(bytesOfMessage);

            BigInteger bigInt = new BigInteger(1, thedigest);
            hashtext = bigInt.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

        } catch (UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null, "Your machine does not support UTF-8 encoding. \n\n" + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, "Your machine does not support MD5 hashing. \n\n" + e.getMessage());
        }

        return hashtext;
    }

    public static String getMD5(String str) {
        String hashtext = "";
        byte[] bytesOfMessage = null;
        byte[] thedigest = null;

        try {

            bytesOfMessage = str.getBytes("UTF-8");
            str = "";

            MessageDigest md = MessageDigest.getInstance("MD5");
            thedigest = md.digest(bytesOfMessage);

            BigInteger bigInt = new BigInteger(1, thedigest);
            hashtext = bigInt.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

        } catch (UnsupportedEncodingException e) {
            System.out.println("Your machine does not support UTF-8 encoding: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Your machine does not support MD5 hashing: " + e.getMessage());
        }

        return hashtext;
    }

    public static String getDate(String unixTime) {
        long unixSeconds = 0;

        try {
            unixSeconds = Long.parseLong(unixTime.replaceAll("\\s", ""));
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Error parsing date:" + e.getMessage(), e);
            return "DATE PARSE ERROR";
        }

        if (unixSeconds < 0) {
            return "Never";
        }

        Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        return sdf.format(date);
    }
    
    /**
     * 
     * @param str string to extract mapname from 
     * @return mapname in format "mp_mapname" (no quotes). if no mapname is found, 
     *         it will return the unaltered string
     */
    public static String extractMapName(String str) {
        Matcher match = MAPNAME.matcher(str);
        
        if (match.find())
            str = match.group();
        
        
        return replaceColorCodes(str);
    }
    
    public static String extractServerName(String str) {
        logger.finest("Extracting servername from str: " + str);
        Matcher match = SERVERNAME.matcher(str);
        
        if (match.find())
            str = match.group("name");
        str = str.substring(1, str.length() -1 );
        return replaceColorCodes(str);
    }

    public static String replaceColorCodes(String str) {
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
        
        return str;
    }
}
