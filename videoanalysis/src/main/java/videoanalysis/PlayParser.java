package videoanalysis;

import java.util.ArrayList;

public class PlayParser {


    public static ArrayList<Play> getPlays(String folderName) throws Exception {
	ArrayList<Play> returnArray = new ArrayList<Play>();
	ArrayList<String> fileStrings = Tools.readFile(folderName + "/Plays.txt");
	ArrayList<String> currentStrings = new ArrayList<String>();
	currentStrings.add(fileStrings.get(0));
	for (int inx = 1; inx < fileStrings.size(); inx++) {
	    String currentString = fileStrings.get(inx);
	    if (currentString.startsWith("Play:")) {
		Play play = new Play(currentStrings);
		returnArray.add(play);
		currentStrings = new ArrayList<String>();
	    } 	    
	}
	Play play = new Play(currentStrings);
	returnArray.add(play);

	return returnArray;
    }
    
    public static void parsePlays(String folderName) throws Exception {
	ArrayList<String> fileStrings = Tools.readFile(folderName + "/ScoreboardScanner.txt");
	ArrayList<ArrayList<String>> sortedStrings = sortStrings(fileStrings);
	ArrayList<String> printStrings = new ArrayList<String>();
	for (int inx = 0; inx + 1 < sortedStrings.size(); inx=inx+2) {
	    int playCount = inx / 2 + 1;
	    printStrings.add("Play: " + playCount);
	    for (String string : sortedStrings.get(inx)) {
		printStrings.add(string);
	    }
	    for (String string : sortedStrings.get(inx + 1)) {
		printStrings.add(string);
	    }
	}
	Tools.printFile(folderName +"/Plays.txt", printStrings);
    }

    private static ArrayList<ArrayList<String>> sortStrings(ArrayList<String> inputStrings) {
	ArrayList<ArrayList<String>> returnArray = new ArrayList<ArrayList<String>>();
	String currentMatch = getPictureType(inputStrings.get(0));
	ArrayList<String> currentStrings = new ArrayList<String>();
	for (String string : inputStrings) {
	    if (string.endsWith(currentMatch)) {
		currentStrings.add(string);
	    } else {
		if (currentStrings.size() > 5) {		    
		    returnArray.add(currentStrings);
		}
		currentStrings = new ArrayList<String>();
		currentMatch = getPictureType(string);
	    }
	}	
	return returnArray;
    }

    private static String getPictureType(String inputString) {
	String returnString = "";
	for (int inx = inputString.length() - 1; inx >= 0; inx--) {
	    char charx = inputString.charAt(inx);
	    if (charx == '|') {
		return returnString;
	    }
	    returnString = charx + returnString;
	}
	return null;
    }

}
