package server.ui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.model.WorldManager;

import java.util.Observable;
import java.util.Observer;

public class PopulationGraphGUI extends Application implements Observer {

    private LineChart<Number, Number> chart;
    private XYChart.Series<Number, Number> dataSeries;
    private NumberAxis generationAxis;
    private Timeline animation;
    private final int MAX_DATA_POINTS = 60;
    private static PopulationGraphGUI instance;

    /**
     * Singleton pattern
     * @return instance
     */
    public static PopulationGraphGUI getInstance() {
        if(instance == null){
            instance = new PopulationGraphGUI();
        }
        return instance;
    }

    public PopulationGraphGUI() {
        // create timeline to add new data every 60th of second
        animation = new Timeline();
        animation.getKeyFrames()
                .add(new KeyFrame(Duration.millis(100),
                        (ActionEvent actionEvent) -> plotTime()));
        animation.setCycleCount(Animation.INDEFINITE);
    }

    public Parent createContent() {

        generationAxis = new NumberAxis(0, MAX_DATA_POINTS + 1, 2);
        final NumberAxis populationAxis = new NumberAxis();
        chart = new LineChart<>(generationAxis, populationAxis);

        // setup chart
        chart.setLegendVisible(false);
        chart.setCreateSymbols(false);
        chart.setAnimated(false);
        generationAxis.setLabel("Generation");
        generationAxis.setForceZeroInRange(false);

        populationAxis.setLabel("Population");
        populationAxis.setForceZeroInRange(false);

        // add starting data
        dataSeries = new XYChart.Series<>();
        dataSeries.setName("Population vs. Generation");

        chart.getData().add(dataSeries);

        return chart;
    }

    private void plotTime() {
        dataSeries.getData().add(new XYChart.Data<Number, Number>(WorldManager.getInstance().getGeneration(),
                WorldManager.getInstance().getPopulationSize()));
        if (WorldManager.getInstance().getGeneration() > MAX_DATA_POINTS) {
            generationAxis.setLowerBound(WorldManager.getInstance().getGeneration() - MAX_DATA_POINTS + 1);
            generationAxis.setUpperBound(WorldManager.getInstance().getGeneration() + 1);
            if (dataSeries.getData().size() > MAX_DATA_POINTS) {
                dataSeries.getData().remove(0);
            }
        }
    }

    public void play() {
        animation.play();
    }

    @Override
    public void stop() {
        animation.pause();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setTitle("Total Population Graph");
        primaryStage.show();
        primaryStage.setMaximized(true);
        play();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}