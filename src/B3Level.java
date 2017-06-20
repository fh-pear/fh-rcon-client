/*
 * Datastruture for a b3 level
 */
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class B3Level implements Comparator<B3Level>
{

    private String groupbits, name, keyword, level;
    private Logger logger = Logger.getLogger(B3Level.class.getName());

    /**
     *
     * @param groupbits string representation of the int id from the `groups`
     * table
     * @param name name of the group. ex Guest, Admin, SuperAdmin, etc
     * @param keyword keyword for the group name
     * @param level string representation of the int level
     */
    public B3Level(String groupbits, String name, String keyword, String level)
    {
        this.groupbits = groupbits;
        this.name = name;
        this.keyword = keyword;
        this.level = level;

        logger.log(Level.FINER, "B3Level created: {0}", showFields());
    }

    /**
     *
     * @param input one line string in the format:
     * groupbits::name::keyword::level
     */
    public B3Level(String input)
    {
        logger.log(Level.FINEST, "Creating B3Level with string: {0}", input);
        String fields[] = input.trim().split("::");

        groupbits = fields[0];
        name = fields[1];
        keyword = fields[2];
        level = fields[3];

        logger.log(Level.FINER, "B3Level created: {0}", showFields());
    }

    public String getGroupbits()
    {
        return groupbits;
    }

    public int getbits()
    {
        int bits;

        try
        {
            bits = Integer.parseInt(groupbits);
        }
        catch (NumberFormatException e)
        {
            bits = 0;
        }

        return bits;
    }

    public String getName()
    {
        return name;
    }

    public String getKeyword()
    {
        return keyword;
    }

    public String getLevel()
    {
        return level;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public int compare(B3Level l1, B3Level l2)
    {
        return l1.getbits() - l2.getbits();
    }

    public String showFields()
    {
        return groupbits + "::" + name + "::" + keyword + "::" + level;
    }
}
