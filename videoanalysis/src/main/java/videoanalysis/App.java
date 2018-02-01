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
	System.out.println(folderName);
	File folder = new File(folderName);	
	File[] listOfFiles = folder.listFiles();
	for (File file : listOfFiles) {
	    if (file.toString().endsWith(".png")) {
		Image image = new Image(file.toString());
		System.out.println(file);
	    }
	}
    }
}
