import java.util.ArrayList;

/**
 * Abstract class to simply provide conversion methods to both the airbnb and the
 * place of interest loader.
 * 
 * Conversion methods taken from AirbnbDataLoader class
 *
 * @author Galin Mihaylov, Ricky Brown, Reuben Atendido, Oliver Macpherson
 */
public abstract class Loader
{
    /**
     * Taken from AirbnbDataLoader Class
     * @param doubleString the string to be converted to Double type
     * @return the Double value of the string, or -1.0 if the string is 
     * either empty or just whitespace
     */
    protected Double convertDouble(String doubleString){
        if(doubleString != null && !doubleString.trim().equals("")){
            return Double.parseDouble(doubleString);
        }
        return -1.0;
    }

    /**
     * Taken from AirbnbDataLoader Class
     * @param intString the string to be converted to Integer type
     * @return the Integer value of the string, or -1 if the string is 
     * either empty or just whitespace
     */
    protected Integer convertInt(String intString){
        if(intString != null && !intString.trim().equals("")){
            return Integer.parseInt(intString);
        }
        return -1;
    }

    /**
     * Converts an integer value to a corresponding boolean.
     * @param num The integer to be converted.
     * @return true If the parameter is equal to 1.
     */
    protected boolean convertBool(Integer num)
    {
        if(num==1) {
            return true;
        }
        else {
            return false;
        }
    }
}
