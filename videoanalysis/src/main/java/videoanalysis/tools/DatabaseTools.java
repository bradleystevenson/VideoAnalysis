package videoanalysis.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import videoanalysis.Color;

public class DatabaseTools {

    private static Connection connection;

    public static ArrayList<String> getResultTypes(String tableName) {
	ArrayList<String> returnStrings = new ArrayList<String>();
	try {
	    PreparedStatement statement = connection.prepareStatement("SELECT distinct(result) from " + tableName);
	    ResultSet set = statement.executeQuery();
	    while (set.next()) {
		returnStrings.add(set.getString(1));
	    }
	    set.close();
	    statement.close();
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(1);
	}
	return returnStrings;
    }
    
    
    public static void insertImageValues(String imageName, ArrayList<String> columns, ArrayList<Integer> values) {
	try {
	    String string = "INSERT INTO imageValues (";
	    for (String column : columns) {
		string = string + column + ", ";
	    }
	    string = string + "imageName) values (";
	    for (int value : values) {
		string = string + value + ", ";
	    }
	    string = string + "\"" + imageName + "\")";
	    System.out.println(string);
	    PreparedStatement statement = connection.prepareStatement(string);
	    statement.executeUpdate();
	    statement.close();
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }
    
    public static void createImageValuesTable(ArrayList<Color> colors) {
	try {
	    String string = "CREATE TABLE imageValues (imageName varchar, ";
	    for (int inx = 0; inx < colors.size() - 1; inx++) {
		string = string + colors.get(inx).getColor() + "pixelcount integer, " + colors.get(inx).getColor() + "verticalline integer, " + colors.get(inx).getColor() +"horizontalline integer, ";
	    }
	    string = string + colors.get(colors.size() - 1).getColor() + "verticalline integer, " + colors.get(colors.size() - 1).getColor() + "pixelcount integer, " + colors.get(colors.size() -1).getColor() + "horizontalline integer)";
	    PreparedStatement statement = connection.prepareStatement(string);
	    statement.executeUpdate();
	    statement.close();

	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }

    public static void dropImageValuesTable() {
	try {
	    PreparedStatement statement = connection.prepareStatement("DROP TABLE imageValues");
	    statement.executeUpdate();
	    statement.close();
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }

    public static ArrayList<String> getImageTableFields() {
	ArrayList<String> returnStrings = new ArrayList<String>();
	try {
	    
	    PreparedStatement statement = connection.prepareStatement("select * from imageValues");
	    ResultSet set = statement.executeQuery();
	    ResultSetMetaData rsmd = set.getMetaData();
	    int columnCount = rsmd.getColumnCount();
	    for (int inx = 1; inx <= columnCount; inx++) {
		returnStrings.add(rsmd.getColumnName(inx));
	    }
	    set.close();
	    statement.close();
	    return returnStrings;
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(1);
	}
	return returnStrings;
    }
	
    public static int getValuesOfImageType(String minOrMax, String colorString, String resultString) throws Exception {
	PreparedStatement statement = connection.prepareStatement("select " + minOrMax + "(" + colorString + ") from scoreboardresults natural join imageValues where result like '" + resultString + "'");
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
	    PreparedStatement statement = connection.prepareStatement("insert into " + tableName + " (red, green, blue) values (?, ?, ?)");
	    statement.setInt(1, red);
	    statement.setInt(2, green);
	    statement.setInt(3, blue);
	    statement.executeUpdate();
	    statement.close();

	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }

    public static void createColorTable(String color) {
	try {
	    PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + color + " (red integer, green integer, blue integer)");
	    statement.executeUpdate();
	    statement.close();	    
	} catch (Exception e) {
	    e.printStackTrace();
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
	    e.printStackTrace();
	    System.exit(1);
	}
	return -1;
    }

    public static ArrayList<String> getColorTableNames() {
	ArrayList<String> returnStrings = new ArrayList<String>();
	try {
	    PreparedStatement statement = connection.prepareStatement("SELECT name from sqlite_master WHERE type='table'");
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
	    e.printStackTrace();
	    System.exit(1);
	}
	return returnStrings;
    }

    public static void connect() {
	try {
	    connection = DriverManager.getConnection("jdbc:sqlite:/Users/Bradley/Programs/VideoAnalysis/videoanalysis/database.db");
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }
}
