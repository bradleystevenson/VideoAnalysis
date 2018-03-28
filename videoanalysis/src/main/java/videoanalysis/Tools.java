package videoanalysis;

import videoanalysis.tools.FileTools;
import videoanalysis.tools.DatabaseTools;
import java.util.ArrayList;

public class Tools {


    public static ArrayList<String> getResultTypes(String tableName) {
	return DatabaseTools.getResultTypes(tableName);
    }
    
    public static void insertImageValues(String imageName, ArrayList<String> columns, ArrayList<Integer> values) {
	DatabaseTools.insertImageValues(imageName, columns, values);
    }

    public static void dropImageValuesTable() {
	DatabaseTools.dropImageValuesTable();
    }

    public static void createImageValuesTable(ArrayList<Color> colors) {
	DatabaseTools.createImageValuesTable(colors);
    }
	   
    
    public static ArrayList<String> getImageTableFields() {
	return DatabaseTools.getImageTableFields();
    }

    public static int getValuesOfImageType(String minOrMax, String colorString, String resultString) throws Exception {
	return DatabaseTools.getValuesOfImageType(minOrMax, colorString, resultString);
    }

    public static void insertImageValues(String imageName, int blackPixels) throws Exception {
	DatabaseTools.insertImageValues(imageName, blackPixels);
    }

    public static void insertResult(String tableName, String imageName, String result) {
	DatabaseTools.insertResult(tableName, imageName, result);
    }

    public static void insertPixelColor(String tableName, int red, int green, int blue) {
	DatabaseTools.insertPixelColor(tableName, red, green, blue);
    }
    
    public static void createColorTable(String color) {
	DatabaseTools.createColorTable(color);
    }

    public static int calculateColor(String colorTable, String rgbValue) {
	return DatabaseTools.calculateColor(colorTable, rgbValue);
    }

    public static ArrayList<String> getColorTableNames() {
	return DatabaseTools.getColorTableNames();
    }
    
    public static void connect() {
	DatabaseTools.connect();
    }
    
    public static ArrayList<String> readFile(String filePath) {
	return FileTools.readFile(filePath);
    }

    public static void printFile(String filePath, ArrayList<String> printStrings) {
	FileTools.printFile(filePath, printStrings);
    }

}
