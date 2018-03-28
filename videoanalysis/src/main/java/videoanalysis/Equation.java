package videoanalysis;

import java.util.ArrayList;

public class Equation {

    private String tableName;
    private ArrayList<String> columnNames;
    private ArrayList<Boolean> bools;
    private ArrayList<String> results;
    
    public Equation(String tableNameInput) {
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
	    int firstMax = Tools.getValuesOfImageType("max", column, results.get(0));
	    int firstMin = Tools.getValuesOfImageType("min", column, results.get(0));
	    int secondMax = Tools.getValuesOfImageType("max", column, results.get(1));
	    int secondMin = Tools.getValuesOfImageType("min", column, results.get(1));
	    if (firstMax < secondMin) {
		return true;
	    }
	    if (secondMax < firstMin) {
		return true;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(1);
	}
	return false;
    }
    

}
