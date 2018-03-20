package videoanalysis;

import videoanalysis.tools.FileTools;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Tools {

    private static Connection connection;

    public static int getValuesOfImageType(String minOrMax, String colorString, String resultString) throws Exception {
	PreparedStatement statement = connection.prepareStatement("select " + minOrMax +"(" + colorString + "count) from scoreboardresults natural join imageValues where result like '" + resultString + "'");
	ResultSet set = statement.executeQuery();
	set.next();
	int returnInt = set.getInt(1);
	set.close();
	statement.close();
	return returnInt;
    }

    public static int getMinScoreboardBlack() throws Exception {
	PreparedStatement statement = connection.prepareStatement("select min(blackpixelcount) from scoreboardresults natural join imageValues where result like 'Scoreboard'");
	ResultSet set = statement.executeQuery();
	set.next();
	int returnInt = set.getInt(1);
	set.close();
	statement.close();
	return returnInt;

    }

    public static int getMaxFieldBlack() throws Exception {
	PreparedStatement statement = connection.prepareStatement("select max(blackpixelcount) from imageValues natural join scoreboardresults where result like 'Field'");
	ResultSet set = statement.executeQuery();
	set.next();
	int returnInt = set.getInt(1);
	set.close();
	statement.close();
	return returnInt;
    }

    public static int getMinFieldBlack() throws Exception {
	PreparedStatement statement = connection.prepareStatement("select min(blackpixelcount) from imageValues natural join scoreboardresults where result like 'Field'");
	ResultSet set = statement.executeQuery();
	set.next();
	int returnInt = set.getInt(1);
	set.close();
	statement.close();
	return returnInt;
    }

    public static int getMaxScoreboardBlack() throws Exception {
	PreparedStatement statement = connection.prepareStatement("select max(blackpixelcount) from imageValues natural join scoreboardresults where result like 'Scoreboard'");
	ResultSet set = statement.executeQuery();
	set.next();
	int returnInt = set.getInt(1);
	set.close();
	statement.close();
	return returnInt;
    }

    
    public static void insertImageValues(String imageName, int blackPixels) throws Exception {
	try {
	    PreparedStatement statement = connection.prepareStatement("insert into imageValues (imageName, blackpixelcount) values (?, ?)");
	    statement.setString(1, imageName);
	    statement.setInt(2, blackPixels);
	    statement.executeUpdate();
	    statement.close();
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }
    
    public static void insertResult(String tableName, String imageName, String result) {
	try {
	    PreparedStatement statement = connection.prepareStatement("insert into " + tableName + " (imageName, result) values (?, ?)");
	    statement.setString(1, imageName);
	    statement.setString(2, result);
	    statement.executeUpdate();
	    statement.close();
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(1);
	}

    }

    public static void insertPixelColor(String tableName, int red, int green, int blue) {
	try {
	    PreparedStatement statement = connection.prepareStatement("insert into " + tableName + " (red, green, blue) values (?, ?, ?");
	    statement.setInt(1, red);
	    statement.setInt(2, green);
	    statement.setInt(3, blue);
	    statement.executeUpdate();
	    statement.close();
	} catch (Exception e) {
	    System.out.println("Error inserting pixel");
	    System.exit(1);
	}
    }
    
    public static void createColorTable(String color) {
	try {
	    PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + color + " (red integer, green integer, blue integer)");
	    statement.executeUpdate();
	    statement.close();
	} catch (Exception e) {
	    System.out.println("Error creating colorTable");
	    System.exit(1);
	}
    }

    public static int calculateColor(String colorTable, String rgbValue) {
	try {
	    PreparedStatement statement = connection.prepareStatement("select avg(" + rgbValue + ") from " + colorTable + "pixels");
	    ResultSet set = statement.executeQuery();
	    set.next();
	    int returnInt = set.getInt(1);
	    set.close();
	    statement.close();
	    return returnInt;
	} catch (Exception e) {
	    System.out.println("Error determining colors");	    
	    System.exit(1);
	}
	return -1;
    }

    public static ArrayList<String> getColorTableNames() {
	ArrayList<String> returnStrings = new ArrayList<String>();
	try {
	    PreparedStatement statement = connection.prepareStatement("SELECT name FROM sqlite_master WHERE type='table'");
	    ResultSet set = statement.executeQuery();
	    while (set.next()) {
		String temp = set.getString(1).toLowerCase();
		if (temp.endsWith("pixels")) {
		    returnStrings.add(temp.replace("pixels", ""));
		}
		
	    }
	    set.close();
	    statement.close();
	} catch (Exception e) {
	    System.out.println("Error getting table names");
	    System.exit(1);
	}
	return returnStrings;
    }
    
    public static void connect() {
	try {
	    connection = DriverManager.getConnection("jdbc:sqlite:/Users/Bradley/Programs/VideoAnalysis/videoanalysis/database.db");
	} catch (Exception e) {
	    System.out.println("Unable to connect to database");
	    System.exit(1);
	}
    }
    
    public static ArrayList<String> readFile(String filePath) {
	return FileTools.readFile(filePath);
    }

    public static void printFile(String filePath, ArrayList<String> printStrings) {
	FileTools.printFile(filePath, printStrings);
    }

}
