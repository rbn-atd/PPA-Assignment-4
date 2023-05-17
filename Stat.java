/**
 * Class which holds all the information about the stats on the third page.
 *
 * @author Galin Mihaylov, Ricky Brown, Reuben Atendido, Oliver Macpherson
 */
public class Stat
{
    // The information for the top, value and bottom of each stat.
    private String statTop;
    private String statValue;
    private String statBottom;

    // The minimum and maximum prices.
    private int minPrice;
    private int maxPrice;

    /**
     * Set the values.
     * @param top Set the top of the stat.
     * @param value Set the value of the stat.
     * @param bottom Set the bottom of the stat.
     */
    public Stat(String top, String value, String bottom)
    {
        statTop = top;
        statValue = value;
        statBottom = bottom;
    }

    /**
     * Set the minimum price.
     * @param min The price minPrice should be set to.
     */
    public void setMinPrice(int min) {
        minPrice = min;
    }

    /**
     * Get the minimum price.
     * @return the minimum price as an int.
     */
    public int getMinPrice() 
    {
        return minPrice;
    }

    /**
     * Set the maximum price.
     * @param max The price maxPrice should be set to.
     */
    public void setMaxPrice(int max) {
        maxPrice = max;
    }

    /**
     * Get the maximum price.
     * @return the maximum price as an int.
     */
    public int getMaxPrice()
    {
        return maxPrice;
    }

    /**
     * Get the top of the stat.
     * @return the top of the stat as a String.
     */
    public String getStatHeader()
    {
        return statTop;
    }

    /**
     * Get the bottom of the stat.
     * @return the bottom of the stat as a String.
     */
    public String getStatBody()
    {
        return statBottom;
    }

    /**
     * Get the value of the stat.
     * @return the value of the stat as a String.
     */
    public String getStatValue()
    {
        return statValue;
    }
}

