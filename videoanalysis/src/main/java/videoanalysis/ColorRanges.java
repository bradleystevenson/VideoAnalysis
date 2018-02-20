package videoanalysis;

import java.util.ArrayList;

public class ColorRanges {

    private static Color black;
    private static ArrayList<Color> colors;

    public static boolean pixelIsColor(Pixel pixel, String colorString) {
	for (Color color : colors) {
	    if (color.isColor(colorString)) {
		return color.pixelIsColor(pixel);
	    }
	}
	return false;
    }
    
    public static boolean pixelIsBlack(Pixel pixel) {
	return black.pixelIsColor(pixel);
    }

    public static void determineColorRanges()  throws Exception {
	colors = new ArrayList<Color>();
	ArrayList<String> tableNames = Tools.getColorTableNames();
	for (String tableName : tableNames) {
	    Color color = new Color(tableName);
	    colors.add(color);
	}
    }

}
