package videoanalysis;

import java.sql.*;

public class Color {

    private String color;
    private String databaseString = "dbc:sqlite:/Users/Bradley/Programs/VideoAnalysis/videoanalysis/database.db";
    private  Connection connection;
    private int blueColor;
    private int redColor;
    private int greenColor;

    
    
    private void connect() throws Exception {
	connection = DriverManager.getConnection(databaseString);
    }

    
    public Color(String colorInput) throws Exception {
	color = colorInput;
	connect();
	blueColor = calculateColor("blue");
	redColor = calculateColor("red");
	greenColor = calculateColor("green");
    }

    private int calculateColor(String colorName) throws Exception {
	PreparedStatement statement = connection.prepareStatement("select avg(" + colorName + ") from " + color + "pixels");
	ResultSet set = statement.executeQuery();
	set.next();
	int returnInt = set.getInt(1);
	set.close();
	statement.close();
	return returnInt;
    }


    public boolean pixelIsColor(Pixel pixel) {
	int red = pixel.getRed();
	int green = pixel.getGreen();
	int blue = pixel.getBlue();
	return (Math.abs(red - redColor) <= 30 && Math.abs(green - greenColor) <= 30 && Math.abs(blue - blueColor) <= 30);
    }
	
    
}
