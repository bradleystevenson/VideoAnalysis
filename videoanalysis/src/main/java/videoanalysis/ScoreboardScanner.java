package videoanalysis;

import java.util.ArrayList;
import java.io.File;

public class ScoreboardScanner {

    public static void determineScoreboards(String folderName) throws Exception {
	ArrayList<String> printStrings = Tools.readFile(folderName + "/ScoreboardScanner.txt");
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
	Tools.printFile(folderName + "/ScoreboardScanner.txt", printStrings);
    }

    private static boolean imageAlreadyCalculated(String imageString, ArrayList<String> printStrings) {
	for (String string : printStrings) {
	    if (string.contains(imageString)) {
		return true;
	    }	    
	}
	return false;
    }
}
