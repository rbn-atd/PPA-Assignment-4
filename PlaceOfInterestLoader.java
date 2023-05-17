import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import com.opencsv.CSVReader;
import java.net.URISyntaxException;

/**
 * Class to load the "placesofinterest.csv" csv file.
 * Syntax and layout lifted from AirbnbDataLoader.
 * Displays the data in PlacesOfInterest objects.
 *
 * @author Galin Mihaylov, Ricky Brown, Reuben Atendido, Oliver Macpherson
 */

/**
 * Seperate implementation of CSV loader, syntax taken from airbnbdataloader implementation
 */
public class PlaceOfInterestLoader extends Loader
{
    public ArrayList<PlaceOfInterest> load()
    {
        ArrayList<PlaceOfInterest> places = new ArrayList<PlaceOfInterest>();
        try {
            URL url = getClass().getResource("placesofinterest.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;

            reader.readNext();
            while((line = reader.readNext()) != null) {
                String name = line[0];
                double latitude = convertDouble(line[1]);
                double longitude = convertDouble(line[2]);
                String type = line[3];
                String link = line[4];
                int price = convertInt(line[5]);
                String desc = line[6];
                boolean forKids = convertBool(convertInt(line[7]));
                boolean giftShop = convertBool(convertInt(line[8]));
                boolean foodAndDrink = convertBool(convertInt(line[9]));
                boolean worldFamous = convertBool(convertInt(line[10]));
                PlaceOfInterest place = new PlaceOfInterest(name, latitude, longitude, type, link, price, desc, forKids, giftShop, foodAndDrink, worldFamous);
                places.add(place);
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }

        //System.out.println("Success! Number of loaded records: " + places.size());
        return places;
    }
}