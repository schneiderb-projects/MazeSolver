import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ReadFiles {
	int maze[][];
	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		String fileName = "/Users/brettschneider/Desktop/35166431-maze-game-illustration-isolated-on-white-background.png";
		BufferedImage img = ImageIO.read(new File(fileName));
		//create FileInputStream which obtains input bytes from a file in a file system
		//FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.
		int width = img.getWidth();
		int height = img.getHeight();

		int array2D[][] = new int[height][width];
		// Iterate through your image's pixels and set the correct bits.

		for(int i = 0; i<array2D.length;i++)
			for(int j = 0; j<array2D[0].length;j++)
				array2D[i][j] = 1;
	
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				// Check against 0xffffffff which is the RGB value of white.
				if (img.getRGB(x, y) == 0xffffffff)
				{
					//bits.set(y * width + x);
					array2D[y][x] = 0;
				}
			}
		}
		ArrayToImage display = new ArrayToImage(array2D);
	}
	
	ReadFiles(String fileName) throws FileNotFoundException, IOException, InterruptedException {
		BufferedImage img = ImageIO.read(new File(fileName));		//create FileInputStream which obtains input bytes from a file in a file system
		//FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.
		int width = img.getWidth();
		int height = img.getHeight();

		int array2D[][] = new int[height][width];
		// Iterate through your image's pixels and set the correct bits.

		for(int i = 0; i<array2D.length;i++)
			for(int j = 0; j<array2D[0].length;j++)
				array2D[i][j] = 1;
	
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				// Check against 0xffffffff which is the RGB value of white.
				if (img.getRGB(x, y) == 0xffffffff)
				{
					//bits.set(y * width + x);
					array2D[y][x] = 0;
				}
			}
		}
		maze = array2D;
	}
	public int[][] getMaze()
	{
		return maze;
	}
}