package videoanalysis;

import java.io.File;
import java.util.ArrayList;

public class CompareImages {


    public static void compareImages(String folderName) throws Exception {
	File folder = new File(folderName);
	File[] files = folder.listFiles();	
	ArrayList<String> originalImages = new ArrayList<String>();
	ArrayList<File> folders = new ArrayList<File>();
	for (File file : files) {
	    if (file.toString().endsWith(".png")) {
		originalImages.add(file.toString());
	    }
	    if (file.isDirectory()) {
		folders.add(file);
	    }
	}
	ArrayList<ArrayList<String>> alteredImages = new ArrayList<ArrayList<String>>();
	ArrayList<String> folderNames = new ArrayList<String>();
	for (File alteredFolder : folders) {
	    folderNames.add(alteredFolder.toString());
	    alteredImages.add(getImagesFromFolder(alteredFolder));
	}
	for (int inx = 0; inx < alteredImages.size(); inx++) {
	    compareImages(alteredImages.get(inx), originalImages);
	}
    }

    private static void compareImages(ArrayList<String> alteredImages, ArrayList<String> originalImages) {
	int matchCount = 0;
	for (int inx = 0; inx < alteredImages.size(); inx++) {
	    for (int iny = 0; iny < originalImages.size(); iny++) {
		if (getFileName(alteredImages.get(inx)).equals(getFileName(originalImages.get(iny)))) {
		    matchCount++;
		}
	    }
	}
	System.out.println(matchCount);
    }

    private static String getFileName(String input) {
	String returnLine = "";
	int inx = input.length() - 1;
	char charx = input.charAt(inx);
	while (charx != '/') {
	    returnLine = charx + returnLine;
	    inx--;
	    charx = input.charAt(inx);	    
	}
	return returnLine;
    }
    

    private static ArrayList<String> getImagesFromFolder(File folder) {
	ArrayList<String> returnStrings = new ArrayList<String>();
	File[] files = folder.listFiles();
	for (File file : files) {
	    if (file.toString().endsWith(".png")) {
		returnStrings.add(file.toString());
	    }
	}
	return returnStrings;
    }
}
