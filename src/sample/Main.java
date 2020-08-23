package sample;

import CurveDrawer.PointsDrawer;
import CurveDrawer.ICurveGenerator;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Vector;

public class Main extends Application {

    static Vector<Point2D> points = new Vector<>();

    @Override
    public void start(Stage primaryStage) throws Exception{

        Stage stage = new Stage();

        Pane pane = new Pane();

        Scene scene = new Scene(pane, 1500, 800);

        Pane drawPane = new Pane();
        drawPane.setPrefHeight(800);
        drawPane.setPrefWidth(1500);

        pane.getChildren().add(drawPane);

        PointsDrawer drawer = new PointsDrawer(drawPane, points, new ICurveGenerator() {

            @Override
            public QuadCurve createCurve(double v, double v1, double v2, double v3, double v4, double v5) {

                QuadCurve curve = new QuadCurve(v, v1, v2,v3, v4, v5);

                curve.setFill(Color.TRANSPARENT);
                curve.setStroke(Color.BLACK);
                curve.setStrokeWidth(2);

                return curve;

            }
        });


        scene.setOnMouseClicked(e -> {

            points.add(new Point2D(e.getX(), e.getY()));

            pane.getChildren().add(new Rectangle(e.getX() - 5, e.getY() - 5, 10, 10));

            if (points.size() >= 3) {
                //drawPane.getChildren().clear();
                //drawer.resetCounter();
                drawer.drawNext();
                drawer.drawNext();
            }

        });

        scene.setOnMouseReleased(e -> {
            //points.clear();
            //pane.getChildren().clear();
        });

        stage.setScene(scene);

        stage.showAndWait();

    }


    public static void main(String[] args) {

        launch(args);

    }
}
