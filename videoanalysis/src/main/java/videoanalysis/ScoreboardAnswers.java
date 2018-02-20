package videoanalysis;

import java.util.ArrayList;
import java.io.File;
import java.util.ArrayList;
import java.sql.*;


public class ScoreboardAnswers {

    private static String folderName;
    private static String databaseString = "jdbc:sqlite:/Users/Bradley/Programs/VideoAnalysis/videoanalysis/database.db";
    private static Connection connection;
    private static boolean blackCutoffValid = false;
    private static int blackCutoffValue;
    
    private static void connect() throws Exception{
        connection = DriverManager.getConnection(databaseString);
    }

    public static String scoreboardScanner(Image image) {
	if (blackCutoffValid) {
	    int blackPixelCount = image.colorPixelCount("black");
	    if (blackPixelCount > blackCutoffValue) {
		return "Scoreboard";
	    }
	    return "Field";
	}
	return null;
    }

    private static int getMaxFieldBlack() throws Exception {
	PreparedStatement statement = connection.prepareStatement("select max(blackpixelcount) from scoreboardresults where result like 'Field'");
	ResultSet set = statement.executeQuery();
	set.next();
	int returnInt = set.getInt(1);
	set.close();
	statement.close();
	return returnInt;
    }

    private static int getMinFieldBlack() throws Exception {
	PreparedStatement statement = connection.prepareStatement("select min(blackpixelcount) from scoreboardresults where result like 'Field'");
	ResultSet set = statement.executeQuery();
	set.next();
	int returnInt = set.getInt(1);
	set.close();
	statement.close();
	return returnInt;
    }

    private static int getMaxScoreboardBlack() throws Exception {
	PreparedStatement statement = connection.prepareStatement("select max(blackpixelcount) from scoreboardresults where result like 'Scoreboard'");
	ResultSet set = statement.executeQuery();
	set.next();
	int returnInt = set.getInt(1);
	set.close();
	statement.close();
	return returnInt;
    }

    private static int getMinScoreboardBlack() throws Exception {
	PreparedStatement statement = connection.prepareStatement("select min(blackpixelcount) from scoreboardresults where result like'Scoreboard'");
	ResultSet set = statement.executeQuery();
	set.next();
	int returnInt = set.getInt(1);
	set.close();
	statement.close();
	return returnInt;
    }

    public static void determineScoreboardEquation() throws Exception {
	connect();
	int maxFieldBlack = getMaxFieldBlack();
	int minFieldBlack = getMinFieldBlack();
	int maxScoreboardBlack = getMaxScoreboardBlack();
	int minScoreboardBlack = getMinScoreboardBlack();
	if (maxFieldBlack < minScoreboardBlack) {
	    blackCutoffValid = true;
	    blackCutoffValue = (maxFieldBlack + minScoreboardBlack) / 2;
	}
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
	ArrayList<String> lines = Tools.readFile(fileName);
	int maxDigits = 0;
	for (String string : lines) {
	    int current = maxDigits(string);
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
	    insertScoreboardResult(imageName, result);
	    insertImageValues(imageName, image.colorPixelCount("black"));
	}
    }

    private static void insertScoreboardResult(String imageName, String result) throws Exception {
	PreparedStatement statement = connection.prepareStatement("insert into scoreboardresults (imageName, result) values (?, ?)");
	statement.setString(1, imageName);
	statement.setString(2, result);
	statement.executeUpdate();
	statement.close();
    }

    private static void insertImageValues(String imageName, int blackPixels) throws Exception {
	PreparedStatement statement = connection.prepareStatement("insert into imageValues (imageName, blackpixelcount) values (?, ?)");
	statement.setString(1, imageName);
	statement.setInt(2, blackPixels);
	statement.executeUpdate();
	statement.close();
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
