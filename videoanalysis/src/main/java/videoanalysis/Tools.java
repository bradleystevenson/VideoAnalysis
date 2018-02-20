package videoanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;


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

    public static void printFile(String filePath, ArrayList<String> printStrings) {
	try {
	    PrintWriter out = new PrintWriter(new FileWriter(filePath));
	    for (String string : printStrings) {
		out.println(string);
	    }
	    out.close();
	} catch (Exception e) {
	    System.out.println("Error printing to file");
	    System.exit(1);
	}
    }

}
