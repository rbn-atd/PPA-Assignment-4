import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.*;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;

/**
 * Class to load the fxml file for the first page in the application.
 *
 * @author Galin Mihaylov, Ricky Brown, Reuben Atendido, Oliver Macpherson
 */
public class Page1control

{
    private static int minPrice;
    private static int maxPrice;

    @FXML
    private Label priceRange;

    /**
     * Loads the fxml file.
     * @return the fxml file as a Pane
     */
    public Pane createPage() throws java.io.IOException {
        URL url = getClass().getResource("/fxml files/page1.fxml");
        Pane page1 = FXMLLoader.load(url);
        return page1;
    }

    /**
     * Gets the current min and max prices in the comboboxes and
     * concatenates them to the label on the first page.
     */
    @FXML
    public void setPrices() {
        if(minPrice >= maxPrice){
            priceRange.setText("Current Price Range: ");
        }
        else{
            priceRange.setText("Current Price Range (£): "+ minPrice +" - "+ maxPrice);
        }
    }

    /**
     * Set the minimum price.
     * @param min The price which minPrice is to be set to.
     */
    public void setMinPrice(int min){
        minPrice = min;
    }

    /**
     * Set the maximum price.
     * @param max The price which maxPrice is to be set to.
     */
    public void setMaxPrice(int max){
        maxPrice = max;
    }
}
