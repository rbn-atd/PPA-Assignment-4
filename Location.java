
/**
 * Abstract class to provide the common properties of listings and places of interest.
 *
 * @author Galin Mihaylov, Ricky Brown, Reuben Atendido, Oliver Macpherson
 */
public abstract class Location
{
    // the name of the location
    protected String name;

    //longitude and latitude coordinates for location
    protected double latitude;
    protected double longitude;

    protected int price;

    /**
     * @param name
     * @param latitude
     * @param longitude
     * @param price
     */
    public Location(String name, double latitude, double longitude, int price)
    {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
    }

    public abstract String getName();

    public abstract double getLatitude();

    public abstract double getLongitude();

    public abstract int getPrice();
}
