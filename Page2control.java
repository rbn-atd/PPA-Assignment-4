import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.*;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Class to load the fxml file for the second page in the application.
 *
 * @author Galin Mihaylov, Ricky Brown, Reuben Atendido, Oliver Macpherson
 */
public class Page2control
{
    // All the ToggleButtons that make up the map
    @FXML 
    private ToggleButton ENFI, BARN, HRGY, WALT, HRRW, BREN, CAMD, ISLI, HACK, REDB, HAVE, 
    HILL, EALI, KENS, WSTM, TOWH, NEWH, BARK, HOUN, HAMM, WAND, CITY, 
    GWCH, BEXL, RICH, MERT, LAMB, STHW, LEWS, KING, SUTT, CROY, BROM;

    @FXML
    private ToggleGroup buttons;

    @FXML
    private Label numProperties;

    @FXML
    private Label filteredNumProperties;

    @FXML
    private Label boroughName;

    @FXML
    private Button viewPropertiesButton;

    private HashSet<ToggleButton> tgButtons = new HashSet<>();
    private HashMap<ToggleButton, String> map = new HashMap<>();

    private AirbnbDataLoader dataLoader = new AirbnbDataLoader();

    // ArraysLists to hold the data and data based on fiters.
    private ArrayList<AirbnbListing> data;
    private ArrayList<AirbnbListing> filteredData = new ArrayList<>();

    private static int minPrice;
    private static int maxPrice;

    /**
     * Adds all toggle buttons to the hashset and to the hashmap.
     * Loads all the property data from the csv file.
     * Sets the initial colour of each toggle button.
     */
    public void initialize() {
        addButtonsToHashSet();
        addToHashMap();
        data = dataLoader.load();

        for(ToggleButton tgb: tgButtons) {
            buttonColour(tgb);
        }
    }

    /**
     * Loads the fxml file.
     * @return the fxml file as a Pane
     */
    public Pane createPage() throws java.io.IOException {      
        URL url = getClass().getResource("/fxml files/page2.fxml");
        Pane page2 = FXMLLoader.load(url);
        return page2;
    }

    /**
     * Gets the number of properties in a borough.
     * @param borough A toggle button for which the number of properties is wanted.
     * @return The number of properties in the borough which the toggle button corresponds to.
     */
    private int getNumberOfProperties(ToggleButton borough) {
        int count = 0;
        for(AirbnbListing listing: data) {
            if(listing.getNeighbourhood().equals(map.get(borough))) {
                count++;
            }
        }
        return count;
    }

    /**
     * Gets the number of properties in a borough based on the filters set by the user.
     * @param borough A toggle button for which the number of properties is wanted.
     * @return The number of properties in the borough which the toggle button corresponds to.
     */
    private int getNumberOfFilteredProperties(ToggleButton borough) {
        int count = 0;
        for(AirbnbListing listing: data) {
            if(listing.getNeighbourhood().equals(map.get(borough))) {
                if(listing.getPrice() >= minPrice && listing.getPrice() <= maxPrice) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Adds the properties which fall within the filters set by the user, to an ArrayList.
     * @param borough A toggle button of which the properties are wanted.
     */
    private void locationFilter(ToggleButton borough) {
        for(AirbnbListing listing: data) {
            if(listing.getNeighbourhood().equals(map.get(borough))) {
                if(listing.getPrice() >= minPrice && listing.getPrice() <= maxPrice) {
                    filteredData.add(listing);
                }
            }
        }
    }

    /**
     * Get the name of the borough which corresponds to the toggle button passed in.
     * @param borough The toggle button of which the corresponding borough name is wanted.
     * @return The borough name as a String.
     */
    private String getBoroughName(ToggleButton borough) {
        String boroughName = "";
        for(AirbnbListing listing: data) {
            if(listing.getNeighbourhood().equals(map.get(borough))) {
                boroughName = listing.getNeighbourhood();
            }
        }
        return boroughName;
    }

    /**
     * Change the colour of the toggle button depending on the number of total properties available in that borough.
     * @param borough The toggle button of which the colour is wanted to be changed.
     */
    private void buttonColour(ToggleButton borough) {        
        int number = getNumberOfProperties(borough);

        if(number<100) {
            borough.setStyle("-fx-background-color: #ccffcd;");
        }
        else if (number < 250) {
            borough.setStyle("-fx-background-color: #a3ffa5;");
        }
        else if (number < 500) {
            borough.setStyle("-fx-background-color: #9aff75;");
        }
        else if(number<750) {
            borough.setStyle("-fx-background-color: #afff91;");
        }
        else if(number<1000) {
            borough.setStyle("-fx-background-color: #6eff70;");
        }
        else if(number<2000) {
            borough.setStyle("-fx-background-color: #4dff4f;");
        }
        else if(number<4000) {
            borough.setStyle("-fx-background-color: #26ed29;");
        }
        else{
            borough.setStyle("-fx-background-color: #1dc420;");
        } 
    }

    /**
     * When a toggle button is pressed, if the filters are not valid an alert will show.
     * If the filters are valid, the selected toggle button is check for and the gui is updated
     * with the corresponding information to that toggle button.
     */
    @FXML
    private void toggleButton(ActionEvent event) {       
        if(minPrice >= maxPrice) {            
            String concatenated ="file:///" + System.getProperty("user.dir")+ "\\Images\\airbnb-logo-2.png";
            Alert alert = new Alert(AlertType.ERROR);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(concatenated));           
            alert.setTitle("Invalid Price Range");
            alert.setHeaderText("MINIMUM PRICE IS BIGGER THAN MAXIMUM PRICE");
            alert.showAndWait();
        }
        else {
            filteredData.clear();
            for(ToggleButton tgb: tgButtons) {
                if(tgb.isSelected()) {
                    tgb.setStyle("-fx-background-color: #FF5A60;");
                    numProperties.setText("" + getNumberOfProperties(tgb));
                    filteredNumProperties.setText("" + getNumberOfFilteredProperties(tgb));
                    boroughName.setText(""+ getBoroughName(tgb));
                    locationFilter(tgb);
                    viewPropertiesButton.setDisable(false);
                }
                else {                
                    buttonColour(tgb);
                    if(getSelected() == null) {
                        numProperties.setText("0");
                        filteredNumProperties.setText("0");
                        boroughName.setText("<-- Select Borough on Map");

                        viewPropertiesButton.setDisable(true);
                    }
                }
            } 
        }
    }

    /**
     * Get the selected toggle button.
     * @return The selected toggle button or null if there is no selected toggle button.
     */
    private ToggleButton getSelected() {
        for(ToggleButton tgb: tgButtons) {
            if(tgb.isSelected()) {
                return tgb;
            }
        }
        return null;
    }

    /**
     * Load up a new window containing the property information for the toggle button selected.
     * If there are zero properties available based on the filter and selected toggle button
     * then an alert shows.
     * If a property in the list is selected, a new window displays the description of that property.
     */
    @FXML
    private void viewPropertiesButton(ActionEvent event) throws java.io.IOException {        
        if(getNumberOfFilteredProperties(getSelected()) == 0) {
            String concatenated ="file:///" + System.getProperty("user.dir")+ "\\Images\\airbnb-logo-2.png";

            Alert alert = new Alert(AlertType.ERROR);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(concatenated));
            alert.setTitle("Warning!");
            alert.setHeaderText("INVALID!");
            alert.setContentText("There are 0 properties within your price range!");
            alert.showAndWait();
        }
        else {
            ObservableList<AirbnbListing> observableList = FXCollections.observableArrayList(filteredData);
            TableView<AirbnbListing> table = new TableView();
            Stage stage = new Stage();
            TableColumn<AirbnbListing, Integer> minStay = new TableColumn("Minimum Stay");;
            TableColumn<AirbnbListing, String> nameHost = new TableColumn("Host name");;
            TableColumn<AirbnbListing, Integer> numberReviews = new TableColumn("Number Of Reviews");
            TableColumn<AirbnbListing, Integer> price = new TableColumn("Price");

            Scene scene = new Scene(table, 600, 500);
            stage.setTitle(map.get(getSelected()));
            stage.setScene(scene);

            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            nameHost.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("host_name"));
            price.setCellValueFactory(new PropertyValueFactory<AirbnbListing, Integer>("price"));
            numberReviews.setCellValueFactory(new PropertyValueFactory<AirbnbListing, Integer>("numberOfReviews"));
            minStay.setCellValueFactory(new PropertyValueFactory<AirbnbListing, Integer>("minimumNights"));

            table.getColumns().addAll(nameHost, price, numberReviews, minStay);
            table.setItems(observableList);
            table.getStylesheets().add("tableViewStyles.css");    

            String concatenated ="file:///" + System.getProperty("user.dir")+ "\\Images\\airbnb-logo-2.png";
            stage.getIcons().add(new Image(concatenated));

            table.setOnMouseClicked(e -> {
                    table.setOnSort(f -> table.getSelectionModel().clearSelection());
                    AirbnbListing listing = table.getSelectionModel().getSelectedItem();

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.initOwner(stage.getScene().getWindow());
                    alert.getDialogPane().setGraphic(viewOnMapButton(listing));
                    alert.setTitle(map.get(getSelected()));
                    alert.setHeaderText("PROPERTY DESCRIPTION");
                    alert.setContentText(listing.getName());
                    alert.showAndWait();

                });

            stage.show(); 
        }
    }

    /**
     * Creates a button which upon being pressed, shows the location of the property on google maps.
     * @param listing The property to be shown the location of.
     * @return The created button.
     */
    private Button viewOnMapButton(AirbnbListing listing)
    {
        Button button = new Button("View On Map");

        button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    double latitude = listing.getLatitude();
                    double longitude = listing.getLongitude();

                    try
                    {
                        Desktop.getDesktop().browse(new URL("https://www.google.com/maps/place/" + latitude + "," + longitude).toURI());
                    }
                    catch (java.io.IOException ioe)
                    {
                        ioe.printStackTrace();
                    }
                    catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            });

        return button;
    }

    /**
     * Adds all the toggle buttons to a hashmap with their corresponding borough names
     * as their values.
     */
    private void addToHashMap() {
        map.put(ENFI, "Enfield");
        map.put(BARN, "Barnet");
        map.put(HRGY, "Haringey");
        map.put(WALT, "Waltham Forest");
        map.put(HRRW, "Harrow");
        map.put(BREN, "Brent");
        map.put(CAMD, "Camden");
        map.put(ISLI, "Islington");
        map.put(HACK, "Hackney");
        map.put(REDB, "Redbridge");
        map.put(HAVE, "Havering");
        map.put(HILL, "Hillingdon");
        map.put(EALI, "Ealing");
        map.put(KENS, "Kensington and Chelsea");
        map.put(WSTM, "Westminster");
        map.put(TOWH, "Tower Hamlets");
        map.put(NEWH, "Newham");
        map.put(BARK, "Barking and Dagenham");
        map.put(HOUN, "Hounslow");
        map.put(HAMM, "Hammersmith and Fulham");
        map.put(WAND, "Wandsworth");
        map.put(CITY, "City of London");
        map.put(GWCH, "Greenwich");
        map.put(BEXL, "Bexley");
        map.put(RICH, "Richmond upon Thames");
        map.put(MERT, "Merton");
        map.put(LAMB, "Lambeth");
        map.put(STHW, "Southwark");
        map.put(LEWS, "Lewisham");
        map.put(KING, "Kingston upon Thames");
        map.put(SUTT, "Sutton");
        map.put(CROY, "Croydon");
        map.put(BROM, "Bromley");
    }

    /**
     * Adds all the toggle buttons to a hashset.
     */
    private void addButtonsToHashSet() {
        tgButtons.add(ENFI); 
        tgButtons.add(BARN);
        tgButtons.add(HRGY);
        tgButtons.add(WALT);
        tgButtons.add(HRRW);
        tgButtons.add(BREN);
        tgButtons.add(CAMD);
        tgButtons.add(ISLI);
        tgButtons.add(HACK);
        tgButtons.add(REDB);
        tgButtons.add(HAVE);
        tgButtons.add(HILL);
        tgButtons.add(EALI);
        tgButtons.add(KENS);
        tgButtons.add(WSTM);
        tgButtons.add(TOWH);
        tgButtons.add(NEWH);
        tgButtons.add(BARK);
        tgButtons.add(HOUN);
        tgButtons.add(HAMM);
        tgButtons.add(WAND);
        tgButtons.add(CITY);
        tgButtons.add(GWCH);
        tgButtons.add(BEXL);
        tgButtons.add(RICH);
        tgButtons.add(MERT);
        tgButtons.add(LAMB);
        tgButtons.add(STHW);
        tgButtons.add(LEWS);
        tgButtons.add(KING);
        tgButtons.add(SUTT);
        tgButtons.add(CROY);
        tgButtons.add(BROM);
    }

    /**
     * Set the minimum price.
     * @param min The price which minPrice is to be set to.
     */
    public void setMinPrice(int min) {
        minPrice = min;
    }

    /**
     * Set the maximum price.
     * @param max The price which maxPrice is to be set to.
     */
    public void setMaxPrice(int max) {
        maxPrice = max;
    }
}