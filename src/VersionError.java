
public class VersionError extends Exception
{

    public VersionError()
    {
        super("You are using an old version of the software, please update your client.");
    }

    public VersionError(String str)
    {
        super(str);
    }
}
