package videoanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Tools {

    private static Connection connection;

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
	ArrayList<String> returnStrings = new ArrayList<String>();
	try {
	    BufferedReader in = new BufferedReader(new FileReader(filePath));
	    String readLine;
	    while ((readLine = in.readLine()) != null) {
		returnStrings.add(readLine);
	    }
	    in.close();
	} catch (Exception e) {
	    System.out.println("File it requested to read does not exist");
	    System.exit(1);
	}
	return returnStrings;
    }

    public static void printFile(String filePath, ArrayList<String> printStrings) {
	try {
	    PrintWriter out = new PrintWriter(new FileWriter(filePath));
	    for (String string : printStrings) {
		out.println(string);
	    }
	    out.close();
	} catch (Exception e) {
	    System.out.println("Error printing to file");
	    System.exit(1);
	}
    }

}
