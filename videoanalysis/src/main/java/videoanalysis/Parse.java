package videoanalysis;

public class Parse {

    private boolean oneDivide = false;
    private int divide;
    private String lowerReturn;
    private String higherReturn;
    private String columnName;
    private boolean twoDivide = false;
    private String returnString;
    private int lowerDivide;
    private int higherDivide;

    public Parse(int lowerDivideInput, int higherDivideInput, String returnStringInput, String columnNameInput) {
	columnName = columnNameInput;
	twoDivide = true;
	lowerDivide = lowerDivideInput;
	higherDivide = higherDivideInput;
	returnString = returnStringInput;
    }
    
    
    public Parse(int divideInput, String lowerReturnInput, String higherReturnInput, String columnNameInput) {
	columnName = columnNameInput;
	oneDivide = true;
	divide = divideInput;
	lowerReturn = lowerReturnInput;
	higherReturn = higherReturnInput;
    }

}
