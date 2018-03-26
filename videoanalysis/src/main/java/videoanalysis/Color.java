package videoanalysis;

public class Color {

    private String color;
    private int blueColor;
    private int redColor;
    private int greenColor;

    public String getColor() {
	return color;
    }
    
    public boolean isColor(String colorCheck) {
	return color.toLowerCase().equals(colorCheck.toLowerCase());
    }

    
    public Color(String colorInput) throws Exception {
	color = colorInput;
	blueColor = Tools.calculateColor(color, "blue");
	redColor = Tools.calculateColor(color, "red");
	greenColor = Tools.calculateColor(color, "green");
    }

    public boolean pixelIsColor(Pixel pixel) {
	int red = pixel.getRed();
	int green = pixel.getGreen();
	int blue = pixel.getBlue();
	return (Math.abs(red - redColor) <= 30 && Math.abs(green - greenColor) <= 30 && Math.abs(blue - blueColor) <= 30);
    }
	
    
}
