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
	/*	CompareImages.compareImages(folderName);
	 */
	File folder = new File(folderName);	
	File[] listOfFiles = folder.listFiles();
	for (File file : listOfFiles) {
	    if (file.toString().endsWith(".png")) {
		//		Image image = new Image(file.toString());
		//System.out.println(file);
	    } else if (file.toString().endsWith("ScoreboardAnswers.txt")) {
		System.out.println("fund the file i was looking for");

	    }
	}
	ColorRanges.determineColorRanges();
    }

}
