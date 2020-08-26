package Main;


import VisualizerGUI.Visualizer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Vector;

public class Main extends Application {

    static Vector<Point2D> points = new Vector<>();

    @Override
    public void start(Stage primaryStage) throws Exception{

        Stage stage = new Stage();

        stage.setFullScreen(true);

        stage.setTitle("Quad Spline Interpolation Visualizer");

        Visualizer visualizer = new Visualizer();

        Scene scene = new Scene(visualizer.getParentPane(), 1500, 800);

        stage.setScene(scene);

        stage.showAndWait();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
