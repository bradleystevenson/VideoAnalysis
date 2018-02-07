package videoanalysis;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;


public class ScoreboardScanner {

    public static void determineScoreboards(String folderName) throws Exception {
	ArrayList<String> printStrings = new ArrayList<String>();
	File folder = new File(folderName);
	File[] files = folder.listFiles();
	for (File file : files) {
	    if (file.toString().endsWith(".png")) {
		Image image = new Image(file.toString());
		String result = ScoreboardAnswers.scoreboardScanner(image);
		printStrings.add(file.toString() + "|" + result);
	    }
	}
	printStrings(printStrings, folderName);
    }

    private static void printStrings(ArrayList<String> printStrings, String folderName) throws Exception {
	PrintWriter out = new PrintWriter(new FileWriter(folderName + "/ScoreboardScanner.txt"));
	for (String string : printStrings) {
	    out.println(string);
	}
	out.close();
    }

}
