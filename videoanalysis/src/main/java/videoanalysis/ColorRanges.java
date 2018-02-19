package videoanalysis;

public class ColorRanges {

    private static Color black;

    
    public static boolean pixelIsBlack(Pixel pixel) {
	return black.pixelIsColor(pixel);
    }


    public static void determineColorRanges()  throws Exception {
	black = new Color("black");
    }

}
