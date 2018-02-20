package videoanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Tools {


    public static ArrayList<String> readFile(String filePath) {
	ArrayList<String> returnStrings = new ArrayList<String>();
	try {
	    BufferedReader in = new BufferedReader(new FileReader(filePath));
	    String readLine;
	    while ((readLine = in.readLine()) != null) {
		returnStrings.add(readLine);
	    }
	    in.close();
	} catch (Exception e) {
	    System.out.println("File it requested to read does not exist");
	    System.exit(1);
	}
	return returnStrings;
    }

}
