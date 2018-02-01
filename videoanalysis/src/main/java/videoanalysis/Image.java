package videoanalysis;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Image {

    private String imageName;
    private int width;
    private int height;
    private Pixel[][] pixels;
    
    public Image(String imageNameInput) throws Exception {
	imageName = imageNameInput;
	BufferedImage img = ImageIO.read(new File(imageName));
	width = img.getWidth();
	height = img.getHeight();
	pixels = new Pixel[width][height];
	for (int inx = 0; inx < width; inx++) {
	    for (int iny = 0; iny < height; iny++) {
		Pixel pixel = new Pixel(inx, iny, img.getRGB(inx, iny));
		pixels[inx][iny] = pixel;
	    }
	}
    }

}
