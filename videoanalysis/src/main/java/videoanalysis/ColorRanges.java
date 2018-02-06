package videoanalysis;

import java.sql.*;

public class ColorRanges {


    private static int blackPixelRed;
    private static int blackPixelGreen;
    private static int blackPixelBlue;

    private static String databaseString = "jdbc:sqlite:/Users/Bradley/Programs/VideoAnalysis/videoanalysis/database.db";
    private static Connection connection;

    private static void connect() throws Exception{
        connection = DriverManager.getConnection(databaseString);
    }

    public static boolean pixelIsBlack(Pixel pixel) {
	int red = pixel.getRed();
	int green = pixel.getGreen();
	int blue = pixel.getBlue();
	return (Math.abs(red - blackPixelRed) <= 30 && Math.abs(green - blackPixelGreen) <= 30 && Math.abs(blue - blackPixelBlue) <= 30);
    }


    public static void determineColorRanges()  throws Exception {
	connect();
	calculateBlackPixelRed();
	calculateBlackPixelGreen();
	calculateBlackPixelBlue();
    }

    private static void calculateBlackPixelBlue() throws Exception {
	PreparedStatement statement = connection.prepareStatement("select avg(blue) from blackpixels");
	ResultSet set = statement.executeQuery();
	set.next();
	blackPixelBlue = set.getInt(1);
	set.close();
	statement.close();
	System.out.println(blackPixelBlue);

    }

    private static void calculateBlackPixelGreen() throws Exception {
	PreparedStatement statement = connection.prepareStatement("select avg(green) from blackpixels");
	ResultSet set = statement.executeQuery();
	set.next();
	blackPixelGreen = set.getInt(1);
	set.close();
	statement.close();
	System.out.println(blackPixelGreen);
    }

    private static void calculateBlackPixelRed() throws Exception {
	PreparedStatement statement = connection.prepareStatement("select avg(red) from blackpixels");
	ResultSet set = statement.executeQuery();
	set.next();
	blackPixelRed = set.getInt(1);
	set.close();
	statement.close();
    }
    
}
