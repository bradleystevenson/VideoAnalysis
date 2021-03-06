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

    public int fiveVerticalLine(String color) {
	int returnInt = 0;
	for (int inx = 0; inx < width - 5; inx++) {
	    int current = fiveVerticalLine(color, inx);
	    if (current > returnInt) {
		returnInt = current;
	    }
	}
	return returnInt;
    }

    public int threeVerticalLine(String color) {
	int returnInt = 0;
	for (int inx = 0; inx < width - 3; inx++) {
	    int current = threeVerticalLine(color, inx);
	    if (current > returnInt) {
		returnInt = current;
	    }
	}
	return returnInt;
    }

    public int threeHorizontalLine(String color) {
	int returnInt = 0;
	for (int iny = 0; iny < height - 3; iny++) {
	    int current = threeHorizontalLine(color, iny);
	    if (current > returnInt) {
		returnInt = current;
	    }
	}
	return returnInt;
    }

    public int fiveHorizontalLine(String color) {
	int returnInt = 0;
	for (int iny = 0; iny < height - 5; iny++) {
	    int current = fiveHorizontalLine(color, iny);
	    if (current > returnInt) {
		returnInt = current;
	    }	    
	}
	return returnInt;
    }


    private int fiveHorizontalLine(String color, int y) {
	int returnInt = 0;
	int current = 0;
	for (int inx = 0; inx < width; inx++) {
	    boolean match = false;
	    for (int iny = y; iny < y + 5; iny++) {
		if (ColorRanges.pixelIsColor(pixels[inx][iny], color)) {
		    match = true;
		}
	    }
	    if (match) {
		current++;
	    } else {
		if (current > returnInt) {
		    returnInt = current;
		}
		current = 0;
	    }	    
	}
	if (current > returnInt) {
	    returnInt = current;
	}
	return returnInt;
    }

    private int fiveVerticalLine(String color, int x) {
	int returnInt = 0;
	int current = 0;
	for (int iny = 0; iny < height; iny++) {
	    boolean match = false;
	    for (int inx = x; inx < x + 5; inx++) {
		if (ColorRanges.pixelIsColor(pixels[inx][iny], color)) {
		    match = true;
		}		
	    }
	    if (match) {
		current++;
	    } else {
		if (current > returnInt) {
		    returnInt = current;
		}
		current = 0;
	    }
	}
	if (current > returnInt) {
	    returnInt = current;
	}
	return returnInt;
    }
    
    private int threeVerticalLine(String color, int x) {
	int returnInt = 0;
	int current = 0;
	for (int iny = 0; iny < height; iny++) {
	    boolean match = false;
	    for (int inx = x; inx < x + 3; inx++) {
		if (ColorRanges.pixelIsColor(pixels[inx][iny], color)) {
		    match = true;
		}
	    }
	    if (match) {
		current++;
	    } else {
		if (current > returnInt) {
		    returnInt = current;
		}
		current = 0;
	    }
	}
	if (current > returnInt) {
	    returnInt = current;
	}
	return returnInt;
    }

    private int threeHorizontalLine(String color, int y) {
	int returnInt = 0;
	int current = 0;
	for (int inx = 0; inx < width; inx++) {
	    boolean match = false;
	    for (int iny = y; iny < y + 3; iny++) {
		if (ColorRanges.pixelIsColor(pixels[inx][iny], color)) {
		    match = true;
		}
	    }
	    if (match) {
		current++;
	    } else {
		if (current > returnInt) {
		    returnInt = current;
		}
		current = 0;
	    }
	}
	if (current > returnInt) {
	    returnInt = current;
	}
	return returnInt;
    }
    
    public int threeVerticalCount(String color) {
	int returnInt = 0;
	for (int inx = 0; inx < width - 3; inx++) {
	    int current = threeVerticalCount(color, inx);
	    if (current > returnInt) {
		returnInt = current;
	    }
	}
	return returnInt;	
    }

    public int fiveVerticalCount(String color) {
	int returnInt = 0;
	for (int inx = 0; inx < width - 5; inx++) {
	    int current = fiveVerticalCount(color, inx);
	    if (current > returnInt) {
		returnInt = current;
	    }
	}
	return returnInt;
    }

    public int fiveHorizontalCount(String color) {
	int returnInt = 0;
	for (int iny = 0; iny < height - 5; iny++) {
	    int current = fiveHorizontalCount(color, iny);
	    if (current > returnInt) {
		returnInt = current;
	    }
	}
	return returnInt;
    }

    public int threeHorizontalCount(String color) {
	int returnInt = 0;
	for (int iny = 0; iny < height - 3; iny++) {
	    int current = threeHorizontalCount(color, iny);
	    if (current > returnInt) {
		returnInt = current;
	    }	    
	}
	return returnInt;
    }

    private int threeVerticalCount(String color, int x) {
	int returnInt = 0;
	for (int inx = x; inx <= x + 2; inx++) {
	    for (int iny = 0; iny < height; iny++) {
		if (ColorRanges.pixelIsColor(pixels[inx][iny], color)) {
		    returnInt++;
		}
	    }
	}
	return returnInt;
    }

    private int threeHorizontalCount(String color, int y) {
	int returnInt = 0;
	for (int iny = y; iny <= y + 2; iny++) {
	    for (int inx = 0; inx < width; inx++) {
		if (ColorRanges.pixelIsColor(pixels[inx][iny], color)) {
		    returnInt++;
		}
	    }
	}
	return returnInt;
    }

    private int fiveVerticalCount(String color, int x) {
	int returnInt = 0;
	for (int inx = x; inx <= x + 4; inx++) {
	    for (int iny = 0; iny < height; iny++) {
		if (ColorRanges.pixelIsColor(pixels[inx][iny], color)) {
		    returnInt++;
		}
	    }
	}
	return returnInt;
    }
    
    private int fiveHorizontalCount(String color, int y) {
	int returnInt = 0;
	for (int iny = y; iny <= y + 4; iny++) {
	    for (int inx = 0; inx < width; inx++) {
		if (ColorRanges.pixelIsColor(pixels[inx][iny], color)) {
		    returnInt++;
		}
	    }
	}
	return returnInt;
    }
    
    public int horizontalCount(String color) {
	int returnInt = 0;
	for (int iny = 0; iny < height; iny++) {
	    int current = horizontalCount(color, iny);
	    if (current > returnInt) {
		returnInt = current;
	    }
	}
	return returnInt;
    }

    public int horizontalLine(String color) {
	int returnInt = 0;
	for (int iny = 0; iny < height; iny++) {
	    int current = horizontalLine(color, iny);
	    if (current > returnInt) {
		returnInt = current;
	    }
	}
	return returnInt;
    }

    public int verticalCount(String color) {
	int returnInt = 0;
	for (int inx = 0; inx < width; inx++) {
	    int current = verticalCount(color, inx);
	    if (current > returnInt) {
		returnInt = current;
	    }
	}
	return returnInt;
    }

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

    private int horizontalLine(String color, int y) {
	int returnInt = 0;
	int current = 0;
	for (int inx = 0; inx < width; inx++) {
	    if (ColorRanges.pixelIsColor(pixels[inx][y], color)) {
		current++;
	    } else {
		if (current > returnInt) {
		    returnInt = current;
		}
		current = 0;
	    }
	}
	if (current > returnInt) {
	    returnInt = current;
	}
	return returnInt;
    }

    private int verticalCount(String color, int x) {
	int returnInt = 0;
	for (int iny = 0; iny < height; iny++) {
	    if (ColorRanges.pixelIsColor(pixels[x][iny], color)) {
		returnInt++;
	    }
	}
	return returnInt;
    }

    private int horizontalCount(String color, int y) {
	int returnInt = 0;
	for (int inx = 0; inx < width; inx++) {
	    if (ColorRanges.pixelIsColor(pixels[inx][y], color)) {
		returnInt++;
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
	if (current > returnInt) {
	    returnInt = current;
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
