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

    public int verticalLine(String color) {
	int returnInt = 0;
	for (int inx = 0; inx < width; inx++) {
	    int current = verticalLine(color, inx);
	    if (current > returnInt) {
		returnInt = current;
	    }
	}
	return returnInt;
    }

    private int verticalLine(String color, int x) {
	int returnInt = 0;
	int current = 0;
	for (int iny = 0; iny < height; iny++) {
	    if (ColorRanges.pixelIsColor(pixels[x][iny], color)) {
		current++;
	    } else {
		if (current > returnInt) {
		    returnInt = current;
		}
		current = 0;
	    }		
	}
	return returnInt;
    }

    public int colorPixelCount(String color) {
	int returnInt = 0;
	for (int inx = 0; inx < pixels.length; inx++) {
	    for (int iny = 0; iny < pixels[inx].length; iny++) {
		if (ColorRanges.pixelIsColor(pixels[inx][iny], color)) {
		    returnInt++;
		}
	    }
	}
	return returnInt;
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public Pixel getPixel(int width, int height) {
	return pixels[width][height];
    }
    
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
