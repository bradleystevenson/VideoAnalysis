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
