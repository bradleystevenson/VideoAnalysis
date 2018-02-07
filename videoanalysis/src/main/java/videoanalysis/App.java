package videoanalysis;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
    
    public static void main( String[] args ) throws Exception
    {
	String folderName = args[0];
	/* CompareImages.compareImages(folderName);
	 */
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
	Image image = new Image("/Users/Bradley/Documents/BucsAtFalcons/BucsAtFalcons 0002.png");
	System.out.println(ScoreboardAnswers.scoreboardScanner(image));
	image = new Image("/Users/Bradley/Documents/BucsAtFalcons/BucsAtFalcons 3026.png");
	System.out.println(ScoreboardAnswers.scoreboardScanner(image));
    }

}
