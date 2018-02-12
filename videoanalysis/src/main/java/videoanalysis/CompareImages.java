
package videoanalysis;

import java.io.File;
import java.util.ArrayList;
import java.sql.*;

public class CompareImages {
    
    private static String databaseString = "jdbc:sqlite:/Users/Bradley/Programs/VideoAnalysis/videoanalysis/database.db";
    private static Connection connection;
    
    private static void connect() throws Exception{
	connection = DriverManager.getConnection(databaseString);	
    }
    
    public static void compareImages(String folderName) throws Exception {
	connect();
	File folder = new File(folderName);
	File[] files = folder.listFiles();	
	ArrayList<String> originalImages = new ArrayList<String>();
	ArrayList<File> folders = new ArrayList<File>();
	for (File file : files) {
	    if (file.toString().endsWith(".png")) {
		originalImages.add(file.toString());
	    }
	    if (file.isDirectory()) {
		folders.add(file);
	    }
	}
	ArrayList<ArrayList<String>> alteredImages = new ArrayList<ArrayList<String>>();
	ArrayList<String> folderNames = new ArrayList<String>();
	ArrayList<String> tableNames = new ArrayList<String>();
	for (File alteredFolder : folders) {
	    createTable(alteredFolder.getName());
	    folderNames.add(alteredFolder.toString());
	    alteredImages.add(getImagesFromFolder(alteredFolder));
	    tableNames.add(alteredFolder.getName());
	}
	for (int inx = 0; inx < alteredImages.size(); inx++) {
	    compareImages(alteredImages.get(inx), originalImages, tableNames.get(inx));
	}
    }

    private static void createTable(String tableName) throws Exception {
	PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + tableName + " (red integer, green integer, blue integer)");
	statement.executeUpdate();
	statement.close();	
    }

    private static void compareImages(ArrayList<String> alteredImages, ArrayList<String> originalImages, String tableName) throws Exception {
	int matchCount = 0;
	for (int inx = 0; inx < alteredImages.size(); inx++) {
	    for (int iny = 0; iny < originalImages.size(); iny++) {
		if (getFileName(alteredImages.get(inx)).equals(getFileName(originalImages.get(iny)))) {
		    Image originalImage = new Image(originalImages.get(iny));
		    Image alteredImage = new Image(alteredImages.get(inx));
		    compareImages(originalImage, alteredImage, tableName);
		}
	    }
	}
	System.out.println(matchCount);
    }

    private static void compareImages(Image originalImage, Image alteredImage, String tableName) throws Exception {
	int width = originalImage.getWidth();
	int height = originalImage.getHeight();
	for (int inx = 0; inx < width; inx++) {
	    for (int iny = 0; iny < height; iny++) {
		Pixel originalPixel = originalImage.getPixel(inx, iny);
		Pixel alteredPixel = alteredImage.getPixel(inx, iny);
		if (originalPixel.comparePixel(alteredPixel)) {
		    PreparedStatement statement = connection.prepareStatement("insert into " + tableName + " (red, green, blue) values (?, ?, ?)");
		    statement.setInt(1, originalPixel.getRed());
		    statement.setInt(2, originalPixel.getGreen());
		    statement.setInt(3, originalPixel.getBlue());
		    statement.executeUpdate();
		    statement.close();
		}

	    }
	}

    }

    private static String getFileName(String input) {
	String returnLine = "";
	int inx = input.length() - 1;
	char charx = input.charAt(inx);
	while (charx != '/') {
	    returnLine = charx + returnLine;
	    inx--;
	    charx = input.charAt(inx);	    
	}
	return returnLine;
    }

    private static ArrayList<String> getImagesFromFolder(File folder) {
	ArrayList<String> returnStrings = new ArrayList<String>();
	File[] files = folder.listFiles();
	for (File file : files) {
	    if (file.toString().endsWith(".png")) {
		returnStrings.add(file.toString());
	    }
	}
	return returnStrings;
    }
}
