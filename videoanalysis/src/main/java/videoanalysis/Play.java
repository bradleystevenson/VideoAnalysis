package videoanalysis;

import java.util.ArrayList;

public class Play {

    private ArrayList<String> scoreboardStrings;
    private ArrayList<String> fieldStrings;

    public ArrayList<String> getFieldStrings() {
	return fieldStrings;
    }
    
    public Play(ArrayList<String> inputStrings) {
	scoreboardStrings = new ArrayList<String>();
	fieldStrings = new ArrayList<String>();
	for (String string : inputStrings) {
	    if (string.endsWith("Scoreboard")) {
		scoreboardStrings.add(string);
	    } else if (string.endsWith("Field")) {
		fieldStrings.add(string);
	    }
	    
	  
	}
    }


}
