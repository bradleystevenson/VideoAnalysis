package videoanalysis;

import java.io.File;
import java.util.ArrayList;
/**
 * Hello world!
 *
 */
public class App 
{

    private static boolean colorFlag = false;
    private static boolean scoreboardFlag = false;
    private static boolean angleFlag = false;
    private static boolean imageFlag = false;
    
    public static void main( String[] args ) throws Exception
    {
	Tools.connect();
	for (int inx = 0; inx < args.length; inx++) {
	    if (args[inx].startsWith("-")) {
		determineFlags(args[inx]);
	    }
	}
	String folderName = args[indexOfFile(args)];
	if (colorFlag) {
	    CompareImages.compareImages(folderName);
	}
	ColorRanges.determineColorRanges();
	if (scoreboardFlag) {
	    ScoreboardAnswers.parseFile(folderName + "/ScoreboardAnswers.txt");
	}
	if (angleFlag) {
	    CameraAngleAnswers.parseFile(folderName + "/CameraAngleAnswers.txt");
	}
	if (imageFlag) {
	    Answers.calculateImageValues(folderName);
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
	*/
	ScoreboardAnswers.determineScoreboardEquation();
	Equation angle = new Equation("cameraangleresults");
	if (angle.foundEquation()) {
	    System.out.println("FOUND EQUATION");
	} else {
	    System.out.println("ERROR NO EQUATION");
	}
	PlayParser.parsePlays(folderName);
	ArrayList<Play> plays = PlayParser.getPlays(folderName);
	for (Play play : plays) {
	    CameraAngleScanner.scanImages(play);
	}
    }

    private static void determineFlags(String inputLine) {
	if (inputLine.contains("c")) {
	    colorFlag = true;
	}
	if (inputLine.contains("s")) {
	    scoreboardFlag = true;
	}
	if (inputLine.contains("a")) {
	    angleFlag = true;
	}
	if (inputLine.contains("i")) {
	    imageFlag = true;
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
