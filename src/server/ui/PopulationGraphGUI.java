package server.ui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.model.WorldManager;

import java.util.Observable;
import java.util.Observer;

public class PopulationGraphGUI extends Application implements Observer {
    private static final int MAX_GENERATIONS = 100; // maximum number of generations to be shown
    private static PopulationGraphGUI instance;
    private static XYChart.Series dataSeries = new XYChart.Series();
    private Timeline animation;
    private boolean update = false;


    public PopulationGraphGUI() {
        // create timeline to add new data every 60th of second
        animation = new Timeline();
        animation.getKeyFrames()
                .add(new KeyFrame(Duration.millis(1000),
                        (ActionEvent actionEvent) -> plotTime()));
        animation.setCycleCount(Animation.INDEFINITE);
    }

    private void plotTime() {
        dataSeries.getData().add(new XYChart.Data(WorldManager.getInstance().getGeneration(),
                WorldManager.getInstance().getPopulationSize()));
    }

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

    public void update(Observable o, Object arg) {
        if (arg.equals("tick")) {
            //update = true;
/*
            dataSeries.getData().add(new XYChart.Data(WorldManager.getInstance().getGeneration(),
                    WorldManager.getInstance().getPopulationSize()));

            // reduce visible data points if there are too many of them
            if (dataSeries.getData().size() > MAX_GENERATIONS) {
                dataSeries.getData().remove(0);
            }
            */
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Total Population Graph");

        // CHARTS START
        final NumberAxis generationAxis = new NumberAxis();
        generationAxis.setForceZeroInRange(false);
        final NumberAxis populationAxis = new NumberAxis();
        populationAxis.setForceZeroInRange(false);
        //defining the axes
        generationAxis.setLabel("Generation");
        populationAxis.setLabel("Population");
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(generationAxis,populationAxis);

        lineChart.setTitle("Population vs. Generation");

        lineChart.getData().add(dataSeries);
        lineChart.setCreateSymbols(false);
        lineChart.setLegendVisible(false);

        // CHARTS END

        // UPDATE BEGIN
        Task task = new Task<XYChart.Series>() {
            @Override public XYChart.Series call() {
                while (!Thread.currentThread().isInterrupted()) {
                    if (update) {
                        dataSeries.getData().add(new XYChart.Data(WorldManager.getInstance().getGeneration(),
                                WorldManager.getInstance().getPopulationSize()));

                        // reduce visible data points if there are too many of them
                        if (dataSeries.getData().size() > MAX_GENERATIONS) {
                            dataSeries.getData().remove(0);
                        }
                    }
                    update = false;
                }
                return dataSeries;
            }
        };
        dataSeries.dataProperty().bind(task.valueProperty());
        new Thread(task).start();

        // UPDATE END

        StackPane root = new StackPane();
        root.getChildren().add(lineChart);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
