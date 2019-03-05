package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Main extends Application {

    public static Random rand = new Random();
    public static FXMLLoader loader;
    public static final Object lock = new Object();
    public static boolean war = false;
    public static CountDownLatch warriorsStart = new CountDownLatch(1);

    public final static King king = new King(0);
    public final static Princess princess = new Princess(king, 400, 0);
    public final static QuarterMaster quartermaster = new QuarterMaster(500,0);
    public final static Jewelcrafter jewelcrafter = new Jewelcrafter(princess, 700,0);
    public final static IronMaster ironMaster = new IronMaster(jewelcrafter,quartermaster,400,0);
    public final static Miner miner = new Miner(ironMaster,500,0);
    public final static Farmer farmer = new Farmer(miner,ironMaster,jewelcrafter,quartermaster,1500,0);
    public final static ArrayList<Warrior> warriors = new ArrayList<>();

    public static final King king2 = new King(1);
    public final static Princess princess2 = new Princess(king2, 400, 1);
    public final static QuarterMaster quartermaster2 = new QuarterMaster(500,1);
    public final static Jewelcrafter jewelcrafter2 = new Jewelcrafter(princess2, 700,1);
    public final static IronMaster ironMaster2 = new IronMaster(jewelcrafter2,quartermaster2,400,1);
    public final static Miner miner2 = new Miner(ironMaster2,500,1);
    public final static Farmer farmer2 = new Farmer(miner2,ironMaster2,jewelcrafter2,quartermaster2,1500,1);
    public final static ArrayList<Warrior> warriors2 = new ArrayList<>();

    public final static Kingdom kingdom1 = new Kingdom(100,0,king, ironMaster);
    public final static Kingdom kingdom2 = new Kingdom(100,1,king2, ironMaster2);

    public final static Reaper reaper = new Reaper(warriors, warriors2);



    @Override
    public void start(Stage primaryStage) throws Exception{
        loader = new FXMLLoader(getClass().getResource("SettlersWindow.fxml"));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Settlers Gabriel Hoang");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
