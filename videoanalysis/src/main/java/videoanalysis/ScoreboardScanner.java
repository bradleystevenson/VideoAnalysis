package videoanalysis;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;

public class ScoreboardScanner {

    public static void determineScoreboards(String folderName) throws Exception {
	ArrayList<String> printStrings = getAlreadyStrings(folderName);
	File folder = new File(folderName);
	File[] files = folder.listFiles();
	for (File file : files) {
	    if (file.toString().endsWith(".png")) {
		if (!imageAlreadyCalculated(file.toString(), printStrings)) {
		    Image image = new Image(file.toString());
		    String result = ScoreboardAnswers.scoreboardScanner(image);
		    printStrings.add(file.toString() + "|" + result);
		}
	    }
	}
	printStrings(printStrings, folderName);
    }

    private static boolean imageAlreadyCalculated(String imageString, ArrayList<String> printStrings) {
	for (String string : printStrings) {
	    if (string.contains(imageString)) {
		return true;
	    }	    
	}
	return false;
    }

    private static ArrayList<String> getAlreadyStrings(String folderName) throws Exception {
	ArrayList<String> returnStrings = new ArrayList<String>();
	BufferedReader in = new BufferedReader(new FileReader(folderName + "/ScoreboardScanner.txt"));
	String readLine;
	while ((readLine = in.readLine()) != null) {
	    returnStrings.add(readLine);
	}
	return returnStrings;
    }
    
    private static void printStrings(ArrayList<String> printStrings, String folderName) throws Exception {
	PrintWriter out = new PrintWriter(new FileWriter(folderName + "/ScoreboardScanner.txt"));
	for (String string : printStrings) {
	    out.println(string);
	}
	out.close();
    }

}
