import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.net.URL;
//from   ww  w . j a v a 2  s  . co m
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ArrayToImage {
	static JFrame f;
	static AutoResize currentImage;
	ArrayToImage(int maze[][]) {
		f = new JFrame();
		BufferedImage img = toBufferedImage(maze);
        currentImage = new AutoResize(img);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(currentImage);
        f.setSize(400,400);
        f.setLocation((Driver.getRound()*400-400)%1500,0);
        f.setVisible(true);
	}
	
	public void refreshImage(int maze[][])
	{
		f.remove(currentImage);
		BufferedImage img = toBufferedImage(maze);
        currentImage = new AutoResize(img);
		f.add(currentImage);
		f.setVisible(true);
	}
	
	public static BufferedImage toBufferedImage(int[][] rawRGB) {
	    int h = rawRGB.length;
	    int w = rawRGB[0].length;
	    int black = Color.BLACK.getRGB();
	    int white = Color.WHITE.getRGB();
	    int red = Color.RED.getRGB();
	    BufferedImage i = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    for (int y=0; y<h; ++y) {
	        for (int x=0; x<w; ++x) {
	            int argb = rawRGB[y][x];
	            if (argb == 1)
	            	i.setRGB(x, y, black);
	            else if (argb == 0)
	            	i.setRGB(x, y, white);
	            else
	            	i.setRGB(x, y, red);
	        }
	    }
	    return i;
	}
	
	public static BufferedImage getImageFromArray(int[] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return image;
    }
}
