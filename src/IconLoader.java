import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Static class for loading icon images
 */
public class IconLoader
{
	private static ArrayList<Image> array;
	private static Image image16, image32, image48;

	public static void init()
	{
		array = new ArrayList<>();
		add16();
		add32();
		add48();
	}

	public static ArrayList<Image> getList()
	{
		return array;
	}

	private static void add48()
	{
		URL url = ClassLoader.getSystemResource("resources/icons/fhclan.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		image48 = kit.createImage(url);
		array.add(image48);
	}

	public static Image get48()
	{
		return image48;
	}

	private static void add32()
	{
		URL url = ClassLoader.getSystemResource("resources/icons/fhclan_32.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		image32 = kit.createImage(url);
		array.add(image32);
	}

	public static Image get32()
	{
		return image32;
	}

	private static void add16()
	{
		URL url = ClassLoader.getSystemResource("resources/icons/fhclan_16.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		image16 = kit.createImage(url);
		array.add(image16);
	}

	public static Image get16()
	{
		return image16;
	}
}
