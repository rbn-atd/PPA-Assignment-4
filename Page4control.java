import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.net.URL;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.*;
import javafx.scene.image.Image;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.scene.image.ImageView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.StackPane;
import javafx.scene.control.CheckBox;
import java.util.HashSet;
import java.awt.Desktop;
import java.net.URISyntaxException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.File;

/**
 * Class to load the fxml file for the fourth page in the application.
 *
 * @author Galin Mihaylov, Ricky Brown, Reuben Atendido, Oliver Macpherson
 */

public class Page4control

{
    // We keep track of the count, and label displaying the count:
    private PlaceOfInterestLoader loader;
    private ArrayList<PlaceOfInterest> places;
    private ArrayList<PlaceOfInterest> data;
    private int index;
    private boolean dropDownSet = false;
    private boolean enabledButtons = false;

    @FXML
    private Button continueButton, leftButton, rightButton, mapButton;
    @FXML
    private ImageView arrowLabel;
    @FXML
    private BorderPane welcomeBP, totalBorder;
    @FXML
    private Label welcomeTitle, imageLabel, titleLabel, descLabel, priceLabel, kidLabel,
    foodLabel, giftLabel, famousLabel;
    @FXML
    private ToolBar toolBar;
    @FXML
    private StackPane arrowPane, webStack;
    @FXML
    private ComboBox maxPrice;
    @FXML
    private CheckBox musCheck, ridCheck, royCheck, expCheck, zooCheck, chuCheck, sigCheck,
    parCheck, spoCheck, themCheck, shoCheck, theaCheck, kidCheckBox,
    foodCheckBox, giftCheckBox, famousCheckBox;

    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    public void start(Stage stage)
    {
    }

    /**
     * Constructor which loads all the places of interest data.
     */
    public Page4control()
    {
        loader = new PlaceOfInterestLoader();
        places = loader.load();
        data = new ArrayList<>();
    }

    /**
     * Loads the fxml file.
     * @return the fxml file as a Pane
     */
    public Pane createPage() throws java.io.IOException {
        URL url = getClass().getResource("/fxml files/page4.fxml");
        Pane page4 = FXMLLoader.load(url);
        return page4;
    }

    /**
     * Loads new pane when continue button is pressed.
     */
    @FXML
    public void switchPane() throws java.io.IOException
    {
        URL url = getClass().getResource("/fxml files/page4inner.fxml");
        welcomeBP.setCenter(FXMLLoader.load(url));
        welcomeBP.setTop(null);
    }

    /**
     * Hides the tool bar at the top of the pane when
     * the mouse is no longer near the top of the pane.
     */
    @FXML
    private void hideToolBar()
    {
        TranslateTransition hideBar = new TranslateTransition(Duration.millis(200), toolBar);
        hideBar.setToY(-(toolBar.getHeight()));
        TranslateTransition hideArrow = new TranslateTransition(Duration.millis(200), arrowPane);
        hideArrow.setToY(-(toolBar.getHeight()));
        hideBar.play();
        hideArrow.play();
        toolBar.setVisible(false);
    }

    /**
     * Shows the tool bar at the top of the pane when
     * the mouse is near the top of the pane.
     */
    @FXML
    private void showToolBar()
    {
        TranslateTransition showBar = new TranslateTransition(Duration.millis(200), toolBar);
        showBar.setToY(0);
        TranslateTransition showArrow = new TranslateTransition(Duration.millis(200), arrowPane);
        showArrow.setToY(0);
        showBar.play();
        showArrow.play();
        toolBar.setVisible(true);
        fadeIn(750, toolBar);
    }

    /**
     * Fades nodes into scene.
     * @param fadeSpeed How long the fade animation takes.
     * @param node The ToolBar to fade.
     */
    private void fadeIn(int fadeSpeed, ToolBar node) {
        FadeTransition ft = new FadeTransition(Duration.millis(fadeSpeed), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    /**
     * Updates display depending on whether there is data
     * within the selected criteria.
     */
    @FXML
    private void updateDisplay()
    {
        updateDisplayData();
        if(!data.isEmpty()) {
            index = 0;
            setLabels();
            if(!enabledButtons) {
                enableButtons();
                enabledButtons = true;
            }
        }
        else {
            setupNoFit();

            String concatenated ="file:///" + System.getProperty("user.dir")+ "\\Images\\airbnb-logo-2.png";
            Alert alert = new Alert(AlertType.ERROR);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(concatenated));
            alert.setTitle("Warning!");
            alert.setHeaderText("INVALID!");
            alert.setContentText("There are no landmarks within given criteria!");
            alert.showAndWait();
        }
    }

    /**
     * What to happen when there is no data within
     * the criteria specified.
     */
    private void setupNoFit()
    {
        imageLabel.setGraphic(null);
        titleLabel.setText("No landmarks within given criteria.");
        descLabel.setText("Please widen your search criteria.");
        priceLabel.setText("");
        kidCheckBox.setVisible(false);
        kidLabel.setVisible(false);
        giftCheckBox.setVisible(false);
        giftLabel.setVisible(false);
        foodCheckBox.setVisible(false);
        foodLabel.setVisible(false);
        famousCheckBox.setVisible(false);
        famousLabel.setVisible(false);
    }

    /**
     * Set all the labels for the pane.
     */
    @FXML
    private void setLabels()
    {   
        Image image = new Image(File.separator+"Images"+File.separator + data.get(index).getName() + ".jpg");
        ImageView view = new ImageView(image);
        view.setFitWidth(500);
        view.setFitHeight(240);
        view.setPreserveRatio(true);
        view.setSmooth(true);
        view.setCache(true);
        imageLabel.setGraphic(view);
        imageLabel.setText("");
        titleLabel.setText(data.get(index).getName());
        descLabel.setText(data.get(index).getDesc());
        setPrice();
        setCheckBoxes();
    }

    /**
     * Set price label according to the data that is within the criteria.
     */
    private void setPrice()
    {
        String appendString;
        if(data.get(index).getPrice()==-1) {
            appendString = "Varies";
        }
        else if(data.get(index).getPrice()==0) {
            appendString = "Free";
        }
        else {
            appendString = "£" + data.get(index).getPrice();
        }
        priceLabel.setText("Price: " + appendString);
    }

    /**
     * Set check boxes according to the data that is within the criteria.
     */
    private void setCheckBoxes()
    {
        PlaceOfInterest current = data.get(index);
        kidCheckBox.setSelected(current.getForKids());
        kidCheckBox.setVisible(true);
        kidLabel.setVisible(true);

        giftCheckBox.setSelected(current.getGiftShop());
        giftCheckBox.setVisible(true);
        giftLabel.setVisible(true);

        foodCheckBox.setSelected(current.getFoodAndDrink());
        foodCheckBox.setVisible(true);
        foodLabel.setVisible(true);

        famousCheckBox.setSelected(current.getWorldFamous());
        famousCheckBox.setVisible(true);
        famousLabel.setVisible(true);
    }

    /**
     * Enables all buttons in the pane.
     */
    private void enableButtons()
    {
        leftButton.setDisable(false);
        rightButton.setDisable(false);
        musCheck.setDisable(false);
        ridCheck.setDisable(false);
        royCheck.setDisable(false);
        expCheck.setDisable(false);
        zooCheck.setDisable(false);
        chuCheck.setDisable(false);
        sigCheck.setDisable(false);
        parCheck.setDisable(false);
        spoCheck.setDisable(false);
        themCheck.setDisable(false);
        shoCheck.setDisable(false);
        theaCheck.setDisable(false);
        mapButton.setVisible(true);
    }

    /**
     * Opens google map with the latitude and longtitude of current data on screen.
     */
    @FXML
    private void openGoogleMap()
    {
        if(!data.isEmpty()) {
            double latitude = data.get(index).getLatitude();
            double longitude = data.get(index).getLongitude();

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
    }

    /**
     * Updates available data based on the criteria set by the user.
     */
    @FXML
    private void updateDisplayData()
    {
        HashSet<String> selected = generateSelectedStrings();
        ArrayList<PlaceOfInterest> temp = new ArrayList<>();
        if(selected.isEmpty()) {
            for(PlaceOfInterest place : places) {
                if(checkPrice(place.getPrice())) {
                    temp.add(place);
                }
            }
            data = temp;
        }
        else{
            for(PlaceOfInterest place : places) {
                if(selected.contains(place.getType()) && checkPrice(place.getPrice())) {
                    temp.add(place);
                }
            }
            data = temp;
        }
    }

    /**
     * Check the price of a place of interest compared to the price set by the user
     * in the dropdown box.
     * @param toCheck integer to check.
     * @return true If the price of a place of interest is less than the price set
     * by the user in the dropdown box.
     */
    private boolean checkPrice(int toCheck)
    {
        int checkAgainst;
        if(maxPrice.getValue().equals("Max Price") || toCheck==-1) {
            return true;
        }
        else if(maxPrice.getValue().equals("Free")) {
            checkAgainst = 0;
        }
        else if(maxPrice.getValue().equals("No Max")) {
            checkAgainst = Integer.MAX_VALUE;
        }
        else {
            checkAgainst = (int) maxPrice.getValue();
        }
        return toCheck <= checkAgainst;
    }

    /**
     * Change the current place of interest displayed on screen
     * if the left button is pressed.
     */
    @FXML
    private void progressDataDisplayLeft()
    {
        index--;
        if(!(index >= 0)) {
            index = data.size()-1;
        }
        setLabels();
    }

    /**
     * Change the current place of interest displayed on screen
     * if the right button is pressed.
     */
    @FXML
    private void progressDataDisplayRight()
    {
        index++;
        if(!(index < data.size())) {
            index = 0;
        }
        setLabels();
    }

    /**
     * Set the values in the dropdown box.
     */
    @FXML
    private void initialiseComboBox()
    {
        if(!dropDownSet) {
            maxPrice.getItems().removeAll(maxPrice.getItems());
            maxPrice.getItems().addAll("Free", 5, 10, 20, 50, 100, "No Max");
        }
        dropDownSet = true;
    }

    /**
     * Generate a set of strings to check for depending on whether
     * the user has ticked any checkboxes.
     * @return a HashSet containing all String which correspond
     * to the selected checkboxes.
     */
    private HashSet<String> generateSelectedStrings()
    {
        HashSet<String> toReturn = new HashSet<>();
        if(musCheck.isSelected()) {
            toReturn.add("Museum");
        }
        if(ridCheck.isSelected()) {
            toReturn.add("Ride");
        }
        if(royCheck.isSelected()) {
            toReturn.add("Royal");           
        }
        if(expCheck.isSelected()) {
            toReturn.add("Experience");           
        }
        if(zooCheck.isSelected()) {
            toReturn.add("Zoo");          
        }
        if(chuCheck.isSelected()) {
            toReturn.add("Church");          
        }
        if(sigCheck.isSelected()) {
            toReturn.add("Sightseeing");       
        }
        if(parCheck.isSelected()) {
            toReturn.add("Park");        
        }
        if(spoCheck.isSelected()) {
            toReturn.add("Sports");
        }
        if(themCheck.isSelected()) {
            toReturn.add("Theme Park");
        }
        if(shoCheck.isSelected()) {
            toReturn.add("Shopping");
        }
        if(theaCheck.isSelected()) {
            toReturn.add("Theatre");
        }
        return toReturn;
    }
}