package videoanalysis;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.sql.*;


public class ScoreboardAnswers {

    private static String folderName;
    private static String databaseString = "jdbc:sqlite:/Users/Bradley/Programs/VideoAnalysis/videoanalysis/database.db";
    private static Connection connection;
    
    private static void connect() throws Exception{
        connection = DriverManager.getConnection(databaseString);
    }

    
    private static String getUpToLastSlash(String fileName) {
	int inx = fileName.length() - 1;
	char charx = fileName.charAt(inx);
	while (charx != '/') {
	    inx = inx - 1;
	    charx = fileName.charAt(inx);
	}
	String returnString = "";
	for (int iny = 0; iny <= inx; iny++) {
	    returnString = returnString + fileName.charAt(iny);
	}
	return returnString;
    }
    
    public static void parseFile(String fileName) throws Exception {
	connect();
	folderName = getUpToLastSlash(fileName);
	BufferedReader in = new BufferedReader(new FileReader(fileName));
	String readLine;
	ArrayList<String> lines = new ArrayList<String>();
	int maxDigits = 0;
	while ((readLine = in.readLine()) != null) {
	    lines.add(readLine);
	    int current = maxDigits(readLine);
	    if (maxDigits < current) {
		maxDigits = current;
	    }
	}
	for (String line : lines) {
	    parseLine(line, maxDigits);
	}
    }

    private static void parseLine(String line, int maxDigits) throws Exception {
	int inx = 0;
	char charx = line.charAt(inx);
	String firstNumberString = "";
	while (charx != '-') {
	    firstNumberString = firstNumberString + charx;
	    inx++;
	    charx = line.charAt(inx);	    
	}
	inx++;
	charx = line.charAt(inx);
	String secondNumberString = "";
	while (charx != ' ') {
	    secondNumberString = secondNumberString + charx;
	    inx++;
	    charx = line.charAt(inx);
	}
	int firstNumber = Integer.parseInt(firstNumberString);
	int secondNumber = Integer.parseInt(secondNumberString);
	String result = "";
	for (inx = inx + 1; inx < line.length(); inx++) {
	    result = result + line.charAt(inx);
	}
	for (inx = firstNumber; inx <= secondNumber; inx++) {
	    String imageNumber = convertIntToString(inx, maxDigits) + ".png";
	    String imageName = getImageName(imageNumber);
	    Image image = new Image(imageName);
	    insertToDatabase(imageName, result, image.blackPixelCount());
	}
    }

    private static void insertToDatabase(String imageName, String result, int blackPixelCount) throws Exception {
	PreparedStatement statement = connection.prepareStatement("insert into scoreboardresults (imageName, result, blackpixelcount) values (?, ?, ?)");
	statement.setString(1, imageName);
	statement.setString(2, result);
	statement.setInt(3, blackPixelCount);
	statement.executeUpdate();
	statement.close();
    }
										 
    
    private static String getImageName(String searchString) throws Exception {
	File folder = new File(folderName);
	File[] files = folder.listFiles();
	for (File file : files) {
	    if (file.toString().endsWith(searchString)) {
		return file.toString();
	    }	    
	}
	return null;
    }

    private static String convertIntToString(int number, int maxDigits) {
	int numberPlace = (new Integer(number)).toString().length();
	int difference = maxDigits - numberPlace;
	String zeroString = "";
	for (int inx = 0; inx < difference; inx++) {
	    zeroString = zeroString + "0";
	}
	String numberString = (new Integer(number)).toString();
	return zeroString + numberString;
    }

    private static int maxDigits(String input) {
	int maxDigits = 0;
	int currentDigits = 0;
	for (int inx =0 ; inx < input.length(); inx++) {
	    char charx = input.charAt(inx);
	    if (charx >= '0' && charx <= '9') {
		currentDigits++;
	    } else {
		if (maxDigits < currentDigits) {
		    maxDigits = currentDigits;
		}
		currentDigits = 0;
	    }
	}
	return maxDigits;
    }

}
