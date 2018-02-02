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
	File folder = new File(folderName);	
	File[] listOfFiles = folder.listFiles();
	for (File file : listOfFiles) {
	    if (file.toString().endsWith(".png")) {
		//		Image image = new Image(file.toString());
		//System.out.println(file);
	    }
	    if (file.isDirectory()) {
		listFilesInFolder(file);
	    }
	}
    }

    private static void listFilesInFolder(File folder) throws Exception {
	File[] files = folder.listFiles();
	for (File file : files) {
	    System.out.println(file);
	}
    }
}
