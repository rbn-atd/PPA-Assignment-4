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
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.event.Event;
import java.util.ArrayDeque;

/**
 * Class to load the fxml file for the third page in the application.
 *
 * @author Galin Mihaylov, Ricky Brown, Reuben Atendido, Oliver Macpherson
 */

public class Page3control

{
    //Objects for generating the statistics for display
    private StatGenerator generator; 
    private ArrayList<Stat> stats; 
    //Deque for holding out-of-use stats and holders for the displayed stats
    private ArrayDeque<Stat> queue;
    private Stat tlCurrentStat;
    private Stat trCurrentStat;
    private Stat blCurrentStat;
    private Stat brCurrentStat;
    //FXML references for the labels to be changed in order to display the stats
    @FXML private Label tlTop;
    @FXML private Label tlMid;
    @FXML private Label tlBot;

    @FXML private Label trTop;
    @FXML private Label trMid;
    @FXML private Label trBot;

    @FXML private Label blTop;
    @FXML private Label blMid;
    @FXML private Label blBot;

    @FXML private Label brTop;
    @FXML private Label brMid;
    @FXML private Label brBot;


    
    // Objects to check and change inital state of statistics on page 3.
    private boolean set;

    /**
     * Constructor for the page which initialises data structures and fields, and calls
     * the initialiseStats method. 
     */
    public Page3control() {
        generator = new StatGenerator();
        stats = generator.generateStats();
        queue = new ArrayDeque<>();
        initialiseStats();

    }

    /**
     * Loads the fxml file.
     * @return the fxml file as a Pane
     */
    public Pane createPage() throws java.io.IOException {
        URL url = getClass().getResource("/fxml files/page3.fxml");
        Pane page3 = FXMLLoader.load(url);
        return page3;
    }

    /**
     * Method to place the first 4 stats into holders, before placeing the rest into the 
     * dequeue.
     */
    private void initialiseStats()
    {
        tlCurrentStat = stats.get(0);
        trCurrentStat = stats.get(1);
        blCurrentStat = stats.get(2);
        brCurrentStat = stats.get(3);
        for(int i = 4 ; i < stats.size(); i++) {
            queue.addLast(stats.get(i));
        }
    }

    /**
     * Simple helper method the check whether a stat is currently being displayed
     * @param check Stat to be checked whether it is already being displayed.
     * @return true If the stat is not already being displayed.
     */
    private boolean isUnique(Stat check)
    {
        return !(tlCurrentStat.equals(check)) && !(trCurrentStat.equals(check)) && !(blCurrentStat.equals(check)) && !(brCurrentStat.equals(check));
    }

    /**
     * Method for changing labels to intial stats.
     */
    @FXML
    public void placeInitialStats()
    {
        placeTL();
        placeTR();
        placeBL();
        placeBR();
    }

    //Methods for changing labels to display stats
    /**
     * Method for changing the top-left box to display current stat.
     */
    private void placeTL()
    {
        tlTop.setText(tlCurrentStat.getStatHeader());
        tlMid.setText(tlCurrentStat.getStatValue());
        tlBot.setText(tlCurrentStat.getStatBody());
        fadeInInfo(50, tlTop);
        fadeInInfo(250, tlMid);
        fadeInInfo(450, tlBot);
    }

    /**
     * Method for changing the top-right box to display current stat.
     */
    private void placeTR()
    {
        trTop.setText(trCurrentStat.getStatHeader());
        trMid.setText(trCurrentStat.getStatValue());
        trBot.setText(trCurrentStat.getStatBody());
        fadeInInfo(50, trTop);
        fadeInInfo(250, trMid);
        fadeInInfo(450, trBot);
    }

    /**
     * Method for changing the bottom-left box to display current stat.
     */
    private void placeBL()
    {
        blTop.setText(blCurrentStat.getStatHeader());
        blMid.setText(blCurrentStat.getStatValue());
        blBot.setText(blCurrentStat.getStatBody());
        fadeInInfo(100, blTop);
        fadeInInfo(250, blMid);
        fadeInInfo(350, blBot);
    }

    /**
     * Method for changing the bottom-right box to display current stat.
     */
    private void placeBR()
    {
        brTop.setText(brCurrentStat.getStatHeader());
        brMid.setText(brCurrentStat.getStatValue());
        brBot.setText(brCurrentStat.getStatBody());
        fadeInInfo(50, brTop);
        fadeInInfo(250, brMid);
        fadeInInfo(450, brBot);
    }

    //Action on button presses to cycle through each stat pane.
    /**
     * Method for top-left previous button
     */
    @FXML
    public void tlPrev()
    {
        if (set == false) {
            placeInitialStats();
            set = true;
        }

        else if(isUnique(queue.getFirst())) {
            queue.addLast(tlCurrentStat);
            tlCurrentStat = queue.removeFirst();
            placeTL();
        }
    }

    /**
     * Method for top-right next button
     */
    @FXML
    public void tlNext()
    {   
        if (set == false) {
            placeInitialStats();
            set = true;
        }

        else if(isUnique(queue.getLast())) {
            queue.addFirst(tlCurrentStat);
            tlCurrentStat = queue.removeLast();
            placeTL();
        }
    }

    /**
     * Method for top-right previous button
     */
    @FXML
    public void trPrev()
    {
        if (set == false) {
            placeInitialStats();
            set = true;
        }

        else if(isUnique(queue.getFirst())) {
            queue.addLast(trCurrentStat);
            trCurrentStat = queue.removeFirst();
            placeTR();
        }
    }

    /**
     * Method for top-right next button
     */
    @FXML
    public void trNext()
    {   
        if (set == false) {
            placeInitialStats();
            set = true;
        }

        else if(isUnique(queue.getLast())) {
            queue.addFirst(trCurrentStat);
            trCurrentStat = queue.removeLast();
            placeTR();
        }
    }

    /**
     * Method for bottom-left previous button
     */
    @FXML
    public void blPrev()
    {   if (set == false) {
            placeInitialStats();
            set = true;
        }

        else if(isUnique(queue.getFirst())) {
            queue.addLast(blCurrentStat);
            blCurrentStat = queue.removeFirst();
            placeBL();
        }
    }

    /**
     * Method for bottom-left next button
     */
    @FXML
    public void blNext()
    {   if (set == false) {
            placeInitialStats();
            set = true;
        }

        else if(isUnique(queue.getLast())) {
            queue.addFirst(blCurrentStat);
            blCurrentStat = queue.removeLast();
            placeBL();
        }
    }

    /**
     * Method for bottom-right previous button
     */
    @FXML
    public void brPrev()
    {   if (set == false) {
            placeInitialStats();
            set = true;
        }

        else if(isUnique(queue.getFirst())) {
            queue.addLast(brCurrentStat);
            brCurrentStat = queue.removeFirst();
            placeBR();
        }
    }

    /**
     * Method for bottom-right next button
     */
    @FXML
    public void brNext()
    {   if (set == false) {
            placeInitialStats();
            set = true;
        }

        else if(isUnique(queue.getLast())) {
            queue.addFirst(brCurrentStat);
            brCurrentStat = queue.removeLast();
            placeBR();
        }
    }

    /**
     * Fades nodes into scene.
     * @param fadeSpeed How long the fade animation takes.
     * @param node The Label to fade.
     */
    private void fadeInInfo(int fadeSpeed, Label node) {
        FadeTransition ft = new FadeTransition(Duration.millis(fadeSpeed), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }
}

