/**
 * Basic class to store details of a stat over multiple strings
 * This is in order to make displaying stats more flexible
 *
 * @author Galin Mihaylov, Ricky Brown, Reuben Atendido, Oliver Macpherson
 */
public class PlaceOfInterest extends Location
{
    //the type of attraction it is
    private String type;
    private String imageLink;
    private String desc;
    private boolean forKids;
    private boolean giftShop;
    private boolean foodAndDrink;
    private boolean worldFamous;

    /**
     * Constructor for objects of class PlaceOfInterest
     * @param name
     * @param latitude
     * @param longitude
     * @param type
     * @param imageLink
     * @param price
     * @param desc
     * @param forKids
     * @param giftShop
     * @param foodAndDrink
     * @param worldFamous
     */
    public PlaceOfInterest(String name, double latitude, double longitude, String type, String imageLink, int price, String desc, boolean forKids, boolean giftShop, boolean foodAndDrink, boolean worldFamous)
    {
        // initialise instance variables
        super(name, latitude, longitude, price);
        this.type = type;
        this.imageLink = imageLink;
        this.desc = desc;
        this.forKids = forKids;
        this.giftShop = giftShop;
        this.foodAndDrink = foodAndDrink;
        this.worldFamous = worldFamous;
    }

    public String getName()
    {
        return name;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public String getType()
    {
        return type;
    }

    public String getImageLink()
    {
        return imageLink;
    }

    public int getPrice()
    {
        return price;
    }

    public String getDesc()
    {
        return desc;
    }

    public boolean getForKids()
    {
        return forKids;
    }

    public boolean getGiftShop()
    {
        return giftShop;
    }

    public boolean getFoodAndDrink()
    {
        return foodAndDrink;
    }

    public boolean getWorldFamous()
    {
        return worldFamous;
    }
}
