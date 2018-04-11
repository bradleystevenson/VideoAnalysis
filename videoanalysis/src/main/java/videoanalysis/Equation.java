package videoanalysis;

import java.util.ArrayList;

public class Equation {

    private String tableName;
    private ArrayList<String> columnNames;
    private ArrayList<Boolean> bools;
    private ArrayList<String> results;
    private ArrayList<Parse> parsers;
    
    public Equation(String tableNameInput) {
	parsers = new ArrayList<Parse>();
	tableName = tableNameInput;
	ArrayList<String> fields = Tools.getImageTableFields();
	columnNames = new ArrayList<String>();
	bools = new ArrayList<Boolean>();
	results = Tools.getResultTypes(tableName);
	for (String string : fields) {
	    if (!string.contains("imageName")) {
		columnNames.add(string);
		bools.add(determineColumn(string));
	    }
	}
	if (!foundEquation()) {
	    determineEquation();
	}
    }

    private void determineEquation() {
	ArrayList<String> imageNames = Tools.getImageNames(tableName);
	while (imageNames.size() != 0) {
	    System.out.println(imageNames.size());
	    ArrayList<String> columns = Tools.getImageTableFields();
	    ArrayList<Integer> ints = new ArrayList<Integer>();
	    int max = 0;
	    String maxColumn = "";
	    for (String column : columns) {
		//Get the most in the current pool
		int current = getMostRemoved(column, imageNames);
		if (current > max) {
		    max = current;
		    maxColumn = column;
		}
	    }
	    ArrayList<String> matches = getNewestMatchStrings(maxColumn, imageNames);
	    imageNames = getUnmatchedStrings(imageNames, matches);
	}		
    }

    private ArrayList<String> getNewestMatchStrings(String columnName, ArrayList<String> imageNames) {
	ArrayList<String> returnStrings = new ArrayList<String>();
	int min = Tools.getExtremeOfValue("min", columnName);
	int max = Tools.getExtremeOfValue("max", columnName);
	int currentMin = min;
	int currentMax = min;
	while (currentMax != max) {
	    if (Tools.allOneType(currentMin, currentMax, columnName, imageNames, tableName)) {
		ArrayList<String> current = Tools.getStringsInRange(currentMin, currentMax, columnName, imageNames, tableName);
		if (current.size() > returnStrings.size()) {
		    returnStrings = current;
		}
		currentMax++;
	    } else {
		currentMin++;
		currentMax = currentMin;
	    }
	}

	return returnStrings;
    }

    private int getMostRemoved(String columnName, ArrayList<String> imageNames) {
	int returnInt = 0;
	int min = Tools.getExtremeOfValue("min", columnName);
	int max = Tools.getExtremeOfValue("max", columnName);
	int currentMin = min;
	int currentMax = min;
	int returnMax = 0;
	while (currentMax != max) {
	    if (Tools.allOneType(currentMin, currentMax, columnName, imageNames, tableName)) {
		int current = Tools.mostInRange(currentMin, currentMax, columnName, imageNames, tableName);
		if (current > returnMax){
		    returnMax = current;
		}
		currentMax++;
		//GET COUNT OF ALL THE MATCHES
	    } else {
		currentMin++;
		currentMax = currentMin;
	    }
	}
	//HERE
	return returnMax;
    }

    private ArrayList<String> getUnmatchedStrings(ArrayList<String> strings, ArrayList<String> matches) {
	ArrayList<String> returnStrings = new ArrayList<String>();
	for (String string : strings) {
	    if (!isMatchString(string, matches)) {
		returnStrings.add(string);
	    }
	}
       	return returnStrings;
    }

    private boolean isMatchString(String string, ArrayList<String> matches) {
	for (String match : matches) {
	    if (string.equals(match)) {
		return true;
	    }
	}
	return false;
    }
    
    public boolean foundEquation() {
	for (boolean bool : bools) {
	    if (bool) {
		return true;
	    }
	}
	return false;
    }

    private boolean determineColumn(String column) {
	try {
	    for (int inx =0 ; inx < results.size(); inx++) {
		System.out.println(results.get(inx));
	    }
	    int firstMax = Tools.getValuesOfImageType("max", column, results.get(0), tableName);
	    int firstMin = Tools.getValuesOfImageType("min", column, results.get(0), tableName);
	    int secondMax = Tools.getValuesOfImageType("max", column, results.get(1), tableName);
	    int secondMin = Tools.getValuesOfImageType("min", column, results.get(1), tableName);
	    System.out.println(firstMax);
	    System.out.println(firstMin);
	    System.out.println(secondMax);
	    System.out.println(secondMin);
	    System.out.println("HERE");
	    if (firstMax < secondMin) {
		Parse parse = new Parse(secondMin, results.get(0), results.get(1), column);
		parsers.add(parse);
		return true;
	    }
	    if (secondMax < firstMin) {
		Parse parse = new Parse(firstMin, results.get(1), results.get(0), column);
		parsers.add(parse);
		return true;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(1);
	}
	return false;
    }
    

}
