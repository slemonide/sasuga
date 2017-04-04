package server.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import server.model.WorldManager;

import java.util.Observable;
import java.util.Observer;

public class PopulationGraphGUI extends Application implements Observer {
    private static final int MAX_GENERATIONS = 150; // maximum number of generations to be shown
    private static PopulationGraphGUI instance;
    private static XYChart.Series dataSeries = new XYChart.Series();

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

            dataSeries.getData().add(new XYChart.Data(WorldManager.getInstance().getGeneration(),
                    WorldManager.getInstance().getPopulationSize()));

            // reduce visible data points if there are too many of them
            if (dataSeries.getData().size() > MAX_GENERATIONS) {
                dataSeries.getData().remove(0);
            }
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

        StackPane root = new StackPane();
        root.getChildren().add(lineChart);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
