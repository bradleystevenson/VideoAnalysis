
package videoanalysis;

import java.util.ArrayList;
import java.io.File;


public class Answers {

    private static boolean columnExists(String color, String extension, ArrayList<String> columnStrings) {
	for (String columnName : columnStrings) {
	    if (columnName.equals(color + extension)) {
		return true;
	    }
	}
	return false;
    }
    
    private static boolean checkTable() {
	ArrayList<Color> colors = ColorRanges.getColors();
	ArrayList<String> columnStrings = Tools.getImageTableFields();
	String[] strings = {"horizontalcount", "verticalcount", "horizontalline", "verticalline", "pixelcount", "threeverticalcount", "fiveverticalcount", "fivehorizontalcount", "threehorizontalcount", "fivehorizontalline", "threehorizontalline", "threeverticalline", "fiveverticalline"};
	for (Color color : colors) {
	    String colorString = color.getColor();
	    for (String string : strings) {
		if (!columnExists(colorString, string, columnStrings)) {
		    return false;
		}
	    }
	}
	return true;
    }
    
    public static void calculateImageValues(String folderName) throws Exception {
	if (!checkTable()) {
	    Tools.dropImageValuesTable();
	    Tools.createImageValuesTable(ColorRanges.getColors());
	}
	File folder = new File(folderName);
	File[] files = folder.listFiles();
	for (File file : files) {
	    if (file.toString().endsWith(".png")) {
		Image image = new Image(file.toString());
		ArrayList<Color> colors = ColorRanges.getColors();
		ArrayList<String> strings = new ArrayList<String>();
		ArrayList<Integer> values = new ArrayList<Integer>();
		for (Color color : colors) {
		    strings.add(color.getColor() + "pixelcount");
		    values.add(image.colorPixelCount(color.getColor()));
		    strings.add(color.getColor() + "verticalline");
		    values.add(image.verticalLine(color.getColor()));
		    strings.add(color.getColor() + "horizontalline");
		    values.add(image.horizontalLine(color.getColor()));
		    strings.add(color.getColor() + "verticalcount");
		    values.add(image.verticalCount(color.getColor()));
		    strings.add(color.getColor() + "horizontalcount");
		    values.add(image.horizontalCount(color.getColor()));
		    strings.add(color.getColor() + "threeverticalcount");
		    values.add(image.threeVerticalCount(color.getColor()));
		    strings.add(color.getColor() + "fiveverticalcount");
		    values.add(image.fiveVerticalCount(color.getColor()));
		    strings.add(color.getColor() + "threehorizontalcount");
		    values.add(image.threeHorizontalCount(color.getColor()));
		    strings.add(color.getColor() + "fivehorizontalcount");
		    values.add(image.fiveHorizontalCount(color.getColor()));
		    strings.add(color.getColor() + "threeverticalline");
		    values.add(image.threeVerticalLine(color.getColor()));
		    strings.add(color.getColor() + "fiveverticalline");
		    values.add(image.fiveVerticalLine(color.getColor()));
		    strings.add(color.getColor() + "threehorizontalline");
		    values.add(image.threeHorizontalLine(color.getColor()));
		    strings.add(color.getColor() + "fivehorizontalline");
		    values.add(image.fiveHorizontalLine(color.getColor()));		    
		}
		//		Tools.insertImageValues(file.toString(), image.colorPixelCount("black"));
		Tools.insertImageValues(file.toString(), strings, values);
	    }
	}
    }

    private static void parseFile(String fileName, String tableName) throws Exception {
	String folderName = getUpToLastSlash(fileName);
	ArrayList<String> lines = Tools.readFile(fileName);
	int maxDigits = 0;
	for (String string : lines) {
	    int current = maxDigits(string);
	    if (maxDigits < current) {
		maxDigits = current;
	    }
	}
	for (String line : lines) {
	    parseLine(line, maxDigits, folderName, tableName);
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

    private static void parseLine(String line, int maxDigits, String folderName, String tableName) throws Exception {
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
	    String imageName = getImageName(imageNumber, folderName);
	    Tools.insertResult(tableName, imageName, result);
	}    
    }

    private static String getImageName(String searchString, String folderName) throws Exception {
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
	for (int inx = 0; inx < input.length(); inx++) {
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
    
