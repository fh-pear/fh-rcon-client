import javax.swing.*;
import javax.naming.ConfigurationException;

public class ForgottenHeroesRconClient 
{
   public static void main(String[] args) 
   {
   	
      try
      {
         Config.init("/resources/config.properties");
      }
      catch (ConfigurationException e)
      {
         System.out.println(e.getMessage());
         System.exit(1);
      }
      catch (NumberFormatException e)
      {
         System.out.println(e.getMessage());
         System.out.println("Could not parse argument to a number format. Please check your config");
      }
      catch (Exception e)
      {
         e.printStackTrace();
         System.out.println("Could not read config file.");
         System.exit(1);
      }
   	
      //NetProtocol.init(1);
      SwingUtilities.invokeLater(
         new Runnable() {  //Note 1
            public void run() {
               new LoginUI();
            }
         });
   }
	
	
}

