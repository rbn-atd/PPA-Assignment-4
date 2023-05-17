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
import java.util.ArrayList;
import java.net.URL;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.*;
import javafx.scene.image.Image;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.layout.VBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * Model and view for the whole application
 *
 * @author Galin Mihaylov, Ricky Brown, Reuben Atendido, Oliver Macpherson
 */
public class Mastercontroller extends Application
{
    // We keep track of the count, and label displaying the count:
    private Label fromLabel = new Label("Price range (£)  From:");
    private Label toLabel = new Label("To:");

    private Stage stage;
    private BorderPane systemRoot;
    private AnchorPane dpane;
    private AnchorPane apane;
    private ArrayList<Pane> sceneList;
    private Duration duration;

    private int currentPane;

    private ComboBox dropDown1;
    private ComboBox dropDown2;
    private Button rightButton;
    private Button leftButton;

    private Page1control page1 = new Page1control();
    private Page2control page2 = new Page2control();
    private Page3control page3 = new Page3control();
    private Page4control page4 = new Page4control();

    /**
     * Adds key listener to the left and right arrow keys.
     */
    private EventHandler<KeyEvent> keyListener = new EventHandler<>() 
        {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()==KeyCode.RIGHT) {
                    replaceSceneRight(event);
                }

                else if(event.getCode()==KeyCode.LEFT) {
                    replaceSceneLeft(event);
                }
                event.consume();
            }
        };

    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override

    public void start(Stage stage) throws Exception
    {   
        sceneList = new ArrayList<>();
        currentPane = 0;

        sceneList.add(page1.createPage());
        sceneList.add(page2.createPage());
        sceneList.add(page3.createPage());
        sceneList.add(page4.createPage());

        VBox root = new VBox();

        systemRoot = new BorderPane();
        root.getChildren().addAll(systemRoot);

        systemRoot.setPrefSize(2000.0, 2000.0);
        systemRoot.setTop(addTopNodes());
        systemRoot.setBottom(addBottomNodes());
        systemRoot.setCenter(sceneList.get(currentPane));
        fadeIn(2000, root);

        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(keyListener);
        stage.setTitle("airbnb Property Viewer");
        stage.setScene(scene);
        stage.setMinHeight(750);
        stage.setMinWidth(1200);
        String concatenated ="file:///" + System.getProperty("user.dir")+ "\\Images\\airbnb-logo-2.png";
        stage.getIcons().add(new Image(concatenated));
        stage.setMaximized(true);

        scene.getStylesheets().add("arrowStyles.css"); 

        // Show the Stage (window)
        stage.show();
    }

    /**
     * Iterates through the arraylist containing pane object types and cycles through them to 
     * simulate circular clicking leftwards.
     */
    private void replaceSceneLeft(Event event) {
        if(minBiggerThanMax((int)dropDown2.getValue(), (int)dropDown1.getValue())) {
            showAlert(); //and do nothing else
        }
        else{
            fadeOut(500,sceneList.get(currentPane));
            if(currentPane==0) {
                currentPane = sceneList.size()-1;
            }
            else {
                currentPane--;
            }
            fadeIn(750,sceneList.get(currentPane));
            systemRoot.setCenter(sceneList.get(currentPane));
        }
    }

    /**
     * Iterates through the arraylist containing pane object types and cycles through them to 
     * simulate circular clicking rightwards.
     */
    private void replaceSceneRight(Event event) {
        if(minBiggerThanMax((int)dropDown2.getValue(), (int)dropDown1.getValue())) {
            showAlert(); //and do nothing else
        }
        else{
            fadeOut(500,sceneList.get(currentPane));
            if(currentPane==sceneList.size()-1) {
                currentPane = 0;
            }
            else {
                currentPane++;
            }
            fadeIn(750,sceneList.get(currentPane));
            systemRoot.setCenter(sceneList.get(currentPane));
        }
    }

    /**
     * Generates dropdowns for the top node in the root borderpane
     * @return the anchor pane in which the nodes are in.
     */
    public AnchorPane addTopNodes() {
        AnchorPane dpane = new AnchorPane();
        dpane.setPadding(new Insets(15, 12, 15, 12));
        dpane.setStyle("-fx-background-color: #FF5A60;");

        dropDown1 = new ComboBox();
        dropDown1.setPrefSize(100, 20);
        dropDown1.setOnAction(this::dropBoxRefresh);
        dropDown1.setValue("-");

        dropDown2 = new ComboBox();
        dropDown2.setPrefSize(100, 20);
        dropDown2.setOnAction(this::dropBoxRefresh);
        dropDown2.setValue("-");

        dropDown1.getItems().addAll(25, 50, 100, 200, 500, 1000, 2500, 5000, 10000);
        dropDown2.getItems().addAll(0, 25, 50, 100, 200, 500, 1000, 2500, 5000);

        Button infoButton = new Button("About this page");
        infoButton.setOnAction(this::showInfo);

        fromLabel.setId("topText");
        toLabel.setId("topText");
        infoButton.setId("topButton");

        dpane.getChildren().addAll(dropDown1, dropDown2 , fromLabel, toLabel, infoButton);

        AnchorPane.setTopAnchor(dropDown1, 0.0);
        AnchorPane.setRightAnchor(dropDown1, 0.0);
        AnchorPane.setTopAnchor(fromLabel, 0.0);
        AnchorPane.setRightAnchor(fromLabel, 260.0);
        AnchorPane.setTopAnchor(dropDown2, 0.0);
        AnchorPane.setRightAnchor(dropDown2, 150.0);
        AnchorPane.setTopAnchor(toLabel, 0.0);
        AnchorPane.setRightAnchor(toLabel, 110.0);
        AnchorPane.setLeftAnchor(infoButton, 0.0);
        AnchorPane.setTopAnchor(infoButton, 0.0);

        return dpane;
    }

    /**
     * Generates arrow buttons for the bottom node in the root borderpane.
     * @return the anchor pane in which the nodes are in.
     */
    public AnchorPane addBottomNodes() {
        AnchorPane apane = new AnchorPane();
        apane.setPadding(new Insets(15, 12, 15, 12));
        apane.setStyle("-fx-background-color: #FF5A60;");

        ImageView buttonImage = new ImageView(getClass().getResource("/Images/airbnb-logo.png").toExternalForm());

        leftButton = new Button();
        leftButton.setId("left");
        leftButton.setTooltip(new Tooltip("Ctrl/Cmd + Left Arrow"));
        leftButton.setOnAction(this::replaceSceneLeft);
        leftButton.setDisable(true);

        rightButton = new Button();
        rightButton.setId("right");
        rightButton.setTooltip(new Tooltip("Ctrl/Cmd + Right Arrow"));
        rightButton.setOnAction(this::replaceSceneRight);
        rightButton.setDisable(true);

        apane.getChildren().addAll(leftButton, rightButton);
        AnchorPane.setTopAnchor(rightButton, 0.0);
        AnchorPane.setRightAnchor(rightButton, 0.0);
        AnchorPane.setTopAnchor(leftButton, 0.0);
        AnchorPane.setLeftAnchor(leftButton, 0.0);

        return apane;
    }

    /**
     * Fades nodes into scene.
     * @param fadeSpeed How long the fade animation takes.
     * @param node The pane to fade.
     */
    private void fadeIn(int fadeSpeed, Pane node) {
        FadeTransition ft = new FadeTransition(Duration.millis(fadeSpeed), node);
        ft.setFromValue(0.8);
        ft.setToValue(1.0);
        ft.play();
    }

    /**
     * Fades nodes out of scene.
     * @param fadeSpeed How long the fade animation takes.
     * @param node The pane to fade.
     */
    private void fadeOut(int fadeSpeed, Pane node) {
        FadeTransition ft = new FadeTransition(Duration.millis(fadeSpeed), node);
        ft.setFromValue(1.0);
        ft.setToValue(0.8);
        ft.play();
    }

    /**
     * Refreshes the dropdown boxes.
     */
    private void dropBoxRefresh(Event event) {
        if(dropDown1.getValue()== "-" || dropDown2.getValue() == "-") {
            return;
        }
        else {
            page2.setMinPrice((int)dropDown2.getValue());
            page2.setMaxPrice((int)dropDown1.getValue());
            page1.setMinPrice((int)dropDown2.getValue());
            page1.setMaxPrice((int)dropDown1.getValue());
        }

        if (minBiggerThanMax((int)dropDown2.getValue(), (int)dropDown1.getValue()) ) {            
            leftButton.setDisable(true);
            rightButton.setDisable(true);
            showAlert();
            //do nothing
        }
        else {
            leftButton.setDisable(false);
            rightButton.setDisable(false);       
        }
    }

    /**
     * Action event which shows an information alert and displays descriptions relevant to the current page active.
     */
    private void showInfo(ActionEvent event){
        String concatenated ="file:///" + System.getProperty("user.dir")+ "\\Images\\airbnb-logo-2.png";
        Alert alert = new Alert(AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(concatenated));
        alert.setTitle("About each page in the application");
        alert.setHeaderText("Informational");
        String pageInfo = "";
        if (currentPane == 0){
            pageInfo = "Just a welcome page,\n begin by selecting your price range on the drop boxes top right.\nThis alert will show different text on other pages";
        }
        else if(currentPane == 1){
            pageInfo = "This is the map page to view properties through a selected filter\nThe map colours indicate property density, \ndarker green meaning more properties.\nSome boroughs have blue on them to indicate\n they are next to the river.";
        }
        else if(currentPane == 2){
            pageInfo = "This page comprises of miscellaneous but useful information \nand statistics about the app and properties.\n Use the left and right buttons on each sub-window to navigate";
        }
        else if(currentPane == 3){
            pageInfo = "This page contains info about points of interest around london \nTo use the page first hover your cursor around the area of the top white triangle and select a maximum price range and click the check boxes for types of POI \nafter you can use the nav buttons on the bottom left corner to look at images, names and other general info about the POI";
        }
        alert.setContentText(pageInfo);
        alert.getDialogPane().setMinSize(400.0, 200.0);
        alert.show();
    }

    /**
     * Quits the application
     */
    private void quitApp(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Check whether the minimum price is greater than the maximum price.
     * @param min The minimum price.
     * @param max The maximum price.
     * @return true If the minimum is greater than the maximum.
     */
    public boolean minBiggerThanMax(int min, int max) {
        if (min >= max) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Creates a new Alert frame which will wait until use interacts with it.
     */
    public void showAlert() {
        String concatenated ="file:///" + System.getProperty("user.dir")+ "\\Images\\airbnb-logo-2.png";
        Alert alert = new Alert(AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(concatenated));

        alert.setTitle("Invalid Price Range");
        alert.setHeaderText("MINIMUM PRICE IS BIGGER THAN MAXIMUM PRICE or NO SELECTION MADE");
        alert.showAndWait();
    }
}   

