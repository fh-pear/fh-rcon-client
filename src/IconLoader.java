import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Static class for loading icon images
 */
public class IconLoader
{

    private static ArrayList<Image> array;
    private static Image image16, image32, image48, image64, image96, image128, image256, fullSize;

    public static void init()
    {
        array = new ArrayList<>();
        URL url = ClassLoader.getSystemResource("resources/icons/fhclan_edited.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        fullSize = kit.createImage(url);

        add16();
        add32();
        add48();
        add64();
        add96();
        add128();
        add256();
        array.add(fullSize);
    }

    public static ArrayList<Image> getList()
    {
        return array;
    }

    private static void add256()
    {
        image256 = fullSize.getScaledInstance(256, 256, Image.SCALE_SMOOTH);
        array.add(image256);
    }

    public static Image getImage64()
    {
        return image64;
    }

    public static Image getImage96()
    {
        return image96;
    }

    public static Image getImage128()
    {
        return image128;
    }

    public static Image getImage256()
    {
        return image256;
    }

    public static Image getFullSize()
    {
        return fullSize;
    }

    private static void add128()
    {
        image128 = fullSize.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
        array.add(image128);
    }

    private static void add96()
    {
        image96 = fullSize.getScaledInstance(96, 96, Image.SCALE_SMOOTH);
        array.add(image96);
    }

    private static void add64()
    {
        image64 = fullSize.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        array.add(image64);
    }

    private static void add48()
    {
        image48 = fullSize.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        array.add(image48);
    }

    public static Image get48()
    {
        return image48;
    }

    private static void add32()
    {
        image32 = fullSize.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        array.add(image32);
    }

    public static Image get32()
    {
        return image32;
    }

    private static void add16()
    {
        image16 = fullSize.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        array.add(image16);
    }

    public static Image get16()
    {
        return image16;
    }
}
