package videoanalysis;

import java.sql.*;
import java.util.ArrayList;

public class ColorRanges {

    private static Color black;
    private static String databaseString = "jdbc:sqlite:/Users/Bradley/Programs/VideoAnalysis/videoanalysis/database.db";
    private static Connection connection;
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


    private static void connect() throws Exception {
	connection = DriverManager.getConnection(databaseString);	
    }
    
    public static void determineColorRanges()  throws Exception {
	connect();
	colors = new ArrayList<Color>();
	ArrayList<String> tableNames = Tools.getColorTableNames();
	for (String tableName : tableNames) {
	    Color color = new Color(tableName);
	    colors.add(color);
	}
    }

}
