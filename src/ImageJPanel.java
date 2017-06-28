/*
 * Panel for placing images on to rescale when the window is resized
 */
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.IOException;

public class ImageJPanel extends JPanel
{

    private BufferedImage image;
    
    public ImageJPanel()
    {
        
    }

    /**
     * 
     * @param path path to image file
     * @throws IOException if image path is invalid
     */
    public ImageJPanel(String path) throws IOException
    {

    }
    
    /**
     * 
     * @param img image to display on JPanel
     */
    public ImageJPanel(BufferedImage img)
    {
        image = img;
    }
    
    public void addImage(BufferedImage img)
    {
        if (image != null)
        {
            image = img;
            repaint();
        }
        else
            image = img;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
