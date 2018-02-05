package videoanalysis;

public class Pixel {

    private int x;
    private int y;
    private int red;
    private int green;
    private int blue;

    public int getRed() {
	return red;
    }

    public int getGreen() {
	return green;
    }

    public int getBlue() {
	return blue;
    }
	

    public boolean comparePixel(Pixel inputPixel) {
	if (red != inputPixel.getRed() || green != inputPixel.getGreen() || blue != inputPixel.getBlue()) {
	    return true;
	}
	return false;
    }

    public Pixel(int xInput, int yInput, int colorInput) {
	x = xInput;
	y = yInput;
	red = (colorInput >> 16) & 0xFF;
        green = (colorInput >> 8) & 0xFF;
        blue = (colorInput >> 0) & 0xFF;
    }
}
