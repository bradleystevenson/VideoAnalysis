package videoanalysis;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{

    private static boolean colorFlag = false;
    private static boolean scoreboardFlag = false;

    
    public static void main( String[] args ) throws Exception
    {

	for (int inx = 0; inx < args.length; inx++) {
	    if (args[inx].startsWith("-")) {
		determineFlags(args[inx]);
	    }
	}
	String folderName = args[indexOfFile(args)];
	if (colorFlag) {
	    CompareImages.compareImages(folderName);
	}
	if (scoreboardFlag) {
	    ScoreboardAnswers.parseFile(folderName + "/ScoreboardAnswers.txt");
	}
	/*
	String folderName = args[0];
	/* CompareImages.compareImages(folderName);
	 */
	/*
	File folder = new File(folderName);	
	File[] listOfFiles = folder.listFiles();
	ColorRanges.determineColorRanges();
	for (File file : listOfFiles) {
	    if (file.toString().endsWith(".png")) {
		//		Image image = new Image(file.toString());
		//System.out.println(file);
	    } else if (file.toString().endsWith("ScoreboardAnswers.txt")) {
		//ScoreboardAnswers.parseFile(file.toString());
	    }
	}
	ScoreboardAnswers.determineScoreboardEquation();
	ScoreboardScanner.determineScoreboards(folderName);
	*/
	PlayParser.parsePlays(folderName);
    }

    private static void determineFlags(String inputLine) {
	if (inputLine.contains("c")) {
	    colorFlag = true;
	}
	if (inputLine.contains("s")) {
	    scoreboardFlag = true;
	}
    }

    private static int indexOfFile(String[] args) {
	for (int inx = 0; inx < args.length; inx++) {
	    if (!args[inx].startsWith("-")) {
		return inx;
	    }
	}
	return -1;
    }

}
