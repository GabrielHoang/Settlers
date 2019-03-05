package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class WindowController implements Initializable {

    @FXML
    private TextArea currentState1;
    @FXML
    private TextArea currentState2;
    @FXML
    private TextArea events1;
    @FXML
    private TextArea events2;

    public synchronized void addEvent(String event, int kingdomId) {
        //clear the textboxes if too much text
        if (events1.getText().length() > 300 || events2.getText().length() > 300) {
            events1.clear();
            events2.clear();
        }
        if (kingdomId == 0) {

            events1.appendText(event+'\n');
        } else {
            events2.appendText(event+'\n');
        }
    }
    @Override
    public synchronized void initialize(URL location, ResourceBundle resources) {

        Main.kingdom1.setEnemyKingdom(Main.kingdom2);
        Main.kingdom2.setEnemyKingdom(Main.kingdom1);


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentState1.clear();
                currentState2.clear();
                currentState1.appendText(Main.kingdom1.showState());
                currentState2.appendText(Main.kingdom2.showState());
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        Main.farmer.start();
        Main.farmer2.start();
        Main.quartermaster.start();
        Main.quartermaster2.start();
        Main.jewelcrafter.start();
        Main.jewelcrafter2.start();
        Main.miner.start();
        Main.miner2.start();
        Main.ironMaster.start();
        Main.ironMaster2.start();
        Main.princess.start();
        Main.princess2.start();
        Main.king.start();
        Main.king2.start();

        for(Warrior w : Main.warriors2) {
            w.start();
        }

        for(Warrior w : Main.warriors) {
            w.start();
        }

        //Main.reaper.start();


        //war starts equally for both kingdoms
        Main.warriorsStart.countDown();

    }
}
