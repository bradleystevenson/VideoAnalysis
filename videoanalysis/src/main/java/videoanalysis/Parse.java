package videoanalysis;

public class Parse {

    private boolean oneDivide = false;
    private int divide;
    private String lowerReturn;
    private String higherReturn;
    private String columnName;
    
    public Parse(int divideInput, String lowerReturnInput, String higherReturnInput, String columnName) {
	oneDivide = true;
	divide = divideInput;
	lowerReturn = lowerReturnInput;
	higherReturn = higherReturnInput;
    }

}
