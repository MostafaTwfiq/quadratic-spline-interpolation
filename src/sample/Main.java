package sample;

import InterpolationGenerator.InterpolationGenerator;
import InterpolationGenerator.QuadraticEquation;
import InterpolationGenerator.LinearEquation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.stage.Stage;

import java.util.Vector;

public class Main extends Application {

    static Vector<Point2D> points = new Vector<>();

    @Override
    public void start(Stage primaryStage) throws Exception{

        Stage stage = new Stage();

        /*QuadraticEquation equation = new QuadraticEquation(1, 0, 0);

        Point2D point1 = new Point2D(10, equation.calculateAtPoint(10));
        Point2D point2 = new Point2D(300, 300);

        double m1 = equation.getA() * 2 * point1.getX() + equation.getB();
        double m2 = equation.getA() * 2 * point2.getX() + equation.getB();

        LinearEquation linearEquation1 = new LinearEquation(m1, point1.getY() - point1.getX() * m1);
        LinearEquation linearEquation2 = new LinearEquation(m2, point2.getY() - point2.getX() * m2);

        double x = (linearEquation1.getB() - linearEquation2.getB()) / (linearEquation2.getA() - linearEquation1.getA());
        double y = linearEquation1.getA() * x + linearEquation1.getB();

        QuadCurve curve = new QuadCurve(point1.getX(), point1.getY(), x, y, point2.getX(),  point2.getY());

        curve.setFill(Color.TRANSPARENT);
        curve.setStroke(Color.BLACK);
        Group g = new Group(curve);*/

        Pane pane = new Pane();

        Scene scene = new Scene(pane, 500, 500);

        scene.setOnMouseDragged(e -> {

            //double distance = points.size() == 0 ? 30 : Math.sqrt( Math.pow(points.lastElement().getY() - e.getY(), 2) + Math.pow(points.lastElement().getX() - e.getX(), 2));
            //if (distance >= 30)
                points.add(new Point2D(e.getX(), e.getY()));

            if (points.size() >= 3)
                drawCurve(pane);

        });

        scene.setOnMouseReleased(e -> {
            points.clear();
            pane.getChildren().clear();
        });

        stage.setScene(scene);

        stage.showAndWait();

    }

    public static void drawCurve(Pane pane) {

        pane.getChildren().clear();

        InterpolationGenerator generator = new InterpolationGenerator(points);

        generator.generateEquations();

        Vector<QuadraticEquation> equations = generator.getEquations();

        Line line = new Line(points.get(0).getX(), points.get(0).getY(), points.get(1).getX(), points.get(1).getY());

        pane.getChildren().add(line);

        for (int i = 1; i < equations.size(); i++) {

            QuadraticEquation equation = equations.get(i);

            Point2D point1 = points.get(i);
            Point2D point2 = points.get(i + 1);

            double m1 = equation.getA() * 2 * point1.getX() + equation.getB();
            double m2 = equation.getA() * 2 * point2.getX() + equation.getB();

            LinearEquation linearEquation1 = new LinearEquation(m1, point1.getY() - point1.getX() * m1);
            LinearEquation linearEquation2 = new LinearEquation(m2, point2.getY() - point2.getX() * m2);

            double x = (linearEquation1.getB() - linearEquation2.getB()) / (linearEquation2.getA() - linearEquation1.getA());
            double y = linearEquation1.getA() * x + linearEquation1.getB();

            QuadCurve curve = new QuadCurve(point1.getX(), point1.getY(), x, y, point2.getX(),  point2.getY());

            curve.setFill(Color.TRANSPARENT);
            curve.setStroke(Color.BLACK);

            pane.getChildren().add(curve);

        }

    }


    public static void main(String[] args) {

        launch(args);


        // Equations should be:
        // firstEquations   =   0.0                     (X ^ 2)  +  22.704              X  +     0.0
        // secondEquation   =   0.8887999999999999      (X ^ 2)  +  4.928000000000047   X  +     88.87999999999971
        // thirdEquation    =   -0.13560000000000855    (X ^ 2)  +  35.66000000000029   X  +     -141.61000000000232
        // fourthEquation   =   1.6048000000000138      (X ^ 2)  +  -33.95600000000152  X  +     554.5500000000139
        // fifthEquation    =   0.2088888888890061      (X ^ 2)  +  28.859999999993725  X  +     -152.1299999999172


        Vector<Point2D> points = new Vector<>();
        points.add(new Point2D(0, 0));
        points.add(new Point2D(10, 227.04));
        points.add(new Point2D(15, 362.78));
        points.add(new Point2D(20, 517.35));
        points.add(new Point2D(22.5, 602.97));
        points.add(new Point2D(30, 901.67));

        InterpolationGenerator generator = new InterpolationGenerator(points);

        generator.generateEquations();

        Vector<QuadraticEquation> equations = generator.getEquations();

        for (var e : equations)
            System.out.println(e);

    }
}
