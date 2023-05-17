import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.lang.Math;
import java.text.NumberFormat;

/**
 * Class to generate the stats used on the third page.
 *
 * @author Galin Mihaylov, Ricky Brown, Reuben Atendido, Oliver Macpherson
 */
public class StatGenerator
{
    // List to return the generated stats
    private ArrayList<Stat> statList;

    // List of all listings and landmarks
    private ArrayList<AirbnbListing> listings;
    private ArrayList<PlaceOfInterest> places;

    // Loaders for data
    private AirbnbDataLoader loader;
    private PlaceOfInterestLoader placeLoader;

    // Constants
    private static final double LONDON_CENTRE_LATITUDE = 51.50899;
    private static final double EARTH_RADIUS = 3958.8;

    //private NumberFormat myFormat;

    /**
     * Class which generates all the stats used on the third page.
     */
    public StatGenerator()
    {
        // Load all the property data.
        loader = new AirbnbDataLoader();
        listings = loader.load();

        // Load all the place of interest data.
        placeLoader = new PlaceOfInterestLoader();
        places = placeLoader.load();

    }

    /**
     * Generate the stats.
     * @return An arraylist containing all the stats.
     */
    public ArrayList<Stat> generateStats()
    {
        statList = new ArrayList<>();
        statList.add(reviewsPerProperty());
        statList.add(totalAvailable());
        statList.add(numNotPrivateRooms());
        statList.add(highestPriceBorough());
        statList.add(cheapestPrice());
        statList.add(mostPropertyBorough());
        statList.add(westLondonProperties());
        statList.add(withinLandmarkDistance());

        return statList;
    }

    //Methods for generating statistics
    /**
     * Calculates the average number of reviews.
     * @return the avergage number of reviews as a Stat.
     */
    public Stat reviewsPerProperty()
    {
        int total = 0;
        for(AirbnbListing listing : listings) {
            total+=listing.getNumberOfReviews();
        }
        Integer val = (total/listings.size());
        Stat toReturn = new Stat("Average number of reviews per property:", val.toString(),null);
        return toReturn;
    }

    /**
     * Calculate the total number of properties available.
     * @return the total number of properties available as a Stat.
     */
    public Stat totalAvailable()
    {
        ArrayList<AirbnbListing> streamList = listings.stream()
            .filter(b -> b.getAvailability365() != 0)
            .collect(Collectors.toCollection(ArrayList::new));

        Stat toReturn = new Stat("Total number of available properties:", statFormatter(streamList.size()), null);
        return toReturn;
    }

    /**
     * Calculate the total number of properties that are not private rooms.
     * @return the total number of properties that are not private rooms as a Stat.
     */
    public Stat numNotPrivateRooms()
    {
        ArrayList<AirbnbListing> streamList = listings.stream()
            .filter(b -> !b.getRoom_type().equals("Private room"))
            .collect(Collectors.toCollection(ArrayList::new));

        Stat toReturn = new Stat("Total number of entire) homes and apartments:", statFormatter(streamList.size()), null);
        return toReturn;
    }

    /**
     * Find the most expensive borough.
     * @return the most expensive borough as a Stat.
     */
    public Stat highestPriceBorough()
    {
        String highestBorough = null;
        int highestPrice = 0;
        HashMap<String, Integer> boroughCount = generateBoroughs();
        for(String borough : boroughCount.keySet()) {
            int total = 0;
            for(AirbnbListing listing : listings) {
                if(listing.getNeighbourhood().equals(borough)) {
                    total+=(listing.getPrice()*listing.getMinimumNights());
                }
            }
            int price = total/(boroughCount.get(borough));
            if(price > highestPrice) {
                highestPrice = price;
                highestBorough = borough;
            }
        }
        Stat toReturn = new Stat("Most expensive borough:", highestBorough, null);
        return toReturn;
    }

    /**
     * Find the cheapest priced property.
     * @return the cheapest prices property as a Stat.
     */
    public Stat cheapestPrice()
    {
        Integer lowestPrice = Integer.MAX_VALUE;
        for(AirbnbListing listing : listings) {
            if(listing.getPrice() < lowestPrice) {
                lowestPrice = listing.getPrice();
            }
        }
        Stat toReturn = new Stat("Prices start at:", "£" +lowestPrice.toString(), "per night.");
        return toReturn;
    }

    /**
     * Find the borough with the most properties.
     * @return the borough with the most properties as a Stat.
     */
    public Stat mostPropertyBorough()
    {
        HashMap<String, Integer> boroughCount = generateBoroughs();
        String highestBorough = listings.get(0).getNeighbourhood();
        for(String borough : boroughCount.keySet()) {
            if(boroughCount.get(borough) > boroughCount.get(highestBorough)) {
                highestBorough = borough;
            }
        }
        Stat toReturn = new Stat("The borough with the most properties is:", highestBorough, null);
        return toReturn;
    }

    /**
     * Calculate the number of properties that are in west London.
     * @return the number of properties that are in west London as a Stat.
     */
    public Stat westLondonProperties()
    {
        Integer count = 0;
        for(AirbnbListing listing : listings) {
            if(listing.getLatitude() < LONDON_CENTRE_LATITUDE) {
                count++;
            }
        }

        Stat toReturn = new Stat("Our site has over:", statFormatter(count), "west london properties.");
        return toReturn;
    }

    /**
     * Calculate how many properties are within a mile of a place of interest.
     * @return the number of properties within a mile of a place of interest, as a Stat.
     */
    public Stat withinLandmarkDistance()
    {
        Integer count = 0;
        ArrayList<PlaceOfInterest> places = placeLoader.load();
        for(AirbnbListing listing : listings) {
            for(PlaceOfInterest place : places) {
                double startLat = listing.getLatitude();
                double startLong = listing.getLongitude();
                double endLat = place.getLatitude();
                double endLong = place.getLongitude();
                double distance = calcDistance(startLat, startLong, endLat, endLong);

                if(distance < 0.25) {
                    count++;
                    break;
                }
            }
        }

        Stat toReturn = new Stat("Over", statFormatter(count), "properties are within a quarter of a mile of a place of interest.");
        return toReturn;
    }

    //Helper methods for generating statistics
    /**
     * Generate a list of all the different boroughs
     * @return a hashmap containing the borough name and the number of properties in that borough.
     */
    public HashMap<String, Integer> generateBoroughs()
    {
        HashMap<String, Integer> boroughCount = new HashMap<>();
        for(AirbnbListing listing : listings) {
            String borough = listing.getNeighbourhood();
            if(!boroughCount.keySet().contains(borough)){
                boroughCount.put(borough, 1);
            }
            boroughCount.put(borough, boroughCount.get(borough)+1);
        }
        return boroughCount;
    }

    /**
     * Calculate the distance between two points given longitude and latitude.
     * 
     * @param startLat The starting latitiude.
     * @param startLong The starting longitude.
     * @param endLat The ending latitude.
     * @param endLong The ending longitude.
     * 
     * @return The distance between two points given as a double.
     */
    public double calcDistance(double startLat, double startLong, double endLat, double endLong)
    {
        double startDToEquator = EARTH_RADIUS * Math.toRadians(startLat);
        double startDToMeridian = EARTH_RADIUS * Math.toRadians(startLong);
        double endDToEquator = EARTH_RADIUS * Math.toRadians(endLat);
        double endDToMeridian = EARTH_RADIUS * Math.toRadians(endLong);

        double vertDistance = Math.abs(startDToEquator-endDToEquator);
        double horizDistance = Math.abs(startDToMeridian-endDToMeridian);

        double finalDistance = Math.sqrt(Math.pow(vertDistance,2)*Math.pow(horizDistance,2));
        return finalDistance;
    } 

    /**
     * Method to format numerical statistics to include comma's every three digits
     * and cast the numbers to string for use within the Stat object.
     * @param stat integer to be formatted.
     * @return a String of the formatted integer.
     */
    public String statFormatter(int stat)
    {
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);

        String formattedString = myFormat.format(stat);

        return formattedString;
    }
}

