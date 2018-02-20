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
	ArrayList<String> tableNames = getTableNames();
	for (String tableName : tableNames) {
	    Color color = new Color(tableName);
	    colors.add(color);
	}
    }

    private static ArrayList<String> getTableNames() throws Exception {
	ArrayList<String> returnArray = new ArrayList<String>();
	PreparedStatement statement = connection.prepareStatement("SELECT name FROM sqlite_master WHERE type='table'");
	ResultSet set = statement.executeQuery();
	while (set.next()) {
	    String temp = set.getString(1).toLowerCase();
	    if (temp.endsWith("pixels")) {
		returnArray.add(temp.replace("pixels", ""));
	    }
	}
	set.close();
	statement.close();
	return returnArray;
    }

}
