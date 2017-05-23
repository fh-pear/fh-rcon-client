import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Static class for loading icon images
 */
public class IconLoader
{
	private static ArrayList<Image> array;

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
		Image img = kit.createImage(url);
		array.add(img);
	}

	private static void add32()
	{
		URL url = ClassLoader.getSystemResource("resources/icons/fhclan_32.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		array.add(img);
	}

	private static void add16()
	{
		URL url = ClassLoader.getSystemResource("resources/icons/fhclan_16.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		array.add(img);
	}
}
